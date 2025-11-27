package edu.univ.erp.domain;

public class Courseinfo {
    public String coursecode;
    public String coursetitle;
    public String credits;
    public String prerequisites;

    public Courseinfo(String coursecode,String coursetitle,String credits,String prerequisites){
        this.coursecode=coursecode;
        this.coursetitle=coursetitle;
        this.credits=credits;
        this.prerequisites=prerequisites;
    }

    public boolean isempty(){
        if(this.coursecode.isEmpty()&&this.coursetitle.isEmpty()&&this.credits.isEmpty()&&this.prerequisites.isEmpty()){
            return true;
        }
        return false;
    }
}
