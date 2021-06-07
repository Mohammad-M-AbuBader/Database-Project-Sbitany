package DataBaseClasses;

public class OurProduct {
    private String productName,quantity,productCode,parCode;


    public OurProduct() {
    }

    public OurProduct(String quantity, String productCode, String parCode,String productName) {
        this.quantity = quantity;
        this.productCode = productCode;
        this.parCode = parCode;
        this.productName=productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getParCode() {
        return parCode;
    }

    public void setParCode(String parCode) {
        this.parCode = parCode;
    }

    @Override
    public String toString() {
        return "quantity: " + quantity + ", productCode: " + productCode +", productName: " + productName + ", parCode: " + parCode ;

    }
}