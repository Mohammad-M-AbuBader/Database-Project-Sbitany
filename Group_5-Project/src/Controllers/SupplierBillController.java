/**
 * @autor: Mohammad AbuBader, Ameer Eleyan
 * ID: 1190478, 1191076
 * At: 1-6-2021  12:00 AM
 */

package Controllers;

import DataBaseClasses.SupplierBill;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class SupplierBillController implements Initializable {

    @FXML // fx:id="tableSupplier"
    private TableView<SupplierBill> tableSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="cmSupplierBillID"
    private TableColumn<SupplierBill, String> cmSupplierBillID; // Value injected by FXMLLoader

    @FXML // fx:id="cmOrderAt"
    private TableColumn<SupplierBill, String> cmOrderAt; // Value injected by FXMLLoader

    @FXML // fx:id="cmValueOfBill"
    private TableColumn<SupplierBill, String> cmValueOfBill; // Value injected by FXMLLoader

    @FXML // fx:id="cmDeposit"
    private TableColumn<SupplierBill, String> cmDeposit; // Value injected by FXMLLoader

    @FXML // fx:id="cmPatches"
    private TableColumn<SupplierBill, String> cmPatches; // Value injected by FXMLLoader
    @FXML // fx:id="txtSearch"
    private TextField txtSearch; // Value injected by FXMLLoader

    @FXML // fx:id="txNumberOfBill"
    private TextField txNumberOfBill; // Value injected by FXMLLoader

    @FXML // fx:id="lblValues"
    private Label lblValues; // Value injected by FXMLLoader

    @FXML // fx:id="txtValueOfBills"
    private TextField txtValueOfBills; // Value injected by FXMLLoader

    @FXML // fx:id="combBranchName"
    private ComboBox<String> combSuppliersNames; // Value injected by FXMLLoader

    @FXML // fx:id="rbBillNumber"
    private RadioButton rbBillNumber; // Value injected by FXMLLoader


    @FXML // fx:id="rbDetailsOf"
    private RadioButton rbDetailsOf; // Value injected by FXMLLoader


    @FXML // fx:id="combShow"
    private ComboBox<String> combShow; // Value injected by FXMLLoader

    private Connection con;

    public static int billID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        this.tableSupplier.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:15; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        cmSupplierBillID.setCellValueFactory(new PropertyValueFactory<>("supplierBillID"));
        cmOrderAt.setCellValueFactory(new PropertyValueFactory<>("orderAt"));
        cmValueOfBill.setCellValueFactory(new PropertyValueFactory<>("valueOfBill"));
        cmDeposit.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        cmPatches.setCellValueFactory(new PropertyValueFactory<>("patches"));
        this.execute(" ");
        this.fillCombSupplierNames();
        this.combShow.getItems().add("The paid bills");
        this.combShow.getItems().add("The unpaid bill");

    }

    private void execute(String str) {
        this.tableSupplier.getItems().clear();
        this.txtSearch.clear();
        this.rbBillNumber.setSelected(false);
        this.rbDetailsOf.setSelected(false);
        try {
            String getSupplierBill = "SELECT * from SupplierBill S " + str;
            Statement statNumberOfBill = con.createStatement();
            ResultSet resultNumberOfBill = statNumberOfBill.executeQuery("SELECT COUNT(*) FROM SupplierBill S " + str);
            boolean isExist = resultNumberOfBill.next();
            if (isExist) txNumberOfBill.setText(resultNumberOfBill.getString(1));
            else txNumberOfBill.setText("0");

            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(S.valueOfBill) FROM SupplierBill S " + str);
            boolean isExist2 = resultValueOfBill.next();
            this.lblValues.setText("Value Of Bills:");
            if (isExist2) txtValueOfBills.setText(resultValueOfBill.getString(1));
            else txtValueOfBills.setText("0");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getSupplierBill);

            boolean flag = true;
            while (rs.next()) {
                SupplierBill supplierBill = new SupplierBill();
                supplierBill.setSupplierBillID(rs.getString(1));
                supplierBill.setOrderAt(rs.getString(2));
                supplierBill.setValueOfBill(rs.getString(4));
                supplierBill.setDeposit(rs.getString(5));
                supplierBill.setPatches(rs.getString(6));

                this.tableSupplier.getItems().add(supplierBill);
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning", "There are no bills");
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    @FXML
    void handleBtRefresh() {
        execute(" ");
    }

    private void searchByBillID() {
        this.execute(" where S.supplierBillID=" + Integer.parseInt(this.txtSearch.getText().trim()));
    }


    private void detailsOf() {
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from supplierbill S  where S.SupplierBillID=" + billID);
            boolean isExist = resultSet.next();

            if (!isExist) {
                Message.displayMassage("Warning", billID + " Does not exist ");
                this.txtSearch.clear();
                this.rbDetailsOf.setSelected(false);
                return;
            }

            BillDetailsController.setTypeOfBill(false);
            BillDetailsController.setBillID(billID);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/BillDetails.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Bill Details");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.setOnCloseRequest(e -> {
                this.txtSearch.clear();
                this.rbDetailsOf.setSelected(false);
            });
            window.show();
        } catch (IOException | SQLException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    @FXML
    void handleBtSearch() {
        if (!this.txtSearch.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txtSearch.getText().trim())) {
                Message.displayMassage("Warning", " The ID is invalid ");
                this.txtSearch.clear();
                return;
            }
        }
        billID = Integer.parseInt(this.txtSearch.getText().trim());
        if (this.rbBillNumber.isSelected()) {
            this.searchByBillID();
        } else if (this.rbDetailsOf.isSelected()) {
            this.detailsOf();
        } else {
            Message.displayMassage("Warning", " Please choose how to search ");
        }

    }

    private void fillCombSupplierNames() {
        try {
            String sqlBranchesName = "SELECT S.supplierName from Supplier S";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlBranchesName);
            while (rs.next()) {
                this.combSuppliersNames.getItems().add(rs.getString(1).trim());
            }
            stmt.close();
            rs.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    @FXML
    void handleCombSupplierBills() {
        this.tableSupplier.getItems().clear();
        try {
            String supplierName = this.combSuppliersNames.getValue().trim();
            String getSupplierID = "SELECT S.supplierID from supplier S where S.supplierName= '" + supplierName + "'";
            Statement bID = con.createStatement();
            ResultSet resultBId = bID.executeQuery(getSupplierID);
            resultBId.next();
            int supplierID = Integer.parseInt(resultBId.getString(1).trim());
            this.execute(" where S.supplierID=" + supplierID);
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }


    @FXML
    void handleCombShow() {
        if (this.combShow.getValue().equals("The paid bills")) getPaidBills();
        else getUnpaidBills();
    }

    private void getPaidBills() {
        try {
            this.execute(" where S.patches=0");
            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(S.deposit) FROM SupplierBill S where S.patches=0");
            resultValueOfBill.next();
            this.lblValues.setText("Total paid bills:");
            this.txtValueOfBills.setText(resultValueOfBill.getString(1));
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    private void getUnpaidBills() {
        try {
            this.execute(" where S.patches>0");
            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(S.patches)  FROM SupplierBill S where S.patches>0");
            resultValueOfBill.next();
            this.lblValues.setText("Total unpaid bills:");
            this.txtValueOfBills.setText(resultValueOfBill.getString(1));
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }


    @FXML
    void handleBtInsertNewBill() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/NewSupplierBill.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Supplier Bill");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

}