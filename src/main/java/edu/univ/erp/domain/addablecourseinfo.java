package edu.univ.erp.domain;

public class addablecourseinfo {
     String coursecode="";
     String coursetitle="";
     String prerequisite="";
     int credits;
     int currentcapacity=0;
     int maxcapacity=0;
     String instructor="";
     boolean selected=false;
    //table info for the enrollments table
     String sectionid="";
     String timings="";
     String venue="";
     String instructorid="";


    public addablecourseinfo(String coursecode,String coursetitle,String prerequisite,int credits,int currentcapacity,int maxcapacity,String instructor,String sectionid,String timings,String venue,String instructorid){
        this.coursecode=coursecode;
        this.currentcapacity=currentcapacity;
        this.coursetitle=coursetitle;
        this.prerequisite=prerequisite;
        this.credits=credits;
        this.maxcapacity=maxcapacity;
        this.instructor=instructor;

        this.sectionid=sectionid;
        this.timings=timings;
        this.venue=venue;
        this.instructorid=instructorid;


    }
    public addablecourseinfo(){

    }

    public boolean isempty(){
        return !this.coursetitle.isEmpty() || !this.coursecode.isEmpty() ||  !this.instructor.isEmpty() || !this.prerequisite.isEmpty()  ;
    }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public String getCourseCode() { return this.coursecode; }
    public String getCourseTitle() { return this.coursetitle; }
    public String getPrerequisite() { return this.prerequisite; }
    public int getCredits() { return this.credits; }
    public int getCurrentCapacity() { return this.currentcapacity; }
    public int getMaxCapacity() { return this.maxcapacity; }
    public String getInstructor() { return this.instructor; }
    public String getSectionId() { return this.sectionid; }
    public String getTimings() { return this.timings; }
    public String getVenue() { return this.venue; }
    public String getInstructorId() { return this.instructorid; }

}
