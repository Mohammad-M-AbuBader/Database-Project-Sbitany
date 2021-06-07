/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 6-7-2021  12:43 PM
 */
package DataBaseClasses;


public class Profit {

    private String productName,
            productCode,
            sellingPrice,
            purchasingPrice,
            quantity,
            profit;

    public Profit() {
    }

    public Profit(String productName, String productCode, String sellingPrice, String purchasingPrice, String quantity, String profit) {
        this.productName = productName;
        this.productCode = productCode;
        this.sellingPrice = sellingPrice;
        this.purchasingPrice = purchasingPrice;
        this.quantity = quantity;
        this.profit = profit;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(String purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return
                "productName: " + productName + ", productCode: " + productCode + ", sellingPrice: " + sellingPrice +
                        ", purchasingPrice: " + purchasingPrice + ", quantity: " + quantity + ", profit: " + profit;

    }

}
