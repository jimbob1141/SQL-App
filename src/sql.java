import java.sql.*;
import java.util.Scanner;
public class sql {
    static int f;
    static String user = null;
    static String pass = null;
    String fname;
    String sname;

    static void upSet() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter username: ");
        user = sc.next();
        System.out.print("Please enter password: ");
        pass = sc.next();
    }

    public static void main(String[] args) {
        sql gen = new sql();
        if (user == null) {
            upSet();
        } else {

        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
        } catch (Exception e) {
            System.out.println("Please enter a valid username and password");
            upSet();
        }
        System.out.println("Welcome to the patient database for WFS Surgery");
        System.out.println("new: add new customer");
        System.out.println("update: update existing customer");
        System.out.println("remove: remove a customers records");
        System.out.println("exit: exit program");
        Scanner sc = new Scanner(System.in);
        String f;
        f = sc.next();
        if (f.equals("new")) {
            gen.newCustomer();
        } else if (f.equals("update")) {
            gen.updateCustomer();
        } else if (f.equals("exit")) {
            System.out.println("Program now exiting");
        } else if (f.equals("remove")) {
            gen.removeCustomer();
        } else {
            System.out.println("Please enter a valid command");
            main(null);
        }
    }

    public int idgen() {
        try {
            int i;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(id) AS total FROM patients;");
            rs.next();
            i = rs.getInt("Total");
            i++;
            System.out.println("ID automatically assigned: " + i);
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public String firstName() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter customers first name");
            String f = sc.next();
            return f;
        } catch (Exception e) {
            System.out.println(e);
            sql recurse = new sql();
            recurse.firstName();
        }
        return null;
    }

    public String secondName() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter customers surname name");
            String f = sc.next();
            return f;
        } catch (Exception e) {
            System.out.println(e);
            sql recurse = new sql();
            recurse.secondName();
        }
        return null;
    }

    public int moneyOwed() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter customers owed amount");
            int f = sc.nextInt();
            return f;
        } catch (Exception e) {
            System.out.println(e);
            sql recurse = new sql();
            recurse.moneyOwed();
        }
        return 0;
    }

    public void newCustomer() {
        Scanner sc = new Scanner(System.in);
        sql gen = new sql();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO patients VALUES (" + gen.idgen() + ",'" + gen.firstName() + "','" + gen.secondName() + "'," + gen.moneyOwed() + ");");
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients ORDER BY id DESC LIMIT 1;");
            while (rs.next())
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            System.out.println("Would you like to add another user? y/n");
            gen.newCustomerRecurse();
        } catch (Exception e) {
            System.out.println(e);
            main(null);
        }
    }

    public void newCustomerRecurse() {
        Scanner sc = new Scanner(System.in);
        sql gen = new sql();
        String f;
        f = sc.next();
        if (f.equals("y")) {
            gen.newCustomer();
        } else if (f.equals("n")) {
            main(null);
        } else {
            System.out.println("Please enter a valid character y or n");
            gen.newCustomerRecurse();
        }
    }

    public void updateCustomer() {
        try {
            sql pass1 = new sql();
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            System.out.println("Please enter surname of customer you wish to update or type all to list all customers");
            String all = sc.next();
            if (all.equals("all")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM patients;");
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
                }
            } else {

                ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE secondname LIKE '%" + all + "%';");
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
                }
                if (!rs.first()) {
                    System.out.println("No customers found, please search again");
                    updateCustomer();
                } else {
                }
                System.out.println("Please enter ID of customer to update: ");
                rs.close();
                pass1.updateCustomer2();
                updateCustomerRecurse();
            }
            System.out.println("Please enter ID of customer to update: ");
            pass1.updateCustomer2();
            updateCustomerRecurse();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateCustomerRecurse() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Would you like to update another customer? y/n ");
        try {
            String z = sc.next();
            if (z.equals("y")) {
                updateCustomer();
            } else if (z.equals("n")) {
                main(null);
            } else {
                System.out.println("Please enter a valid character y or n");
                updateCustomerRecurse();
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid character y or n");
            updateCustomerRecurse();

        }
    }

    public void updateCustomer2() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            f = sc.nextInt();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
            }
            System.out.println("Is this the correct customer? y/n");
            updateCustomer2Recurse();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateCustomer2Recurse() {
        Scanner sc = new Scanner(System.in);
        String k = sc.next();
        if (k.equals("y")) {
            updateCustomer3();
        } else if (k.equals("n")) {
            System.out.println("Please search again");
            updateCustomer();
        } else {
            System.out.println("Please enter a valid character y or n");
            updateCustomer2Recurse();
        }
    }

    public void updateCustomer3() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            System.out.println("please type which field you would you like to edit");
            System.out.println("firstname");
            System.out.println("secondname");
            System.out.println("moneyowed");
            System.out.println("all");
            updateCustomer4();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateCustomer4() {
        try {
            Scanner sc = new Scanner(System.in);
            String t = sc.next();
            if (t.equals("firstname")) {
                setName();
            } else if (t.equals("secondname")) {
                setName2();
            } else if (t.equals("moneyowed")) {
                setMoney();
            } else if (t.equals("all")) {
                setName3();
                setName4();
                setMoney2();
            } else {
                System.out.println("Invalid command entered, please enter a valid command");
                updateCustomer();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setName() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Current firstname: " + rs.getString(2));
            rs.close();
            System.out.print("Please enter customers updated first name: ");
            stmt.executeUpdate("UPDATE patients SET firstname='" + sc.next() + "'WHERE id=" + f + ";");
            System.out.println("Customers first name successfully updated");
            rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Customers new first name: " + rs.getString(2));
            updateCustomerRecurse();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setName2() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Current secondname: " + rs.getString(3));
            rs.close();
            System.out.print("Please enter customers updated second name: ");
            stmt.executeUpdate("UPDATE patients SET secondname='" + sc.next() + "'WHERE id=" + f + ";");
            System.out.println("Customers second name successfully updated");
            rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Customers new second name: " + rs.getString(3));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setMoney() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Current amount owed: " + rs.getString(4));
            rs.close();
            System.out.print("Please enter customers updated amount owed: ");
            stmt.executeUpdate("UPDATE patients SET moneyowed='" + sc.nextDouble() + "'WHERE id=" + f + ";");
            System.out.println("Customers amount owed successfully updated");
            rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Customers new second name: " + rs.getString(4));
            updateCustomerRecurse();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setName3() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Current firstname: " + rs.getString(2));
            rs.close();
            System.out.print("Please enter customers updated first name: ");
            stmt.executeUpdate("UPDATE patients SET firstname='" + sc.next() + "'WHERE id=" + f + ";");
            System.out.println("Customers first name successfully updated");
            rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Customers new first name: " + rs.getString(2));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setName4() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Current secondname: " + rs.getString(3));
            rs.close();
            System.out.print("Please enter customers updated second name: ");
            stmt.executeUpdate("UPDATE patients SET secondname='" + sc.next() + "'WHERE id=" + f + ";");
            System.out.println("Customers second name successfully updated");
            rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Customers new second name: " + rs.getString(3));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setMoney2() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Current amount owed: " + rs.getString(4));
            rs.close();
            System.out.print("Please enter customers updated amount owed: ");
            stmt.executeUpdate("UPDATE patients SET moneyowed='" + sc.nextDouble() + "'WHERE id=" + f + ";");
            System.out.println("Customers amount owed successfully updated");
            rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            System.out.println("Customers new second name: " + rs.getString(4));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeCustomer() {
        try {
            sql pass1 = new sql();
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            System.out.println("Please enter surname of customer you wish to remove or type all to list all customers");
            String all = sc.next();
            if (all.equals("all")) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM patients;");
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
                }
            } else {

                ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE secondname LIKE '%" + all + "%';");
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
                }
                if (!rs.first()) {
                    System.out.println("No customers found, please search again");
                    removeCustomer();
                } else {
                }
                System.out.println("Please enter ID of customer to remove: ");
                rs.close();
                pass1.removeCustomer2();
                removeCustomerRecurse();
            }
            System.out.println("Please enter ID of customer to remove: ");
            pass1.removeCustomer2();
            removeCustomerRecurse();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void removeCustomerRecurse() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Would you like to remove another customer? y/n ");
        try {
            String z = sc.next();
            if (z.equals("y")) {
                removeCustomer();
            } else if (z.equals("n")) {
                main(null);
            } else {
                System.out.println("Please enter a valid character y or n");
                removeCustomerRecurse();
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid character y or n");
            removeCustomerRecurse();

        }
    }

    public void removeCustomer2() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            f = sc.nextInt();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
            }
            System.out.println("Is this the correct customer? y/n");
            removeCustomer2Recurse();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeCustomer2Recurse() {
        Scanner sc = new Scanner(System.in);
        String h = sc.next();
        if (h.equals("y")) {
            removeCustomer3();
        } else if (h.equals("n")) {
            System.out.println("Please search again");
            removeCustomer();
        } else {
            System.out.println("Please enter a valid character y or n");
            removeCustomer2Recurse();
        }
    }

    public void removeCustomer3() {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.187.176.141:3306/testing?verifyServerCertificate=false&useSSL=true", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients WHERE id =" + f + ";");
            rs.next();
            sql setter = new sql();
            setter.fname = rs.getString(2);
            setter.sname = rs.getString(3);
            System.out.println("are you sure you would like to delete customer " + rs.getString(2) + rs.getString(3) + "? y/n ");
            String g = sc.next();
            if (g.equals("y")) {
                stmt.executeUpdate("DELETE FROM patients WHERE id =" + f + ";");
                System.out.println("Customer " + setter.fname + "" + setter.sname + " successfully deleted");
                removeCustomerRecurse();
            }
            else if (g.equals("n")){
                removeCustomer();
            }
            else {
                System.out.println("Please enter a valid character");
                removeCustomer3();
            }

        } catch (Exception e) {
            System.out.println(e);
            removeCustomer3();
        }
    }
}









