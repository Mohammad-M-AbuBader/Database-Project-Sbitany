/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 5-6-2021  6:27 PM
 */
package Controllers;

import DataBaseClasses.OurProduct;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class OurProductController implements Initializable {

    @FXML // fx:id="tableBranchProduct"
    private TableView<OurProduct> tableBranchProduct; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductCode"
    private TableColumn<OurProduct, String> cmProductCode; // Value injected by FXMLLoader

    @FXML // fx:id="cmParCode"
    private TableColumn<OurProduct, String> cmParCode; // Value injected by FXMLLoader

    @FXML // fx:id="cmQuantity"
    private TableColumn<OurProduct, String> cmQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="cmProductName"
    private TableColumn<OurProduct, String> cmProductName; // Value injected by FXMLLoader

    public static int storageID;

    public static void setStorageID(int storageID) {
        OurProductController.storageID = storageID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        Connection con = connection.connectSbitanyDB();
        cmProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        cmProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cmParCode.setCellValueFactory(new PropertyValueFactory<>("parCode"));
        cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        try {
            // get products for specific branch
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from storedproducts S where S.storageID=" + storageID+" order by S.productQuantity DESC");

            // get par code for the products
            Statement getParCodeAndNameStatement;
            ResultSet getParCodeAndName;

            while (rs.next()) {
                getParCodeAndNameStatement = con.createStatement();
                getParCodeAndName = getParCodeAndNameStatement.executeQuery("select  P.parCode,P.ProductName from product P where P.productCode=" + Integer.parseInt(rs.getString(2).trim()));
                getParCodeAndName.next();

                OurProduct ourProduct = new OurProduct();
                ourProduct.setParCode(getParCodeAndName.getString(1));
                ourProduct.setProductName(getParCodeAndName.getString(2));
                ourProduct.setProductCode(rs.getString(2));
                ourProduct.setQuantity(rs.getString(3));
                this.tableBranchProduct.getItems().add(ourProduct);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }
}
