/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 3/6/2021 4:27 AM
 */
package Controllers;

import Utilities.ConnectionToSbitanyDatabase;
import Utilities.Message;
import Utilities.Methods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class OrderProductController implements Initializable {


    @FXML // fx:id="txtCodeProduct"
    private TextField txtCodeProduct; // Value injected by FXMLLoader

    @FXML // fx:id="txtQuantityOf"
    private TextField txtQuantityOf; // Value injected by FXMLLoader

    @FXML // fx:id="txtEmployeeID"
    private TextField txtEmployeeID; // Value injected by FXMLLoader

    @FXML // fx:id="cmbStorageName"
    private ComboBox<String> cmbStorageName; // Value injected by FXMLLoader

    private Connection con;

    int remainingQuantity, requiredQuantity, availableQuantityFromSourceStorage,
            sourceBranchID, sourceStorageID, destinationStorageID;

    private static int destinationBranchID;

    PreparedStatement psStorageProducts;

    public static void setInfo(int branchID) {
        OrderProductController.destinationBranchID = branchID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();
        this.fillCombStorageName();
    }


    public void handleBtOrder() {
        try {

            if (txtCodeProduct.getText().trim().isEmpty() || !Methods.isNumber(txtCodeProduct.getText().trim())) {
                Message.displayMassage("Warning", "Please enter valid product code");
                return;
            }

            if (txtQuantityOf.getText().trim().isEmpty() || !Methods.isNumber(txtQuantityOf.getText().trim())) {
                Message.displayMassage("Warning", "Please enter valid quantity");
                return;
            }

            if (txtEmployeeID.getText().trim().isEmpty() || !Methods.isNumber(txtEmployeeID.getText().trim())) {
                Message.displayMassage("Warning", "Please enter valid employee ID");
                return;
            }

            if (cmbStorageName == null) {
                Message.displayMassage("Warning", "Please select the branch");
                return;
            }

            //  check Employee if exist or not
            Statement checkEmployeeIDStatement = con.createStatement();
            ResultSet checkEmployeeID = checkEmployeeIDStatement.executeQuery("select E.EmployeeID from employee E where E.EmployeeId = " + Integer.parseInt(txtEmployeeID.getText().trim()) + " and E.employeeFiringDate is NULL ");
            boolean isEmployeeExist = checkEmployeeID.next();

            // if employee is  not exist
            if (!isEmployeeExist) {
                Message.displayMassage("Warning", "This Employee dose Not Exist");
                return;
            }

            //get Source Branch ID
            Statement getSourceBranchIDStatement = con.createStatement();
            ResultSet getSourceBranchID = getSourceBranchIDStatement.executeQuery("select B.branchID from Branch B where B.branchName = '" + cmbStorageName.getValue() + "'");
            getSourceBranchID.next();
            sourceBranchID = Integer.parseInt(getSourceBranchID.getString(1));

            //get Source Storage ID
            Statement getSourceStorageIDStatement = con.createStatement();
            ResultSet getSourceStorageID = getSourceStorageIDStatement.executeQuery("select S.StorageID from Storages S where S.branchID = " + sourceBranchID);
            getSourceStorageID.next();
            sourceStorageID = Integer.parseInt(getSourceStorageID.getString(1));

            // get Destination Storage ID
            Statement getDestinationStorageIDStatement = con.createStatement();
            ResultSet getDestinationStorageID = getDestinationStorageIDStatement.executeQuery("select S.StorageID from Storages S where S.branchID = " + destinationBranchID);
            getDestinationStorageID.next();
            destinationStorageID = Integer.parseInt(getDestinationStorageID.getString(1));


            //check product if exist or not
            Statement checkProductCodeStatement = con.createStatement();
            ResultSet checkProductCode = checkProductCodeStatement.executeQuery("select  S.ProductQuantity from StoredProducts S where S.ProductCode = " + Integer.parseInt(txtCodeProduct.getText().trim()) + " and S.storageID= " + sourceStorageID);
            boolean isProductExist = checkProductCode.next();

            if (!isProductExist) { // the product does not exist
                Message.displayMassage("Warning", "This Product dose not Exist");
            } else {

                availableQuantityFromSourceStorage = Integer.parseInt(checkProductCode.getString(1));
                requiredQuantity = Integer.parseInt(txtQuantityOf.getText().trim());

                Statement getQuantityFromDestinationStorageStatement = con.createStatement();
                ResultSet getQuantityFromDestinationStorage = getQuantityFromDestinationStorageStatement.executeQuery("select S.productQuantity From  storedProducts S  where S.StorageID=" + destinationStorageID + " and S.productCode= " + Integer.parseInt(txtCodeProduct.getText().trim()));
                boolean isQuantityExist = getQuantityFromDestinationStorage.next();

                if (requiredQuantity < availableQuantityFromSourceStorage) {
                    remainingQuantity = availableQuantityFromSourceStorage - requiredQuantity;

                    psStorageProducts = con.prepareStatement("Update storedProducts S set productQuantity = " + remainingQuantity + "  where S.StorageID= " + sourceStorageID + " and S.productCode= " + Integer.parseInt(txtCodeProduct.getText().trim()));
                    psStorageProducts.executeUpdate();

                    if (isQuantityExist) {
                        int availableQuantityFromDestinationStorage = Integer.parseInt(getQuantityFromDestinationStorage.getString(1));
                        psStorageProducts = con.prepareStatement("Update storedProducts S set  productQuantity = " + (requiredQuantity + availableQuantityFromDestinationStorage) + "  where S.StorageID=" + destinationStorageID + " and S.productCode= " + Integer.parseInt(txtCodeProduct.getText().trim()));
                    } else {
                        psStorageProducts = con.prepareStatement("insert into storedProducts(StorageID," +
                                "productCode,productQuantity) " + "values(?,?,?)");
                        psStorageProducts.setInt(1, destinationStorageID);
                        psStorageProducts.setInt(2, Integer.parseInt(txtCodeProduct.getText().trim()));
                        psStorageProducts.setInt(3, requiredQuantity);
                    }

                } else {

                    psStorageProducts = con.prepareStatement("Delete From storedProducts S where S.StorageID= " + sourceStorageID + " and S.productCode= " + Integer.parseInt(txtCodeProduct.getText().trim()));
                    psStorageProducts.executeUpdate();
                    Message.displayMassage("Warning", "Only " + availableQuantityFromSourceStorage + "pieces were sent due to lack of required quantity");

                    if (isQuantityExist) {
                        int availableQuantityFromDestinationStorage = Integer.parseInt(getQuantityFromDestinationStorage.getString(1));
                        psStorageProducts = con.prepareStatement("Update storedProducts S set productQuantity = " + (availableQuantityFromSourceStorage +
                                availableQuantityFromDestinationStorage) + " where S.StorageID=" + destinationStorageID + " and S.productCode= " + Integer.parseInt(txtCodeProduct.getText().trim()));

                    } else {
                        psStorageProducts = con.prepareStatement("insert into storedProducts(StorageID," +
                                "productCode,productQuantity) " + "values(?,?,?)");
                        psStorageProducts.setInt(1, destinationStorageID);
                        psStorageProducts.setInt(2, Integer.parseInt(txtCodeProduct.getText().trim()));
                        psStorageProducts.setInt(3, availableQuantityFromSourceStorage);
                    }
                }
                psStorageProducts.executeUpdate();

                //  writing the movement
                Calendar calendar = Calendar.getInstance();
                psStorageProducts = con.prepareStatement("insert into branchGetFrom (getAt," +
                        "employeeID,sourceStorageID,destinationStorageID,productCode,quantity) " + "values(?,?,?,?,?,?)");

                psStorageProducts.setDate(1, new java.sql.Date(calendar.getTime().getTime()));
                psStorageProducts.setInt(2, Integer.parseInt(txtEmployeeID.getText().trim()));
                psStorageProducts.setInt(3, sourceBranchID);
                psStorageProducts.setInt(4, destinationBranchID);
                psStorageProducts.setInt(5, Integer.parseInt(txtCodeProduct.getText().trim()));
                if (requiredQuantity > availableQuantityFromSourceStorage)
                    psStorageProducts.setInt(6, availableQuantityFromSourceStorage);
                else psStorageProducts.setInt(6, Integer.parseInt(txtQuantityOf.getText().trim()));
                psStorageProducts.executeUpdate();

                txtCodeProduct.clear();
                txtQuantityOf.clear();
                txtEmployeeID.clear();
                Message.displayMassage("Successfully", "Product ordering and transaction registration completed successfully");
            }

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

    private void fillCombStorageName() {
        try {
            String sqlBranchesName = "SELECT B.branchName from branch B";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlBranchesName);
            while (rs.next()) {
                this.cmbStorageName.getItems().add(rs.getString(1).trim());
            }
            stmt.close();
            rs.close();
        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }

}
