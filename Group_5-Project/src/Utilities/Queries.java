package Utilities;

public class Queries {

    private final static String getCityID = "SELECT C.cityID from city C where cityName=";
    private final static String getVillageID = "SELECT V.villageID from village V where villageName=";
    private final static String getBranchID = "SELECT B.branchID from branch B where branchName=";
    // 16 attribute
    // without display security data
    private final static String getAllEmployee = "SELECT * from employee";// table display some them attribute
    private final static String numberOfEmployee = "SELECT COUNT(*) from employee";
    private final static String getAllEmployeeFromBranch = "SELECT * from employee Where branchID=";
    private final static String getAllEmployeeFromCity = "SELECT * from employee where cityID=";

    //table display branched info and #of employee on txtFiled and #of Accountant, without mainCompanyName
    private final static String getAllBranches = "SELECT * from branch";
    private final static String getAllBranchesFromCity = "SELECT * from branch where cityID=";
    private final static String numberOfEmployeeInTheBranch = "SELECT COUNT(*) from employee where branchID= And jobTitleID=2";
    private final static String numberOfAccountantInTheBranch = "SELECT COUNT(*) from employee where branchID= And jobTitleID=1";

    //and their number
    private final static String getAllMangersInfo = "SELECT * from employee where jobTitleID=3";
    private final static String getAllAccountantsInfo = "SELECT * from employee where jobTitleID=1";

    //get general manger and general accountant and personal officer....


    private final static String getMainStorageInfo = "SELECT * from mainstorge";
    private final static String getAllCategories = "SELECT * from categories";
    private final static String getALlProducts = "SELECT * from products";
    private final static String getProductsInfo = "SELECT * from products where categoriesId=";
    private final static String getCategoriesId = "SELECT C.categoriesId from categories where categoriesName=";

    private final static String getAllBranchGetFrom = "SELECT * branchgetfrom";
    private final static String getAllBranchGetFromForBranch = "SELECT * branchgetfrom where branchID=";

    // Supplier bills
    private final static String getSupplierBills = "SELECT * from supplierbill";
    private final static String getTotalSupplierBills = "SELECT COUNT(*) from supplierbill";
    private final static String getAllSupplierBillsFromSupp = "SELECT * from supplierbill S where S.supplierID=";
    private final static String getTotalSupplierBillsFromSupp = "SELECT COUNT(*) from supplierbill S where S.supplierID=";
    private final static String getSupplierBillsOfBillID = "SELECT * from supplierbill S where S.SupplierBillID=";

    private final static String getPaidBillsOfSuppliers = "SELECT * from supplierbill S where S.patches=0";
    private final static String getTotalPaidBillsOfSuppliers = "SELECT COUNT(*) from supplierbill S where S.patches=0";
    private final static String getValueOfPaidBillsOfSuppliers = "SELECT SUM(S.deposit) from supplierbill S where S.patches=0";
    private final static String getPaidBillsOfSupplierForSupp = "SELECT * from supplierbill S where S.patches=0 and S.supplierID=";
    private final static String getTotalPaidBillsOfSupplierForSupp = "SELECT COUNT(*) from supplierbill S where S.patches=0 and S.supplierID=";
    private final static String getValueOfPaidBillsOfSuppliersForSupp = "SELECT SUM(S.deposit) from supplierbill S where S.patches=0 and S.supplierID=";

    private final static String getUnpaidBillsOfSuppliers = "SELECT * from supplierbill S where S.patches>0";
    private final static String getTotalUnpaidBillsOfSuppliers = "SELECT COUNT(*) from supplierbill S where S.patches>0";
    private final static String getValueOfUnpaidBillsOfSuppliers = "SELECT SUM(S.patches) from supplierbill S where S.patches>0";
    private final static String getUnpaidBillsOfSupplierForSupp = "SELECT * from supplierbill S where S.patches>0 and S.supplierID=";
    private final static String getTotalUnpaidBillsOfSupplierForSupp = "SELECT COUNT(*) from S.supplierbill where S.patches>0 and S.supplierID=";
    private final static String getValueOfUnpaidBillsOfSuppliersForSupp = "SELECT SUM(S.patches) from supplierbill S where S.patches>0 and S.supplierID=";

    private final static String getDetailsOfSupplierBill = " SELECT * from supplierbilldetails S where S.SupplierBillID=";

    // customer bill
    private final static String getCustomerBills = "SELECT * from customerbill";
    private final static String getTotalCustomerBills = "SELECT COUNT(*) from customerbill";
    private final static String getAllCustomerBillsFromBranch = "SELECT * from customerbill C where C.branchID=";
    private final static String getTotalCustomerBillsFromBranch = "SELECT COUNT(*) from customerbill C where C.branchID=";
    private final static String getCustomerBillOfBillID = "SELECT * from customerbill C where C.customerBillID=";
    private final static String getCustomerBillsOfCustomer = "SELECT * from customerbill C where C.customerID";


}
