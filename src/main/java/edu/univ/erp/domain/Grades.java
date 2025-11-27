package edu.univ.erp.domain;

import java.util.ArrayList;

public class Grades {

    public ArrayList<ArrayList<String>>semester1=new ArrayList<>();
    public ArrayList<ArrayList<String>>semester2=new ArrayList<>();
    public ArrayList<ArrayList<String>>semester3=new ArrayList<>();
    public double sgpa1=0;
    public double sgpa2=0;
    public double sgpa3=0;
    public double cgpa=0;

    public Grades(ArrayList<ArrayList<String>>semester1,ArrayList<ArrayList<String>>semester2,ArrayList<ArrayList<String>>semester3,double sgpa1,double sgpa2,double sgpa3){
        this.semester1=semester1;
        this.semester2=semester2;
        this.semester3=semester3;
        this.sgpa1=sgpa1;
        this.sgpa2=sgpa2;
        this.sgpa3=sgpa3;
        this.cgpa=Math.round((((sgpa1+sgpa2+sgpa3)/3)*100)/100);

    }
    public Grades(){

    }

    public boolean isempty(){
        return this.semester1.isEmpty() && this.semester2.isEmpty() && this.semester3.isEmpty();
    }



}
