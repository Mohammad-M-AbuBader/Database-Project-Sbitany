/**
 * @autor: Ameer ELeyan
 * 1191076
 * At: 31/5/2021  2:10 PM
 */

package Controllers;

import DataBaseClasses.Customer;
import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

public class CustomerController implements Initializable {

    @FXML // fx:id="tableCustomers"
    private TableView<Customer> tableCustomers; // Value injected by FXMLLoader

    @FXML // fx:id="cmCustomerID"
    private TableColumn<Customer, String> cmCustomerID; // Value injected by FXMLLoader

    @FXML // fx:id="cmPersonalID"
    private TableColumn<Customer, String> cmPersonalID; // Value injected by FXMLLoader

    @FXML // fx:id="cmCustomerName"
    private TableColumn<Customer, String> cmCustomerName; // Value injected by FXMLLoader

    @FXML // fx:id="cmCustomerPhone"
    private TableColumn<Customer, String> cmCustomerPhone; // Value injected by FXMLLoader

    @FXML // fx:id="cmAddress"
    private TableColumn<Customer, String> cmAddress; // Value injected by FXMLLoader

    @FXML // fx:id="numberOFCustomer"
    private Label numberOFCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="txNumberOfCustomer"
    private TextField txNumberOfCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="txCustomerId"
    private TextField txCustomerId; // Value injected by FXMLLoader

    Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tableCustomers.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        cmCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cmPersonalID.setCellValueFactory(new PropertyValueFactory<>("cardID"));
        cmCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.refresh(" ");
    }


    @FXML
    void handleBtRefresh() {
        this.refresh(" ");
    }

    @FXML
    void handleBtSearch() {
        if (!this.txCustomerId.getText().trim().isEmpty()) {
            if (!isNumber(this.txCustomerId.getText().trim())) {
                Message.displayMassage("Warning", " Transfer number is invalid ");
                this.txCustomerId.clear();
                return;
            }
        }

        String search = "SELECT * from Customer C where C.customerID=" + Integer.parseInt(this.txCustomerId.getText().trim());

        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(search);
            boolean empty = rs.next();
            if (!empty) {
                Message.displayMassage("Warning", this.txCustomerId.getText() + " Does not exist ");
                this.txCustomerId.clear();
                return;
            }
            Customer customer = new Customer();
            String id = rs.getString(1);
            customer.setCustomerID(id);
            customer.setCustomerName(rs.getString(2));
            customer.setCardID(rs.getString(3));
            customer.setPhone(rs.getString(9));

            String regionName = rs.getString(7);
            String streetName = rs.getString(6);
            String bulldingNumber = rs.getString(8);

            Statement stmtCity = con.createStatement();
            ResultSet rsCity;
            rsCity = stmtCity.executeQuery("SELECT S.cityName From City S , Customer C where C.cityID = S.cityID and C.CustomerID=" + Integer.parseInt(id));
            rsCity.next();
            String cityName = rsCity.getString(1);


            Statement stmtVillage = con.createStatement();
            ResultSet rsVillageName;
            rsVillageName = stmtVillage.executeQuery("SELECT V.villageName From Village V , Customer C where C.villageID = V.villageID and C.CustomerID=" + Integer.parseInt(id));
            boolean result = rsVillageName.next();
            String villageName = null;
            if (result) villageName = rsVillageName.getString(1);

            String Address = cityName + ", " + (villageName == null ? "" : villageName) + ", " + (regionName == null ? "" : regionName) + ", " + (streetName == null ? "" : streetName) + ", " + (bulldingNumber == null ? "" : bulldingNumber);
            customer.setAddress(Address);
            this.tableCustomers.getItems().clear();
            this.tableCustomers.getItems().add(customer);
            this.txCustomerId.clear();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

    }


    private void refresh(String str) {
        this.tableCustomers.getItems().clear();
        this.txCustomerId.clear();
        try {
            ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
            con = connection.connectSbitanyDB();
            String getCustomer = "SELECT * from Customer C " + str;

            assert con != null;
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM Customer C " + str);
            rs1.next();
            txNumberOfCustomer.setText(rs1.getString(1));

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCustomer);

            while (rs.next()) {
                Customer customer = new Customer();
                String id = rs.getString(1);
                customer.setCustomerID(id);
                customer.setCustomerName(rs.getString(2));
                customer.setCardID(rs.getString(3));
                customer.setPhone(rs.getString(9));

                String regionName = rs.getString(7);
                String streetName = rs.getString(6);
                String bulldingNumber = rs.getString(8);

                Statement stmtCity = con.createStatement();
                ResultSet rsCity;
                rsCity = stmtCity.executeQuery("SELECT S.cityName From City S , Customer C where C.cityID = S.cityID and C.CustomerID=" + Integer.parseInt(id));
                rsCity.next();
                String cityName = rsCity.getString(1);


                Statement stmtVillage = con.createStatement();
                ResultSet rsVillageName;
                rsVillageName = stmtVillage.executeQuery("SELECT V.villageName From Village V , Customer C where C.villageID = V.villageID and C.CustomerID=" + Integer.parseInt(id));
                boolean result = rsVillageName.next();
                String villageName = null;
                if (result) villageName = rsVillageName.getString(1);

                String Address = cityName + ", " + (villageName == null ? "" : villageName) + ", " + (regionName == null ? "" : regionName) + ", " + (streetName == null ? "" : streetName) + ", " + (bulldingNumber == null ? "" : bulldingNumber);
                customer.setAddress(Address);
                this.tableCustomers.getItems().add(customer);
            }
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
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
            return number.matches("\\d+") && temp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}