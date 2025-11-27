package edu.univ.erp.data;

import edu.univ.erp.domain.addablecourseinfo;
import edu.univ.erp.domain.sectionlogistics;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Update {

    public static void loginstatus(String id,Boolean status){
        try{
            Connection connection=DatabasePool.getauthdbConnection();
            Statement statement=connection.createStatement();
            String query="Update credentials set status="+status+" where userid='"+id+"'";
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void gradedsection(String section_id) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="Update sections set isgraded=true where sectionid='"+section_id+"';";
        statement.executeUpdate(query);
    }

    public static void inccap(ArrayList<addablecourseinfo> input) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="update sections set currcap=currcap+1 where sectionid=?;";
        PreparedStatement ps=connection.prepareStatement(query);
        for(addablecourseinfo course: input){
            ps.setString(1,course.getSectionId());
            ps.addBatch();
        }
        ps.executeBatch();
        connection.close();
    }

    public static void enrollments(String section_id) throws SQLException {
        Connection connection= DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="Update enrollments set isgraded=true where sectionid='"+section_id+"';";
        statement.executeUpdate(query);
    }

    public static void maintainencestat(boolean val) throws SQLException {
        Connection connection= DatabasePool.getauthdbConnection();
        Statement statement=connection.createStatement();
        String query="Update maintainence_mode set status="+val+";";
        statement.executeUpdate(query);
        connection.close();
    }

    public static void seclog(ArrayList<sectionlogistics> input) throws SQLException,IllegalArgumentException {
        Connection connection= DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="update sections set maxcap=?,venue=?,timings=? where sectionid=?;";
        PreparedStatement ps=connection.prepareStatement(query);
        for(sectionlogistics logistics: input){
            ps.setInt(1,logistics.maxcapacity);
            ps.setString(2,logistics.venue);
            Time sqlTime = Time.valueOf(logistics.timings);
            ps.setTime(3,sqlTime);
            ps.setString(4,logistics.sectionid);
            ps.addBatch();
        }
        ps.executeBatch();
    }

    public static void password(String password,String username) throws SQLException {
        Connection connection= DatabasePool.getauthdbConnection();
        String passwd= BCrypt.hashpw(password,BCrypt.gensalt());
        String query="update credentials set password_hash=? where userid=?;";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1,passwd);
        ps.setString(2,username);
        ps.executeUpdate();
    }


    public static void main(String[] args) throws SQLException {
        Update.maintainencestat(true);
    }

}
