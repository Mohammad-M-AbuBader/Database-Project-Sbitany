/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  2:55 AM
 */
package DataBaseClasses;

import java.util.Date;

public class SupplierBill {

    private String supplierBillID;
    private String orderAt;
    private String supplierID;
    private String valueOfBill;
    private String patches;
    private String  deposit;


    public SupplierBill() {
    }

    public SupplierBill(String supplierBillID, String orderAt,
                        String supplierID, String valueOfBill, String patches, String deposit) {
        this.supplierBillID = supplierBillID;
        this.orderAt = orderAt;
        this.supplierID = supplierID;
        this.valueOfBill = valueOfBill;
        this.patches = patches;
        this.deposit = deposit;

    }

    public String getSupplierBillID() {
        return supplierBillID;
    }

    public void setSupplierBillID(String supplierBillID) {
        this.supplierBillID = supplierBillID;
    }

    public String getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(String orderAt) {
        this.orderAt = orderAt;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getValueOfBill() {
        return valueOfBill;
    }

    public void setValueOfBill(String valueOfBill) {
        this.valueOfBill = valueOfBill;
    }

    public String getPatches() {
        return patches;
    }

    public void setPatches(String patches) {
        this.patches = patches;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return
                "SupplierBillID: " + supplierBillID + ", orderAt: " + orderAt +
                        ", supplierID: " + supplierID + ", valueOfBill: " + valueOfBill + ", patches: " + patches + ", deposit: " + deposit;


    }
}
