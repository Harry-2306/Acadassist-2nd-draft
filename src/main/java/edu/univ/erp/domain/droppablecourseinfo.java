package edu.univ.erp.domain;

public class droppablecourseinfo {
    public boolean selected=false;
    public String courseCode;
    public String courseTitle;
    public String instructor;
    public String venue;

    public droppablecourseinfo( String courseCode,
                               String courseTitle, String instructor, String venue) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.instructor = instructor;
        this.venue = venue;
    }
}

