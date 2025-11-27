package edu.univ.erp.data;

import edu.univ.erp.domain.StudentGrade;
import edu.univ.erp.domain.addablecourseinfo;
import edu.univ.erp.service.GradeUploader;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static edu.univ.erp.service.GradeUploader.markstocg;

public class INSERT {
    public static void evaluations(ArrayList<StudentGrade> array, String sectionid, String courseid, String credits, String title) throws SQLException {
        Connection connection = DatabasePool.geterpdbConnection();
        String sql = "INSERT INTO evaluations (courseid, userid, quiz, assignment, project, midsem, endsem, sectionid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        for (StudentGrade s : array) {
            ps.setString(1, s.courseid);
            ps.setString(2, s.userId);
            ps.setInt(3, s.quiz);
            ps.setInt(4, s.assignment);
            ps.setInt(5, s.project);
            ps.setInt(6, s.midsem);
            ps.setInt(7, s.endsem);
            ps.setString(8,sectionid);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
        connection.close();
    }


    public static void grades(ArrayList<StudentGrade> array, String sectionid, String courseid, String credits, String title) throws SQLException {
        Connection connection = DatabasePool.geterpdbConnection();
        String sql = "INSERT INTO grades "
                + "(courseid, credits, cgpa, grade, semester, title, userid) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "credits = VALUES(credits), "
                + "cgpa = VALUES(cgpa), "
                + "grade = VALUES(grade), "
                + "semester = VALUES(semester), "
                + "title = VALUES(title)";

        PreparedStatement ps = connection.prepareStatement(sql);



        for (StudentGrade s : array) {
            ps.setString(1, courseid);
            ps.setInt(2, Integer.parseInt(credits));
            double totalmarks = (0.15*s.quiz)+(0.15*s.assignment)+(0.2*s.project)+(0.2*s.midsem)+(0.3*s.endsem);
            int cg = GradeUploader.markstocg(totalmarks);

            ps.setInt(3, cg);
            ps.setString(4, GradeUploader.cgtograde(cg));
            ps.setString(5, s.semester);
            ps.setString(6, title);
            ps.setString(7, s.userId);

            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();
        connection.close();
    }

    public static void studata(String id,String name,String password,String program,String rollno) throws SQLException {
        Connection connection= DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="INSERT INTO students (user_id, name, program,roll_no,DOB,credits,year) VALUES (?, ?, ?, ?,'1997-04-24',50,'Freshman')";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1,id);
        ps.setString(2,name);
        ps.setString(3,program);
        ps.setInt(4,Integer.parseInt(rollno));
        ps.executeUpdate();
        connection.close();

        Connection connection1=DatabasePool.getauthdbConnection();
        String query1="Insert into credentials values(?,?,'Student',?,false);";
        PreparedStatement ps1=connection1.prepareStatement(query1);
        ps1.setString(1,id);
        ps1.setString(2,name);
        ps1.setString(3, BCrypt.hashpw(password,BCrypt.gensalt()));
        ps1.executeUpdate();
        connection1.close();
    }

    public static void instdata(String id,String name,String password,String program) throws SQLException {
        Connection connection= DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="INSERT INTO instructors (user_id, department, Occupation,DOB,name) VALUES (?,?,'Assistant Prof','1997-04-24',?)";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1,id);
        ps.setString(2,program);
        ps.setString(3,name);
        ps.executeUpdate();
        connection.close();

        Connection connection1=DatabasePool.getauthdbConnection();
        String query1="Insert into credentials values(?,?,'Instructor',?,false);";
        PreparedStatement ps1=connection1.prepareStatement(query1);
        ps1.setString(1,id);
        ps1.setString(2,name);
        ps1.setString(3, BCrypt.hashpw(password,BCrypt.gensalt()));
        ps1.executeUpdate();
        connection1.close();
    }

    public static void admdata(String id,String name,String password,String yoe) throws SQLException {
        Connection connection= DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="INSERT INTO admin (userid, name, yoe,dob,permaddr,curraddr) VALUES (?,?,?,'1997-04-24','Rohini','Rohtak');";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1,id);
        ps.setString(2,name);
        ps.setInt(3,Integer.parseInt(yoe));
        ps.executeUpdate();
        connection.close();

        Connection connection1=DatabasePool.getauthdbConnection();
        String query1="Insert into credentials values(?,?,'Admin',?,false);";
        PreparedStatement ps1=connection1.prepareStatement(query1);
        ps1.setString(1,id);
        ps1.setString(2,name);
        ps1.setString(3, BCrypt.hashpw(password,BCrypt.gensalt()));
        ps1.executeUpdate();
        connection1.close();
    }

    public static void section(String courseid,String sectionid,String coursetitle,String instructorid,String instructorname,String venue,String timings,String capacity) throws SQLException {
        Connection connection= DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="INSERT INTO sections VALUES (?,?,?,?,?,0,?,0,?,?);";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime lt = LocalTime.parse(timings, formatter);

        Time sqlTime = Time.valueOf(lt);

        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1,sectionid);
        ps.setString(2,courseid);
        ps.setString(3,coursetitle);
        ps.setString(4,instructorid);
        ps.setString(5,instructorname);
        ps.setInt(6,Integer.parseInt(capacity));
        ps.setString(7,venue);
        ps.setTime(8,sqlTime);
        ps.executeUpdate();
        connection.close();
    }

    public static void course(String coursecode,String coursetitle,int credits,String prerequisite) throws SQLException {
        Connection connection= DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="INSERT INTO courses VALUES (?,?,?,?);";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1,coursecode);
        ps.setString(2,coursetitle);
        ps.setInt(3,credits);
        if(prerequisite.contentEquals("NA")){
            ps.setNull(4,java.sql.Types.VARCHAR);
        }else{
            ps.setString(4,prerequisite);
        }
        ps.executeUpdate();
        connection.close();
    }

    public static void newenrollments(ArrayList<addablecourseinfo> input,String id) throws SQLException {
        Connection connection= DatabasePool.geterpdbConnection();
        String query="insert into enrollments(sectionid,courseid,venue,timings,isgraded,coursetitle,userid,instructor) values(?,?,?,?,0,?,?,?);";
        PreparedStatement ps=connection.prepareStatement(query);
        for(addablecourseinfo course:input){
            ps.setString(1,course.getSectionId());
            ps.setString(2,course.getCourseCode());
            ps.setString(3,course.getVenue());
            ps.setTime(4,Time.valueOf(course.getTimings()));
            ps.setString(5,course.getCourseTitle());
            ps.setString(6,id);
            ps.setString(7,course.getInstructor());

            ps.addBatch();
        }
        ps.executeBatch();
    }

    public static void main(String[] args){

        System.out.println(Time.valueOf(LocalTime.parse("9:30")));
    }
}
