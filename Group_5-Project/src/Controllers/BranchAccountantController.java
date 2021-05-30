/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 23-5-2021  7:20 PM
 */
package Controllers;

import Utilities.Message;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BranchAccountantController {

    public void handleBtProducts() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/ProductsForBranches.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Products");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message message = new Message();
            message.displayMassage("Warning",exception.getMessage());
        }

    }

    public void handleBtOrderProduct() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Order.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Product Order");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message message = new Message();
            message.displayMassage("Warning",exception.getMessage());
        }

    }


    @FXML
    void handleBtBranches() {

    }

    @FXML
    void handleBtEmployees() {

    }

    @FXML
    void handleBtLogout() {

    }

    @FXML
    void handleBtNewCustomer() {

    }


}

