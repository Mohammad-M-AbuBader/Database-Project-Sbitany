/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  2:20 AM
 */

package DataBaseClasses;

public class Branch {

    private String branchId;
    private String branchName;
    private String branchPhone;
    private String address;

    public Branch() {

    }


    public Branch(String branchId, String branchName, String branchPhone, String address) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchPhone = branchPhone;
        this.address = address;

    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "branchId: " + branchId +
                ", branchName: " + branchName +
                ", branchPhone: " + branchPhone + "Address:" + this.address;


    }
}

