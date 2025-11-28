package edu.univ.erp.service;
import edu.univ.erp.util.Messages;
//import com.mysql.cj.Messages;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.univ.erp.data.Fetch;
import edu.univ.erp.domain.StudentGrade;
import edu.univ.erp.ui.errorinfodialog;
import edu.univ.erp.util.simpmsgdialog;

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
            errorinfodialog msg=new errorinfodialog(Messages.ALREADY_GRADED_SECN);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

        if (!filePath.toLowerCase().endsWith(".csv")) {
            errorinfodialog msg=new errorinfodialog(Messages.SELECT_CSV);
            return;
        }

        ArrayList<StudentGrade> gradesList = new ArrayList<>();
        HashSet<String> userIds = new HashSet<>();
        int lineNum = 1;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] row;

            if ((row = reader.readNext()) == null) {
                errorinfodialog msg=new errorinfodialog(Messages.EMPTY_CSV);
                return;
            }

            while ((row = reader.readNext()) != null) {
                lineNum++;

                if (row.length != 7) {
                    errorinfodialog msg=new errorinfodialog(Messages.INVALID_COL);
                    return;
                }

                String userId = row[0].trim();
                String semester = row[1].trim();

                if (userId.isEmpty()) {

                    errorinfodialog msg=new errorinfodialog(Messages.EMPTY_LINE);
                    return;
                }
                if(!GET.checkifinsec(sectionId,userId)){
                    System.out.println(userId);
                    errorinfodialog ms=new errorinfodialog("student id in file does not belong to your section");
                    return;
                }

                if (!ALLOWED_SEMESTERS.contains(semester)) {
                    errorinfodialog ms=new errorinfodialog(Messages.INV_SEM_ENTRY);
                    return;
                }

                if (userIds.contains(userId)) {
                    errorinfodialog msg=new errorinfodialog(Messages.DUPLICATE_ENTRY);
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
                    errorinfodialog msg=new errorinfodialog(Messages.NON_SENSICAL_VALUE);
                    return;
                }

                if (!isValidMark(quiz) || !isValidMark(assignment) ||
                        !isValidMark(project) || !isValidMark(midsem) || !isValidMark(endsem)) {
                    errorinfodialog msg=new errorinfodialog(Messages.SHOOT_BOUND);
                    return;
                }

                gradesList.add(new StudentGrade(sectionId, userId, quiz, assignment, project, midsem, endsem, semester,courseid));
            }

        } catch (IOException | CsvValidationException e) {
            errorinfodialog msg=new errorinfodialog(Messages.CSV_READ_ERR);
            return;
        }

        simpmsgdialog msg=new simpmsgdialog(Messages.CSV_LOADING_COMPLETE);
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
