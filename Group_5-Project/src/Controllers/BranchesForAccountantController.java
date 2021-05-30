/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 30-5-2021  10:40 PM
 */

package Controllers;

import DataBaseClasses.Branch;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;

public class BranchesForAccountantController implements Initializable {

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
        if (!this.txtSearch.getText().trim().isEmpty()) {
            if (!isNumber(this.txtSearch.getText().trim())) {
                Message.displayMassage("Warning", " Branch ID is invalid ");
                this.txtSearch.clear();
                return;
            }
            String search = "SELECT * from branch B where B.branchID=" + Integer.parseInt(this.txtSearch.getText().trim());

            try {
                assert con != null;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(search);
                boolean empty = rs.next();
                if (!empty) {
                    Message.displayMassage("Warning", this.txtSearch.getText() + " Does not exist ");
                    this.txtSearch.clear();
                    return;
                }
                this.refresh(" where B.branchID=" + Integer.parseInt(this.txtSearch.getText().trim()));

            } catch (SQLException sqlException) {
                Message.displayMassage("Warning", sqlException.getMessage());
            }
        }
    }

    public void handleBtRefresh() {
        this.refresh(" ");
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
                Branch branch = new Branch();
                String id = rs.getString(1);
                branch.setBranchId(id);
                branch.setBranchName(rs.getString(2).trim());

                if (rs.getString(3) != null)
                    branch.setBranchPhone(rs.getString(3).trim());

                int cityID = Integer.parseInt(rs.getString(4).trim());
                Statement stmt5 = con.createStatement();
                ResultSet rs5 = stmt5.executeQuery("SELECT C.cityName" +
                        "  From city C where C.cityID =" + cityID);
                rs5.next();

                String address = rs5.getString(1).trim();
                if (rs.getString(5) != null)
                    address += (", " + rs.getString(5).trim());
                if (rs.getString(6) != null)
                    address += (", " + rs.getString(6).trim());
                if (rs.getString(7) != null)
                    address += (", " + rs.getString(7).trim());

                branch.setAddress(address);
                this.tableBranches.getItems().add(branch);
            }

            Statement total = con.createStatement();
            ResultSet resultSetTotal = total.executeQuery(getNumOfBranch);
            resultSetTotal.next();
            this.lblNumBranches.setText(resultSetTotal.getString(1).trim());
            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    // To check the value of the entered numberOfShares if contain only digits or not
    public static boolean isNumber(String number) {
        // To check the entered number of shares, that it consists of only digits
        try {
            int temp = Integer.parseInt(number);
            return number.matches("\\d+") && temp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

