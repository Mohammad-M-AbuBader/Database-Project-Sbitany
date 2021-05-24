/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 23-5-2021  12:30 AM
 */

package Controllers;

import DataBaseClasses.QuantityOf;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
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

    private Message message;

    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.cmStorageName.setCellValueFactory(new PropertyValueFactory<>("storageName"));
        this.cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.quantityTableView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

    }

    public void handleBtQuantity() {
        if (!this.txQuantityOf.getText().trim().isEmpty()) {

            if (!isNumber(this.txQuantityOf.getText().trim())) {
               this.message = new Message();
                message.displayMassage("Warning", " Product code is invalid ");
                this.txQuantityOf.clear();
                return;
            }

            try {
                String quantityOf = "SELECT B.branchName, S.productQuantity FROM storedproducts S, branch B where S.productCode= " + Integer.parseInt(this.txQuantityOf.getText().trim()) + " And S.storageID=B.branchID order by S.productQuantity DESC";
                assert con != null;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(quantityOf);

              /*  if (!rs.next()) {
                    Message message = new Message();
                    message.displayMassage("Warning", this.txQuantityOf.getText() + " Does not exist ");
                    this.txQuantityOf.clear();
                    return;
                }*/
                while (rs.next()) {
                    QuantityOf quantity = new QuantityOf(rs.getString(1), rs.getString(2));
                    this.quantityTableView.getItems().add(quantity);
                }

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

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
