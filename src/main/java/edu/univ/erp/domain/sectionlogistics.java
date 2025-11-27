package edu.univ.erp.domain;

public class sectionlogistics {
    public String coursecode;
    public String venue;
    public String timings;
    public String sectionid;
    public int maxcapacity;
    public int currcapacity;

    public sectionlogistics(String coursecode,String venue,String timings,String sectionid,int maxcapacity,int currcapacity){
        this.coursecode=coursecode;
        this.venue=venue;
        this.timings=timings;
        this.maxcapacity=maxcapacity;
        this.currcapacity=currcapacity;
        this.sectionid=sectionid;
    }
}
