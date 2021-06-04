/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 24-5-2021  3:10 PM
 */
package Controllers;

import DataBaseClasses.Employee;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
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

public class AllDataOfEmployee implements Initializable {


    @FXML // fx:id="txtSearch"
    private TextField txtSearch; // Value injected by FXMLLoader

    @FXML // fx:id="combBranch"
    private ComboBox<String> combBranch; // Value injected by FXMLLoader

    @FXML // fx:id="tableEmployee"
    private TableView<Employee> tableEmployee; // Value injected by FXMLLoader

    @FXML // fx:id="cmID"
    private TableColumn<Employee, String> cmID; // Value injected by FXMLLoader

    @FXML // fx:id="cmPersonalID"
    private TableColumn<Employee, String> cmPersonalID; // Value injected by FXMLLoader

    @FXML // fx:id="cmName"
    private TableColumn<Employee, String> cmName; // Value injected by FXMLLoader

    @FXML // fx:id="cmJobTitle"
    private TableColumn<?, ?> cmJobTitle; // Value injected by FXMLLoader

    @FXML // fx:id="cmDOB"
    private TableColumn<Employee, String> cmDOB; // Value injected by FXMLLoader

    @FXML // fx:id="cmAge"
    private TableColumn<Employee, String> cmAge; // Value injected by FXMLLoader

    @FXML // fx:id="cmHiringDate"
    private TableColumn<Employee, String> cmHiringDate; // Value injected by FXMLLoader

    @FXML // fx:id="cmFiringDate"
    private TableColumn<Employee, String> cmFiringDate; // Value injected by FXMLLoader

    @FXML // fx:id="cmEmail"
    private TableColumn<Employee, String> cmEmail; // Value injected by FXMLLoader

    @FXML // fx:id="cmSalary"
    private TableColumn<Employee, String> cmSalary; // Value injected by FXMLLoader

    @FXML // fx:id="cmUname"
    private TableColumn<Employee, String> cmUname; // Value injected by FXMLLoader

    @FXML // fx:id="cmPasswd"
    private TableColumn<Employee, String> cmPasswd; // Value injected by FXMLLoader

    @FXML // fx:id="cmPhone"
    private TableColumn<Employee, String> cmPhone; // Value injected by FXMLLoader

    @FXML // fx:id="cmBranchName"
    private TableColumn<Employee, String> cmBranchName; // Value injected by FXMLLoader

    @FXML // fx:id="cmAddress"
    private TableColumn<Employee, String> cmAddress; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumOfEmployee"
    private TextField txtNumOfEmployee; // Value injected by FXMLLoader

    @FXML // fx:id="cbxShow"
    private ComboBox<String> cbxShow; // Value injected by FXMLLoader

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        this.cmID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        this.cmPersonalID.setCellValueFactory(new PropertyValueFactory<>("employeeCard"));
        this.cmName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        this.cmDOB.setCellValueFactory(new PropertyValueFactory<>("employeeDateOfBirth"));
        this.cmSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        this.cmEmail.setCellValueFactory(new PropertyValueFactory<>("employeeEmail"));
        this.cmUname.setCellValueFactory(new PropertyValueFactory<>("employeeUserName"));
        this.cmPasswd.setCellValueFactory(new PropertyValueFactory<>("employeePassword"));
        this.cmHiringDate.setCellValueFactory(new PropertyValueFactory<>("employeeHiringDate"));
        this.cmFiringDate.setCellValueFactory(new PropertyValueFactory<>("employeeFiringDate"));
        this.cmJobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitleID"));
        this.cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.cmBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        this.cmPhone.setCellValueFactory(new PropertyValueFactory<>("employeePhone"));
        this.cmAge.setCellValueFactory(new PropertyValueFactory<>("employeeAge"));

