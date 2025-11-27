package edu.univ.erp.data;

import com.mysql.cj.Messages;
import com.mysql.cj.protocol.x.Notice;
import edu.univ.erp.domain.*;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.plaf.nimbus.State;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class Fetch {
    private final static String urlauth="jdbc:mysql://127.0.0.1:3306/authdb";
    private final static String urlerp="jdbc:mysql://127.0.0.1:3306/erpdb";
    private final static String rootusername="root";
    private final static String Password="1234";

    public static boolean userexists(String username) throws SQLException {
        Connection connection=DatabasePool.getauthdbConnection();
        Statement statement=connection.createStatement();
        String query="Select Userid from credentials where Userid='"+username+"';";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return true;
        }
        connection.close();
        return false;
    }

    public static Person authdetails(String username, String pswd){
        String role="";
        String name="";
        String retrievedpswd="";
        //set up the connection to the database
        try{
            Connection connection= DatabasePool.getauthdbConnection();
            Statement statement=connection.createStatement();
            String query="Select Role,name,password_hash from credentials where Userid = '"+username+ "';";
            ResultSet output=statement.executeQuery(query);


            while(output.next()){
                role=output.getString("Role");
                name=output.getString("name");
                retrievedpswd=output.getString("password_hash");
            }
            if(!BCrypt.checkpw(pswd,retrievedpswd)){
                role="";
                name="";
            }
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //or maybe throw another exception idk
        }finally {

            return new Person(name,role);
        }

    }

    public static int credits(String id){
        int credits=0;
        try{
            Connection connection=DatabasePool.geterpdbConnection();
            Statement statement=connection.createStatement();
            String query="Select Credits from students where user_id='"+id+"';";
            ResultSet output=statement.executeQuery(query);
            while(output.next()){
              credits=output.getInt("credits");
            }
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            return credits;
        }
    }

    public static Student student_det(String id) throws Exception{
        String name,program,year,roll_no,DOB;
        name=program=year=roll_no=DOB="";
        try{
            Connection connection=DatabasePool.geterpdbConnection();
            Statement statement=connection.createStatement();
            String query="Select name,program,year,roll_no,DOB from students where user_id='"+id+"';";

            ResultSet rs= statement.executeQuery(query);

            while (rs.next()){
                name=rs.getString("name");
                program=rs.getString("program");
                year=rs.getString("year");
                roll_no=Integer.toString(rs.getInt("roll_no"));
                DOB=rs.getString("DOB");
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception();
        }finally {
            return new Student(name,roll_no,program,year,DOB);
        }
    }

    public static ResultSet grades(String id){
        try{
            Connection connection=DatabasePool.geterpdbConnection();
            Statement statement=connection.createStatement();
            String query="Select courseid,credits,cgpa,grade,semester,title from grades where userid='"+id+"' order by semester";
            ResultSet rs=statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Evaluations evaluationcomp(String course_id,String id){
        String quiz,assignment,project,midsem,endsem;
        quiz="";assignment="";project="";midsem="";endsem="";
        try{
            Connection connection=DatabasePool.geterpdbConnection();
            Statement statement=connection.createStatement();
            String query="Select quiz,assignment,project,midsem,endsem from evaluations where courseid='"+course_id+"' AND userid='"+id+"';";
            ResultSet rs= statement.executeQuery(query);
            while(rs.next()){
                quiz=rs.getString("quiz");
                assignment=rs.getString("assignment");
                project=rs.getString("project");
                midsem=rs.getString("midsem");
                endsem=rs.getString("endsem");
            }
            connection.close();
            return new Evaluations(assignment,quiz,project,midsem,endsem);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Courseinfo> coursescatalog() throws SQLException {
        ArrayList<Courseinfo> result=new ArrayList<>();
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="Select * from courses;";
        ResultSet rs=statement.executeQuery(query);
        if(rs!=null){
            while (rs.next()) {
                String coursecode = rs.getString("coursecode");
                String coursetitle = rs.getString("coursetitle");
                String credits = rs.getString("credits");
                String prerequisite = rs.getString("prerequisite") == null ? "None" : rs.getString("Prerequisite");
                Courseinfo course = new Courseinfo(coursecode, coursetitle, credits, prerequisite);
                result.add(course);
            }
        }
        return result;
    }

    public static ArrayList<sectioninfo> mycourses(String id) throws SQLException {
        ArrayList<sectioninfo> output = new ArrayList<>();
        Connection connection = DatabasePool.geterpdbConnection();
        if(connection==null){
            System.out.println("this is the problem");
        }
        Statement statement = connection.createStatement();
        String query = "select courseid,coursetitle,venue,instructor from enrollments where userid='" + id + "';";
        System.out.println("'" + id + "'");
        System.out.println(query);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String courseid = rs.getString("courseid");
            String title = rs.getString("coursetitle");
            String venue = rs.getString("venue");
            String instructor = rs.getString("instructor");
            sectioninfo object = new sectioninfo(courseid, title, venue, instructor);
            output.add(object);
        }

        connection.close();
        return output;

    }

    public static ArrayList<addablecourseinfo> addablecourses() throws SQLException{
        ArrayList<addablecourseinfo> output=new ArrayList<>();

            Connection connection=DatabasePool.geterpdbConnection();
            Statement statement=connection.createStatement();
            String query = "SELECT sections.coursecode, sections.title, sections.instname, " +
                    "courses.prerequisite, courses.credits, sections.currcap, sections.maxcap, " +
                    "sections.sectionid, sections.venue, sections.timings, sections.instructorid " +
                    "FROM sections " +
                    "INNER JOIN courses ON sections.coursecode = courses.coursecode " +
                    "WHERE sections.isgraded = 0;";
            ResultSet rs=statement.executeQuery(query);
            while(rs.next()){
                String coursecode,coursetitle,sectionid,instructorid,instructor,timings,venue,prerequisite;
                int currentcapacity,maxcapacity,credits;
                coursecode=rs.getString("coursecode");
                coursetitle=rs.getString("title");
                sectionid=rs.getString("sectionid");
                instructorid=rs.getString("instructorid");
                instructor=rs.getString("instname");
                currentcapacity=rs.getInt("currcap");
                maxcapacity=rs.getInt("maxcap");
                timings=rs.getString("timings");
                venue=rs.getString("venue");
                prerequisite=rs.getString("prerequisite");
                System.out.println(prerequisite);
                credits=rs.getInt("credits");
                output.add(new addablecourseinfo(coursecode,coursetitle,prerequisite,credits,currentcapacity,maxcapacity,instructor,sectionid,timings,venue,instructorid));
            }
            return output;

    }

    public static instructorprofile inst_details(String id) throws SQLException {
        String name="";String dob="";String occupation="";String department="";
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select department,occupation,DOB from instructors where user_id='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            dob=rs.getString("DOB");
            occupation=rs.getString("Occupation");
            department=rs.getString("department");
        }
        return new instructorprofile(name,dob,occupation,department);


    }


    public static ArrayList<mysection> mysec(String id) throws SQLException {
        ArrayList<mysection> output=new ArrayList<>();
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement= connection.createStatement();;
        String query="select sectionid,title from sections where instructorid='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            String sectionid=rs.getString("sectionid");
            String coursetitle=rs.getString("title");
            output.add(new mysection(sectionid,coursetitle));
        }
        return output;
    }

    public static boolean isgraded(String id) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select sectionid from sections where sectionid='"+id+"' and isgraded=false;";
        ResultSet rs=statement.executeQuery(query);
        return !rs.next();
    }

    public static String coursecode(String secid) throws SQLException {
        String output="";
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="Select coursecode from sections where sectionid='"+secid+"';";
        System.out.println(query);
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            output=rs.getString("coursecode");
        }
        return output;
    }

    public static String creditsfromcourse(String id) throws SQLException {
        String output="";
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select credits from courses where coursecode='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            output=rs.getString("credits");
        }
        return output;
    }

    public static String coursetitle(String id) throws SQLException{
        String output="";
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select title from sections where sectionid='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            output=rs.getString("title");
        }
        connection.close();
        return output;
    }

    public static ArrayList<StudentGrade> allmarks(String sectionid) throws SQLException {
        ArrayList<StudentGrade> output=new ArrayList<>();
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select quiz,assignment,project,midsem,endsem from evaluations where sectionid='"+sectionid+"';";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
             String sectionId=sectionid;
             String userId="";
             int quiz= rs.getInt("quiz");
             int assignment=rs.getInt("assignment");
             int project=rs.getInt("project");
             int midsem=rs.getInt("midsem");
             int endsem=rs.getInt("endsem");
             String semester="";
             String courseid="";
             output.add(new StudentGrade(sectionId,userId,quiz,assignment,project,midsem,endsem,semester,courseid));
        }
        return output;

    }

    public static admninfo admin(String id) throws SQLException {

        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="Select yoe,dob,Permaddr,curraddr from admin where userid='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            return new admninfo(rs.getInt("yoe"),rs.getString("curraddr"),rs.getString("Permaddr"),rs.getString("dob"));
        }
        return new admninfo(-1,"","","");
    }


    public static boolean maintstat() throws SQLException {
        boolean output;
        Connection connection=DatabasePool.getauthdbConnection();
        Statement statement=connection.createStatement();
        String query="select * from maintainence_mode;";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            output=rs.getBoolean("status");
            return output;
        }
        return true;

    }

    public static boolean id(String id) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select user_id from students where user_id='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return false;
        }
        connection.close();
        return true;

    }

    public static boolean checkuniqroll(String roll) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select user_id from students where roll_no="+roll+";";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return false;
        }
        connection.close();
        return true;
    }

    public static boolean checkuniqinstid(String id) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select user_id from instructors where user_id='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return false;
        }
        connection.close();
        return true;
    }

    public static boolean checkuniqadmid(String id) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select userid from admin where userid='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return false;
        }
        connection.close();
        return true;
    }

    public static boolean coursecodeexists(String id) throws SQLException{
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select coursecode from courses where coursecode='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return true;
        }
        connection.close();
        return false;
    }



   public static String  coursetitlefromcourse(String id) throws SQLException{
        String output="";
       Connection connection=DatabasePool.geterpdbConnection();
       Statement statement=connection.createStatement();
       String query="select coursetitle from courses where coursecode='"+id+"';";
       ResultSet rs=statement.executeQuery(query);
       while(rs.next()){
           output=rs.getString("coursetitle");
       }
       return output;
   }

    public static boolean sectionidexists(String id) throws SQLException{
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select sectionid from sections where sectionid='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return true;
        }
        connection.close();
        return false;
    }

    public static boolean instructorexists(String id) throws SQLException{
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select user_id from instructors where user_id='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        if(rs.next()){
            connection.close();
            return true;
        }
        connection.close();
        return false;
    }

    public static String instructorfrominst(String id) throws SQLException{
        String output="";
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select name from instructors where user_id='"+id+"';";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            output=rs.getString("name");
        }
        return output;
    }

    public static ArrayList<sectionlogistics> seclog() throws SQLException {
        ArrayList<sectionlogistics> output=new ArrayList<>();
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select sectionid,coursecode,currcap,maxcap,venue,timings from sections where isgraded=false;";
        ResultSet rs=statement.executeQuery(query);
        while(rs.next()){
            String sectionid=rs.getString("sectionid");
            String coursecode=rs.getString("coursecode");
            int currcap=rs.getInt("currcap");
            int maxcap=rs.getInt("maxcap");
            String venue=rs.getString("venue");
            String timings=rs.getString("timings");
            output.add(new sectionlogistics(coursecode,venue,timings,sectionid,maxcap,currcap));
        }
        return output;
    }

    public static boolean iscompleted(String userid,String coursecode) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select userid from enrollments where courseid=? and userid=?;";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1,coursecode);
        ps.setString(2,userid);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            connection.close();
            return true;
        }
        connection.close();
        return false;
    }

    public static ArrayList<TimeTableRow> timetable(String id) throws SQLException {
        ArrayList<TimeTableRow> output=new ArrayList<>();
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select coursetitle,venue,timings from enrollments where userid='"+id+"' order by timings;";
        ResultSet rs=statement.executeQuery(query);

        while(rs.next()){
            String venue=rs.getString("venue");
            String coursetitle=rs.getString("coursetitle");
            String timings=rs.getString("timings");
            output.add(new TimeTableRow(coursetitle,venue,timings));
        }
        connection.close();
        return output;
    }

    public static ArrayList<droppablecourseinfo> details(String id) throws SQLException {
        ArrayList<droppablecourseinfo> output=new ArrayList<>();
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String sql =
                "SELECT s.courseid, c.coursetitle, s.instructor, s.venue " +
                        "FROM courses c " +
                        "INNER JOIN enrollments s ON s.courseid = c.coursecode " +
                        "WHERE s.userid = ?";



        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, id);   // userId is the value you want to filter by
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String courseid=rs.getString("courseid");
            String coursetitle=rs.getString("coursetitle");
            String instructor=rs.getString("instructor");
            String venue =rs.getString("venue");
            output.add(new droppablecourseinfo(courseid,coursetitle,instructor,venue));
        }
        return output;
    }

    public static ArrayList<message> messages() throws SQLException {
        ArrayList<message> output=new ArrayList<>();
        Connection connection=DatabasePool.geterpdbConnection();
        Statement statement=connection.createStatement();
        String query="select * from messages;";
        ResultSet rs=statement.executeQuery(query);

        while(rs.next()){
            String id=rs.getString("userid");
            String type=rs.getString("type");
            String message=rs.getString("message");
            output.add(new message(id,type,message));
        }
        return output;

    }


    public static void main(String[] args) throws Exception {
    }




}
