/**
 * @autor: Ameer Eleyan
 * 1191076
 * At: 31/5/2021  2:37 AM
 */

package Controllers;

import DataBaseClasses.BranchGetFrom;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
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

public class RecordMovementsController implements Initializable {

    @FXML // fx:id="txtSearch"
    private TextField txtSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tableRecordMovements"
    private TableView<BranchGetFrom> tableRecordMovements; // Value injected by FXMLLoader

    @FXML // fx:id="cmTransferNumber"
    private TableColumn<BranchGetFrom, String> cmTransferNumber; // Value injected by FXMLLoader

    @FXML // fx:id="cmDate"
    private TableColumn<BranchGetFrom, String> cmDate; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeeID"
    private TableColumn<BranchGetFrom, String> cmEmployeeID; // Value injected by FXMLLoader

    @FXML // fx:id="cmFrom"
    private TableColumn<BranchGetFrom, String> cmFrom; // Value injected by FXMLLoader

    @FXML // fx:id="cmTo"
    private TableColumn<BranchGetFrom, String> cmTo; // Value injected by FXMLLoader


    @FXML // fx:id="cmProductName"
    private TableColumn<BranchGetFrom, String> cmProductName; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductCode"
    private TableColumn<BranchGetFrom, String> cmProductCode; // Value injected by FXMLLoader

    @FXML // fx:id="cmQuantity"
    private TableColumn<BranchGetFrom, String> cmQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="lblNumBranches"
    private Label lblNumBranches; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeeName"
    private TableColumn<BranchGetFrom, String> cmEmployeeName; // Value injected by FXMLLoader

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        
        this.cmTransferNumber.setCellValueFactory(new PropertyValueFactory<>("transferNumber"));
        this.cmDate.setCellValueFactory(new PropertyValueFactory<>("getAt"));
        this.cmEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        this.cmEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        this.cmProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        this.cmProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        this.cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.cmFrom.setCellValueFactory(new PropertyValueFactory<>("sourceBranchID"));
        this.cmTo.setCellValueFactory(new PropertyValueFactory<>("destinationBranchID"));

        this.execute(" ");
    }

    @FXML
    void handleBtRefresh() {
        this.execute(" ");
    }

    @FXML
    void handleBtSearch() {
        if (!this.txtSearch.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txtSearch.getText().trim())) {
                Message.displayMassage("Warning", " Transfer number is invalid ");
                this.txtSearch.clear();
                return;
            }
        }
        this.execute(" where B.transferNumber=" + Integer.parseInt(this.txtSearch.getText().trim()));
    }

    private void execute(String str) {
        this.txtSearch.clear();
        this.tableRecordMovements.getItems().clear();
        try {
            String getTransfers = "SELECT * from branchgetfrom B " + str;
            String getNumOfTransfers = "SELECT COUNT(*) from branchgetfrom B " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getTransfers);

            boolean flag = true;
            // transferNumber getAt employeeID sourceStorageID destinationStorageID productCode quantity
            while (rs.next()) {
                BranchGetFrom branchGetFrom = new BranchGetFrom();
                branchGetFrom.setTransferNumber(rs.getString(1));
                branchGetFrom.setGetAt(rs.getString(2));
                branchGetFrom.setEmployeeID(rs.getString(3));
                branchGetFrom.setProductCode(rs.getString(6));
                branchGetFrom.setQuantity(rs.getString(7));

                Statement  getEmployeeNameStatement = con.createStatement();
                ResultSet getEmployeeName = getEmployeeNameStatement.executeQuery("select  E.employeeName from employee E where E.employeeID=" + Integer.parseInt(rs.getString(3).trim()));
                getEmployeeName.next();

                branchGetFrom.setEmployeeName(getEmployeeName.getString(1));

                Statement  getProductNameStatement = con.createStatement();
                ResultSet getProductName = getProductNameStatement.executeQuery("select  P.ProductName from product P where P.productCode=" + Integer.parseInt(rs.getString(6).trim()));
                getProductName.next();

                branchGetFrom.setProductName(getProductName.getString(1));

                Statement stmt5 = con.createStatement();
                ResultSet rs5 = stmt5.executeQuery("SELECT B.branchName" +
                        "  From branch B where B.branchID= " + Integer.parseInt(rs.getString(4).trim()));
                rs5.next();

                branchGetFrom.setSourceBranchID(rs5.getString(1));

                rs5 = stmt5.executeQuery("SELECT B.branchName" +
                        "  From branch B where B.branchID= " + Integer.parseInt(rs.getString(5).trim()));
                rs5.next();
                branchGetFrom.setDestinationBranchID(rs5.getString(1));

                this.tableRecordMovements.getItems().add(branchGetFrom);
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning", "Does not exits");
                return;
            }

            Statement total = con.createStatement();
            ResultSet resultSetTotal = total.executeQuery(getNumOfTransfers);
            resultSetTotal.next();

            this.lblNumBranches.setText(resultSetTotal.getString(1).trim());

            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
