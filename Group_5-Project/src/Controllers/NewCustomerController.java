/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 31-5-2021  3:32 PM
 */

package Controllers;

import DataBaseClasses.Address;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Calendar;
import java.util.Date;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
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

    @FXML // fx:id="brCreateBill"
    private Button brCreateBill; // Value injected by FXMLLoader

    private Connection con;

    String villageName = combVillage.getValue();

    private PreparedStatement psCustomerBill;

    private static int numberOfQuantity = 0, branchID, employeeID, customerBillID, patches;

    private boolean status; // will be true if the new customer will be add or update his info

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
            con = connection.connectSbitanyDB();

            assert con != null;
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT C.cityName From City C ");
            while (rs1.next()) {
                combCity.getItems().add(rs1.getString(1));
            }

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }

    }


    public void handleCombCity() {
        switch (combCity.getValue()) {
            case "Bethlahem" -> fill(combVillage, Address.bethlahem);
            case "Hebron" -> fill(combVillage, Address.hebron);
            case "Jenen" -> fill(combVillage, Address.jenen);
            case "Jericho " -> fill(combVillage, Address.jericho);
            case "Nablus " -> fill(combVillage, Address.nablus);
            case "Qalqilya " -> fill(combVillage, Address.qalqilya);
            case "Ramallah " -> fill(combVillage, Address.ramallah);
            case "Salfit " -> fill(combVillage, Address.salfit);
            case "Tubas " -> fill(combVillage, Address.tubas);
            default -> fill(combVillage, Address.tulkarm);
        }
    }


    private void fill(ComboBox<String> comboBox, String[] Village) {
        for (String s : Village) comboBox.getItems().add(s);
    }

    public void handleBtCreateBill() {
        if (txtName.getText().trim().isEmpty()) {
            Message.displayMassage("Warning", "Please Enter the name ");
            return;
        }
        if (txtIDCard.getText().trim().isEmpty() && isNumber(txtIDCard.getText().trim())) {
            Message.displayMassage("Warning", "Please Enter the valid personal ID ");
            return;
        }
        if (txtPhoneNumber.getText().trim().isEmpty() && isNumber(txtPhoneNumber.getText().trim())) {
            Message.displayMassage("Warning", "Please Enter the valid phone number ");
            return;
        }
        if (combCity == null) {
            Message.displayMassage("Warning", "Please chose the city ");
            return;
        }
        String buildingNumber;
        if (!txtBuldingNumber.getText().trim().isEmpty() && isNumber(txtBuldingNumber.getText().trim())) {
            buildingNumber = txtBuldingNumber.getText().trim();
        } else if (txtBuldingNumber.getText().trim().isEmpty()) {
            buildingNumber = null;
        } else {
            Message.displayMassage("Warning", "Please enter valid building number ");
            return;
        }
        int cityID;
        int villageID;
        try {
            // find customer if exist
            Statement stmt = con.createStatement();
            ResultSet getPersonalID = stmt.executeQuery("select C.customerID From  customer C  where C.cardID= " + Integer.parseInt(txtIDCard.getText().trim()));
            getPersonalID.next();

            // find city ID
            Statement stmt2 = con.createStatement();
            ResultSet getCityID = stmt2.executeQuery("select C.cityID From City C where C.CityName='" + combCity.getValue() + "'");
            getCityID.next();
            cityID = Integer.parseInt(getCityID.getString(1));

            Statement stmt3 = con.createStatement(); // get the village id
            ResultSet getVillageID = stmt3.executeQuery("select V.VillageID From Village V ,City C where V.cityID = " + cityID + " and V.villageName='" + villageName + "'");

            PreparedStatement psCustomer;

            if (getPersonalID.getString(1) == null) {// this customer is not exist and insert this customer to the system
                String sqlInsert = "insert into Customer(customerName," + "cardID,cityId,phone,villageID," +
                        "regionName,streetName,buildingNumber) values(?,?,?,?,?,?,?,?,?)";
                psCustomer = con.prepareStatement(sqlInsert);

            } else { // // this customer is exist, and update his info
                String sqlUpdate = "UPDATE Customer " + "SET CustomerName = ?, cardID=?, cityId=?, phone=?, regionName=?,streetName=? ,buildingNumber=?" + " where cardID=" + Integer.parseInt(txtIDCard.getText().trim());
                psCustomer = con.prepareStatement(sqlUpdate);
            }

            psCustomer.setString(1, txtName.getText().trim()); // set the customer name
            psCustomer.setInt(2, Integer.parseInt(txtIDCard.getText().trim())); // set the personal id for this customer
            psCustomer.setInt(3, cityID);// set the city id fot this customer
            psCustomer.setString(4, txtPhoneNumber.getText().trim());// set the phone number for this customer

            if (combVillage == null) { // no village name entered
                psCustomer.setNull(5, Types.NULL);
            } else { // the village name is entered
                getVillageID.next();
                villageID = Integer.parseInt(getVillageID.getString(1));
                psCustomer.setInt(5, villageID);
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
            ResultSet getCustomerBillID = stmt5.executeQuery("select C.CustomerBillID from customerBill C  where CustomerBillID=(Select max(C.customerBillID) from customerBill C)");
            getCustomerBillID.next();
            // save the CustomerBillID to use it in customer bill details
            customerBillID = Integer.parseInt(getCustomerBillID.getString(1));

            this.hBox1.setDisable(true);
            this.hBox2.setDisable(true);
            this.hBox3.setDisable(true);
            this.hBox4.setDisable(true);
            this.brCreateBill.setDisable(true);
            this.status = true;

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    public void btAddBill() {
        try {
            if (txtParCode.getText().trim().isEmpty() && !isNumber(txtParCode.getText().trim())) {
                System.out.println("Enter ParCode");
                return;

            }

            Statement stmt = con.createStatement();
            ResultSet getQuantity = stmt.executeQuery("select S.productQuantity From  storedProducts S  where S.StorageID= ? and S.productCode= " + txtParCode.getText().trim());
            getQuantity.next();

            if (Integer.parseInt(txtQuantityOf.getText().trim()) < Integer.parseInt(getQuantity.getString(1))) {
                numberOfQuantity = (Integer.parseInt(getQuantity.getString(1)) - Integer.parseInt(txtQuantityOf.getText().trim()));

                Statement stmt2 = con.createStatement();
                ResultSet updateQuantity = stmt2.executeQuery("Update storedProducts set   productQuantity = " + numberOfQuantity + "   where S.StorageID= ? and S.productCode= " + txtParCode.getText().trim());
                updateQuantity.next();
            } else {
                numberOfQuantity = (Integer.parseInt(txtQuantityOf.getText().trim()) - Integer.parseInt(getQuantity.getString(1)));
                Statement stmt2 = con.createStatement();
                ResultSet updateQuantity = stmt2.executeQuery("Drop S.productQuantity From  storedProducts S  where S.StorageID= ? and S.productCode= " + txtParCode.getText().trim());
                updateQuantity.next();
            }


            PreparedStatement psCustomerBillDetails = con.prepareStatement("insert into CustomerBillDetails(CustomerBillID," +
                    "ProductCode,SellingPrice,Quantity" +
                    ") " +
                    "values(?,?,?,?,?,)");

            psCustomerBillDetails.setInt(1, customerBillID);
            psCustomerBillDetails.setInt(2, Integer.parseInt(txtParCode.getText().trim()));

            Statement stmt5 = con.createStatement();
            ResultSet getSellingPrice = stmt5.executeQuery("Select S.sellingPrice From  SockedProducts S  where S.StorageID= ? and S.productCode= " + txtParCode.getText().trim());
            getSellingPrice.next();
            psCustomerBillDetails.setInt(3, getSellingPrice.getShort(1));
            psCustomerBillDetails.setInt(4, numberOfQuantity);

            psCustomerBillDetails.executeUpdate();


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void handleBtAddToBill() {

        btAddBill();

    }

    public void handleBtPrintBill() {
        try {
            String sqlUpdate = "UPDATE CustomerBill " + "SET  valueOfBill=?,deposit=? ,patches=?" + " where CustomerBillID=" + customerBillID;
            psCustomerBill = con.prepareStatement(sqlUpdate);
            Statement stmtValueOfBill;
            ResultSet resultValueOfBill;
            if (isNumber(txtQuantityOf.getText().trim())) {
                stmtValueOfBill = con.createStatement();
                resultValueOfBill = stmtValueOfBill.executeQuery("SELECT SUM(C.valueOfBill*" + numberOfQuantity + ") FROM customerbillDetails C where C.customerBillID=" + customerBillID);
                resultValueOfBill.next();
                psCustomerBill.setInt(1, Integer.parseInt(resultValueOfBill.getString(1)));
            } else {
                System.out.println("Enter Number");
                return;
            }
            if (isNumber(txtdeposit.getText().trim()))
                psCustomerBill.setInt(2, Integer.parseInt(txtdeposit.getText().trim()));
            else {
                System.out.println("Enter number");
                return;
            }

            patches = Integer.parseInt(resultValueOfBill.getString(1)) - Integer.parseInt(txtdeposit.getText().trim());

            psCustomerBill.setInt(3, patches);
            psCustomerBill.executeUpdate();

            txtdeposit.setText("");
            txtQuantityOf.setText("");


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


    }


    public static void setInfo(int branchID, int employeeID) {
        branchID = branchID;
        employeeID = employeeID;
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
            return number.matches("\\d+") && temp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
