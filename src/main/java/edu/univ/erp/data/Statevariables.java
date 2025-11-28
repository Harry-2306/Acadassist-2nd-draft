package edu.univ.erp.data;

import edu.univ.erp.domain.*;
import edu.univ.erp.service.GET;
import edu.univ.erp.util.Messages;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Statevariables{
    //common to all
    public LocalDate expirydate=LocalDate.of(2026,1,1);
    public LocalDate now=LocalDate.of(2025,12,31);
    private String username="Student";
    private String id="";
    private String DOB="YYYY-MM-DD";
    //common to students only
    private String rollno="XXXXXX";
    private String year="ABCDEF";
    private String program="Lorem Ipsum";
    Grades grades=new Grades();
    Evaluations courseeval=new Evaluations("","","","","");
    Courseinfo dummycourse=new Courseinfo("","","","");
    Courseinfo[] courselist={dummycourse};

    sectioninfo mycourseinfo=new sectioninfo("","","","");
    sectioninfo[] mycourselist={mycourseinfo};

    addablecourseinfo addablecourses=new addablecourseinfo();
    addablecourseinfo[] arrayofaddblcourses={addablecourses};

    ArrayList<addablecourseinfo> listofaddables=new ArrayList<>();

    ArrayList<TimeTableRow> tt=new ArrayList<>();

    ArrayList<message> messageArrayList=new ArrayList<>();

    public void setMessageArrayList(ArrayList<message> input) {
        this.messageArrayList=input;
        for(Runnable listener:listeners){
            listener.run();
        }
    }
    public ArrayList<message> getMessageArrayList(){
        return this.messageArrayList;
    }

    public void setTt(ArrayList<TimeTableRow> input){
        this.tt=input;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public ArrayList<TimeTableRow> gettt(){
        return this.tt;
    }

    public void setListofaddables(ArrayList<addablecourseinfo> input){
        this.listofaddables=input;
        for(Runnable listener:listeners){
            listener.run();
        }
    }

    public ArrayList<addablecourseinfo> getListofaddables(){
        return this.listofaddables;
    }

    //for instructors only
    instructorprofile instprofile=new instructorprofile("","","","");

    ArrayList<mysection> mysec=new ArrayList<>();
    //for admin
    admninfo admobj=new admninfo(-1,"","","");
    boolean maintainencecheck= GET.maintstat(); //populate
    ArrayList<sectionlogistics> sections=new ArrayList<>();

    ArrayList<droppablecourseinfo> dropcourses=new ArrayList<>();

    public ArrayList<droppablecourseinfo> getDropcourses(){
        return this.dropcourses;
    }
    public  void setDropcourses(ArrayList<droppablecourseinfo> input){
        this.dropcourses=input;

        for(Runnable listener: listeners){
            listener.run();
        }

    }


    public ArrayList<sectionlogistics> getSections() {
        return this.sections;
    }
    public void setSections(ArrayList<sectionlogistics> input){
        this.sections=input;
        for(Runnable listener:listeners){
            listener.run();
        }
    }

    public Statevariables() throws SQLException {
    }

    public void setMaintainencecheck(boolean input){
        this.maintainencecheck=input;
        for(Runnable listener:listeners){
            listener.run();
        }
    }
    public boolean getmaintainencecheck(){
        return this.maintainencecheck;
    }


    public admninfo getAdmobj(){
        return this.admobj;
    }
    public void setAdmobj(admninfo input){
        this.admobj=input;
        for(Runnable listener: listeners){
            listener.run();
        }
    }


    public ArrayList<mysection> getMysec() {
        return new ArrayList<>(this.mysec);
    }

    public void setMysec(ArrayList<mysection> input){
        this.mysec = new ArrayList<>(input);    // <-- safe copy
        for (Runnable listener : listeners){
            listener.run();
        }
    }



    public instructorprofile getInstprofile(){
        return this.instprofile;
    }

    public void setInstprofile(instructorprofile input){
        this.instprofile=input;
        for(Runnable listener: listeners) {
            listener.run();
        }
    }

    public addablecourseinfo[] getarrayofaddblecourses(){
        return this.arrayofaddblcourses;
    }
    public void setarrayofaddblcourses(addablecourseinfo[] input){
        this.arrayofaddblcourses=input;
        for(Runnable listener:listeners){
            listener.run();
        }
    }

    public void setMycourseinfo(sectioninfo[] input){
        this.mycourselist=input;
        for(Runnable listener:listeners){
            listener.run();
        }
    }
    public sectioninfo[] getMycourseinfo(){
        return this.mycourselist;
    }

    public Courseinfo[] getCourselist(){
        return this.courselist;
    }
    public void setCourselist(Courseinfo[] input){
        this.courselist=input;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public Evaluations getCourseeval(){
        return this.courseeval;
    }
    public void setCourseeval(Evaluations evalcomp){
        this.courseeval=evalcomp;
        for(Runnable listener:listeners) {
            listener.run();
        }
    }



    public Grades getgrades(){
        return this.grades;
    }
    public void setgrades(Grades object){
        this.grades=object;
        for(Runnable listener: listeners){
            listener.run();
        }
    }


    private ArrayList<Runnable> listeners = new ArrayList<>();

    int credits=0;

    public void setDOB(String dob){
        this.DOB=dob;
        for(Runnable listener:listeners){
            listener.run();
        }
    }

    public String getDOB(){
        return this.DOB;
    }

    public void setRollno(String roll){
        this.rollno=roll;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public String getRollno(){
        return this.rollno;
    }

    public void setYear(String year){
        this.year=year;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public String getYear(){
        return this.year;
    }

    public void setProgram(String pgm){
        this.program=pgm;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public String getProgram(){
        return this.program;
    }

    public void setId(String id){
        this.id=id;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public String getId(){
        return this.id;
    }

    public void setCredits(int credits) {
        this.credits = credits;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public int getCredits(){
        return this.credits;
    }

    public void setUsername(String input){
        this.username=input;
        for(Runnable listener: listeners){
            listener.run();
        }
    }

    public void addListener(Runnable listener){
        listeners.add(listener);
    }

    public String getUsername(){
        return this.username;
    }
}

