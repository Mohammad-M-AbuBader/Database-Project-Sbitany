/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 23-5-2021  7:20 PM
 */
package Controllers;

import Utilities.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BranchAccountantController {

    @FXML // fx:id="btLogout"
    private Button btLogout; // Value injected by FXMLLoader
    private static int branchID, employeeID;

    public static void setInfo(int branchID, int employeeID) {
        BranchAccountantController.branchID = branchID;
        BranchAccountantController.employeeID = employeeID;
    }

    public void handleBtProducts() {
        try {
            ProductForBranchesController.setBranchID(branchID);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/ProductForBranches.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Products");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }

    }

    public void handleBtOrderProduct() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Order.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Order Products");
            window.setScene(new Scene(root));
            window.setResizable(false);
            OrderProductController.setInfo(branchID);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }


    @FXML
    void handleBtBranches() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/BranchesForAccountant.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Branches");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    @FXML
    void handleBtEmployees() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/SpecificDataOfEmployee.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Company Employee");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    @FXML
    void handleBtLogout() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Login.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Login");
            window.setScene(new Scene(root));
            window.setResizable(false);
            Stage currentStage = (Stage) this.btLogout.getScene().getWindow();
            currentStage.close();
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    @FXML
    void handleBtNewCustomer() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/NewCustomer.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("New Customer");
            window.setScene(new Scene(root));
            window.setResizable(false);
            NewCustomerController.setInfo(branchID, employeeID);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

}

