/**
 * Sample Skeleton for 'GeneralManager.fxml' Controller Class
 */

package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;

public class GeneralManagerController {

    @FXML // fx:id="btBranches"
    private Button btBranches; // Value injected by FXMLLoader

    @FXML // fx:id="btAccount"
    private Button btAccount; // Value injected by FXMLLoader

    @FXML // fx:id="btLogout"
    private Button btLogout; // Value injected by FXMLLoader

    @FXML // fx:id="btEmployee"
    private Button btEmployee; // Value injected by FXMLLoader

    @FXML // fx:id="btCustomres"
    private Button btCustomres; // Value injected by FXMLLoader

    @FXML // fx:id="btProducts"
    private Button btProducts; // Value injected by FXMLLoader

    @FXML // fx:id="btSupplierBills"
    private Button btSupplierBills; // Value injected by FXMLLoader

    @FXML // fx:id="btCustomerBills"
    private Button btCustomerBills; // Value injected by FXMLLoader

    @FXML // fx:id="btSuppliers"
    private Button btSuppliers; // Value injected by FXMLLoader


    public void handleBtBranches() {

    }

    public void handleBtAccount() {

    }

    public void handleBtLogout() {

    }

    public void handleBtEmployee() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/CompanyEmployees.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Company Employees");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            //
        }
    }

    public void handleBtCustomres() {


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
            //
        }

    }

    public void handleBtSupplierBills() {

    }

    public void handleBtCustomerBills() {

    }

    public void handleBtSuppliers() {

    }


}
