/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 6-7-2021  12:43 PM
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
import java.sql.*;
import java.util.ResourceBundle;

public class ProfitController implements Initializable {

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

    @FXML
    private TextField txtTotalProfits;

    @FXML
    private TextField txtTotalRemaning;

    @FXML
    private TextField txtValueOfBills;

    @FXML
    private TextField txtTotalAmountPaid;


    private static Date from, to;
    private int sum = 0;

    public static void setDates(Date from, Date to) {
        ProfitController.from = from;
        ProfitController.to = to;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        Connection con = connection.connectSbitanyDB();

        this.cmProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        this.cmProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        this.cmSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        this.cmPurchasingPrice.setCellValueFactory(new PropertyValueFactory<>("purchasingPrice"));
        this.cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.cmProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));

        try {

            assert con != null;
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT C.productCode,SUM(C.quantity),C.sellingPrice from customerbilldetails C " +
                    "where C.customerBillID IN(Select C2.customerBillID from customerbill C2 where C2.customerBillID=C.customerBillID and C2.orederAt" +
                    " between '" + from + "' and '" + to + "') group by C.productCode order by 1");
            while (rs.next()) {
                Profit profit = new Profit();

                profit.setProductCode(rs.getString(1));
                profit.setQuantity(rs.getString(2));
                profit.setSellingPrice(rs.getString(3));
                int sellingPrice = Integer.parseInt(rs.getString(3));

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("select productName,purchasingPrice from product P where P.productCode=" + Integer.parseInt(rs.getString(1)));
                rs2.next();
                profit.setProductName(rs2.getString(1));
                profit.setPurchasingPrice(rs2.getString(2));
                int purchasingPrice = Integer.parseInt(rs2.getString(2));

                // calculate profit
                int quantity = Integer.parseInt(rs.getString(2));
                int profits = ((sellingPrice - purchasingPrice) * quantity);

                profit.setProfit(String.valueOf(profits));
                sum += profits;
                this.tableProfit.getItems().add(profit);
            }

            // calculate totals
            Statement values = con.createStatement();
            ResultSet resultSet = values.executeQuery("SELECT SUM(C.valueOfBill), SUM(C.deposit) , SUM(C.patches) from customerBill C" +
                    " where C.orederAt between '" + from + "' and '" + to + "'");
            resultSet.next();

            txtValueOfBills.setText(resultSet.getString(1));
            txtTotalAmountPaid.setText(resultSet.getString(2));
            txtTotalRemaning.setText(resultSet.getString(3));
            txtTotalProfits.setText(String.valueOf(sum));
            from = null;
            to = null;
            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }
}
