/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 4-6-2021  6:01 PM
 */

package Controllers;

import DataBaseClasses.BillDetails;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BillDetailsController {

    @FXML // fx:id="billTableView"
    private TableView<BillDetails> billTableView; // Value injected by FXMLLoader

    @FXML // fx:id="cmCodeProducts"
    private TableColumn<BillDetails,String> cmCodeProducts; // Value injected by FXMLLoader

    @FXML // fx:id="cmPurchasingPrice"
    private TableColumn<BillDetails,String> cmPurchasingPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cmQuantity"
    private TableColumn<BillDetails,String> cmQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="txtBillId"
    private TextField txtBillId; // Value injected by FXMLLoader

    @FXML // fx:id="txtFrom"
    private TextField txtFrom; // Value injected by FXMLLoader

    @FXML // fx:id="textReleasseDate"
    private TextField textReleasseDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtTo"
    private TextField txtTo; // Value injected by FXMLLoader

    @FXML // fx:id="txtValueOfBill"
    private TextField txtValueOfBill; // Value injected by FXMLLoader

    @FXML // fx:id="txtDeposit"
    private TextField txtDeposit; // Value injected by FXMLLoader

    @FXML // fx:id="txtPatches"
    private TextField txtPatches; // Value injected by FXMLLoader

}
