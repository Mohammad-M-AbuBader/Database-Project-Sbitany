/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 4-6-2021  3:20 AM
 */
package Controllers;

import DataBaseClasses.Address;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class InsertNewEmployeeController implements Initializable {


    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="dateOFBirth"
    private DatePicker dateOFBirth; // Value injected by FXMLLoader

    @FXML // fx:id="txtIDCard"
    private TextField txtIDCard; // Value injected by FXMLLoader

    @FXML // fx:id="txtPhoneNumber"
    private TextField txtPhoneNumber; // Value injected by FXMLLoader

    @FXML // fx:id="txtSalary"
    private TextField txtSalary; // Value injected by FXMLLoader

    @FXML // fx:id="combBranch"
    private ComboBox<String> combBranch; // Value injected by FXMLLoader

    @FXML // fx:id="combCity"
    private ComboBox<String> combCity; // Value injected by FXMLLoader

    @FXML // fx:id="combVillage"
    private ComboBox<String> combVillage; // Value injected by FXMLLoader

    @FXML // fx:id="combJob"
    private ComboBox<String> combJob; // Value injected by FXMLLoader

    @FXML // fx:id="hboxPasswd"
    private HBox hboxPasswd; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassword"
    private PasswordField txtPassword; // Value injected by FXMLLoader

    private Connection con;

    private static int jobTitleID, branchID, cityID, villageID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.fillComboBoxes();
    }

    // handle button insert new employee
    public void handleBtInsertNewEmployee() {
        try {

            if (this.txtName.getText().trim().isEmpty()) {
                Message.displayMassage("Warning", "Please enter the name");
                return;
            }
            if (dateOFBirth.getValue() == null) {
                Message.displayMassage("Warning", "Please select the date of birth");
                return;
            }
            if (this.txtIDCard.getText().trim().isEmpty() || !Methods.isNumber(this.txtIDCard.getText().trim())) {
                Message.displayMassage("Warning", "Please enter a valid personal ID");
                return;
            }
            if (this.txtPhoneNumber.getText().trim().isEmpty() || !Methods.isNumber(this.txtPhoneNumber.getText().trim())) {
                Message.displayMassage("Warning", "Please enter a valid phone number");
                return;
            }
            if (this.txtSalary.getText().trim().isEmpty() || !Methods.isNumber(this.txtSalary.getText().trim())) {
                Message.displayMassage("Warning", "Please enter a valid salary");
                return;
            }

            if (this.combBranch.getValue() == null) {
                Message.displayMassage("Warning", "Please select the branch");
                return;
            }

            if (this.combCity.getValue() == null) {
                Message.displayMassage("Warning", "Please select the city");
                return;
            }

            if (this.combJob.getValue() == null) {
                Message.displayMassage("Warning", "Please select job title");
                return;
            }

            String passwd;
            if (jobTitleID != 2) {
                if (this.txtPassword.getText().trim().isEmpty()) {
                    Message.displayMassage("Warning", "Please enter the password");
                    return;
                } else passwd = this.txtPassword.getText().trim();
            } else passwd = null;

            String name = this.txtName.getText().trim();
            Date date = Date.valueOf(dateOFBirth.getValue());
            int idCard = Integer.parseInt(this.txtIDCard.getText().trim());
            String phone = this.txtPhoneNumber.getText().trim();
            int salary = Integer.parseInt(this.txtSalary.getText().trim());

            PreparedStatement psEmployee = con.prepareStatement("INSERT INTO Employee (employeeCard,employeeName,employeePhone" +
                    ",employeeDateOfBirth,employeeSalary,employeeEmail,employeeUserName,employeePassword,employeeHiringDate," +
                    "employeeFiringDate,jobTitleID,cityID,villageID,branchID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            psEmployee.setInt(1, idCard);
            psEmployee.setString(2, name);
            psEmployee.setString(3, phone);
            psEmployee.setDate(4, date);
            psEmployee.setInt(5, salary);

            int space = name.indexOf(' ');
            String firstName;
            if (space >= 0) firstName = name.substring(0, space);
            else firstName = name;

            Calendar calendar = Calendar.getInstance();
            psEmployee.setString(6, firstName + "@Sbitany.com");
            psEmployee.setDate(9, new java.sql.Date(calendar.getTime().getTime()));
            psEmployee.setNull(10, Types.NULL);
            psEmployee.setInt(11, jobTitleID);
            psEmployee.setInt(12, cityID);

            this.hasAVillage();
            if (villageID == 0) psEmployee.setNull(13, Types.NULL);
            else psEmployee.setInt(13, villageID);

            psEmployee.setNull(7, Types.NULL);
            if (passwd == null) psEmployee.setNull(8, Types.NULL);
            else psEmployee.setString(8, passwd);

            psEmployee.setInt(14, branchID);
            psEmployee.executeUpdate();

            String uName;
            Statement sqlLastEmployee = con.createStatement();
            ResultSet set = sqlLastEmployee.executeQuery("SELECT E.employeeID from Employee E where E.employeeID = (SELECT MAX(E1.EmployeeID) from Employee E1)");
            set.next();
            int id = Integer.parseInt(set.getString(1).trim());

            if (jobTitleID != 2) {
                uName = id + "@Sbitany";
                psEmployee = con.prepareStatement("UPDATE Employee E set E.employeeUserName=? where E.employeeID=?");
                psEmployee.setString(1, uName);
                psEmployee.setInt(2, id);
                psEmployee.executeUpdate();
                Message.displayMassage("Employee User Name", "The employee ID is: " + id + " and his userName: " + uName);
            } else {
                Message.displayMassage("New Employee Info", "The employee ID is: " + id);
            }
            this.setDefault();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    // fill all combo boxes at initializing
    private void fillComboBoxes() {
        try {

            // Get city names, and add them to the combo box
            assert con != null;
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT C.cityName From City C ");
            while (rs1.next()) {
                combCity.getItems().add(rs1.getString(1));
            }


            // Get jobTitle names, and add them to the combo box
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT J.jobName From JobTitle J");
            while (rs2.next()) {
                combJob.getItems().add(rs2.getString(1));
            }

            // Get branches names, and add them to the combo box
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery("SELECT B.branchName From Branch B");
            while (rs3.next()) {
                combBranch.getItems().add(rs3.getString(1));
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    // get branch ID
    public void handleCombBranch() {
        try {
            Statement sqlBranch = con.createStatement();
            ResultSet getBranchID = sqlBranch.executeQuery("SELECT B.BranchID From Branch B where B.branchName='" + this.combBranch.getValue().trim() + "'");
            getBranchID.next();
            branchID = Integer.parseInt(getBranchID.getString(1).trim());
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    //  get city ID
    public void handleCombCity() {
        try {
            combVillage.getItems().clear();
            Statement sqlCity = con.createStatement();
            ResultSet getCityID = sqlCity.executeQuery("SELECT C.cityID From City C where C.CityName='" + this.combCity.getValue().trim() + "'");
            getCityID.next();
            cityID = Integer.parseInt(getCityID.getString(1));
            switch (combCity.getValue()) {
                case "Bethlahem" -> fill(combVillage, Address.bethlahem);
                case "Hebron" -> fill(combVillage, Address.hebron);
                case "Jenen" -> fill(combVillage, Address.jenen);
                case "Jericho" -> fill(combVillage, Address.jericho);
                case "Nablus" -> fill(combVillage, Address.nablus);
                case "Qalqilya" -> fill(combVillage, Address.qalqilya);
                case "Ramallah" -> fill(combVillage, Address.ramallah);
                case "Salfit" -> fill(combVillage, Address.salfit);
                case "Tubas" -> fill(combVillage, Address.tubas);
                default -> fill(combVillage, Address.tulkarm);
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }

    private void hasAVillage() {
        if (combVillage.getValue() == null) {
            villageID = 0;
        } else {
            try {
                String strVillageID = "SELECT V.VillageID From Village V where V.cityID = " + cityID + " and V.villageName='" + this.combVillage.getValue().trim() + "'";
                Statement sqlVillage = con.createStatement();
                ResultSet getVillageID = sqlVillage.executeQuery(strVillageID);
                boolean result = getVillageID.next();
                if (result) { // the village is exist
                    villageID = Integer.parseInt(getVillageID.getString(1).trim());
                } else { // insert new village
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Village (cityID,villageName) values(?,?)");
                    ps.setInt(1, cityID);
                    ps.setString(2, this.combVillage.getValue().trim());
                    ps.executeUpdate();
                    getVillageID = sqlVillage.executeQuery(strVillageID);
                    getVillageID.next();
                    villageID = Integer.parseInt(getVillageID.getString(1));
                }
            } catch (SQLException sqlException) {
                Message.displayMassage("Warning", sqlException.getMessage());
            }
        }
    }

    // get job title ID
    public void handleCombJob() {
        try {
            Statement sqlJobTitle = con.createStatement();
            ResultSet getJobTitleID = sqlJobTitle.executeQuery("SELECT J.jobTitleID From jobTitle J where J.JobName='" + this.combJob.getValue().trim() + "'");
            getJobTitleID.next();
            jobTitleID = Integer.parseInt(getJobTitleID.getString(1).trim());
            if (!this.combJob.getValue().equals("Sales Employee")) {
                this.hboxPasswd.setVisible(true);
            } else {
                this.hboxPasswd.setVisible(false);
                this.txtPassword.clear();
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    // fill combo village
    private void fill(ComboBox<String> comboBox, String[] Village) {
        for (String s : Village) {
            comboBox.getItems().add(s);
        }
    }


    private void setDefault() {
        this.txtName.clear();
        this.dateOFBirth.getEditor().clear();
        this.txtIDCard.clear();
        this.txtPhoneNumber.clear();
        this.txtSalary.clear();
        this.hboxPasswd.setVisible(false);
    }

}

