package edu.univ.erp.service;

import edu.univ.erp.data.DatabasePool;
import edu.univ.erp.data.INSERT;
import edu.univ.erp.data.Update;
import edu.univ.erp.domain.StudentGrade;
import edu.univ.erp.domain.addablecourseinfo;
import edu.univ.erp.domain.sectionlogistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PUT {

    public static void password(String password,String username) throws SQLException {
        Update.password(password,username);
    }

    public static void enrollments(String section_id) throws SQLException {
        Update.enrollments(section_id);
    }

    public static void gradedsection(String section_id) throws SQLException {
        Update.enrollments(section_id);
    }

    public static void evaluations(ArrayList<StudentGrade> array,String sectionid,String courseid,String credits,String title) throws SQLException {
        INSERT.evaluations(array,sectionid,courseid,credits,title);
    }

    public static void grades(ArrayList<StudentGrade> array,String sectionid,String courseid,String credits,String title) throws SQLException {
        INSERT.grades(array,sectionid,courseid,credits,title);
    }
//    public static void grades()

    public static void maintainenceval(boolean val) throws SQLException {
        Update.maintainencestat(val);
    }

    public static void secinfo(ArrayList<sectionlogistics> input) throws SQLException,IllegalArgumentException {
        Update.seclog(input);
    }

    public static void inccap(ArrayList<addablecourseinfo> e) throws SQLException {
        Update.inccap(e);
    }

    public static void main(String[] args) throws SQLException {
        PUT.gradedsection("SEC101");
    }




}
