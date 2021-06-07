/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 6-7-2021  12:43 PM
 *
 */
package Controllers;

import DataBaseClasses.Profit;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class profitController implements Initializable {

    @FXML // fx:id="tableProfit"
    private TableView<Profit> tableProfit; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductName"
    private TableColumn<Profit, String> cmProductName; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductCode"
    private TableColumn<Profit, String> cmProductCode; // Value injected by FXMLLoader

    @FXML // fx:id="cmSellingPrice"
    private TableColumn<Profit, String> cmSellingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cmPurchasingPrice"
    private TableColumn<Profit, String> cmPurchasingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cmQuantity"
    private TableColumn<Profit, String> cmQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="cmProfit"
    private TableColumn<Profit, String> cmProfit; // Value injected by FXMLLoader
    @FXML // fx:id="lblProfit"
    private Label lblProfit; // Value injected by FXMLLoader

    @FXML // fx:id="txtFinalProfit"
    private TextField txtFinalProfit; // Value injected by FXMLLoader

    private Connection con;
    private String releaseDate;
    private int customerBillID,purchasingPrice,sellingPrice,quantity,profits,sum=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        this.cmProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        this.cmProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        this.cmSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        this.cmPurchasingPrice.setCellValueFactory(new PropertyValueFactory<>("purchasingPrice"));
        this.cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.cmProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));


        try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT C.customerBillID from customerbill C where C.orederAt ='"+releaseDate+"'");
            while (rs.next()) {
                Profit profit = new Profit();
                customerBillID=Integer.parseInt(rs.getString(1));

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * from customerbilldetails C where C.customerBillID =" + customerBillID);
                while (rs2.next())
                {

                    profit.setProductCode(rs2.getString(2));
                    profit.setSellingPrice(rs2.getString(3));
                    profit.setQuantity(rs2.getString(4));

                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("select productName,purchasingPrice from product P where P.productCode=" + Integer.parseInt(rs2.getString(2) ));
                    rs3.next();

                    profit.setProductName(rs3.getString(1));
                    profit.setPurchasingPrice(rs3.getString(2));

                    purchasingPrice=Integer.parseInt(rs3.getString(2));
                    sellingPrice = Integer.parseInt(rs2.getString(3));
                    quantity=Integer.parseInt(rs2.getString(4));
                    profits=((sellingPrice-purchasingPrice)*quantity);

                    profit.setProfit(String.valueOf(profits));
                    sum+=profits;
                }
                rs2.close();
                stmt2.close();

                this.tableProfit.getItems().add(profit);
            }
            rs.close();
            stmt.close();
            txtFinalProfit.setText(String.valueOf(sum));

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }
}
