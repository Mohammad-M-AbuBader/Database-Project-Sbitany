/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  3:11 AM
 */
package DataBaseClasses;


public class CustomerBill {
    private String customerBillID;
    private String releaseDate;
    private String valueOfBill;
    private String customerID;
    private String branchID;
    private String employeeID;
    private String employeeName;
    private String deposit;
    private String patches;
    private String branchName;


    public CustomerBill() {
    }

    public CustomerBill(String customerOrderID, String releaseDate, String valueOfBill, String customerID,
                        String branchID, String employeeID, String employeeName, String deposit, String patches,
                        String branchName) {
        this.customerBillID = customerOrderID;
        this.releaseDate = releaseDate;
        this.valueOfBill = valueOfBill;
        this.customerID = customerID;
        this.employeeName = employeeName;
        this.branchID = branchID;
        this.employeeID = employeeID;
        this.deposit = deposit;
        this.patches = patches;
        this.branchName = branchName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCustomerBillID() {
        return customerBillID;
    }

    public void setCustomerBillID(String customerBillID) {
        this.customerBillID = customerBillID;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getValueOfBill() {
        return valueOfBill;
    }

    public void setValueOfBill(String valueOfBill) {
        this.valueOfBill = valueOfBill;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getPatches() {
        return patches;
    }

    public void setPatches(String patches) {
        this.patches = patches;
    }

    @Override
    public String toString() {
        return
                "customerBillID: " + customerBillID + ", orderAt: " + releaseDate + ", valueOfBill: " + valueOfBill +
                        ", customerID: " + customerID + ", branchID: " + branchID + ", employeeID: " + employeeID +
                        ", deposit: " + deposit + ", patches: " + patches + ", branchName: " + branchName;


    }
}
