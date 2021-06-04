/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 23-5-2021  12:30 AM
 */

package Controllers;

import DataBaseClasses.QuantityOf;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class QuantityOfController implements Initializable {

    @FXML // fx:id="quantityTableView"
    private TableView<QuantityOf> quantityTableView; // Value injected by FXMLLoader

    @FXML // fx:id="cmStorageName"
    private TableColumn<QuantityOf, String> cmStorageName; // Value injected by FXMLLoader

    @FXML // fx:id="cmQuantity"
    private TableColumn<QuantityOf, String> cmQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="txQuantityOf"
    private TextField txQuantityOf; // Value injected by FXMLLoader

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.cmStorageName.setCellValueFactory(new PropertyValueFactory<>("storageName"));
        this.cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    }

    public void handleBtQuantity() {
        if (!this.txQuantityOf.getText().trim().isEmpty()) {

            if (!Methods.isNumber(this.txQuantityOf.getText().trim())) {
                Message.displayMassage("Warning", " Product code is invalid ");
                this.txQuantityOf.clear();
                return;
            }
            this.quantityTableView.getItems().clear();

            try {
                String quantityOf = "SELECT B.branchName, S.productQuantity FROM storedproducts S, branch B where S.productCode= " +
                        Integer.parseInt(this.txQuantityOf.getText().trim()) + " And S.storageID=B.branchID order by S.productQuantity DESC";
                assert con != null;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(quantityOf);
                boolean isExist = rs.next();
                if (isExist) {
                    QuantityOf quantity = new QuantityOf(rs.getString(1), rs.getString(2));
                    this.quantityTableView.getItems().add(quantity);
                } else {
                    Message.displayMassage("Warning", " This product is not currently available ");
                    return;
                }
                while (rs.next()) {
                    QuantityOf quantity = new QuantityOf(rs.getString(1), rs.getString(2));
                    this.quantityTableView.getItems().add(quantity);
                }

            } catch (SQLException sqlException) {
                Message.displayMassage("Warning", sqlException.getMessage());
            }
        }
    }

}
