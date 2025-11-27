package edu.univ.erp.domain;

public class Evaluations {
    public String assignment;
    public String quiz;
    public String project;
    public String midsem;
    public String endsem;

    public Evaluations(String assignment, String quiz, String project, String midsem, String endsem){
        this.assignment=assignment;
        this.quiz=quiz;
        this.endsem=endsem;
        this.midsem=midsem;
        this.project=project;
    }
}
