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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @FXML // fx:id:"lblValues"
    private Label lblValues;

    @FXML // fx:id="rbBillNumber"
    private RadioButton rbBillNumber; // Value injected by FXMLLoader

    @FXML // fx:id="rbCustomerPersonalID"
    private RadioButton rbCustomerPersonalID; // Value injected by FXMLLoader

    @FXML // fx:id="rbDetailsOf"
    private RadioButton rbDetailsOf; // Value injected by FXMLLoader

    private Connection con;

    public static int billID;

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
        this.fillCombBranchName();
        this.combShow.getItems().add("The paid bills");
        this.combShow.getItems().add("The unpaid bill");
    }

    private void refresh(String str) {
        this.tableCustomerBill.getItems().clear();
        this.txtSearch.clear();
        this.rbBillNumber.setSelected(false);
        this.rbCustomerPersonalID.setSelected(false);
        this.rbDetailsOf.setSelected(false);
        try {
            String getCustomerBill = "SELECT * from customerbill C " + str;
            Statement statNumberOfBill = con.createStatement();
            ResultSet resultNumberOfBill = statNumberOfBill.executeQuery("SELECT COUNT(*) FROM customerbill C " + str);
            resultNumberOfBill.next();
            if (resultNumberOfBill.getString(1) != null)
                txNumberOfBill.setText(resultNumberOfBill.getString(1));
            else txNumberOfBill.setText("0");

            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(C.valueOfBill) FROM customerbill C " + str);
            resultValueOfBill.next();
            this.lblValues.setText("Value Of Bills:");
            if (resultValueOfBill.getString(1) != null)
                txtValueOfBills.setText(resultValueOfBill.getString(1));
            else txtValueOfBills.setText("0");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCustomerBill);

            boolean flag = true;
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
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning", "There are no bills");
                this.txtSearch.clear();
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
        if (!this.txtSearch.getText().trim().isEmpty()) {
            if (!isNumber(this.txtSearch.getText().trim())) {
                Message.displayMassage("Warning", " The ID is invalid ");
                this.txtSearch.clear();
                return;
            }
        }
        billID = Integer.parseInt(this.txtSearch.getText().trim());
        if (this.rbBillNumber.isSelected()) {
            this.searchByBillID();
        } else if (this.rbCustomerPersonalID.isSelected()) {
            this.searchByBCustomerPersonalID();
        } else if (this.rbDetailsOf.isSelected()) {
            this.detailsOf();
        } else {
            Message.displayMassage("Warning", " Please choose how to search ");
        }

    }

    private void searchByBillID() {
        this.refresh("  where C.customerBillID=" + Integer.parseInt(this.txtSearch.getText().trim()));
    }

    private void searchByBCustomerPersonalID() {
        try {
            Statement customerID = con.createStatement();
            ResultSet resultCustomerID = customerID.executeQuery("select C.customerID From customer C where C.cardID =" + Integer.parseInt((txtSearch.getText().trim())));
            boolean isExist = resultCustomerID.next();
            if (isExist)
                this.refresh(" Where C.customerID=" + Integer.parseInt(resultCustomerID.getString(1).trim()));
            else {
                Message.displayMassage("Warning", this.txtSearch.getText().trim() + " Does not have bills");
                this.txtSearch.clear();
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    private void detailsOf() {
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customerbill C  where C.customerBillID=" + billID);
            boolean isExist = resultSet.next();
            if (!isExist) {
                Message.displayMassage("Warning", billID + " Does not exist");
                this.txtSearch.clear();
                this.rbDetailsOf.setSelected(false);
                return;
            }
            BillDetailsController.setTypeOfBill(true);
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


    private void fillCombBranchName() {
        try {
            String sqlBranchesName = "SELECT B.branchName from branch B";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlBranchesName);
            while (rs.next()) {
                if (rs.getString(1).trim().equals("Main Company")) continue;
                this.combBranchName.getItems().add(rs.getString(1).trim());
            }
            stmt.close();
            rs.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }

    @FXML
    void handleCombBranchName() {
        this.tableCustomerBill.getItems().clear();
        try {
            String bName = this.combBranchName.getValue().trim();
            String getBranchID = "SELECT B.branchID from branch B where B.branchName= '" + bName + "'";
            Statement bID = con.createStatement();
            ResultSet resultBId = bID.executeQuery(getBranchID);
            resultBId.next();
            int branchID = Integer.parseInt(resultBId.getString(1).trim());
            this.refresh(" where C.branchID=" + branchID);
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
            this.refresh(" where C.patches=0");
            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(C.deposit) FROM customerbill C where C.patches=0");
            resultValueOfBill.next();
            this.lblValues.setText("Total paid bills:");
            this.txtValueOfBills.setText(resultValueOfBill.getString(1));
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    private void getUnpaidBills() {
        try {
            this.refresh(" where C.patches>0");
            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(C.patches) FROM customerbill C where C.patches>0");
            resultValueOfBill.next();
            this.lblValues.setText("Total unpaid bills:");
            this.txtValueOfBills.setText(resultValueOfBill.getString(1));
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
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
