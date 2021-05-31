/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  4:03 AM
 */
package DataBaseClasses;

public class Supplier {
    private String supplierID;
    private String supplierName;
    private String supplierPhone;
    private String supplierEmail;
    private String supplierFax;

    public Supplier() {
    }

    public Supplier(String supplierID, String supplierName, String supplierPhone,
                    String supplierEmail, String supplierFax) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
        this.supplierFax = supplierFax;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierFax() {
        return supplierFax;
    }

    public void setSupplierFax(String supplierFax) {
        this.supplierFax = supplierFax;
    }

    @Override
    public String toString() {
        return
                "supplierID: " + supplierID +
                        ", supplierName: " + supplierName +
                        ", supplierPhone: " + supplierPhone +
                        ", supplierEmail: " + supplierEmail +
                        ", supplierFax: " + supplierFax;

    }
}
