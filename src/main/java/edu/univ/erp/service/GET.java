package edu.univ.erp.service;

import edu.univ.erp.data.DatabasePool;
import edu.univ.erp.data.Fetch;
import edu.univ.erp.domain.*;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.unsucessfulretrievalexception;

import javax.swing.plaf.nimbus.State;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class GET {

    public static Student stu_details(String id) throws unsucessfulretrievalexception {
        try {
          return  Fetch.student_det(id);
        } catch (Exception e) {
            throw new unsucessfulretrievalexception(Messages.TAB_OPEN_ERROR);
        }
    }
    public static Grades grades(String id) throws SQLException {
        ArrayList<ArrayList<String>> semester1,semester2,semester3;
        semester1=new ArrayList<>();
        semester2=new ArrayList<>();
        semester3=new ArrayList<>();
        double sg1,sg2,sg3;
        sg1=0;
        sg2=0;
        sg3=0;
        ResultSet output=Fetch.grades(id);

        while(output.next()){
            String semester=output.getString("semester");
            ArrayList<String> infoarray=new ArrayList<>();
            String courseid=output.getString("courseid");
            infoarray.add(courseid);
            String title=output.getString("title");
            infoarray.add(title);
            String grade=output.getString("grade");
            infoarray.add(grade);
            String gpa=output.getString("cgpa");
            infoarray.add(gpa);
            String credits=output.getString("credits");
            infoarray.add(credits);

            infoarray.add(courseid);infoarray.add(title);infoarray.add(grade);infoarray.add(gpa);infoarray.add(credits);
            if(semester.equals("Semester 1")){
                semester1.add(infoarray);
                sg1+=Integer.parseInt(gpa);
            } else if (semester.equals("Semester 2")) {
                semester2.add(infoarray);
                sg2+=Integer.parseInt(gpa);
            }else if(semester.equals("Semester 3")){
                semester3.add(infoarray);
                sg3+=Integer.parseInt(gpa);
            }

        }

        if(!semester1.isEmpty()){
            sg1=sg1/semester1.size();
        }if(!semester2.isEmpty()){
            sg2=sg2/semester2.size();
        }if(!semester3.isEmpty()){
            sg3=sg3/semester3.size();
        }
        System.out.println(semester1);
        System.out.println(semester2);
        System.out.println(semester3);
        return new Grades(semester1,semester2,semester3,sg1,sg2,sg3);
    }

    public static Evaluations courseeval(String courseid, String id) throws unsucessfulretrievalexception {

        try{
            Evaluations output=Fetch.evaluationcomp(courseid,id);
            System.out.println(output.quiz);
            return output;
        } catch (Exception e) {
            throw new unsucessfulretrievalexception(Messages.TAB_OPEN_ERROR);
        }

    }


    public static Courseinfo[] courselist() throws SQLException {
        Courseinfo[] array=Fetch.coursescatalog().toArray(new Courseinfo[0]);
        return array;
    }

    public static sectioninfo[] mycourses(String id) throws SQLException {
        ArrayList<sectioninfo> output=Fetch.mycourses(id);
        sectioninfo[] auxarray={new sectioninfo("","","","")};
        if(output.isEmpty()){
            return auxarray;
        }
        return output.toArray(new sectioninfo[0]);
    }

    public static addablecourseinfo[] addablecourses() throws SQLException{
        ArrayList<addablecourseinfo> output=Fetch.addablecourses();
        addablecourseinfo[] auxarray={new addablecourseinfo()};
        return output.isEmpty()?auxarray:output.toArray(new addablecourseinfo[0]);
    }

    public static instructorprofile instdetails(String id) throws SQLException {
        return Fetch.inst_details(id);
    }

    public static ArrayList<mysection> mysections(String id) throws SQLException {
        return Fetch.mysec(id);
    }

    public static boolean isgraded(String secid) throws SQLException {
        return Fetch.isgraded(secid);
    }
    public static String coursecode(String sectionid) throws SQLException{
        return Fetch.coursecode(sectionid);
    }

    public static String credits(String coursecode) throws SQLException {
        return Fetch.creditsfromcourse(coursecode);
    }

    public static String coursetitle(String sectionid) throws SQLException{
        return Fetch.coursetitle(sectionid);
    }
    public static ArrayList<StudentGrade> allmarks(String sectionid) throws SQLException {
        return Fetch.allmarks(sectionid);
    }

    public static admninfo admin(String id) throws SQLException{
        return Fetch.admin(id);
    }

    public static boolean maintstat() throws SQLException{
        return Fetch.maintstat();
    }

    public static boolean checkuniqid(String id) throws SQLException{
        return Fetch.id(id);
    }

    public static boolean checkuniqroll(String rollno) throws SQLException{
        return Fetch.checkuniqroll(rollno);
    }
    public static boolean checkuniqinstid(String id) throws SQLException{
        return Fetch.checkuniqinstid(id);
    }

    public static boolean checkuniqadmid(String id) throws SQLException {
        return Fetch.checkuniqadmid(id);
    }

    public static boolean checkcoursecodeexistence(String id) throws SQLException{
        return Fetch.coursecodeexists(id);
    }

    public static boolean checkinstructorexists(String id) throws SQLException {
        return Fetch.instructorexists(id);
    }

    public static boolean checksectionexists(String id) throws SQLException{
        return Fetch.sectionidexists(id);
    }
    public static String courstitle(String id) throws SQLException {
        return Fetch.coursetitlefromcourse(id);
    }
    public static String instname(String id) throws SQLException{
        return Fetch.instructorfrominst(id);
    }

    public static ArrayList<sectionlogistics> seclog() throws SQLException {
        return Fetch.seclog();
    }

    public static ArrayList<addablecourseinfo> addblcourses() throws SQLException {
        return Fetch.addablecourses();
    }

    public static boolean iscompleted(String userid,String courseid) throws SQLException{
        return Fetch.iscompleted(userid,courseid);
    }


    public static ArrayList<TimeTableRow> timetable(String id) throws SQLException {
        return Fetch.timetable(id);
    }

    public static void main(String[] args){
        try{
            addablecourseinfo[] demo=GET.addablecourses();
            System.out.println(GET.checkuniqid("200001"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkifinsec(String courseid,String userid) throws SQLException {
        return Fetch.checkifinsec(courseid,userid);
    }

    public static ArrayList<droppablecourseinfo> details(String id) throws SQLException {
        return Fetch.details(id);
    }

    public static ArrayList<message> messages() throws SQLException {
        return Fetch.messages();
    }

}
