/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 24-5-2021  7:15 PM
 */

package Controllers;

import DataBaseClasses.Branch;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;

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

    @FXML // fx:id="txtBranchName"
    private TextField txtBranchName; // Value injected by FXMLLoader

    @FXML // fx:id="txtBranchPhone"
    private TextField txtBranchPhone; // Value injected by FXMLLoader

    @FXML // fx:id="cbxCityName"
    private ComboBox<String> cbxCityName; // Value injected by FXMLLoader

    @FXML // fx:id="txtStrretName"
    private TextField txtStrretName; // Value injected by FXMLLoader

    @FXML // fx:id="txtRegionName"
    private TextField txtRegionName; // Value injected by FXMLLoader

    @FXML // fx:id="txtBulldingNumber"
    private TextField txtBulldingNumber; // Value injected by FXMLLoader

    private Connection con;

    private int branchID = -1;
    private PreparedStatement ps;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        this.cmBranchId.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        this.cmBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        this.cmBranchPhone.setCellValueFactory(new PropertyValueFactory<>("branchPhone"));
        this.cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        try {

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT C.cityName From city C");
            while (rs.next()) this.cbxCityName.getItems().add(rs.getString(1).trim());

            stmt.close();
            rs.close();
            this.execute("");
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }

    public void handleBtSearch() {
        if (!this.txtSearch.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txtSearch.getText().trim())) {
                Message.displayMassage("Warning", " Branch ID is invalid ");
                this.txtSearch.clear();
                return;
            }
            this.execute(" where B.branchID=" + Integer.parseInt(this.txtSearch.getText().trim()));
        }
    }

    public void handleBtRefresh() {
        this.execute(" ");
    }

    public void handleBtAdd() {
        try {
            ps = con.prepareStatement("INSERT INTO branch(branchName, branchPhone, cityID, streetName, regionName, bulldingNumber)" +
                    "values(?,?,?,?,?,?)");
            // get branch name
            if (!this.txtBranchName.getText().isEmpty()) {
                ps.setString(1, this.txtBranchName.getText().trim());
            } else {
                Message.displayMassage("Warning", " Please Enter the branch name");
                return;
            }

            // get city name
            if (this.cbxCityName == null || this.cbxCityName.getValue().equals("")) {
                Message.displayMassage("Warning", " Please select the city name");
                return;
            } else {
                String cityID = "SELECT C.cityID from city C where C.cityName='" + this.cbxCityName.getValue() + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(cityID);
                rs.next();
                ps.setInt(3, Integer.parseInt(rs.getString(1).trim()));
            }
            // get branch phone
            if (!this.txtBranchPhone.getText().isEmpty()) ps.setString(2, this.txtBranchPhone.getText().trim());
            else ps.setNull(2, Types.NULL);

            // get strretName
            if (!this.txtStrretName.getText().isEmpty()) ps.setString(4, this.txtStrretName.getText().trim());
            else ps.setNull(4, Types.NULL);

            // get region name
            if (!this.txtRegionName.getText().isEmpty()) ps.setString(5, this.txtRegionName.getText().trim());
            else ps.setNull(5, Types.NULL);

            // get bullding number
            if (!this.txtBulldingNumber.getText().isEmpty()) {
                if (Methods.isNumber(this.txtBulldingNumber.getText().trim()))
                    ps.setInt(6, Integer.parseInt(this.txtBulldingNumber.getText().trim()));
                else {
                    Message.displayMassage("Warning", this.txtBulldingNumber.getText().trim() + " Is invalid ");
                    return;
                }
            } else ps.setNull(6, Types.NULL);

            ps.executeUpdate();

            // get branch id for new branch
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT B.branchID from branch B where B.branchName='" + this.txtBranchName.getText().trim() + "'");
            rs2.next();

            // create storage for this branch
            PreparedStatement ps2 = con.prepareStatement("INSERT INTO storages(branchID) values(?)");
            ps2.setInt(1, Integer.parseInt(rs2.getString(1)));
            ps2.executeUpdate();

            // clear texts
            this.txtBranchName.clear();
            this.txtBranchPhone.clear();
            this.txtBulldingNumber.clear();
            this.txtRegionName.clear();
            this.txtStrretName.clear();
            this.cbxCityName.setValue("");
            // display branch with new branch
            this.execute("");
            Message.displayMassage("Successfully", "A new branch has been added successfully");

        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }
    }

    public void handleBtUpdate() {
        try {
            if (this.branchID == -1) return;

            String sqlUpdate = "UPDATE branch " + "SET branchName = ?, branchPhone=?, cityID=?, streetName=?, regionName=?, bulldingNumber=?" + " WHERE branchID = " + this.branchID;
            ps = con.prepareStatement(sqlUpdate);

            // get branch name
            if (!this.txtBranchName.getText().isEmpty())
                ps.setString(1, this.txtBranchName.getText().trim());
            else {
                Message.displayMassage("Warning", " Please Enter the branch name");
                return;
            }

            // get city name
            if (this.cbxCityName == null || this.cbxCityName.getValue().equals("")) {
                Message.displayMassage("Warning", " Please select the city name");
                return;
            } else {
                String cityID = "SELECT C.cityID from city C where C.cityName='" + this.cbxCityName.getValue() + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(cityID);
                rs.next();
                ps.setInt(3, Integer.parseInt(rs.getString(1).trim()));
            }

            // get branch phone
            if (!this.txtBranchPhone.getText().isEmpty()) ps.setString(2, this.txtBranchPhone.getText().trim());
            else ps.setNull(2, Types.NULL);

            // get strretName
            if (!this.txtStrretName.getText().isEmpty()) ps.setString(4, this.txtStrretName.getText().trim());
            else ps.setNull(4, Types.NULL);

            // get region name
            if (!this.txtRegionName.getText().isEmpty()) ps.setString(5, this.txtRegionName.getText().trim());
            else ps.setNull(5, Types.NULL);

            // get bullding number
            if (!this.txtBulldingNumber.getText().isEmpty()) {
                if (Methods.isNumber(this.txtBulldingNumber.getText().trim()))
                    ps.setInt(6, Integer.parseInt(this.txtBulldingNumber.getText().trim()));
                else {
                    Message.displayMassage("Warning", this.txtBulldingNumber.getText().trim() + " Is invalid ");
                    return;
                }
            } else ps.setNull(6, Types.NULL);

            ps.executeUpdate();

            // clear texts
            this.txtBranchName.clear();
            this.txtBranchPhone.clear();
            this.txtBulldingNumber.clear();
            this.txtRegionName.clear();
            this.txtStrretName.clear();
            this.cbxCityName.setValue("");
            // display branch with new branch
            this.execute("");
            Message.displayMassage("Successfully", " The " + this.branchID + " branch information has been successfully updated ");

        } catch (SQLException sqlException) {
            Message.displayMassage("Error", sqlException.getMessage());
        }

    }

    public void handleBtDelete() {
        if (this.branchID == -1) return;
        try {
            String query = "DELETE from branch B " + "where B.branchID=" + this.branchID;
            Statement st = con.createStatement();
            st.executeUpdate(query);

            // clear texts
            this.txtBranchName.clear();
            this.txtBranchPhone.clear();
            this.txtBulldingNumber.clear();
            this.txtRegionName.clear();
            this.txtStrretName.clear();
            this.cbxCityName.setValue("");
            // display branch with new branch
            this.execute("");
            Message.displayMassage("Successfully", " The " + this.branchID + " Branch has been successfully deleted ");
            this.branchID = -1;
        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }

    }

    private void execute(String str) {
        this.txtSearch.clear();
        this.tableBranches.getItems().clear();
        this.txtBranchName.clear();
        this.txtBranchPhone.clear();
        this.txtBulldingNumber.clear();
        this.txtRegionName.clear();
        this.txtStrretName.clear();
        this.cbxCityName.setValue("");
        try {
            String getBranch = "SELECT * from branch B " + str;
            String getNumOfBranch = "SELECT COUNT(*) from branch B " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getBranch);

            boolean flag = true;
            while (rs.next()) {
                Branch branch = new Branch();
                String id = rs.getString(1);
                branch.setBranchId(id);
                branch.setBranchName(rs.getString(2).trim());

                if (rs.getString(3) != null) branch.setBranchPhone(rs.getString(3).trim());

                int cityID = Integer.parseInt(rs.getString(4).trim());
                Statement stmt5 = con.createStatement();
                ResultSet rs5 = stmt5.executeQuery("SELECT C.cityName" + "  From city C where C.cityID =" + cityID);
                rs5.next();

                String address = rs5.getString(1).trim();
                if (rs.getString(5) != null) address += (", " + rs.getString(5).trim());
                if (rs.getString(6) != null) address += (", " + rs.getString(6).trim());
                if (rs.getString(7) != null) address += (", " + rs.getString(7).trim());

                branch.setAddress(address);
                this.tableBranches.getItems().add(branch);
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning",  "Does not exist");
                return;
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

    public void getSelected() {
        int index = this.tableBranches.getSelectionModel().getSelectedIndex();
        if (index <= -1) return;
        this.branchID = Integer.parseInt(cmBranchId.getCellData(index));
        try {

            String getBranchID = "SELECT * from branch B where B.branchID=" + Integer.parseInt(cmBranchId.getCellData(index));
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getBranchID);
            rs.next();

            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT C.cityName From city C where C.cityID=" + Integer.parseInt(rs.getString(4)));
            rs2.next();

            this.txtBranchName.setText(rs.getString(2).trim());
            this.cbxCityName.setValue(rs2.getString(1).trim());

            if (cmBranchPhone.getCellData(index) != null) this.txtBranchPhone.setText(cmBranchPhone.getCellData(index));
            else this.txtBranchPhone.setText("");

            if (rs.getString(5) != null) this.txtStrretName.setText(rs.getString(5).trim());
            else this.txtStrretName.setText("");

            if (rs.getString(6) != null) this.txtRegionName.setText(rs.getString(6).trim());
            else this.txtRegionName.setText("");

            if (rs.getString(7) != null) this.txtBulldingNumber.setText(rs.getString(7).trim());
            else this.txtBulldingNumber.setText("");

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
