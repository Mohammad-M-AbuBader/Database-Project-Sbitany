/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 23-5-2021  9:06 PM
 */

package Controllers;

import DataBaseClasses.Product;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        cmCodeProducts.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        cmNameProducts.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cmManufacturerProduct.setCellValueFactory(new PropertyValueFactory<>("manufacturerCompany"));
        cmPurchasingPrice.setCellValueFactory(new PropertyValueFactory<>("purchasingPrice"));
        cmSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        cmCategoriesName.setCellValueFactory(new PropertyValueFactory<>("categoriesName"));
        cmParCode.setCellValueFactory(new PropertyValueFactory<>("parCode"));
        cmDescription.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
        this.execute(" ");
    }


    public void handleBtSearch() {
        if (!this.txSearch.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txSearch.getText().trim())) {
                Message.displayMassage("Warning", " Product code is invalid ");
                this.txSearch.clear();
                return;
            }
            this.execute(" where P.productCode=" + Integer.parseInt(this.txSearch.getText().trim()));
        }
    }

    public void handleBtQuantity() {
        try {
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

    public void handleBtRefresh() {
        this.execute(" ");
    }

    public void execute(String str) {
        this.txSearch.clear();
        this.tableProducts.getItems().clear();
        try {

            String getProducts = "SELECT * from product P " + str;
            String getTotalProducts = "SELECT COUNT(*) from product P " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getProducts);
            boolean flag = true;
            while (rs.next()) {
                Product product = new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));

                String categoriesName = rs.getString(6).trim();

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT C.catogresName from categories C where C.categoriesId=" + Integer.parseInt(categoriesName));
                rs2.next();

                product.setCategoriesName(rs2.getString(1));
                this.tableProducts.getItems().add(product);
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning", "Does not exist");
                return;
            }
            rs = stmt.executeQuery(getTotalProducts);
            rs.next();
            this.txTotalProducts.setText(rs.getString(1));

            rs.close();
            stmt.close();

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
