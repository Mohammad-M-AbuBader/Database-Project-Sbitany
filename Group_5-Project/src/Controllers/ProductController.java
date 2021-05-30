/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 23-5-2021  9:06 PM
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

import static javafx.fxml.FXMLLoader.*;


public class ProductController implements Initializable {


    @FXML // fx:id="txSearch"
    private TextField txSearch; // Value injected by FXMLLoader

    @FXML // fx:id="btQuantityOf"
    private Button btQuantityOf; // Value injected by FXMLLoader

    @FXML // fx:id="txTotalProducts"
    private TextField txTotalProducts; // Value injected by FXMLLoader

    @FXML // fx:id="tableProducts"
    private TableView<Product> tableProducts; // Value injected by FXMLLoader

    @FXML // fx:id="cmCodeProducts"
    private TableColumn<Product, String> cmCodeProducts; // Value injected by FXMLLoader

    @FXML // fx:id="cmNameProducts"
    private TableColumn<Product, String> cmNameProducts; // Value injected by FXMLLoader

    @FXML // fx:id="cmManufacturerProduct"
    private TableColumn<Product, String> cmManufacturerProduct; // Value injected by FXMLLoader

    @FXML // fx:id="cmPurchasingPrice"
    private TableColumn<Product, String> cmPurchasingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cmSellingPrice"
    private TableColumn<Product, String> cmSellingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cmCategoriesName"
    private TableColumn<Product, String> cmCategoriesName; // Value injected by FXMLLoader

    @FXML // fx:id="cmParCode"
    private TableColumn<Product, String> cmParCode; // Value injected by FXMLLoader

    @FXML // fx:id="cmDescription"
    private TableColumn<Product, String> cmDescription; // Value injected by FXMLLoader

    private Message message;

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.tableProducts.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        cmCodeProducts.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        cmNameProducts.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cmManufacturerProduct.setCellValueFactory(new PropertyValueFactory<>("manufacturerCompany"));
        cmPurchasingPrice.setCellValueFactory(new PropertyValueFactory<>("purchasingPrice"));
        cmSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        cmCategoriesName.setCellValueFactory(new PropertyValueFactory<>("categoriesName"));
        cmParCode.setCellValueFactory(new PropertyValueFactory<>("parCode"));
        cmDescription.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
        this.refresh();
    }


    public void handleBtSearch() {
        if (!this.txSearch.getText().trim().isEmpty()) {
            if (!isNumber(this.txSearch.getText().trim())) {
                this.message = new Message();
                message.displayMassage("Warning", " Product code is invalid ");
                this.txSearch.clear();
                return;
            }

            String search = "SELECT * from product P where P.productCode=" + Integer.parseInt(this.txSearch.getText().trim());

            try {
                assert con != null;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(search);
                boolean empty = rs.next();
                if (!empty) {
                    this.message = new Message();
                    message.displayMassage("Warning", this.txSearch.getText() + " Does not exist ");
                    return;
                }
                Product product = new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));

                String categoriesName = rs.getString(6).trim();

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT C.catogresName from categories C where C.categoriesId=" + Integer.parseInt(categoriesName));
                rs2.next();

                product.setCategoriesName(rs2.getString(1));
                this.tableProducts.getItems().clear();
                this.tableProducts.getItems().add(product);
                this.txSearch.clear();

            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }

        }
    }

    public void handleBtQuantity() {
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

    public void refresh() {
        this.txSearch.clear();
        this.tableProducts.getItems().clear();
        try {

            String getProducts = "SELECT * from product";
            String getTotalProducts = "SELECT COUNT(*) from product";

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getProducts);

            while (rs.next()) {
                Product product = new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));

                String categoriesName = rs.getString(6).trim();

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT C.catogresName from categories C where C.categoriesId=" + Integer.parseInt(categoriesName));
                rs2.next();

                product.setCategoriesName(rs2.getString(1));
                this.tableProducts.getItems().add(product);
            }
            rs = stmt.executeQuery(getTotalProducts);
            rs.next();
            this.txTotalProducts.setText(rs.getString(1));

            rs.close();
            stmt.close();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    /**
     * To check the value of the entered numberOfShares if contain only digits or not
     */
    public static boolean isNumber(String number) {
        /* To check the entered number of shares, that it consists of
           only digits
         */
        try {
            int temp = Integer.parseInt(number);
            if (number.matches("\\d+") && temp > 0) return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
