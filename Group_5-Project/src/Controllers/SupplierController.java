/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 1-6-2021  12:48 AM
 */
package Controllers;

import DataBaseClasses.Supplier;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class SupplierController  implements Initializable {

    @FXML // fx:id="tableSupplier"
    private TableView<Supplier> tableSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="cmSupplierID"
    private TableColumn<Supplier, String> cmSupplierID; // Value injected by FXMLLoader

    @FXML // fx:id="cmSupplierName"
    private TableColumn<Supplier, String> cmSupplierName; // Value injected by FXMLLoader

    @FXML // fx:id="cmSupplierPhone"
    private TableColumn<Supplier, String> cmSupplierPhone; // Value injected by FXMLLoader

    @FXML // fx:id="cmSupplierEmail"
    private TableColumn<Supplier, String> cmSupplierEmail; // Value injected by FXMLLoader

    @FXML // fx:id="cmSupplierFax"
    private TableColumn<Supplier, String> cmSupplierFax; // Value injected by FXMLLoader

    @FXML // fx:id="txNumberOfSupplier"
    private TextField txtNumberOfSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="txSupplierID"
    private TextField txSupplierID; // Value injected by FXMLLoader


    Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tableSupplier.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:15; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        cmSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        cmSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        cmSupplierPhone.setCellValueFactory(new PropertyValueFactory<>("supplierPhone"));
        cmSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        cmSupplierFax.setCellValueFactory(new PropertyValueFactory<>("supplierFax"));
        this.refresh(" ");
    }

    private void refresh(String str) {
        this.tableSupplier.getItems().clear();
        this.txSupplierID.clear();
        try {
            ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
            con = connection.connectSbitanyDB();
            String getSupplier = "SELECT * from Supplier S " + str;

            assert con != null;
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM Supplier S " + str);
            rs1.next();
            txtNumberOfSupplier.setText(rs1.getString(1));

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getSupplier);

            while (rs.next()) {
                Supplier supplier = new Supplier();
                String id = rs.getString(1);
                supplier.setSupplierID(id);
                supplier.setSupplierName(rs.getString(2));
                supplier.setSupplierPhone(rs.getString(3));
                supplier.setSupplierEmail(rs.getString(4));
                supplier.setSupplierFax(rs.getString(5));


                this.tableSupplier.getItems().add(supplier);
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    @FXML
    void handleBtRefresh() {
        this.refresh(" ");
    }

    @FXML
    void handleBtSearch() {
        if (!this.txSupplierID.getText().trim().isEmpty()) {
            if (!isNumber(this.txSupplierID.getText().trim())) {
                Message.displayMassage("Warning", " Supplier ID is invalid ");
                this.txSupplierID.clear();
                return;
            }
        }

        String search = "SELECT * from Supplier S where S.SupplierID=" + Integer.parseInt(this.txSupplierID.getText().trim());

        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(search);
            boolean empty = rs.next();
            if (!empty) {
                Message.displayMassage("Warning", this.txSupplierID.getText() + " Does not exist ");
                this.txSupplierID.clear();
                return;
            }
            Supplier supplier = new Supplier();
            String id = rs.getString(1);
            supplier.setSupplierID(id);
            supplier.setSupplierName(rs.getString(2));
            supplier.setSupplierPhone(rs.getString(3));
            supplier.setSupplierEmail(rs.getString(4));
            supplier.setSupplierFax(rs.getString(5));

            this.tableSupplier.getItems().clear();
            this.tableSupplier.getItems().add(supplier);
            this.txSupplierID.clear();

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning",sqlException.getMessage());
        }

    }

    /**
     * To check the value of the entered numberOfShares if contain only digits or not
     */
    public static boolean isNumber(String number) {
        /* To check the entered number of shares, that it consists of
           only digits
         */
        try {
            int temp = Integer.parseInt(number);
            return number.matches("\\d+") && temp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
