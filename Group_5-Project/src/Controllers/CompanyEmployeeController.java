/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 19-5-2021  12:20 AM
 */

package Controllers;

import DataBaseClasses.Employee;
import Utilities.ConnectionToSbitanyDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    @FXML
    private ComboBox<String> combbranch;
    @FXML
    private TextField txProductCode;

    @FXML
    private TableView<Employee> tableEmployee;

    @FXML
    private TableColumn<Employee, String> cmEmployeeID;

    @FXML
    private TableColumn<Employee, String> cmEmployeeName;

    @FXML
    private TableColumn<Employee, String> cmEmployeeAge;

    @FXML
    private TableColumn<Employee, String> cmBranchName;

    @FXML
    private TableColumn<Employee, String> cmPersonalID;

    @FXML
    private TableColumn<Employee, String> cmEmployeePhone;

    @FXML
    private TableColumn<Employee, String> cmEmployeeEmail;

    @FXML
    private TableColumn<Employee, String> cmEmployeeHiringDate;

    @FXML
    private TableColumn<Employee, String> cmJobTitle;

    @FXML
    private TableColumn<Employee, String> cmAddress;

    @FXML
    private TextField txProductCode1;

    @FXML
    private TextField txProductCode11;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String>  BranchName= FXCollections.observableArrayList("بيت لحم","رام الله","الخليل","اريحا"," نابلس","جنين" ," طولكرم","قلقيلية");
        combbranch.setItems(BranchName);

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
        this.tableEmployee.getItems().clear();

        try {

            ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
            Connection con = connection.connectSbitanyDB();
            String getEmployee = "SELECT * from Employee";


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
                ResultSet rs2 = stmt2.executeQuery("SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(now(),E.employeeDateOfBirth)), '%Y')+0 AS Age From Employee E where  E.employeeID="+Integer.parseInt(id.trim()));
                rs2.next();
                employee.setEmployeeAge(rs2.getString(1));

                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT b.branchName From Branch b, Employee E where E.branchID= b.branchID  and E.employeeID="+Integer.parseInt(id.trim()));
                rs3.next();
                employee.setBranchName(rs3.getString(1));

                employee.setEmployeeHiringDate(rs.getString(10));

                Statement stmt4 = con.createStatement();
                ResultSet rs4 = stmt4.executeQuery("SELECT j.jobName From JobTitle j , Employee E where E.jobTitleID= j.jobTitleID and E.employeeID="+Integer.parseInt(id.trim()));
                rs4.next();
                employee.setJobTitleID(rs4.getString(1));

                Statement stmt5 = con.createStatement();
                String villageId=rs.getString(14);
                ResultSet rs5;
                if(villageId != null)
                {
                    rs5 = stmt5.executeQuery("SELECT CONCAT(C.cityname,', ' ,V.villageName)" +
                            "  From city C , Village V, Employee E where C.cityID = E.cityID and V.VillageID = E.villageID and E.employeeID="+Integer.parseInt(id.trim()));
                }
                else
                {
                    rs5 = stmt5.executeQuery("SELECT C.cityName" +
                            "  From city C, Employee E where C.cityID =E.cityID and E.employeeID="+Integer.parseInt(id.trim()));
                }
                rs5.next();
                employee.setAddress(rs5.getString(1));



                this.tableEmployee.getItems().add(employee);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

    }

    @FXML
    void selectBranch(ActionEvent event) {
        combbranch.getSelectionModel().getSelectedItem().toString();

    }

}
