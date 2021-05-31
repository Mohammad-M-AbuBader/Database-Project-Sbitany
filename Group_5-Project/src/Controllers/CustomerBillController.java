/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 25-5-2021  6:26 PM
 */

package Controllers;

import DataBaseClasses.CustomerBill;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomerBillController implements Initializable {

    @FXML // fx:id="tableCustomerBill"
    private TableView<CustomerBill> tableCustomerBill; // Value injected by FXMLLoader

    @FXML // fx:id="cmBillID"
    private TableColumn<CustomerBill, String> cmBillID; // Value injected by FXMLLoader

    @FXML // fx:id="cmCustomerID"
    private TableColumn<CustomerBill, String> cmCustomerID; // Value injected by FXMLLoader

    @FXML // fx:id="cmBranchName"
    private TableColumn<CustomerBill, String> cmBranchName; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeeID"
    private TableColumn<CustomerBill, String> cmEmployeeID; // Value injected by FXMLLoader

    @FXML // fx:id="cmReleaseDate"
    private TableColumn<CustomerBill, String> cmReleaseDate; // Value injected by FXMLLoader

    @FXML // fx:id="cmValueOfBill"
    private TableColumn<CustomerBill, String> cmValueOfBill; // Value injected by FXMLLoader

    @FXML // fx:id="cmDeposit"
    private TableColumn<CustomerBill, String> cmDeposit; // Value injected by FXMLLoader

    @FXML // fx:id="cmPatches"
    private TableColumn<CustomerBill, String> cmPatches; // Value injected by FXMLLoader

    @FXML // fx:id="combBranchID"
    private ComboBox<String> combBranchName; // Value injected by FXMLLoader

    @FXML // fx:id="combBills"
    private ComboBox<String> combShow; // Value injected by FXMLLoader

    @FXML // fx:id="txNumberOfBill"
    private TextField txNumberOfBill; // Value injected by FXMLLoader

    @FXML // fx:id="txtValueOfBills"
    private TextField txtValueOfBills; // Value injected by FXMLLoader

    @FXML // fx:id="txtSearch"
    private TextField txtSearch; // Value injected by FXMLLoader

    @FXML // fx:id="rbBillNumber"
    private RadioButton rbBillNumber; // Value injected by FXMLLoader

    @FXML // fx:id="rbCustomerPersonalID"
    private RadioButton rbCustomerPersonalID; // Value injected by FXMLLoader

    @FXML // fx:id="rbDetailsOf"
    private RadioButton rbDetailsOf; // Value injected by FXMLLoader

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        this.tableCustomerBill.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:15; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        cmBillID.setCellValueFactory(new PropertyValueFactory<>("customerBillID"));
        cmCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cmEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        cmBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        cmReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        cmValueOfBill.setCellValueFactory(new PropertyValueFactory<>("valueOfBill"));
        cmDeposit.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        cmPatches.setCellValueFactory(new PropertyValueFactory<>("patches"));

        this.refresh(" ");
    }


    private void refresh(String str) {
        this.tableCustomerBill.getItems().clear();
        try {


            String getCustomerBill = "SELECT * from customerbill " + str;

            Statement statNumberOfBill = con.createStatement();
            ResultSet resultNumberOfBill = statNumberOfBill.executeQuery("SELECT COUNT(*) FROM customerbill " + str);
            resultNumberOfBill.next();
            txNumberOfBill.setText(resultNumberOfBill.getString(1));

            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(C.valueOfBill) FROM customerbill C " + str);
            resultValueOfBill.next();
            txtValueOfBills.setText(resultValueOfBill.getString(1));

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCustomerBill);

            while (rs.next()) {
                CustomerBill customerBill = new CustomerBill();
                customerBill.setCustomerBillID(rs.getString(1));
                customerBill.setReleaseDate(rs.getString(2));
                customerBill.setValueOfBill(rs.getString(3));
                customerBill.setCustomerID(rs.getString(4));
                customerBill.setDeposit(rs.getString(7));
                customerBill.setPatches(rs.getString(8));
                customerBill.setEmployeeID(rs.getString(6));

                Statement stmtBranchName = con.createStatement();
                ResultSet resultBranchName = stmtBranchName.executeQuery("select B.branchName From Branch B where B.BranchID =" + Integer.parseInt((rs.getString(5))));
                resultBranchName.next();
                customerBill.setBranchName(resultBranchName.getString(1));

                this.tableCustomerBill.getItems().add(customerBill);
            }

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    @FXML
    void handleBtRefresh() {
        refresh(" ");
    }

    @FXML
    void handleBtSearch() {

    }

    @FXML
    void handleCombBranchName() {

    }

    @FXML
    void handleCombShow() {
        
    }

}
