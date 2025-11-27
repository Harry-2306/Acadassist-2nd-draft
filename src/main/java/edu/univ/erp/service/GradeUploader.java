package edu.univ.erp.service;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.univ.erp.domain.StudentGrade;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.Set;

public class GradeUploader {

    private static final Set<String> ALLOWED_SEMESTERS = Set.of(
            "Semester 1", "Semester 2", "Semester 3"
    );

    public static void uploadGrades(String sectionId,String courseid,String credits,String title) throws SQLException {

        if (GET.isgraded(sectionId)) {
            JOptionPane.showMessageDialog(null,
                    "This section has already been graded!", "Oops!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

        if (!filePath.toLowerCase().endsWith(".csv")) {
            JOptionPane.showMessageDialog(null, "Please select a valid CSV file!",
                    "Invalid File", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<StudentGrade> gradesList = new ArrayList<>();
        HashSet<String> userIds = new HashSet<>();
        int lineNum = 1;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] row;

            // Skip header row
            if ((row = reader.readNext()) == null) {
                JOptionPane.showMessageDialog(null, "CSV is empty!", "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            while ((row = reader.readNext()) != null) {
                lineNum++;

                // Expecting 7 columns: userId, semester, quiz, assignment, project, midsem, endsem
                if (row.length != 7) {
                    JOptionPane.showMessageDialog(null,
                            "Line " + lineNum + " doesn't have 7 columns. Check your CSV.",
                            "Format Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String userId = row[0].trim();
                String semester = row[1].trim().replace("\uFEFF", ""); // Remove BOM if present

                if (userId.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "User ID is empty at line " + lineNum,
                            "Data Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!ALLOWED_SEMESTERS.contains(semester)) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid semester '" + semester + "' at line " + lineNum +
                                    ". Allowed values: Semester 1, Semester 2, Semester 3",
                            "Data Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userIds.contains(userId)) {
                    JOptionPane.showMessageDialog(null,
                            "Duplicate User ID '" + userId + "' at line " + lineNum,
                            "Data Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                userIds.add(userId);

                int quiz, assignment, project, midsem, endsem;
                try {
                    quiz = Integer.parseInt(row[2].trim());
                    assignment = Integer.parseInt(row[3].trim());
                    project = Integer.parseInt(row[4].trim());
                    midsem = Integer.parseInt(row[5].trim());
                    endsem = Integer.parseInt(row[6].trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Non-numeric value at line " + lineNum,
                            "Data Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidMark(quiz) || !isValidMark(assignment) ||
                        !isValidMark(project) || !isValidMark(midsem) || !isValidMark(endsem)) {
                    JOptionPane.showMessageDialog(null,
                            "Marks must be 0-100. Check line " + lineNum,
                            "Data Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                gradesList.add(new StudentGrade(sectionId, userId, quiz, assignment, project, midsem, endsem, semester,courseid));
            }

        } catch (IOException | CsvValidationException e) {
            JOptionPane.showMessageDialog(null,
                    "Error reading CSV: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null,
                "CSV loaded successfully! Total records: " + gradesList.size(),
                "Success", JOptionPane.INFORMATION_MESSAGE);

        GradeUploader.saveGradesToDB(gradesList,sectionId,courseid,credits,title);
    }

    private static boolean isValidMark(int mark) {
        return mark >= 0 && mark <= 100;
    }

    public static int markstocg(double totalmarks){
        if(totalmarks>=90){
            return 10;
        } else if (totalmarks<90&&totalmarks>=80) {
            return 9;
        }else if(totalmarks<80&&totalmarks>=70){
            return 8;
        }else if(totalmarks<70&&totalmarks>=60){
            return 7;
        }else if(totalmarks<60&&totalmarks>=50){
            return 6;
        }else if(totalmarks<50&&totalmarks>=40){
            return 5;
        }else{
            return 4;
        }
    }

    public static String cgtograde(int cg){
        if(cg>=8){
            return "A";
        }else if(cg>=7&&cg<8){
            return "B";
        } else if (cg>=6&&cg<7) {
            return "C";
        } else if (cg>=5&&cg<6) {
            return "D";
        }else{
            return "F";
        }
    }

    private static void saveGradesToDB(ArrayList<StudentGrade> grades,String sectionid,String courseid,String credits,String title) throws SQLException {

        PUT.evaluations(grades,sectionid,courseid,credits,title);
        PUT.grades(grades,sectionid,courseid,credits,title);
        for(StudentGrade grade: grades){
            int quiz= grade.quiz;int assignment= grade.quiz;int project= grade.project;int midsem= grade.midsem;int endsem=grade.endsem;
            double totalmarks=(0.15*quiz)+(0.15*assignment)+(0.2*project)+(0.2*midsem)+(0.3*endsem);
            System.out.println(grade.userId);
            int cgpa=markstocg(totalmarks);
            String lettgrade=cgtograde(cgpa);
        }

        PUT.enrollments(sectionid);
        PUT.gradedsection(sectionid);


    }
    public static void main(String[] args) throws SQLException {

    }
}