        String getBranchesName = "SELECT B.branchName from branch B";

        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getBranchesName);

            while (rs.next()) {
                this.combBranch.getItems().add(rs.getString(1).trim());
            }
            this.cbxShow.getItems().add("Firing Employees");
            this.cbxShow.getItems().add("Branch Accountants");
            this.cbxShow.getItems().add("Branch Managers");
            this.cbxShow.getItems().add("Sales Employees");
            this.cbxShow.getItems().add("Personnel Officer");
            this.cbxShow.getItems().add("Company Accountant");

            rs.close();
            stmt.close();
            this.execute(" where E.employeeFiringDate is null");

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }

    public void handleBtSearch() {
        if (!this.txtSearch.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txtSearch.getText().trim())) {
                Message.displayMassage("Warning", this.txtSearch.getText() + " is invalid ");
                this.txtSearch.clear();
                return;
            }
            this.execute("  where E.employeeID=" + Integer.parseInt(this.txtSearch.getText().trim()));
        }
    }

    public void handleBtRefresh() {
        this.txtSearch.clear();
        this.tableEmployee.getItems().clear();
        this.execute(" where E.employeeFiringDate is null");
    }

    public void handleComboBoxBranches() {
        this.tableEmployee.getItems().clear();
        try {
            String bName = this.combBranch.getValue().trim();
            String getBranchID = "SELECT B.branchID from branch B where B.branchName= '" + bName + "'";
            Statement bID = con.createStatement();
            ResultSet resultBId = bID.executeQuery(getBranchID);
            resultBId.next();
            int branchID = Integer.parseInt(resultBId.getString(1).trim());
            this.execute(" where E.branchID=" + branchID);
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    public void handleComboBoxShow() {
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
        } else {
            this.showCompanyAccountant();
        }
    }

    // insert new employee
    public void handleBtNewEmployee() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/InsertNewEmployee.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Employee");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    private void showFiringEmployee() {
        this.execute(" where E.employeeFiringDate is not null");
    }

    private void showBranchAccountants() {
        this.execute(" where E.jobTitleID=1 and E.employeeFiringDate is null");
    }

    private void showBranchManagers() {
        this.execute(" where E.jobTitleID=3 and E.employeeFiringDate is null");
    }

    private void showSalesEmployees() {
        this.execute(" where E.jobTitleID=2 and E.employeeFiringDate is null");
    }

    private void showPersonnelOfficer() {
        this.execute(" where E.jobTitleID=5 and E.employeeFiringDate is null");
    }

    private void showCompanyAccountant() {
        this.execute("where E.jobTitleID=4 and E.employeeFiringDate is null");
    }

    private void execute(String str) {
        this.txtSearch.clear();
        this.tableEmployee.getItems().clear();
        try {
            String getEmployee = "SELECT * from employee E " + str;
            String getNumOfEmployee = "SELECT COUNT(*) from employee E " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getEmployee);
            boolean flag = true;
            while (rs.next()) {
                Employee employee = new Employee();
                String id = rs.getString(1);
                employee.setEmployeeID(id);
                employee.setEmployeeCard(rs.getString(2));
                employee.setEmployeeName(rs.getString(3));
                employee.setEmployeePhone(rs.getString(4));
                employee.setEmployeeDateOfBirth(rs.getString(5));
                employee.setEmployeeSalary(rs.getString(6));
                employee.setEmployeeEmail(rs.getString(7));
                employee.setEmployeeUserName(rs.getString(8));
                employee.setEmployeePassword(rs.getString(9));

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(now(),E.employeeDateOfBirth)), '%Y')+0 AS Age From Employee E where  E.employeeID=" + Integer.parseInt(id.trim()));
                rs2.next();
                employee.setEmployeeAge(rs2.getString(1));

                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT b.branchName From Branch b, Employee E where E.branchID= b.branchID  and E.employeeID=" + Integer.parseInt(id.trim()));
                rs3.next();
                employee.setBranchName(rs3.getString(1));

                employee.setEmployeeHiringDate(rs.getString(10));
                employee.setEmployeeFiringDate(rs.getString(11));

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
                flag = false;
                this.tableEmployee.getItems().add(employee);
            }
            if (flag) {
                Message.displayMassage("Warning",  "Does ot exist");
                return;
            }

            Statement total = con.createStatement();
            ResultSet resultSetTotal = total.executeQuery(getNumOfEmployee);
            resultSetTotal.next();
            this.txtNumOfEmployee.setText(resultSetTotal.getString(1).trim());
            rs.close();
            stmt.close();

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
