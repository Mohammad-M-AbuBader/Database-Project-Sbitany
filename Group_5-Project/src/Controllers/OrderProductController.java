/**
 * @autor: Ameer Eleyan
 * 1191076
 * At: 30/5/2021  7:28 PM
 */

package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class OrderProductController {

    @FXML // fx:id="brOrder"
    private Button brOrder; // Value injected by FXMLLoader

    @FXML // fx:id="txtCodeProduct"
    private TextField txtCodeProduct; // Value injected by FXMLLoader

    @FXML // fx:id="txtQuantityOf"
    private TextField txtQuantityOf; // Value injected by FXMLLoader

    @FXML // fx:id="txtEmployeeID"
    private TextField txtEmployeeID; // Value injected by FXMLLoader

    @FXML // fx:id="cmbStorageName"
    private ComboBox<String> cmbStorageName; // Value injected by FXMLLoader

    @FXML
    void cbxBranchStorage() {

    }

    @FXML
    void handleBtOrder() {

    }

}
