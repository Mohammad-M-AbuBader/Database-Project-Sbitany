/**
 * Sample Skeleton for 'GeneralMangerEmployees.fxml' Controller Class
 */

package Controllers;

import DataBaseClasses.Employee;
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

public class AllDataForEmployee implements Initializable {

    @FXML // fx:id="btSearch"
    private Button btSearch; // Value injected by FXMLLoader

    @FXML // fx:id="txtSearch"
    private TextField txtSearch; // Value injected by FXMLLoader

    @FXML // fx:id="combBranch"
    private ComboBox<?> combBranch; // Value injected by FXMLLoader

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

    @FXML // fx:id="btRefresh"
    private Button btRefresh; // Value injected by FXMLLoader

    private Message message;

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

        this.tableEmployee.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        this.refresh();

    }

    private void refresh() {
        this.txtSearch.clear();
        this.tableEmployee.getItems().clear();
        try {
            String getEmployee = "SELECT * from employee";
            String getNumOfEmployee = "SELECT COUNT(*) from employee";

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


                this.tableEmployee.getItems().add(employee);
            }

            Statement total = con.createStatement();
            ResultSet resultSetTotal = total.executeQuery(getNumOfEmployee);
            resultSetTotal.next();
            this.txtNumOfEmployee.setText(resultSetTotal.getString(1).trim());
            rs.close();
            stmt.close();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }
}
