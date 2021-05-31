/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  2:30 AM
 */

package DataBaseClasses;

public class BranchGetFrom {

    private String transferNumber;
    private String getAt;
    private String employeeID;
    private String productCode;
    private String quantity;
    private String destinationBranchID;
    private String sourceBranchID;


    public BranchGetFrom() {
    }

    public BranchGetFrom(String transferNumber, String getAt, String employeeID, String productCode, String quantity, String destinationBranchID, String sourceBranchID) {
        this.transferNumber = transferNumber;
        this.getAt = getAt;
        this.employeeID = employeeID;
        this.productCode = productCode;
        this.quantity = quantity;
        this.destinationBranchID = destinationBranchID;
        this.sourceBranchID = sourceBranchID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDestinationBranchID() {
        return destinationBranchID;
    }

    public void setDestinationBranchID(String destinationBranchID) {
        this.destinationBranchID = destinationBranchID;
    }

    public String getSourceBranchID() {
        return sourceBranchID;
    }

    public void setSourceBranchID(String sourceBranchID) {
        this.sourceBranchID = sourceBranchID;
    }


    public String getTransferNumber() {
        return transferNumber;
    }

    public void setTransferNumber(String transferNumber) {
        this.transferNumber = transferNumber;
    }

    public String getGetAt() {
        return getAt;
    }

    public void setGetAt(String getAt) {
        this.getAt = getAt;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return
                "transferNumber: " + transferNumber +
                        ", getAt: " + getAt +
                        ", employeeID: " + employeeID +
                        ", productCode: " + productCode +
                        ", quantity: " + quantity +
                        ", destinationBranchID: " + destinationBranchID +
                        ", sourceBranchID: " + sourceBranchID +
                        ", mainStorageID: ";

    }
}
