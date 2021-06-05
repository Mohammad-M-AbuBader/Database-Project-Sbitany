/**
 * @autor: Ameer Eleyan
 * 1191076
 * At: 30/5/2021  8:33 PM
 */

package Controllers;

import DataBaseClasses.Product;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @FXML // fx:id="lblTotalProducts"
    private Label lblTotalProducts; // Value injected by FXMLLoader

    private Connection con;

    private static int branchID;

    public static void setBranchID(int branchID) {
        ProductForBranchesController.branchID = branchID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        cmProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        cmProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cmManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturerCompany"));
        cmSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        cmCategoriesName.setCellValueFactory(new PropertyValueFactory<>("categoriesName"));
        cmParCode.setCellValueFactory(new PropertyValueFactory<>("parCode"));
        cmDescripition.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
        this.execute(" ");
    }

    @FXML
    void handleBtQuantityOf() {
        try {
            Parent root = load(Objects.requireNonNull(getClass().getResource("../FXML/QuantityOf.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Quantity Of:");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    @FXML
    void handleBtRefresh() {
        this.execute(" ");
    }

    private void execute(String str) {
        this.txProductCode.clear();
        this.tvProducts.getItems().clear();
        try {

            String getProducts = "SELECT * from product P " + str;
            String getTotalProducts = "SELECT COUNT(*) from product P " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getProducts);
            boolean flag = true;
            while (rs.next()) {
                Product product = new Product();

                product.setProductCode(rs.getString(1));
                product.setProductName(rs.getString(2));
                product.setManufacturerCompany(rs.getString(3));
                product.setSellingPrice(rs.getString(5));
                product.setParCode(rs.getString(7));

                if (rs.getString(8) != null) product.setDescriptions(rs.getString(8));
                else product.setDescriptions("");

                String categoriesName = rs.getString(6).trim();

                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT C.catogresName from categories C where C.categoriesId=" + Integer.parseInt(categoriesName));
                rs2.next();

                product.setCategoriesName(rs2.getString(1));
                this.tvProducts.getItems().add(product);
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning", "Does not exist");
                return;
            }
            rs = stmt.executeQuery(getTotalProducts);
            rs.next();
            this.lblTotalProducts.setText(rs.getString(1));

            rs.close();
            stmt.close();

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }


    @FXML
    void handleBtMyProduct() {
        try {

            Statement stmtGetStorageID = con.createStatement();
            ResultSet getStorageID = stmtGetStorageID.executeQuery("SELECT S.storageID from storages S where S.branchID = " + branchID);
            getStorageID.next();

            int storageID = Integer.parseInt(getStorageID.getString(1));
            OurProductController.setStorageID(storageID);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/OurProduct.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Our Products In This Branch");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (SQLException | IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }

    }

    @FXML
    void handleBtSearch() {
        if (!this.txProductCode.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txProductCode.getText().trim())) {
                Message.displayMassage("Warning", " Product code is invalid ");
                this.txProductCode.clear();
                return;
            }
            this.execute(" where P.productCode=" + Integer.parseInt(this.txProductCode.getText().trim()));
        }
    }

}

