/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  3:29 AM
 */
package DataBaseClasses;

public class Employee {
    private String employeeID;
    private String employeeCard;
    private String employeeName;
    private String employeePhone;
    private String employeeDateOfBirth;
    private String employeeSalary;
    private String employeeEmail;
    private String employeeUserName;
    private String employeePassword;
    private String employeeHiringDate;
    private String employeeFiringDate;
    private String jobTitleID;
    private String address;
    private String branchName;
    private String branchID;

    private String employeeAge;


    public Employee() {
    }

    public Employee(String employeeID, String employeeCard, String employeeName,
                    String employeePhone,String employeeHiringDate, String jobTitleID, String address,
                    String branchName, String employeeAge) {
        this.employeeID = employeeID;
        this.employeeCard = employeeCard;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeHiringDate = employeeHiringDate;
        this.jobTitleID = jobTitleID;
        this.address = address;
        this.branchName = branchName;
        this.employeeAge = employeeAge;
    }

    public Employee(String employeeID, String employeeCard, String employeeName, String employeePhone,
                    String employeeDateOfBirth, String employeeSalary, String employeeEmail, String employeeUserName,
                    String employeePassword, String employeeHiringDate, String employeeFiringDate, String jobTitleID,
                    String address, String branchName, String branchID, String employeeAge) {
        this.employeeID = employeeID;
        this.employeeCard = employeeCard;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeDateOfBirth = employeeDateOfBirth;
        this.employeeSalary = employeeSalary;
        this.employeeEmail = employeeEmail;
        this.employeeUserName = employeeUserName;
        this.employeePassword = employeePassword;
        this.employeeHiringDate = employeeHiringDate;
        this.employeeFiringDate = employeeFiringDate;
        this.jobTitleID = jobTitleID;
        this.address = address;
        this.branchName = branchName;
        this.branchID = branchID;

        this.employeeAge = employeeAge;
    }


    public String getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }


    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeCard() {
        return employeeCard;
    }

    public void setEmployeeCard(String employeeCard) {
        this.employeeCard = employeeCard;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeDateOfBirth() {
        return employeeDateOfBirth;
    }

    public void setEmployeeDateOfBirth(String employeeDateOfBirth) {
        this.employeeDateOfBirth = employeeDateOfBirth;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeHiringDate() {
        return employeeHiringDate;
    }

    public void setEmployeeHiringDate(String employeeHiringDate) {
        this.employeeHiringDate = employeeHiringDate;
    }

    public String getEmployeeFiringDate() {
        return employeeFiringDate;
    }

    public void setEmployeeFiringDate(String employeeFiringDate) {
        this.employeeFiringDate = employeeFiringDate;
    }

    public String getJobTitleID() {
        return jobTitleID;
    }

    public void setJobTitleID(String jobTitleID) {
        this.jobTitleID = jobTitleID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }


    @Override
    public String toString() {
        return
                "employeeID: " + employeeID + ", employeeCard: " + employeeCard + ", employeeName: " + employeeName +
                        ", employeePhone: " + employeePhone + ", employeeDateOfBirth: " + employeeDateOfBirth + ", employeeSalary: " + employeeSalary +
                        ", employeeEmail: " + employeeEmail + ", employeeUserName: " + employeeUserName +
                        ", employeePassword: " + employeePassword + ", employeeHiringDate: " + employeeHiringDate +
                        ", employeeFiringDate: " + employeeFiringDate + ", jobTitleID: " + jobTitleID +
                        ", address: " + address + ", branchName: " + branchName +
                        ", branchID: " + branchID + ", EmployeeAge: " + employeeAge;


    }
}
