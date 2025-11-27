package edu.univ.erp.domain;

public class Student {
    public String name;
    public String DOB;
    public String Rollno;
    public String program;
    public  String year;

    public Student(String name,String rollno,String program,String year,String DOB){
        this.Rollno=rollno;
        this.DOB=DOB;
        this.year=year;
        this.program=program;
        this.name=name;
    }
}
