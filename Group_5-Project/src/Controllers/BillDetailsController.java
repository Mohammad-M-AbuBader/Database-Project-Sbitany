/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 4-6-2021  6:01 PM
 */

package Controllers;

import DataBaseClasses.BillDetails;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class BillDetailsController implements Initializable {

    @FXML // fx:id="billTableView"
    private TableView<BillDetails> billTableView; // Value injected by FXMLLoader

    @FXML // fx:id="cmCodeProducts"
    private TableColumn<BillDetails, String> cmCodeProducts; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductName"
    private TableColumn<BillDetails, String> cmProductName; // Value injected by FXMLLoader

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
        this.cmProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
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

            // get bill info
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select C.orederAt, C.valueOfBill, C.deposit, C.patches, C.customerID from customerbill C  where C.customerBillID=" + billID);
            resultSet.next();

            txtBillId.setText(billID + "");
            textReleasseDate.setText(resultSet.getString(1));
            txtValueOfBill.setText(resultSet.getString(2));
            txtFrom.setText("Sbitany");
            txtDeposit.setText(resultSet.getString(3));
            txtPatches.setText(resultSet.getString(4));

            // get customer name
            int customerID = Integer.parseInt(resultSet.getString(5).trim());
            statement = con.createStatement();
            resultSet = statement.executeQuery("select C.customerName from customer C where C.customerID=" + customerID);
            resultSet.next();
            txtTo.setText(resultSet.getString(1));


            // get the products in the bill
            statement = con.createStatement();
            resultSet = statement.executeQuery("select  C.productCode, C.sellingPrice, C.quantity from customerbilldetails C  where C.customerBillID=" + billID);

            // add product to the table
            while (resultSet.next()) {
                BillDetails billDetails = new BillDetails();
                billDetails.setProductCode(resultSet.getString(1));
                billDetails.setPrice(resultSet.getString(2));
                billDetails.setQuantity(resultSet.getString(3));

                Statement statement2 = con.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("select P.productName from Product P where P.ProductCode=" + Integer.parseInt(resultSet.getString(1)));
                resultSet2.next();

                billDetails.setProductName(resultSet2.getString(1));

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
            ResultSet resultSet = statement.executeQuery("SELECT S.orederAt, S.valueOfBill, S.deposit, S.patches, S.supplierID from supplierbill S  where S.SupplierBillID=" + billID);
            resultSet.next();


            // get bill info
            this.txtBillId.setText(billID + "");
            this.textReleasseDate.setText(resultSet.getString(1).trim());
            this.txtTo.setText("Sbitany");
            this.txtValueOfBill.setText(resultSet.getString(2).trim());
            this.txtDeposit.setText(resultSet.getString(3).trim());
            this.txtPatches.setText(resultSet.getString(4).trim());

            // get supplier name
            int supplierID = Integer.parseInt(resultSet.getString(5).trim());
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT S.supplierName from supplier S  where S.SupplierID=" + supplierID);
            resultSet.next();
            this.txtFrom.setText(resultSet.getString(1).trim());

            // get the products in the bill
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT S.productCode, S.purchasingPrice , S.quantity from supplierBillDetails S where S.SupplierBillID=" + billID);

            // add product to the table
            while (resultSet.next()) {
                BillDetails billDetails = new BillDetails();
                billDetails.setProductCode(resultSet.getString(1));
                billDetails.setPrice(resultSet.getString(2));
                billDetails.setQuantity(resultSet.getString(3));

                Statement statement2 = con.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("select P.productName from Product P  where P.ProductCode=" + Integer.parseInt(resultSet.getString(1)));
                resultSet2.next();
                billDetails.setProductName(resultSet2.getString(1));

                this.billTableView.getItems().add(billDetails);
            }
            statement.close();
            resultSet.close();

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }
}



