package edu.univ.erp.domain;

public class sectioninfo {
    public String courseid;
    public String coursetitle;
    public String venue;
    public String Instructor;

    public sectioninfo(String courseid,String coursetitle,String venue,String instructor){
        this.courseid=courseid;
        this.Instructor=instructor;
        this.venue=venue;
        this.coursetitle=coursetitle;
    }
    public boolean isempty(){
        if(this.coursetitle.isEmpty()&&this.courseid.isEmpty()&&this.venue.isEmpty()&&this.Instructor.isEmpty()){
            return true;
        }
        return false;
    }
}
