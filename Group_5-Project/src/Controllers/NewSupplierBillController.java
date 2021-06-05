/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 6-3-2021  11:28 AM
 */
package Controllers;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewSupplierBillController implements Initializable {

    @FXML // fx:id="combSuppliers"
    private ComboBox<String> combSuppliers; // Value injected by FXMLLoader

    @FXML // fx:id="hboxFaxAndPhone"
    private HBox hboxFaxAndPhone; // Value injected by FXMLLoader

    @FXML // fx:id="txtFaxNumber"
    private TextField txtFaxNumber; // Value injected by FXMLLoader

    @FXML // fx:id="txtPhoneNumber"
    private TextField txtPhoneNumber; // Value injected by FXMLLoader

    @FXML // fx:id="hboxNameAndEmail"
    private HBox hboxNameAndEmail; // Value injected by FXMLLoader

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="txtEmail"
    private TextField txtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="brCreateBill"
    private Button btCreateBill; // Value injected by FXMLLoader

    @FXML // fx:id="hboxProductCodeAndQu"
    private HBox hboxProductCodeAndQu; // Value injected by FXMLLoader

    @FXML // fx:id="txtProductCode"
    private TextField txtProductCode; // Value injected by FXMLLoader

    @FXML // fx:id="txtQuantityOf"
    private TextField txtQuantityOf; // Value injected by FXMLLoader

    @FXML // fx:id="brAddToBill"
    private Button btAddToBill; // Value injected by FXMLLoader

    @FXML // fx:id="hboxValueOFDeposit"
    private HBox hboxValueOFDeposit; // Value injected by FXMLLoader

    @FXML // fx:id="txtDeposit"
    private TextField txtDeposit; // Value injected by FXMLLoader

    @FXML // fx:id="brPrintBill"
    private Button btPrintBill; // Value injected by FXMLLoader

    @FXML // fx:id="btAddNewProduct"
    private Button btAddNewProduct; // Value injected by FXMLLoader

    @FXML // fx:id="btSaveAndCalculate"
    private Button btSaveAndCalculate; // Value injected by FXMLLoader

    private Connection con;
    private PreparedStatement psSupplierBill;
    private int supplierBillID, valueOfBill, supplierID;
    private boolean flag = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.fillCombSuppliers();
    }

    public void btHandleCreateBill() {

        if (txtName.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please enter the name");
            return;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please enter the email");
            return;
        }

        if (txtPhoneNumber.getText().trim().isEmpty() || !Methods.isNumber(txtPhoneNumber.getText().trim())) {
            Message.displayMassage("Warning", "Please enter  a valid phone number");
            return;
        }
        if (txtFaxNumber.getText().trim().isEmpty() || !Methods.isNumber(txtFaxNumber.getText().trim())) {
            Message.displayMassage("Warning", "Please enter the fax number");
            return;
        }

        try {

            if (!flag) {
                psSupplierBill = con.prepareStatement("insert into Supplier (supplierName," +
                        "supplierPhone,supplierEmail,supplierFax" + ") " +
                        "values(?,?,?,?)");

                psSupplierBill.setString(1, txtName.getText().trim());
                psSupplierBill.setInt(2, Integer.parseInt(txtPhoneNumber.getText().trim()));
                psSupplierBill.setString(3, txtEmail.getText().trim());
                if (txtFaxNumber.getText().trim().isEmpty())
                    psSupplierBill.setNull(4, Types.NULL);
                else
                    psSupplierBill.setInt(4, Integer.parseInt(txtFaxNumber.getText().trim()));

                psSupplierBill.executeUpdate();

                Statement getSupplierIDStatement = con.createStatement();
                ResultSet getSupplierID = getSupplierIDStatement.executeQuery("select S.supplierID from supplier S  where supplierID=(Select max(supplierID) from supplier)");
                getSupplierID.next();
                supplierID = Integer.parseInt(getSupplierID.getString(1));
            }

            psSupplierBill = con.prepareStatement("insert into supplierbill(orederAt,supplierID,valueOfBill,deposit,patches) " +
                    "values(?,?,?,?,?)");
            Calendar calendar = Calendar.getInstance();
            psSupplierBill.setDate(1, new Date(calendar.getTime().getTime()));
            psSupplierBill.setInt(2, supplierID);
            psSupplierBill.setInt(3, 0);
            psSupplierBill.setInt(4, 0);
            psSupplierBill.setInt(5, 0);
            psSupplierBill.executeUpdate();

            Statement getSupplierBillIDStatement = con.createStatement();
            ResultSet getSupplierBillID = getSupplierBillIDStatement.executeQuery("select  S.SupplierBillID from supplierbill S  where SupplierBillID=(Select max(SupplierBillID) from supplierbill)");
            getSupplierBillID.next();
            supplierBillID = Integer.parseInt(getSupplierBillID.getString(1));

            // set controller visible
            this.hboxNameAndEmail.setVisible(true);
            this.hboxFaxAndPhone.setVisible(true);
            this.hboxNameAndEmail.setDisable(true);
            this.hboxFaxAndPhone.setDisable(true);
            this.btCreateBill.setDisable(true);
            this.hboxProductCodeAndQu.setVisible(true);
            this.btAddToBill.setVisible(true);
            this.btAddNewProduct.setVisible(true);
            this.combSuppliers.setDisable(true);

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }

    public void btHandleAddToBill() {

        if (txtProductCode.getText().trim().isEmpty() || !Methods.isNumber(txtProductCode.getText().trim())) {
            Message.displayMassage("Warning", "Please enter a valid product code");
            return;
        }
        if (txtQuantityOf.getText().trim().isEmpty() || !Methods.isNumber(txtQuantityOf.getText().trim())) {
            Message.displayMassage("Warning", "Please enter a valid quantity");
            return;
        }

        try {
            Statement sqlGetProductCode = con.createStatement();
            ResultSet getProductCode = sqlGetProductCode.executeQuery("SELECT P.productCode from product P where P.productCode="
                    + Integer.parseInt(txtProductCode.getText().trim()));

            boolean isExist = getProductCode.next();
            if (!isExist) {
                Message.displayMassage("Warning", "This product does not exist, please add it from 'Add new product'");
                return;
            }

            Statement CheckProductQuantityStatement = con.createStatement();
            int mainStorageID = 1;
            ResultSet CheckProductQuantity = CheckProductQuantityStatement.executeQuery("select S.productQuantity From " +
                    " storedProducts S  where S.StorageID= " + mainStorageID + " and S.productCode= " + txtProductCode.getText().trim());
            boolean isProductExist = CheckProductQuantity.next();

            if (isProductExist) {
                int quantityIHave = Integer.parseInt(CheckProductQuantity.getString(1));
                String sqlUpdate = "UPDATE storedProducts S " + "SET S.productQuantity = ? where S.StorageID= " + mainStorageID + " and S.productCode=" + Integer.parseInt(txtProductCode.getText().trim());
                psSupplierBill = con.prepareStatement(sqlUpdate);
                psSupplierBill.setInt(1, (Integer.parseInt(txtQuantityOf.getText().trim())) + quantityIHave);
            } else {
                psSupplierBill = con.prepareStatement("insert into storedproducts(storageID," +
                        "productCode,productQuantity) " + "values(?,?,?)");
                psSupplierBill.setInt(1, mainStorageID);
                psSupplierBill.setInt(2, Integer.parseInt(txtProductCode.getText().trim()));
                psSupplierBill.setInt(3, Integer.parseInt(txtQuantityOf.getText().trim()));
            }
            psSupplierBill.executeUpdate();

            psSupplierBill = con.prepareStatement("insert into supplierbilldetails(SupplierBillID," +
                    "ProductCode,purchasingPrice,quantity ) " + "values(?,?,?,?)");
            psSupplierBill.setInt(1, supplierBillID);
            psSupplierBill.setInt(2, Integer.parseInt(txtProductCode.getText().trim()));

            Statement getPurchasingPriceStatement = con.createStatement();
            ResultSet getPurchasingPrice = getPurchasingPriceStatement.executeQuery("select P.purchasingPrice From product P where P.productCode= " + txtProductCode.getText().trim());
            getPurchasingPrice.next();
            int purchasingPrice = Integer.parseInt(getPurchasingPrice.getString(1));

            psSupplierBill.setInt(3, purchasingPrice);
            psSupplierBill.setInt(4, Integer.parseInt(txtQuantityOf.getText().trim()));

            psSupplierBill.executeUpdate();
            this.txtProductCode.clear();
            this.txtQuantityOf.clear();
            this.btSaveAndCalculate.setVisible(true);

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }

    public void handleCombSuppliers() {
        if (!this.combSuppliers.getValue().equals("Insert New Supplier")) {
            try {
                this.hboxNameAndEmail.setVisible(false);
                this.hboxFaxAndPhone.setVisible(false);
                Statement supplier = con.createStatement();
                ResultSet getSupplier = supplier.executeQuery("SELECT * from supplier S where S.supplierName='" + this.combSuppliers.getValue().trim() + "'");
                getSupplier.next();
                supplierID = Integer.parseInt(getSupplier.getString(1).trim());
                this.txtName.setText(getSupplier.getString(2));
                this.txtEmail.setText(getSupplier.getString(3));
                this.txtPhoneNumber.setText(getSupplier.getString(4));
                this.txtFaxNumber.setText(getSupplier.getString(5));
                this.flag = true;
            } catch (SQLException sqlException) {
                Message.displayMassage("Warning", sqlException.getMessage());
            }
        } else {
            this.flag = false;
            this.hboxNameAndEmail.setVisible(true);
            this.hboxFaxAndPhone.setVisible(true);
            this.txtName.clear();
            this.txtEmail.clear();
            this.txtPhoneNumber.clear();
            this.txtFaxNumber.clear();
        }
        this.btCreateBill.setVisible(true);
    }

    public void handleBtSave() {
        hboxProductCodeAndQu.setDisable(true);
        this.btAddToBill.setDisable(true);
        this.btSaveAndCalculate.setVisible(false);

        try {
            String getValueOfBill = "SELECT SUM(S.purchasingPrice*S.quantity) from supplierbilldetails S where S.SupplierBillID= " + supplierBillID;
            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery(getValueOfBill);
            resultValueOfBill.next();
            valueOfBill = Integer.parseInt(resultValueOfBill.getString(1));
            Message.displayMassage("Value of bill", "The value of this bill is " + valueOfBill);
            hboxValueOFDeposit.setVisible(true);
            this.btPrintBill.setVisible(true);
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }


    public void handleBtPrintBill() {
        int paidValue;
        if (this.txtDeposit.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please enter the paid value");
            return;
        } else if (!Methods.isNumber(this.txtDeposit.getText().trim())) {
            Message.displayMassage("Warning", "Please enter valid paid value");
            return;
        }
        paidValue = Integer.parseInt(this.txtDeposit.getText().trim());

        int patches = valueOfBill - paidValue;

        try {
            String sqlUpdate = "UPDATE SupplierBill S " + "SET S.valueOfBill=?, S.deposit=? ,S.patches=?  where S.supplierBillID=" + supplierBillID;
            psSupplierBill = con.prepareStatement(sqlUpdate);
            psSupplierBill.setInt(1, valueOfBill);
            psSupplierBill.setInt(2, paidValue);
            psSupplierBill.setInt(3, patches);
            psSupplierBill.executeUpdate();
            this.setDefault();
            try {
                BillDetailsController.setTypeOfBill(false);
                BillDetailsController.setBillID(supplierBillID);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/BillDetails.fxml")));
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Bill Details");
                window.setScene(new Scene(root));
                window.setResizable(false);
                window.show();
            } catch (IOException exception) {
                Message.displayMassage("Warning", exception.getMessage());
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }


    public void handleBtNewProduct() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/NewProduct.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Product");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    private void setDefault() {
        this.txtName.clear();
        this.txtProductCode.clear();
        this.txtQuantityOf.clear();
        this.txtDeposit.clear();
        this.txtEmail.clear();
        this.txtPhoneNumber.clear();
        this.txtFaxNumber.clear();
        this.btCreateBill.setVisible(false);
        this.hboxNameAndEmail.setVisible(false);
        this.hboxFaxAndPhone.setVisible(false);
        this.hboxNameAndEmail.setDisable(false);
        this.hboxFaxAndPhone.setDisable(false);
        this.btCreateBill.setDisable(false);
        this.hboxProductCodeAndQu.setVisible(false);
        this.hboxProductCodeAndQu.setDisable(false);
        this.btSaveAndCalculate.setVisible(false);
        this.btAddToBill.setDisable(false);
        this.btAddToBill.setVisible(false);
        this.btPrintBill.setVisible(false);
        this.btAddNewProduct.setVisible(false);
        this.hboxValueOFDeposit.setVisible(false);
        this.combSuppliers.setDisable(false);
    }

    private void fillCombSuppliers() {
        try {
            Statement suppliers = con.createStatement();
            ResultSet getSuppliers = suppliers.executeQuery("SELECT S.supplierName from supplier S ");
            while (getSuppliers.next())
                this.combSuppliers.getItems().add(getSuppliers.getString(1).trim());
            this.combSuppliers.getItems().add("Insert New Supplier");
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
