package edu.univ.erp.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;

import java.util.*;

public class Gradestats {
    public List<Double> finalMarks = new ArrayList<>();
    public double averageMark;
    public double maxMark;
    public double minMark;
    public int totalStudents;

    public int gradeACount;
    public int gradeBCount;
    public int gradeCCount;
    public int gradeDCount;
    public int gradeFCount;

    public Gradestats(List<StudentGrade> students) {
        for (StudentGrade s : students) {
            double marks = s.getTotalMarks();
            finalMarks.add(marks);

            if (marks >= 90) gradeACount++;
            else if (marks >= 75) gradeBCount++;
            else if (marks >= 60) gradeCCount++;
            else if (marks >= 50) gradeDCount++;
            else gradeFCount++;
        }

        totalStudents = finalMarks.size();
        maxMark = finalMarks.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        minMark = finalMarks.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        averageMark = finalMarks.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}
