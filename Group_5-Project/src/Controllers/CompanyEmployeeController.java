/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 19-5-2021  12:20 AM
 */

package Controllers;

import DataBaseClasses.Employee;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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

public class CompanyEmployeeController implements Initializable {


    @FXML // fx:id="txtEmployeeID"
    private TextField txtEmployeeID; // Value injected by FXMLLoader

    @FXML // fx:id="cbxBranch"
    private ComboBox<String> cbxBranch; // Value injected by FXMLLoader

    @FXML // fx:id="tableEmployee"
    private TableView<Employee> tableEmployee; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeeID"
    private TableColumn<Employee, String> cmEmployeeID; // Value injected by FXMLLoader

    @FXML // fx:id="cmPersonalID"
    private TableColumn<Employee, String> cmPersonalID; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeeName"
    private TableColumn<Employee, String> cmEmployeeName; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeeAge"
    private TableColumn<Employee, String> cmEmployeeAge; // Value injected by FXMLLoader

    @FXML // fx:id="cmBranchName"
    private TableColumn<Employee, String> cmBranchName; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeePhone"
    private TableColumn<Employee, String> cmEmployeePhone; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmployeeHiringDate"
    private TableColumn<Employee, String> cmEmployeeHiringDate; // Value injected by FXMLLoader

    @FXML // fx:id="cmJobTitle"
    private TableColumn<Employee, String> cmJobTitle; // Value injected by FXMLLoader

    @FXML // fx:id="cmAddress"
    private TableColumn<Employee, String> cmAddress; // Value injected by FXMLLoader

    @FXML // fx:id="cbxShow"
    private ComboBox<String> cbxShow; // Value injected by FXMLLoader

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.tableEmployee.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        cmEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        cmEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        cmEmployeeAge.setCellValueFactory(new PropertyValueFactory<>("employeeAge"));
        cmBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        cmPersonalID.setCellValueFactory(new PropertyValueFactory<>("employeeCard"));
        cmEmployeePhone.setCellValueFactory(new PropertyValueFactory<>("employeePhone"));
        cmEmployeeHiringDate.setCellValueFactory(new PropertyValueFactory<>("employeeHiringDate"));
        cmJobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitleID"));
        cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        String getBranchesName = "SELECT B.branchName from branch B";

        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getBranchesName);

            while (rs.next()) {
                this.cbxBranch.getItems().add(rs.getString(1).trim());
            }
            this.cbxShow.getItems().add("Firing Employees");
            this.cbxShow.getItems().add("Branch Accountants");
            this.cbxShow.getItems().add("Branch Managers");
            this.cbxShow.getItems().add("Sales Employees");
            this.cbxShow.getItems().add("Personnel Officer");
            this.cbxShow.getItems().add("Company Accountant");
            this.cbxShow.getItems().add("General Manager");

            rs.close();
            stmt.close();
            this.refresh(" where E.employeeFiringDate is null");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @FXML
    void handleBtRefresh() {
        this.refresh(" where E.employeeFiringDate is null");
    }

    private void refresh(String str) {
        this.tableEmployee.getItems().clear();

        try {

            ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
            Connection con = connection.connectSbitanyDB();
            String getEmployee = "SELECT * from Employee E " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getEmployee);

            while (rs.next()) {
                Employee employee = new Employee();
                String id = rs.getString(1);
                employee.setEmployeeID(id);
                employee.setEmployeeCard(rs.getString(2));
                employee.setEmployeeName(rs.getString(3));
                employee.setEmployeePhone(rs.getString(4));

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(now(),E.employeeDateOfBirth)), '%Y')+0 AS Age From Employee E where  E.employeeID=" + Integer.parseInt(id.trim()));
                rs2.next();
                employee.setEmployeeAge(rs2.getString(1));

                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT b.branchName From Branch b, Employee E where E.branchID= b.branchID  and E.employeeID=" + Integer.parseInt(id.trim()));
                rs3.next();
                employee.setBranchName(rs3.getString(1));

                employee.setEmployeeHiringDate(rs.getString(10));

                Statement stmt4 = con.createStatement();
                ResultSet rs4 = stmt4.executeQuery("SELECT j.jobName From JobTitle j , Employee E where E.jobTitleID= j.jobTitleID and E.employeeID=" + Integer.parseInt(id.trim()));
                rs4.next();
                employee.setJobTitleID(rs4.getString(1));

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

                this.tableEmployee.getItems().add(employee);
            }

            rs.close();
            stmt.close();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @FXML
    void handleBtSearch() {
        if (!this.txtEmployeeID.getText().trim().isEmpty()) {
            if (!isNumber(this.txtEmployeeID.getText().trim())) {
                Message.displayMassage("Warning", this.txtEmployeeID.getText() + " is invalid ");
                this.txtEmployeeID.clear();
                return;
            }
            String search = "SELECT * from employee E where E.employeeFiringDate is null and E.employeeID=" + Integer.parseInt(this.txtEmployeeID.getText().trim());

            try {
                assert con != null;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(search);
                boolean empty = rs.next();
                if (!empty) {
                    Message.displayMassage("Warning", this.txtEmployeeID.getText() + " Does not exist ");
                    this.txtEmployeeID.clear();
                    return;
                }
                this.refresh(" where E.employeeID=" + Integer.parseInt(this.txtEmployeeID.getText().trim()));

            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }
        }
    }

    @FXML
    void handleComboBoxShow() {
        String show = this.cbxShow.getValue().trim();
        if (show.equalsIgnoreCase("Firing Employees")) {
            this.showFiringEmployee();
        } else if (show.equalsIgnoreCase("Branch Accountants")) {
            this.showBranchAccountants();
        } else if (show.equalsIgnoreCase("Branch Managers")) {
            this.showBranchManagers();
        } else if (show.equalsIgnoreCase("Sales Employees")) {
            this.showSalesEmployees();
        } else if (show.equalsIgnoreCase("Personnel Officer")) {
            this.showPersonnelOfficer();
        } else if (show.equalsIgnoreCase("Company Accountant")) {
            this.showCompanyAccountant();
        } else {
            this.showGeneralManger();
        }

    }

    private void showFiringEmployee() {
        this.refresh(" where E.employeeFiringDate is not null");
    }

    private void showBranchAccountants() {
        this.refresh(" where E.jobTitleID=1 and E.employeeFiringDate is null");
    }

    private void showBranchManagers() {
        this.refresh(" where E.jobTitleID=3 and E.employeeFiringDate is null");
    }

    private void showSalesEmployees() {
        this.refresh(" where E.jobTitleID=2 and E.employeeFiringDate is null");
    }

    private void showPersonnelOfficer() {
        this.refresh(" where E.jobTitleID=5 and E.employeeFiringDate is null");
    }

    private void showCompanyAccountant() {
        this.refresh(" where E.jobTitleID=4 and E.employeeFiringDate is null");
    }

    private void showGeneralManger() {
        this.refresh(" where E.jobTitleID=6 and E.employeeFiringDate is null");
    }

    @FXML
    void selectBranch() {
        this.tableEmployee.getItems().clear();
        try {
            String bName = this.cbxBranch.getValue().trim();
            String getBranchID = "SELECT B.branchID from branch B where B.branchName= '" + bName + "'";
            Statement bID = con.createStatement();
            ResultSet resultBId = bID.executeQuery(getBranchID);
            resultBId.next();
            int branchID = Integer.parseInt(resultBId.getString(1).trim());
            this.refresh(" where E.branchID=" + branchID);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    // To check the value of the entered numberOfShares if contain only digits or not
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

