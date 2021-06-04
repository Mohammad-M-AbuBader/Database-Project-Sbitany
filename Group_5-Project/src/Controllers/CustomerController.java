/**
 * @autor: Ameer Eleyan
 * 1191076
 * At: 31/5/2021  2:10 PM
 */

package Controllers;

import DataBaseClasses.Customer;
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

    @FXML // fx:id="txNumberOfCustomer"
    private TextField txNumberOfCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="txCustomerId"
    private TextField txCustomerId; // Value injected by FXMLLoader

    Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cmCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cmPersonalID.setCellValueFactory(new PropertyValueFactory<>("cardID"));
        cmCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.execute(" ");
    }

    @FXML
    void handleBtRefresh() {
        this.execute(" ");
    }

    @FXML
    void handleBtSearch() {
        if (!this.txCustomerId.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txCustomerId.getText().trim())) {
                Message.displayMassage("Warning", " Transfer number is invalid ");
                this.txCustomerId.clear();
                return;
            }
        }
        this.execute(" where C.customerID=" + Integer.parseInt(this.txCustomerId.getText().trim()));
    }


    private void execute(String str) {
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
            boolean flag = true;
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
                flag = false;
            }
            if (flag) Message.displayMassage("Warning", "Does not exist");

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }
}