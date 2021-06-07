/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  3:21 AM
 */
package DataBaseClasses;

public class BillDetails {
    private String billID;
    private String productCode;
    private String price;
    private String quantity;
    private String productName;

    public BillDetails() {
    }

    public BillDetails(String billID, String productCode, String price, String quantity,String productName) {
        this.billID = billID;
        this.productCode = productCode;
        this.price = price;
        this.quantity = quantity;
        this.productName=productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return  "billID: " + billID + ", productCode: " + productCode + ", productName: " + productName + ", price: " + price + ", quantity: " + quantity;
    }
}
