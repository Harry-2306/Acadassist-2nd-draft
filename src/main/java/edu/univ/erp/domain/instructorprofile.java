package edu.univ.erp.domain;

public class instructorprofile {
    public String name="";
    public String dob="";
    public String occupation="";
    public String department="";

    public instructorprofile(String name,String dob,String occupations,String department){
        this.name=name;
        this.dob=dob;
        this.occupation=occupations;
        this.department=department;
    }
    public boolean isempty(){
        if(this.name.isEmpty()&&this.dob.isEmpty()&&this.occupation.isEmpty()&&this.department.isEmpty()){
            return true;
        }
        return false;
    }
}

