/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 3-6-2021  11:57 PM
 */

package Controllers;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NewProductController implements Initializable {

    @FXML // fx:id="txtPurchasingPrice"
    private TextField txtPurchasingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="txtSellingPrice"
    private TextField txtSellingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="txtProductName"
    private TextField txtProductName; // Value injected by FXMLLoader

    @FXML // fx:id="txtManufacturerCompany"
    private TextField txtManufacturerCompany; // Value injected by FXMLLoader

    @FXML // fx:id="combCategories"
    private ComboBox<String> combCategories; // Value injected by FXMLLoader

    @FXML // fx:id="txtCategoriesName"
    private TextField txtCategoriesName; // Value injected by FXMLLoader

    @FXML // fx:id="txtParCode"
    private TextField txtParCode; // Value injected by FXMLLoader

    @FXML // fx:id="txtDescriptions"
    private TextField txtDescriptions; // Value injected by FXMLLoader

    private Connection con;
    PreparedStatement psProduct, psCategories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.fillCategoriesName();
    }

    public void btHandleInsertNewProduct() {
        if (txtProductName.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please enter the product name");
            return;
        }
        if (txtManufacturerCompany.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please enter the manufacturer company");
            return;
        }

        if (txtPurchasingPrice.getText().trim().isEmpty() || !Methods.isNumber(txtPurchasingPrice.getText().trim())) {
            Message.displayMassage("Warning", "Please enter a valid purchasing price");
            return;
        }

        if (txtSellingPrice.getText().trim().isEmpty() || !Methods.isNumber(txtSellingPrice.getText().trim())) {
            Message.displayMassage("Warning", "Please enter a valid selling price");
            return;
        }

        if (txtParCode.getText().trim().isEmpty() || !Methods.isNumber(txtParCode.getText().trim())) {
            Message.displayMassage("Warning", "Please enter a valid product code");
            return;
        }
        if (combCategories == null) {
            Message.displayMassage("Warning", "Please select the categories");
            return;
        }

        try {

            Statement checkParCodeStatement = con.createStatement();
            ResultSet checkParCode = checkParCodeStatement.executeQuery("select P.parCode From  product P where P.parCode= " + Integer.parseInt(txtParCode.getText().trim()));
            boolean isParCodeExist = checkParCode.next();

            if (isParCodeExist) {

                String sqlUpdate = "UPDATE Product " + "SET productName = ?, manufacturerCompany=?, purchasingPrice=?, sellingPrice=?, parCode=?,descriptions=?" + " where parCode=" + Integer.parseInt(txtParCode.getText().trim());
                psProduct = con.prepareStatement(sqlUpdate);
                psProduct.setString(1, txtProductName.getText().trim());
                psProduct.setString(2, txtManufacturerCompany.getText().trim());
                psProduct.setInt(3, Integer.parseInt(txtPurchasingPrice.getText().trim()));
                psProduct.setInt(4, Integer.parseInt(txtSellingPrice.getText().trim()));
                psProduct.setInt(5, Integer.parseInt(txtParCode.getText().trim()));
                if (txtDescriptions.getText().trim().isEmpty())
                    psProduct.setNull(6, Types.NULL);
                else
                    psProduct.setString(6, txtDescriptions.getText().trim());

            } else {
                psProduct = con.prepareStatement("insert into Product (productName," +
                        "manufacturerCompany,purchasingPrice,sellingPrice,catogresId," +
                        "parCode,descriptions) " + "values(?,?,?,?,?,?,?)");

                psProduct.setString(1, txtProductName.getText().trim());
                psProduct.setString(2, txtManufacturerCompany.getText().trim());
                psProduct.setInt(3, Integer.parseInt(txtPurchasingPrice.getText().trim()));
                psProduct.setInt(4, Integer.parseInt(txtSellingPrice.getText().trim()));
                Statement getCategoriesIDStatement = con.createStatement();
                ResultSet getCategoriesID;
                if (!combCategories.getValue().equals("Insert new categories")) {
                    getCategoriesID = getCategoriesIDStatement.executeQuery("select C.categoriesId From categories C where C.catogresName= '" + combCategories.getValue() + "'");
                } else {
                    psCategories = con.prepareStatement("insert into categories (catogresName) " + "values(?)");
                    psCategories.setString(1, txtCategoriesName.getText().trim());
                    psCategories.executeUpdate();
                    getCategoriesID = getCategoriesIDStatement.executeQuery("select C.categoriesId From categories C where C.catogresName='" + txtCategoriesName.getText().trim() + "'");
                    Message.displayMassage("Successfully","The new category has been added successfully");
                }
                getCategoriesID.next();
                psProduct.setInt(5, Integer.parseInt(getCategoriesID.getString(1)));
                psProduct.setInt(6, Integer.parseInt(txtParCode.getText().trim()));

                if (txtDescriptions.getText().trim().isEmpty())
                    psProduct.setNull(7, Types.NULL);
                else psProduct.setString(7, txtDescriptions.getText().trim());
            }

            psProduct.executeUpdate();

            Statement stmtProductID = con.createStatement();
            ResultSet resultProductID = stmtProductID.executeQuery("SELECT P.productCode from product P where P.productCode = (SELECT MAX(P1.ProductCode) from product P1)");
            resultProductID.next();
            Message.displayMassage("Successfully", "The product code for the new product is " + resultProductID.getString(1));
            this.txtProductName.clear();
            this.txtDescriptions.clear();
            this.txtParCode.clear();
            this.txtManufacturerCompany.clear();
            this.txtPurchasingPrice.clear();
            this.txtSellingPrice.clear();
            this.txtCategoriesName.clear();
            this.txtCategoriesName.setVisible(false);
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }


    public void handleComboCatogres() {
        if (this.combCategories.getValue().equals("Insert new categories")) {
            this.txtCategoriesName.setVisible(true);
        } else {
            this.txtCategoriesName.setVisible(false);
            this.txtCategoriesName.clear();
        }
    }

    private void fillCategoriesName() {
        try {
            String sqlCategoriesName = "SELECT C.catogresName from categories C";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCategoriesName);
            while (rs.next()) {
                this.combCategories.getItems().add(rs.getString(1).trim());
            }
            stmt.close();
            rs.close();
            this.combCategories.getItems().add("Insert new categories");
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
