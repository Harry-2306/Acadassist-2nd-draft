package edu.univ.erp.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Write {
    public static void message(String id,String type,String message) throws SQLException {
            Connection connection = DatabasePool.geterpdbConnection();
            Statement statement = connection.createStatement();
            String query = "Insert into messages(userid,type,message) values('" + id + "','" + type + "','" + message + "');";
            statement.executeUpdate(query);
            connection.close();

    }

//    public static void main(String args[])  {
//        Write.message("stu1", "Hostel&Mess", "Rats in my soup");

//    }

}
