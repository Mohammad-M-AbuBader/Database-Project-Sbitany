/**
 * @autor: Ameer Eleyan
 * 1191076
 * At: 30/5/2021  9:12 PM
 */

package Controllers;

import DataBaseClasses.Product;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class ProductForBranchesController implements Initializable {

    @FXML // fx:id="txProductCode"
    private TextField txProductCode; // Value injected by FXMLLoader

    @FXML // fx:id="tvProducts"
    private TableView<Product> tvProducts; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductCode"
    private TableColumn<Product, String> cmProductCode; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductName"
    private TableColumn<Product, String> cmProductName; // Value injected by FXMLLoader

    @FXML // fx:id="cmManufacturer"
    private TableColumn<Product, String> cmManufacturer; // Value injected by FXMLLoader

    @FXML // fx:id="cmSellingPrice"
    private TableColumn<Product, String> cmSellingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cmCategoriesName"
    private TableColumn<Product, String> cmCategoriesName; // Value injected by FXMLLoader

    @FXML // fx:id="cmDescripition"
    private TableColumn<Product, String> cmDescripition; // Value injected by FXMLLoader

    @FXML // fx:id="cmParCode"
    private TableColumn<Product, String> cmParCode; // Value injected by FXMLLoader
    private Message message;

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.tvProducts.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        cmProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        cmProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cmManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturerCompany"));
        cmSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        cmCategoriesName.setCellValueFactory(new PropertyValueFactory<>("categoriesName"));
        cmParCode.setCellValueFactory(new PropertyValueFactory<>("parCode"));
        cmDescripition.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
    }

    @FXML
    void handleBtQuantityOf() {
        try{
            Parent root = load(Objects.requireNonNull(getClass().getResource("../FXML/QuantityOf.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Quantity Of:");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @FXML
    void handleBtSearch() {

    }


}

