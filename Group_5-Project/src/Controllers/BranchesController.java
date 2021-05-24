/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 24-5-2021  7:15 PM
 */

package Controllers;

import DataBaseClasses.Branch;
import DataBaseClasses.Employee;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
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

public class BranchesController implements Initializable {

    @FXML // fx:id="lblNumBranches"
    private Label lblNumBranches; // Value injected by FXMLLoader

    @FXML // fx:id="txtSearch"
    private TextField txtSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tableBranches"
    private TableView<Branch> tableBranches; // Value injected by FXMLLoader

    @FXML // fx:id="cmBranchId"
    private TableColumn<Branch, String> cmBranchId; // Value injected by FXMLLoader

    @FXML // fx:id="cmBranchName"
    private TableColumn<Branch, String> cmBranchName; // Value injected by FXMLLoader

    @FXML // fx:id="cmBranchPhone"
    private TableColumn<Branch, String> cmBranchPhone; // Value injected by FXMLLoader

    @FXML // fx:id="cmAddress"
    private TableColumn<Branch, String> cmAddress; // Value injected by FXMLLoader

    @FXML // fx:id="txtBranchID"
    private TextField txtBranchID; // Value injected by FXMLLoader

    @FXML // fx:id="txtBranchName"
    private TextField txtBranchName; // Value injected by FXMLLoader

    @FXML // fx:id="txtBranchPhone"
    private TextField txtBranchPhone; // Value injected by FXMLLoader

    @FXML // fx:id="txtCityName"
    private TextField txtCityName; // Value injected by FXMLLoader

    @FXML // fx:id="txtStrretName"
    private TextField txtStrretName; // Value injected by FXMLLoader

    @FXML // fx:id="txtRegionName"
    private TextField txtRegionName; // Value injected by FXMLLoader

    @FXML // fx:id="txtBulldingNumber"
    private TextField txtBulldingNumber; // Value injected by FXMLLoader

    private Message message;

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.tableBranches.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");
        this.cmBranchId.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        this.cmBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        this.cmBranchPhone.setCellValueFactory(new PropertyValueFactory<>("branchPhone"));
        this.cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.refresh("");
    }

    public void handleBtSearch() {

    }

    public void handleBtRefresh() {
        this.refresh(" ");
    }

    public void handleBtAdd() {

    }

    public void handleBtUpdate() {

    }

    public void handleBtDelete() {

    }


    private void refresh(String str) {
        this.txtSearch.clear();
        this.tableBranches.getItems().clear();
        try {
            String getBranch = "SELECT * from branch B " + str;
            String getNumOfBranch = "SELECT COUNT(*) from branch B " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getBranch);

            while (rs.next()) {
                // branchID branchName branchPhone cityID streetName regionName bulldingNumber 
                Branch branch = new Branch();
                String id = rs.getString(1);
                branch.setBranchId(id);
                branch.setBranchName(rs.getString(2));
                branch.setBranchPhone(rs.getString(3));

                Statement stmt5 = con.createStatement();
                String villageId = rs.getString(14);
                ResultSet rs5;
                if (villageId != null) {
                    rs5 = stmt5.executeQuery("SELECT CONCAT(C.cityname,', ' ,V.villageName)" +
                            "  From city C , Village V, Employee E where C.cityID = E.cityID and V.VillageID = E.villageID and E.employeeID=" + Integer.parseInt(id.trim()));
                } else {
                    rs5 = stmt5.executeQuery("SELECT C.cityName" +
                            "  From city C, Employee E where C.cityID =E.cityID and E.employeeID=" + Integer.parseInt(id.trim()));
                }
                rs5.next();
                employee.setAddress(rs5.getString(1));


                this.tableBranches.getItems().add(branch);
            }

            Statement total = con.createStatement();
            ResultSet resultSetTotal = total.executeQuery(getNumOfBranch);
            resultSetTotal.next();
            this.lblNumBranches.setText(resultSetTotal.getString(1).trim());
            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }
}
