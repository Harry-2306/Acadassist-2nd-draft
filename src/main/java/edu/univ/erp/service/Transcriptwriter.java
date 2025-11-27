package edu.univ.erp.service;
import com.opencsv.CSVWriter;
import edu.univ.erp.domain.Grades;
import edu.univ.erp.util.Messages;

import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class Transcriptwriter {

    public static void writeTranscriptCSV(Grades sr, String filename) throws Exception {

        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {

            writer.writeNext(new String[]{"Semester", "Course ID", "Title", "Grade", "GPA", "Credits"});
            writeSemester(writer, "1", sr.semester1, sr.sgpa1);
            writeSemester(writer, "2", sr.semester2, sr.sgpa2);
            writeSemester(writer, "3", sr.semester3, sr.sgpa3);
            writer.writeNext(new String[]{"", "", "CGPA", String.valueOf(sr.cgpa), "", ""});
        }catch (IOException e){
            throw new Exception(Messages.DOWNLOAD_FAILED_ERROR);
        }
    }


    private static void writeSemester(CSVWriter writer, String semName, ArrayList<ArrayList<String>> courses, double sgpa) {

        if (courses == null || courses.isEmpty()) {
            writer.writeNext(new String[]{semName, "No data", "", "", "", ""});
            return;
        }

        for (ArrayList<String> row : courses) {
            String courseId = !row.isEmpty() ? row.get(0) : "";
            String title    = row.size() > 1 ? row.get(1) : "";
            String grade    = row.size() > 2 ? row.get(2) : "";
            String gpa      = row.size() > 3 ? row.get(3) : "";
            String credits  = row.size() > 4 ? row.get(4) : "";

            writer.writeNext(new String[]{
                    semName, courseId, title, grade, gpa, credits
            });
        }

        writer.writeNext(new String[]{"", "", "SGPA", String.valueOf(sgpa), "", ""});
    }



    public static void writeTranscriptPDF(Grades sr, String filename) throws IOException {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDType0Font arial = PDType0Font.load(document, new File("C:/Windows/Fonts/arial.ttf"));
        PDType0Font arialBold = PDType0Font.load(document, new File("C:/Windows/Fonts/arialbd.ttf"));

        PDPageContentStream content = new PDPageContentStream(document, page);
        float margin = 50;
        float y = page.getMediaBox().getHeight() - margin;

        content.beginText();
        content.setFont(arialBold, 20);
        content.newLineAtOffset(margin, y);
        content.showText("ACADEMIC TRANSCRIPT");
        content.endText();
        y -= 50;

        y = writeSemester(content, sr.semester1, sr.sgpa1, "Semester 1", margin, y, arial, arialBold);
        y = writeSemester(content, sr.semester2, sr.sgpa2, "Semester 2", margin, y, arial, arialBold);
        y = writeSemester(content, sr.semester3, sr.sgpa3, "Semester 3", margin, y, arial, arialBold);

        y -= 20;
        content.beginText();
        content.setFont(arialBold, 14);
        content.newLineAtOffset(margin, y);
        content.showText("Overall CGPA: " + sr.cgpa);
        content.endText();

        content.close();
        document.save(filename);
        document.close();
    }


    private static float writeSemester(PDPageContentStream content, ArrayList<ArrayList<String>> courses, double sgpa, String semName, float margin, float y, PDType0Font regularFont, PDType0Font boldFont) throws IOException {

        content.beginText();
        content.setFont(boldFont, 16);
        content.newLineAtOffset(margin, y);
        content.showText(semName);
        content.endText();
        y -= 25;

        content.beginText();
        content.setFont(boldFont, 12);
        content.newLineAtOffset(margin, y);
        content.showText(String.format("%-10s %-35s %-6s %-6s %-6s", "CourseID", "Title", "Grade", "GPA", "Credits"));
        content.endText();
        y -= 20;

        content.setFont(regularFont, 11);
        for (ArrayList<String> row : courses) {
            content.beginText();
            content.newLineAtOffset(margin, y);
            String line = String.format("%-10s %-35s %-6s %-6s %-6s", getVal(row, 0), getVal(row, 1), getVal(row, 2), getVal(row, 3), getVal(row, 4));
            content.showText(line);
            content.endText();
            y -= 18;
        }

        y -= 10;
        content.beginText();
        content.setFont(boldFont, 12);
        content.newLineAtOffset(margin, y);
        content.showText("SGPA: " + sgpa);
        content.endText();

        return y - 25;
    }



    private static String getVal(ArrayList<String> row, int index) {
        return (row != null && row.size() > index) ? row.get(index) : "";
    }

}


