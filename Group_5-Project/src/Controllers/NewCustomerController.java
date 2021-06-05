/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 31-5-2021  3:32 PM
 */

package Controllers;

import DataBaseClasses.Address;
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
import java.util.Calendar;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class NewCustomerController implements Initializable {

    @FXML // fx:id="txtStreetName"
    private TextField txtStreetName; // Value injected by FXMLLoader

    @FXML // fx:id="txtRegionName"
    private TextField txtRegionName; // Value injected by FXMLLoader

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="txtIDCard"
    private TextField txtIDCard; // Value injected by FXMLLoader

    @FXML // fx:id="combCity"
    private ComboBox<String> combCity; // Value injected by FXMLLoader

    @FXML // fx:id="combVillage"
    private ComboBox<String> combVillage; // Value injected by FXMLLoader

    @FXML // fx:id="txtPhoneNumber"
    private TextField txtPhoneNumber; // Value injected by FXMLLoader

    @FXML // fx:id="txtBuldingNumber"
    private TextField txtBuldingNumber; // Value injected by FXMLLoader

    @FXML // fx:id="hBox1"
    private HBox hBox1; // Value injected by FXMLLoader

    @FXML // fx:id="hBox2"
    private HBox hBox2; // Value injected by FXMLLoader

    @FXML // fx:id="hBox3"
    private HBox hBox3; // Value injected by FXMLLoader

    @FXML // fx:id="hBox4"
    private HBox hBox4; // Value injected by FXMLLoader

    @FXML // fx:id="hboxValueOFdeposit"
    private HBox hboxValueOFdeposit; // Value injected by FXMLLoader

    @FXML // fx:id="hboxProductCodeAndQu"
    private HBox hboxProductCodeAndQu; // Value injected by FXMLLoader

    @FXML // fx:id="txtParCode"
    private TextField txtParCode; // Value injected by FXMLLoader

    @FXML // fx:id="txtQuantityOf"
    private TextField txtQuantityOf; // Value injected by FXMLLoader

    @FXML // fx:id="txtdeposit"
    private TextField txtdeposit; // Value injected by FXMLLoader

    @FXML // fx:id="btAddCustomer"
    private Button btAddCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="brAddToBill"
    private Button btAddToBill; // Value injected by FXMLLoader

    @FXML // fx:id="btSave"
    private Button btSave; // Value injected by FXMLLoader

    @FXML // fx:id="brPrintBill"
    private Button brPrintBill; // Value injected by FXMLLoader

    private static Connection con;

    private PreparedStatement psCustomerBill;

    private static int branchID, employeeID, storageID, customerBillID, valueOfBill;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
            con = connection.connectSbitanyDB();

            assert con != null;
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT C.cityName From City C ");
            while (rs1.next()) combCity.getItems().add(rs1.getString(1));

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }


    public void handleCombCity() {
        combVillage.getItems().clear();
        switch (combCity.getValue()) {
            case "Bethlahem" -> fill(combVillage, Address.bethlahem);
            case "Hebron" -> fill(combVillage, Address.hebron);
            case "Jenen" -> fill(combVillage, Address.jenen);
            case "Jericho" -> fill(combVillage, Address.jericho);
            case "Nablus" -> fill(combVillage, Address.nablus);
            case "Qalqilya" -> fill(combVillage, Address.qalqilya);
            case "Ramallah" -> fill(combVillage, Address.ramallah);
            case "Salfit" -> fill(combVillage, Address.salfit);
            case "Tubas" -> fill(combVillage, Address.tubas);
            default -> fill(combVillage, Address.tulkarm);
        }
    }


    // fill comboBox villages Name
    private void fill(ComboBox<String> comboBox, String[] Village) {
        for (String s : Village) comboBox.getItems().add(s);
    }


    // actions in the button add customer
    public void handleBtAddCustomer() {

        if (txtName.getText().trim().isEmpty()) { // get the customer name
            Message.displayMassage("Warning", "Please Enter the name ");
            return;
        }

        if (txtIDCard.getText().trim().isEmpty()) { // get the customer personal ID
            Message.displayMassage("Warning", "Please enter the personal ID ");
            return;
        } else if (!Methods.isNumber(txtIDCard.getText().trim())) {
            Message.displayMassage("Warning", "Please enter valid personal ID ");
            return;
        }

        if (txtPhoneNumber.getText().trim().isEmpty()) { // get the customer phone
            Message.displayMassage("Warning", "Please enter the phone number ");
            return;
        } else if (!Methods.isNumber(txtPhoneNumber.getText().trim())) {
            Message.displayMassage("Warning", "Please enter valid phone number ");
            return;
        }

        if (combCity.getValue() == null) { // get the customer city
            Message.displayMassage("Warning", "Please chose the city ");
            return;
        }

        String buildingNumber;
        if (txtBuldingNumber.getText().trim().isEmpty()) {
            buildingNumber = null;
        } else if (Methods.isNumber(txtBuldingNumber.getText().trim())) {
            buildingNumber = txtBuldingNumber.getText().trim();
        } else {
            Message.displayMassage("Warning", "Please enter valid building number ");
            return;
        }

        try {
            // find customer if exist
            Statement stmt = con.createStatement();
            ResultSet getPersonalID = stmt.executeQuery("select C.customerID From  customer C  where C.cardID= " + Integer.parseInt(txtIDCard.getText().trim()));
            boolean isCustomerExist = getPersonalID.next();

            int cityID;

            // find city ID
            Statement stmt2 = con.createStatement();
            ResultSet getCityID = stmt2.executeQuery("select C.cityID From City C where C.CityName='" + combCity.getValue() + "'");
            getCityID.next();
            cityID = Integer.parseInt(getCityID.getString(1));

            String villageID;
            if (combVillage.getValue() == null) {
                villageID = null;
            } else {

                Statement stmt3 = con.createStatement(); // get the village id
                ResultSet getVillageID = stmt3.executeQuery("SELECT V.VillageID From Village V where V.cityID = " + cityID + " and V.villageName='" + combVillage.getValue() + "'");
                boolean result = getVillageID.next();

                if (!result) { // village does not exist

                    String insertNewVillage = "INSERT INTO village(villageName, cityID) values(?, ?)";
                    PreparedStatement psInsertVillage = con.prepareStatement(insertNewVillage);
                    psInsertVillage.setString(1, combVillage.getValue().trim());
                    psInsertVillage.setInt(2, cityID);
                    psInsertVillage.executeUpdate();

                    Statement statement = con.createStatement(); // get the id for the new village
                    ResultSet set = statement.executeQuery("SELECT V.villageID from village V where V.villageID=(Select max(V.villageID) from village V )");
                    set.next();
                    villageID = set.getString(1).trim();

                } else {
                    villageID = getVillageID.getString(1).trim();
                }
            }

            PreparedStatement psCustomer;

            if (!isCustomerExist) {// this customer is not exist and insert this customer to the system
                String sqlInsert = "insert into Customer(customerName," + "cardID,cityId,phone,villageID," +
                        "regionName,streetName, bulldingNumber) values(?,?,?,?,?,?,?,?)";
                psCustomer = con.prepareStatement(sqlInsert);

            } else { // // this customer is exist, and update his info
                String sqlUpdate = "UPDATE Customer " + "SET CustomerName = ?, cardID=?, cityId=?, phone=?,villageID=? ,regionName=?,streetName=? ,bulldingNumber=?" + " where cardID=" + Integer.parseInt(txtIDCard.getText().trim());
                psCustomer = con.prepareStatement(sqlUpdate);
            }

            psCustomer.setString(1, txtName.getText().trim()); // set the customer name
            psCustomer.setInt(2, Integer.parseInt(txtIDCard.getText().trim())); // set the personal id for this customer
            psCustomer.setInt(3, cityID);// set the city id fot this customer
            psCustomer.setString(4, txtPhoneNumber.getText().trim());// set the phone number for this customer

            if (villageID == null) { // no village name entered
                psCustomer.setNull(5, Types.NULL);
            } else { // the village name is entered
                psCustomer.setInt(5, Integer.parseInt(villageID));
            }

            if (txtRegionName.getText().trim().isEmpty()) { // no region name entered
                psCustomer.setNull(6, Types.NULL);
            } else { // the region name is entered
                psCustomer.setString(6, txtRegionName.getText().trim());
            }

            if (txtStreetName.getText().trim().isEmpty()) { // no street name entered
                psCustomer.setNull(7, Types.NULL);
            } else { // the street name entered
                psCustomer.setString(7, txtStreetName.getText().trim());
            }

            if (buildingNumber == null) { // no building number entered
                psCustomer.setNull(8, Types.NULL);
            } else { // the bulding number entered
                psCustomer.setInt(8, Integer.parseInt(buildingNumber));
            }
            psCustomer.executeUpdate();

            Statement stmt4 = con.createStatement();
            ResultSet getCustomerID = stmt4.executeQuery("select C.customerID From  customer C  where C.cardID= " + txtIDCard.getText().trim());
            getCustomerID.next();

            psCustomerBill = con.prepareStatement("insert into CustomerBill(orederAt," +
                    "valueOfBill, customerID, BranchID, EmployeeID, deposit, patches) " + "values(?,?,?,?,?,?,?)");
            Calendar calendar = Calendar.getInstance();
            psCustomerBill.setDate(1, new java.sql.Date(calendar.getTime().getTime()));
            psCustomerBill.setInt(2, 0); // and we will update it later
            psCustomerBill.setInt(3, Integer.parseInt(getCustomerID.getString(1)));
            psCustomerBill.setInt(4, branchID);
            psCustomerBill.setInt(5, employeeID);
            psCustomerBill.setInt(6, 0);// and we will update it later
            psCustomerBill.setInt(7, 0);// and we will update it later
            psCustomerBill.executeUpdate();

            Statement stmt5 = con.createStatement(); // get the CustomerBillID of the new bill
            ResultSet getCustomerBillID = stmt5.executeQuery("select C.CustomerBillID from customerBill C where CustomerBillID=(Select max(C.customerBillID) from customerBill C)");
            getCustomerBillID.next();
            // save the CustomerBillID to use it in customer bill details
            customerBillID = Integer.parseInt(getCustomerBillID.getString(1));

            // set controller visible
            this.hBox1.setDisable(true);
            this.hBox2.setDisable(true);
            this.hBox3.setDisable(true);
            this.hBox4.setDisable(true);
            this.btAddCustomer.setDisable(true);
            this.hboxProductCodeAndQu.setVisible(true);
            this.btAddToBill.setVisible(true);

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    // actions in the button add to bill
    public void handleBtAddToBill() {

        // get the par code
        if (txtParCode.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please enter the par code of the product");
            return;
        } else if (!Methods.isNumber(txtParCode.getText().trim())) {
            Message.displayMassage("Warning", "Please enter valid par code of the product");
            return;
        }
        try {

            int parCode = Integer.parseInt(txtParCode.getText().trim());
            int requiredQuantity = Integer.parseInt(txtQuantityOf.getText().trim());
            int remainingQuantity;

            Statement stmt5 = con.createStatement();
            ResultSet getProductCodeAndSellPrice = stmt5.executeQuery("Select P.productCode, P.sellingPrice From product P where P.parCode=" + parCode);
            boolean isProductExist = getProductCodeAndSellPrice.next();

            int productCode, sellPrice;

            if (!isProductExist) {
                Message.displayMassage("Warning", "This product does not exist");
                return;
            } else {
                productCode = Integer.parseInt(getProductCodeAndSellPrice.getString(1).trim());
                sellPrice = Integer.parseInt(getProductCodeAndSellPrice.getString(2).trim());
            }

            Statement stmt = con.createStatement(); // get available quantity
            ResultSet getQuantity = stmt.executeQuery("select S.productQuantity From storedProducts S  where S.StorageID= " + storageID + " and S.productCode= " + productCode);
            boolean isProductAvailable = getQuantity.next();

            if (!isProductAvailable) { // check if the product is available or not
                Message.displayMassage("Warning", "This product is not available in this branch");
                return;
            }
            int availableQuantity = Integer.parseInt(getQuantity.getString(1));


            if (requiredQuantity < availableQuantity) { // Quantity required is less than available
                remainingQuantity = availableQuantity - requiredQuantity;
                psCustomerBill = con.prepareStatement("Update storedProducts S set S.productQuantity=" + remainingQuantity + " where S.StorageID= " + storageID + " and S.productCode= " + productCode);
            } else {
                psCustomerBill = con.prepareStatement("DELETE from storedProducts S where S.StorageID= " + storageID + " and S.productCode= " + productCode);
            }
            psCustomerBill.executeUpdate();

            PreparedStatement psCustomerBillDetails = con.prepareStatement("INSERT into CustomerBillDetails(CustomerBillID," +
                    "ProductCode,SellingPrice,Quantity ) " + "values(?,?,?,?)");


            // set customer bill id
            psCustomerBillDetails.setInt(1, customerBillID);
            // set the product code
            psCustomerBillDetails.setInt(2, productCode);
            // set the selling price
            psCustomerBillDetails.setInt(3, sellPrice);
            // set the quantity
            psCustomerBillDetails.setInt(4, requiredQuantity);
            psCustomerBillDetails.executeUpdate();

            this.txtParCode.clear();
            this.txtQuantityOf.clear();
            this.btSave.setVisible(true);

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }


    // actions in the button save and calculate the bill
    public void saveAndCalculateTheBill() {
        hboxProductCodeAndQu.setDisable(true);
        btAddToBill.setDisable(true);
        this.btSave.setVisible(false);
        try {
            String getValueOfBill = "SELECT SUM(C.sellingPrice * C.quantity) from customerbilldetails C where C.customerBillID=" + customerBillID;
            Statement stmtValueOfBill = con.createStatement();
            ResultSet resultValueOfBill = stmtValueOfBill.executeQuery(getValueOfBill);
            resultValueOfBill.next();

            valueOfBill = Integer.parseInt(resultValueOfBill.getString(1));
            Message.displayMassage("Value of bill", "The value of this bill is " + valueOfBill);
            hboxValueOFdeposit.setVisible(true);
            this.brPrintBill.setVisible(true);

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }

    public void handleBtPrintBill() {
        int paidValue;

        if (txtdeposit.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please enter the paid value");
            return;
        } else if (!Methods.isNumber(txtdeposit.getText().trim())) {
            Message.displayMassage("Warning", "Please enter valid paid value");
            return;
        }
        paidValue = Integer.parseInt(txtdeposit.getText().trim());

        int patches = valueOfBill - paidValue;

        try {
            String sqlUpdate = "UPDATE CustomerBill C " + "SET C.valueOfBill=?, C.deposit=? ,C.patches=?  where C.customerBillID=" + customerBillID;
            psCustomerBill = con.prepareStatement(sqlUpdate);
            psCustomerBill.setInt(1, valueOfBill);
            psCustomerBill.setInt(2, paidValue);
            psCustomerBill.setInt(3, patches);
            psCustomerBill.executeUpdate();
            try {
                BillDetailsController.setTypeOfBill(true);
                BillDetailsController.setBillID(customerBillID);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/BillDetails.fxml")));
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Bill Details");
                window.setScene(new Scene(root));
                window.setResizable(false);
                window.show();
                this.setDefault();
            } catch (IOException exception) {
                Message.displayMassage("Warning", exception.getMessage());
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    private void setDefault() {
        this.txtName.clear();
        this.txtIDCard.clear();
        this.txtPhoneNumber.clear();
        this.txtStreetName.clear();
        this.txtBuldingNumber.clear();
        this.txtRegionName.clear();
        this.txtParCode.clear();
        this.txtQuantityOf.clear();
        this.txtdeposit.clear();

        this.hBox1.setDisable(false);
        this.hBox2.setDisable(false);
        this.hBox3.setDisable(false);
        this.hBox4.setDisable(false);
        this.btAddCustomer.setDisable(false);
        this.hboxProductCodeAndQu.setVisible(false);
        this.hboxProductCodeAndQu.setDisable(false);
        this.btSave.setVisible(false);
        this.btAddToBill.setDisable(false);
        this.btAddToBill.setVisible(false);
        this.brPrintBill.setVisible(false);
        this.hboxValueOFdeposit.setVisible(false);
    }

    public static void setInfo(int branchID, int employeeID) {
        NewCustomerController.branchID = branchID;
        NewCustomerController.employeeID = employeeID;
        try {
            Statement stmtGetStorageID = con.createStatement();
            ResultSet getStorageID = stmtGetStorageID.executeQuery("SELECT S.storageID from storages S where S.branchID = " + branchID);
            getStorageID.next();
            NewCustomerController.storageID = Integer.parseInt(getStorageID.getString(1));
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
