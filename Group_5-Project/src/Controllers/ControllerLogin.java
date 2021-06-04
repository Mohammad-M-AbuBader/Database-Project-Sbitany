/**
 * @autor: Ameer Eleyan
 * 1191076
 * At: 2/6/2021  11:46 PM
 */


package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ControllerLogin {


    @FXML // fx:id="txtUserName"
    private TextField txtUserName; // Value injected by FXMLLoader

    @FXML // fx:id="txtPasswd"
    private PasswordField txtPasswd; // Value injected by FXMLLoader

    @FXML // fx:id="btLogin"
    private Button btLogin; // Value injected by FXMLLoader


    public void handleBtLogin() {

        try {
            if (this.txtUserName.getText().isEmpty()) {
                Message.displayMassage("Warning", "Please enter the user name");
                return;
            }
            if (this.txtPasswd.getText().isEmpty()) {
                Message.displayMassage("Warning", "Please enter the password");
                return;
            }

            ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
            Connection con = connection.connectSbitanyDB();

            assert con != null;
            Statement statement = con.createStatement();
            String uName = this.txtUserName.getText().trim();
            String passwd = this.txtPasswd.getText().trim();

            // get the employee id and branch id for this accountant
            ResultSet resultSet = statement.executeQuery("SELECT E.employeeID, E.branchID, E.jobTitleID from employee E where E.employeeUserName='" + uName + "' and E.employeePassword='" + passwd + "'" + " and E.employeeFiringDate is null");
            boolean result = resultSet.next();

            if (!result) { // this account does not exist
                Message.displayMassage("Warning", "There is no account at this username and password, Try again");
                this.txtUserName.clear();
                this.txtPasswd.clear();
                return;
            }
            int employeeID = Integer.parseInt(resultSet.getString(1).trim());
            int branchID = Integer.parseInt(resultSet.getString(2).trim());

            BranchAccountantController.setInfo(branchID, employeeID); // send this values to branch accountant controller

            String windowFxml = "BranchAccountant.fxml";
            String title = "Branch Accountant";

            if (resultSet.getString(3).equals("6")) {
                windowFxml = "GeneralManager.fxml";
                title = "GeneralManager";
            }
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/" + windowFxml)));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setScene(new Scene(root));
            window.setResizable(false);
            Stage currentStage = (Stage) this.btLogin.getScene().getWindow();
            currentStage.close();
            window.show();

        } catch (IOException | SQLException ex) {
            Message.displayMassage("Warning", ex.getMessage());
        }
    }
}
