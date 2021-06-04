/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 1-6-2021  12:48 AM
 */
package Controllers;

import DataBaseClasses.Supplier;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
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

public class SupplierController implements Initializable {

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
        this.execute(" ");
    }

    private void execute(String str) {
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
            boolean flag = true;
            while (rs.next()) {
                Supplier supplier = new Supplier();
                String id = rs.getString(1);
                supplier.setSupplierID(id);
                supplier.setSupplierName(rs.getString(2));
                supplier.setSupplierPhone(rs.getString(3));
                supplier.setSupplierEmail(rs.getString(4));
                supplier.setSupplierFax(rs.getString(5));

                this.tableSupplier.getItems().add(supplier);
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning", "Does not exist");
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    @FXML
    void handleBtRefresh() {
        this.execute(" ");
    }

    @FXML
    void handleBtSearch() {
        if (!this.txSupplierID.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txSupplierID.getText().trim())) {
                Message.displayMassage("Warning", " Supplier ID is invalid ");
                this.txSupplierID.clear();
                return;
            }
        }
        this.execute("where S.SupplierID=" + Integer.parseInt(this.txSupplierID.getText().trim()));
    }

}
