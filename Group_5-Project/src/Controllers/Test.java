package Controllers;

import java.io.File;
import java.io.IOException;
import java.sql.*;


import java.util.Scanner;


public class Test {
    /*
    Calendar calendar = Calendar.getInstance();
        System.out.println(new java.sql.Date(calendar.getTime().getTime()));
     */
    private static Connection con;

    public static void main(String[] str) throws IOException {
        ConnectionToSbitanyDatabase connection = new ConnectionToSbitanyDatabase();
        con = connection.connectSbitanyDB();

        try {
            // customerBillID, productCode, sellingPrice, quantity
            File file = new File("CustomerBillDet.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                int pc = Integer.parseInt(str2[1].trim());
                int sp = Integer.parseInt(str2[2].trim());
                int qy = Integer.parseInt(str2[3].trim());


                PreparedStatement ps = con.prepareStatement("insert into customerbilldetails(customerBillID, productCode, sellingPrice, quantity)" + "values(?,?,?,?)");

                ps.setInt(1, id);
                ps.setInt(2, pc);
                ps.setInt(3, sp);
                ps.setInt(4, qy);


                ps.executeUpdate();

            }

            con.close();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

    }

    public static String getDate(String date) {
        //11/16/2002
        date = date.trim();
        int s1 = date.indexOf('/');
        int s2 = date.lastIndexOf('/');
        return date.substring(s2 + 1) + '-' + date.substring(0, s1) + '-' + date.substring(s1 + 1, s2);
    }

    public void insertEmployee() {
        /*
        File file = new File("Employees.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                int card = Integer.parseInt(str2[1].trim());
                String name = str2[2].trim();
                String phone = str2[3].trim();
                String date = getDate(str2[4]);
                int salary = Integer.parseInt(str2[5].trim());
                String email = str2[6].trim();
                String uname = str2[7].trim();
                String passwd = str2[8].trim();
                String hd = getDate(str2[9]).trim();
                String fd;
                if (str2[10].isEmpty())
                    fd = "";
                else
                    fd = getDate(str2[10]);

                int jt = Integer.parseInt(str2[11].trim());
                int cd = Integer.parseInt(str2[12].trim());
                int vd;
                if (str2[13].isEmpty())
                    vd = 0;
                else
                    vd = Integer.parseInt(str2[13].trim());

                int bd = Integer.parseInt(str2[14].trim());
                PreparedStatement ps = con.prepareStatement("insert into employee(employeeID, employeeCard, employeeName, employeePhone," +
                        "employeeDateOfBirth, employeeSalary, employeeEmail, employeeUserName," +
                        "employeePassword, employeeHiringDate, employeeFiringDate, jobTitleID, cityID, villageID, branchID)" +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                ps.setInt(1, id);
                ps.setInt(2, card);
                ps.setString(3, name);
                if phone empty
                ps.setString(4, phone);
                ps.setDate(5, Date.valueOf(date));
                ps.setInt(6, salary);
                if (email.isEmpty())
                    ps.setNull(7, Types.NULL);
                else
                    ps.setString(7, email);

                if (uname.isEmpty())
                    ps.setNull(8, Types.NULL);
                else
                    ps.setString(8, uname);

                if (passwd.isEmpty())
                    ps.setNull(9, Types.NULL);
                else
                    ps.setString(9, passwd);

                ps.setDate(10, Date.valueOf(hd));

                if (fd.isEmpty()) {
                    ps.setNull(11, java.sql.Types.DATE);
                } else {
                    ps.setDate(11, Date.valueOf(fd));
                }


                ps.setInt(12, jt);
                ps.setInt(13, cd);
                if (vd != 0)
                    ps.setInt(14, vd);
                else
                    ps.setNull(14, Types.NULL);

                ps.setInt(15, bd);

                ps.executeUpdate();
         */
    }

    public void insertCustomer() {
        /*
        File file = new File("Customers.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                String name = str2[1].trim();
                int card = Integer.parseInt(str2[2].trim());
                int cd = Integer.parseInt(str2[3].trim());
                int vd;
                if (str2[4].isEmpty())
                    vd = 0;
                else
                    vd = Integer.parseInt(str2[4].trim());

                String stn = str2[5].trim();
                String reg = str2[6].trim();
                int bn;
                if (str2[7].isEmpty()) {
                    bn = 0;
                } else {
                    bn = Integer.parseInt(str2[7].trim());
                }

                String phone = str2[8].trim();
                PreparedStatement ps = con.prepareStatement("insert into customer(customerID, customerName, cardID, cityID, villageID, " +
                        "streetName, regionName, bulldingNumber, phone)" + "values(?,?,?,?,?,?,?,?,?)");

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setInt(3, card);
                ps.setInt(4, cd);
                if (vd != 0)
                    ps.setInt(5, vd);
                else
                    ps.setNull(5, Types.NULL);

                if (stn.isEmpty()) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, stn);
                }

                if (reg.isEmpty()) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7, reg);
                }

                if (bn != 0) {
                    ps.setInt(8, bn);
                } else {
                    ps.setNull(8, Types.NULL);
                }
                if phone
                ps.setString(9, phone);

                ps.executeUpdate();

            }
         */
    }

    public void insertProd() {
        /*

File file = new File("Products.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                String name = str2[1].trim();
                String cmopany = str2[2].trim();
                int pp = Integer.parseInt(str2[3].trim());
                int sp = Integer.parseInt(str2[4].trim());
                int cg = Integer.parseInt(str2[5].trim());
                int pcd;
                if (str2[6].isEmpty()) {
                    pcd = 0;
                } else {
                    pcd = Integer.parseInt(str2[6].trim());
                }
                String des = str2[7].trim();


                PreparedStatement ps = con.prepareStatement("insert into product(productCode, productName, manufacturerCompany," +
                        " purchasingPrice, sellingPrice, catogresId, parCode, descriptions)" + "values(?,?,?,?,?,?,?,?)");

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, cmopany);
                ps.setInt(4, pp);
                ps.setInt(5, sp);
                ps.setInt(6, cg);
                if (pcd != 0) {
                    ps.setInt(7, pcd);
                } else {
                    ps.setNull(7, Types.NULL);
                }
                if (des.isEmpty()) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setString(8, des);
                }

                ps.executeUpdate();

            }

            con.close();
         */
    }

    public void insertSuppBill() {
        /*

        // SupplierBillID orederAt supplierID valueOfBill deposit patches
            File file = new File("SupplierBill.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                String date = getDate(str2[1]);
                int supId = Integer.parseInt(str2[2].trim());
                int vb = Integer.parseInt(str2[3].trim());
                int db = Integer.parseInt(str2[4].trim());
                int pt = Integer.parseInt(str2[5].trim());

                PreparedStatement ps = con.prepareStatement("insert into supplierbill(SupplierBillID, orederAt, supplierID, valueOfBill, deposit, patches)" + "values(?,?,?,?,?,?)");

                ps.setInt(1, id);
                ps.setDate(2, Date.valueOf(date));
                ps.setInt(3, supId);
                ps.setInt(4, vb);
                ps.setInt(5, db);
                ps.setInt(6, pt);

                ps.executeUpdate();

            }

         */
    }

    public void insertSuppBillDet() {
        /*
          // SupplierBillID productCode purchasingPrice quantity
            File file = new File("SupplierBillDet.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                int pc = Integer.parseInt(str2[1].trim());
                int pp = Integer.parseInt(str2[2].trim());
                int qy = Integer.parseInt(str2[3].trim());

                PreparedStatement ps = con.prepareStatement("insert into supplierbilldetails(SupplierBillID, productCode, purchasingPrice, quantity)" + "values(?,?,?,?)");

                ps.setInt(1, id);
                ps.setInt(2, pc);
                ps.setInt(3, pp);
                ps.setInt(4, qy);


                ps.executeUpdate();
         */
    }

    public void insertCusBill() {
        /*
        // customerBillID, orederAt, valueOfBill,customerID, branchID, employeeID, deposit, patches
            File file = new File("CustomersBills.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                String  date = getDate(str2[1]);
                int vb = Integer.parseInt(str2[2].trim());
                int cd = Integer.parseInt(str2[3].trim());
                int bd = Integer.parseInt(str2[4].trim());
                int ed = Integer.parseInt(str2[5].trim());
                int dep = Integer.parseInt(str2[6].trim());
                int pat = Integer.parseInt(str2[7].trim());

                PreparedStatement ps = con.prepareStatement("insert into customerbill(customerBillID, orederAt, valueOfBill," +
                        " customerID, branchID, employeeID, deposit, patches)" + "values(?,?,?,?,?,?,?,?)");

                ps.setInt(1, id);
                ps.setDate(2,Date.valueOf(date));
                ps.setInt(3, vb);
                ps.setInt(4, cd);
                ps.setInt(5, bd);
                ps.setInt(6, ed);
                ps.setInt(7, dep);
                ps.setInt(8, pat);



                ps.executeUpdate();

         */
    }

    public void insertCusBillDet(){
        /*
        // customerBillID, productCode, sellingPrice, quantity
            File file = new File("CustomerBillDet.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] str2 = scanner.nextLine().split(",");
                int id = Integer.parseInt(str2[0].trim());
                int pc = Integer.parseInt(str2[1].trim());
                int sp = Integer.parseInt(str2[2].trim());
                int qy = Integer.parseInt(str2[3].trim());


                PreparedStatement ps = con.prepareStatement("insert into customerbilldetails(customerBillID, productCode, sellingPrice, quantity)" + "values(?,?,?,?)");

                ps.setInt(1, id);
                ps.setInt(2, pc);
                ps.setInt(3, sp);
                ps.setInt(4, qy);




                ps.executeUpdate();
         */
    }
}
