package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.domain.Evaluations;

import javax.swing.*;
import java.awt.*;

public class componentwisegrades extends JFrame{
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    public componentwisegrades(Evaluations evaluations,String coursetitle){
        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500,500);
        this.getContentPane().setBackground(DARK);
        this.setLayout(new BorderLayout(0,10));



        JLabel tttitlepan=new JLabel("Component Wise Marks for subject: "+coursetitle);
        tttitlepan.setPreferredSize(new Dimension(100,80));
        tttitlepan.setHorizontalAlignment(SwingConstants.CENTER);
        tttitlepan.setBackground(DARK);
        tttitlepan.setFont(new Font("Montserrat",Font.BOLD,40));
        tttitlepan.setForeground(Color.WHITE);


        String[] colheaders={"Component","Maximum Marks","Your Marks","Weightage","weighted marks"};

        double quiz=Integer.parseInt(evaluations.quiz)*(0.15);
        double assignment=Integer.parseInt(evaluations.assignment)*(0.15);
        double project=Integer.parseInt(evaluations.project)*(0.2);
        double midsem=Integer.parseInt(evaluations.midsem)*(0.2);
        double endsem=Integer.parseInt(evaluations.endsem)*(0.3);
        double totalmarks=quiz+assignment+project+midsem+endsem;

        String[][] data={
                {"Quizzes","100",evaluations.quiz,"15",quiz+""},
                {"Assignments","100",evaluations.assignment,"15",assignment+""},
                {"Project","100",evaluations.project,"20",project+""},
                {"MidSem","100",evaluations.midsem,"20",midsem+""},
                {"EndSem","100",evaluations.endsem,"30",endsem+""}
        };


        JTable timetable=new JTable(data,colheaders){
            @Override public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        tablecellrenderer myrend=new tablecellrenderer();
        timetable.getTableHeader().setBackground(DARK);
        timetable.setFocusable(false);
        myrend.setHorizontalAlignment(SwingConstants.CENTER);
        timetable.setRowHeight(100);
        for(int i=0;i<timetable.getColumnCount();i++){
            timetable.getColumnModel().getColumn(i).setCellRenderer(myrend);
        }


        JScrollPane ttpane=new JScrollPane(timetable);


        JLabel maint=new JLabel("Total Marks: "+totalmarks);
        maint.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(tttitlepan,BorderLayout.NORTH);
        this.add(ttpane,BorderLayout.CENTER);
        this.add(maint,BorderLayout.SOUTH);
        this.setVisible(true);
    }
}

class compwisegrades{
//    public static void main(String[] args){
//        componentwisegrades demo=new componentwisegrades();
//    }
}