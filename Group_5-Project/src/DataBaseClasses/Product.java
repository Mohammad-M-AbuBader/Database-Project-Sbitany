/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  3:49 AM
 */
package DataBaseClasses;

public class Product {
    private String productCode;
    private String productName;
    private String manufacturerCompany;
    private String purchasingPrice;
    private String sellingPrice;
    private String categoriesName;
    private String parCode;
    private String descriptions;

    public Product(String productCode, String productName, String manufacturerCompany, String purchasingPrice, String sellingPrice
            , String categoriesName,String parCode, String descriptions) {
        this.productCode = productCode;
        this.productName = productName;
        this.manufacturerCompany = manufacturerCompany;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.categoriesName = categoriesName;
        this.parCode = parCode;
        this.descriptions = descriptions;
    }

    public Product() {
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturerCompany() {
        return manufacturerCompany;
    }

    public void setManufacturerCompany(String manufacturerCompany) {
        this.manufacturerCompany = manufacturerCompany;
    }

    public String getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(String purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public String getParCode() {
        return parCode;
    }

    public void setParCode(String parCode) {
        this.parCode = parCode;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return
                "productCode: " + productCode + ", productName: " + productName + ", manufacturerCompany: '" + manufacturerCompany +
                        ", purchasingPrice: " + purchasingPrice + ", sellingPrice: " + sellingPrice +
                        ", categoriesId: " + categoriesName +
                        ", parCode: " + parCode+", descriptions: "+ descriptions;


    }
}
