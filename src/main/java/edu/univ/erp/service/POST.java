package edu.univ.erp.service;

import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.INSERT;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.data.Write;
import edu.univ.erp.domain.addablecourseinfo;
import edu.univ.erp.util.CourseRepeatException;
import edu.univ.erp.util.IneligibleException;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.OverloadException;

import java.sql.SQLException;
import java.util.ArrayList;

public class POST {
    public static void message(String id,String type,String message) throws SQLException {
        Write.message(id,type,message);
    }
    public static void selectedcourses(ArrayList<Integer>selectedrows, ArrayList<addablecourseinfo> totalrows, String id, Statevariables statevar) throws SQLException ,Exception{
        try{
            ArrayList<addablecourseinfo> selectedcourses=new ArrayList<>();
            for(int i=0;i<selectedrows.size();i++){

                addablecourseinfo details = totalrows.get(selectedrows.get(i));
                if (details.getCurrentCapacity() + 1 > details.getMaxCapacity()) {
                    throw new OverloadException(Messages.CLASS_FULL);
                }
                String courseid = details.getCourseCode();
                boolean isrepeating = GET.iscompleted(id, details.getCourseCode());
                if (isrepeating) {
                    throw new CourseRepeatException(Messages.DUPLICATE_VALUE);
                }
                if (details.getPrerequisite()!=null) {
                    String prereq = details.getPrerequisite();
                    boolean iscomplete = GET.iscompleted(id, prereq);
                    if (!iscomplete) {
                        throw new IneligibleException(Messages.INELIGIBLE_ERROR);
                    }
                }
                selectedcourses.add(details);
            }

            POST.enrollment(selectedcourses,id);
            PUT.inccap(selectedcourses);
            statevar.setMycourseinfo(GET.mycourses(id));
            statevar.setTt(GET.timetable(id));
            statevar.setDropcourses(GET.details(statevar.getId()));
        } catch (Exception e) {
            throw new Exception(e);

        }
    }

    public static void enrollment(ArrayList<addablecourseinfo> input,String id) throws SQLException {
        INSERT.newenrollments(input,id);
    }

    public static void studata(String id,String name,String password,String program,String rollno) throws SQLException {
        INSERT.studata(id,name,password,program,rollno);
    }

    public static void instdata(String id,String name,String password,String department) throws SQLException {
        INSERT.instdata(id,name,password,department);
    }

    public static void admdata(String id,String name,String password,String yoe) throws SQLException {
        INSERT.admdata(id,name,password,yoe);
    }

    public static void section(String courseid,String sectionid,String coursetitle,String instructorid,String instructorname,String venue,String timings,String capacity) throws SQLException {
        INSERT.section(courseid,sectionid,coursetitle,instructorid,instructorname,venue,timings,capacity);
    }

    public static void course(String coursecode,String coursetitle,int credits,String prerequisite ) throws SQLException {
        INSERT.course(coursecode,coursetitle,credits,prerequisite);
    }
}
