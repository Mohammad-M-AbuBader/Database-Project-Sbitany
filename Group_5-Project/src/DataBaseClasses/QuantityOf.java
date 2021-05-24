/**
 * @autor: Ameer Eleyan
 * ID: 1191076
 * At: 23-5-2021  11:25 PM
 */
package DataBaseClasses;

public class QuantityOf {

    private String storageName, quantity;

    public QuantityOf(String branchName, String quantity){
        this.storageName = branchName;
        this.quantity = quantity;
    }

    public QuantityOf(){

    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "QuantityOf{" +
                "branchName='" + storageName + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
