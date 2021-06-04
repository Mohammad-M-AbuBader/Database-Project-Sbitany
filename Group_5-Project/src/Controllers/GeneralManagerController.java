/**
 * @autor: Ameer Eleyan
 * 1191076
 * At: 31/5/2021  4:30 AM
 */

package Controllers;

import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class GeneralManagerController {


    @FXML // fx:id="btLogout"
    private Button btLogout; // Value injected by FXMLLoader


    public void handleBtBranches() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Branches.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Company Branches");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    public void handleBtBranchGet() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/RecordMovements.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Record Movements");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    public void handleBtLogout() {
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

    public void handleBtEmployee() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/AllCompanyEmployees.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Company Employees");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    public void handleBtCustomres() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Customer.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Customers");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    public void handleBtProducts() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Products.fxml")));
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

    public void handleBtSupplierBills() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/SupplierBill.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Suppliers Bills");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    public void handleBtCustomerBills() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/CustomerBill.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Customers Bills");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    public void handleBtSuppliers() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Supplier.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Suppliers");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }


}
