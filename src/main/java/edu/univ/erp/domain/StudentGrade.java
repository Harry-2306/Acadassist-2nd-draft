package edu.univ.erp.domain;

public class StudentGrade {
    public String sectionId;
    public String userId;
    public int quiz;
    public int assignment;
    public int project;
    public int midsem;
    public int endsem;
    public String semester;
    public String courseid;
    public StudentGrade(String sectionId, String userId, int quiz, int assignment, int project, int midsem, int endsem,String semester,String courseid) {
        this.sectionId = sectionId;
        this.userId = userId;
        this.quiz = quiz;
        this.assignment = assignment;
        this.project = project;
        this.midsem = midsem;
        this.endsem = endsem;
        this.semester=semester;
        this.courseid=courseid;
    }
    public double getTotalMarks() {
        return 0.15 * quiz + 0.15 * assignment + 0.2 * project + 0.2 * midsem + 0.3 * endsem;
    }

}
