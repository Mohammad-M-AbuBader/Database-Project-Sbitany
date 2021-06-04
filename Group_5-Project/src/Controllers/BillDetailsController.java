/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 4-6-2021  6:01 PM
 */

package Controllers;

import DataBaseClasses.BillDetails;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BillDetailsController implements Initializable {

    @FXML // fx:id="billTableView"
    private TableView<BillDetails> billTableView; // Value injected by FXMLLoader

    @FXML // fx:id="cmCodeProducts"
    private TableColumn<BillDetails, String> cmCodeProducts; // Value injected by FXMLLoader

    @FXML // fx:id="cmPurchasingPrice"
    private TableColumn<BillDetails, String> cmPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cmQuantity"
    private TableColumn<BillDetails, String> cmQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="txtBillId"
    private TextField txtBillId; // Value injected by FXMLLoader

    @FXML // fx:id="txtFrom"
    private TextField txtFrom; // Value injected by FXMLLoader

    @FXML // fx:id="textReleasseDate"
    private TextField textReleasseDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtTo"
    private TextField txtTo; // Value injected by FXMLLoader

    @FXML // fx:id="txtValueOfBill"
    private TextField txtValueOfBill; // Value injected by FXMLLoader

    @FXML // fx:id="txtDeposit"
    private TextField txtDeposit; // Value injected by FXMLLoader

    @FXML // fx:id="txtPatches"
    private TextField txtPatches; // Value injected by FXMLLoader

    private static boolean typeOfBill = true; // true: customer,,,, false: supplier
    private static int billID;
    private Connection con;

    public static void setBillID(int billID) {
        BillDetailsController.billID = billID;
    }

    public static void setTypeOfBill(boolean typeOfBill) {
        BillDetailsController.typeOfBill = typeOfBill;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.cmCodeProducts.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        this.cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.cmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        if (typeOfBill) {
            this.customerBill();
            typeOfBill = false;
        } else {
            this.supplierBill();
            typeOfBill = true;
        }

    }

    private void customerBill() {
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customerbill C  where C.customerBillID=" + billID);
            resultSet.next();

            txtBillId.setText(billID + "");
            textReleasseDate.setText(resultSet.getString(2));
            txtValueOfBill.setText(resultSet.getString(3));
            txtFrom.setText("Sbitany");
            txtDeposit.setText(resultSet.getString(7));
            txtPatches.setText(resultSet.getString(8));

            int customerID = Integer.parseInt(resultSet.getString(4).trim());
            statement = con.createStatement();
            resultSet = statement.executeQuery("select C.customerName from customer C where C.customerID=" + customerID);
            resultSet.next();
            txtTo.setText(resultSet.getString(1));


            statement = con.createStatement();
            resultSet = statement.executeQuery("select  * from customerbilldetails C  where C.customerBillID=" + billID);

            while (resultSet.next()) {
                BillDetails billDetails = new BillDetails();
                billDetails.setProductCode(resultSet.getString(2));
                billDetails.setPrice(resultSet.getString(3));
                billDetails.setQuantity(resultSet.getString(4));

                this.billTableView.getItems().add(billDetails);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }


    private void supplierBill() {
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from supplierbill S  where S.SupplierBillID=" + billID);
            boolean isExist = resultSet.next();

            if (!isExist) {
                Message.displayMassage("Warning", billID + " Does not exist ");
                Stage currentStage = (Stage) this.txtFrom.getScene().getWindow();
                currentStage.close();
            }

            // get bill info
            this.txtBillId.setText(billID + "");
            this.textReleasseDate.setText(resultSet.getString(2).trim());
            this.txtTo.setText("Sbitany");
            this.txtValueOfBill.setText(resultSet.getString(4).trim());
            this.txtDeposit.setText(resultSet.getString(5).trim());
            this.txtPatches.setText(resultSet.getString(6).trim());

            // get supplier name
            int supplierID = Integer.parseInt(resultSet.getString(3).trim());
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT S.supplierName from supplier S  where S.SupplierID=" + supplierID);
            resultSet.next();
            this.txtFrom.setText(resultSet.getString(1).trim());

            // get the products in the bill
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * from supplierBillDetails  S  where S.SupplierBillID=" + billID);

            // add product to the table
            while (resultSet.next()) {
                BillDetails billDetails = new BillDetails();
                billDetails.setProductCode(resultSet.getString(2));
                billDetails.setPrice(resultSet.getString(3));
                billDetails.setQuantity(resultSet.getString(4));

                this.billTableView.getItems().add(billDetails);
            }
            statement.close();
            resultSet.close();

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }
}



