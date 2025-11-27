package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.Gradestats;
import edu.univ.erp.domain.StudentGrade;
import edu.univ.erp.domain.mysection;
import edu.univ.erp.service.GET;
import edu.univ.erp.service.GradeUploader;
import edu.univ.erp.util.InvalidOperationException;
import edu.univ.erp.util.Messages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class mysections extends JPanel {



    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    ArrayList<mysection> mysec=new ArrayList<>();

    public mysections(Main mw, Statevariables statevar){

        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            UIManager.put("Component.focusColor", new Color(0x7C4DFF));  // Pink accent
            UIManager.put("Button.focusColor", new Color(0x7C4DFF));
            UIManager.put("ToggleButton.focusColor", new Color(0x7C4DFF));
            UIManager.put("ProgressBar.selectionForeground", new Color(0x7C4DFF));
            UIManager.put("TextComponent.arc", 95);

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setSize(500,500);
        this.setBackground(DARK);
        this.setLayout(new BorderLayout());

        instnavbar navbar=new instnavbar(1,mw,statevar);

        JLabel myseclab=new JLabel("My Sections");
        myseclab.setFont(new Font("Montserrat",Font.BOLD,44));
        myseclab.setForeground(Color.WHITE);
        myseclab.setHorizontalAlignment(SwingConstants.CENTER);



        //encapsulate from here

//        JPanel right=new JPanel();
//        right.setPreferredSize(new Dimension(100,80));
//        right.setBackground(Color.GRAY);
//
//
//        JPanel left=new JPanel();
//        left.setPreferredSize(new Dimension(100,80));
//        left.setBackground(Color.GRAY);
//
//        JPanel mysectionpan=new JPanel();
//        mysectionpan.setLayout(new BorderLayout());
//        mysectionpan.setPreferredSize(new Dimension(1150,100));
//        mysectionpan.setBackground(Color.BLUE);
//        mysectionpan.add(left,BorderLayout.WEST);
//        mysectionpan.add(right,BorderLayout.EAST);
        mysectioncard mysectionpan1=new mysectioncard("CSE121","Parallel Runtime For Modern Processors",mw,statevar);
        mysectioncard mysectionpan2=new mysectioncard("CSE122","Parallel Runtime For Modern Processors",mw,statevar);
        mysectioncard mysectionpan3=new mysectioncard("CSE123","Parallel Runtime For Modern Processors",mw,statevar);
        mysectioncard mysectionpan4=new mysectioncard("CSE124","Parallel Runtime For Modern Processors",mw,statevar);
        mysectioncard mysectionpan5=new mysectioncard("CSE125","Parallel Runtime For Modern Processors",mw,statevar);
        mysectioncard mysectionpan6=new mysectioncard("CSE126","Parallel Runtime For Modern Processors",mw,statevar);
        mysectioncard mysectionpan7=new mysectioncard("CSE127","Parallel Runtime For Modern Processors",mw,statevar);




        //end it here


        JPanel sectioncontainerpan=new JPanel();
        sectioncontainerpan.setBackground(DARK);
//        sectioncontainerpan.setPreferredSize(new Dimension(1260,450));
        sectioncontainerpan.setLayout(new BoxLayout(sectioncontainerpan, BoxLayout.Y_AXIS));


        statevar.addListener(() -> {
            SwingUtilities.invokeLater(() -> {
                mysec.clear();
                mysec.addAll(statevar.getMysec());
                sectioncontainerpan.removeAll();

                for (mysection section : mysec) {
                    String title = section.coursetitle;
                    String id = section.sectionid;

                    mysectioncard card = new mysectioncard(id, title,mw,statevar);
                    sectioncontainerpan.add(card);
                    sectioncontainerpan.add(Box.createRigidArea(new Dimension(0, 15)));
                }
                sectioncontainerpan.revalidate();
                sectioncontainerpan.repaint();
            });
        });



//        sectioncontainerpan.add(mysectionpan1);
//        sectioncontainerpan.add(Box.createRigidArea(new Dimension(0,15)));
//        sectioncontainerpan.add(mysectionpan2);
//        sectioncontainerpan.add(Box.createRigidArea(new Dimension(0,15)));
//        sectioncontainerpan.add(mysectionpan3);
//        sectioncontainerpan.add(Box.createRigidArea(new Dimension(0,15)));
//        sectioncontainerpan.add(mysectionpan4);
//        sectioncontainerpan.add(Box.createRigidArea(new Dimension(0,15)));
//        sectioncontainerpan.add(mysectionpan5);
//        sectioncontainerpan.add(Box.createRigidArea(new Dimension(0,15)));
//        sectioncontainerpan.add(mysectionpan6);
//        sectioncontainerpan.add(Box.createRigidArea(new Dimension(0,15)));
//        sectioncontainerpan.add(mysectionpan7);


        JScrollPane scrollpane=new JScrollPane();
        scrollpane.setViewportView(sectioncontainerpan);
        scrollpane.getViewport().setBackground(DARK);
        scrollpane.getViewport().setFocusable(false);
        scrollpane.setBorder(new EmptyBorder(0,0,0,0));


        JPanel mainpan= new JPanel();
        mainpan.setBackground(DARK);
        mainpan.setLayout(new BorderLayout());
        mainpan.add(myseclab,BorderLayout.NORTH);
        mainpan.add(scrollpane,BorderLayout.CENTER);

        maintainencemsg msg=new maintainencemsg("Maintainence Underway! You cannot upload any grades for now ");
        this.add(navbar,BorderLayout.NORTH);
        this.add(mainpan,BorderLayout.CENTER);
        if(statevar.getmaintainencecheck()){
            this.add(msg,BorderLayout.SOUTH);
        }

        statevar.addListener(()->{
            BorderLayout layout=(BorderLayout) this.getLayout();
            if(statevar.getmaintainencecheck()&&(layout.getLayoutComponent(BorderLayout.SOUTH)!=msg)){
                this.add(msg,BorderLayout.SOUTH);
                this.revalidate();
                this.repaint();
            } else if ((!statevar.getmaintainencecheck())&&(layout.getLayoutComponent(BorderLayout.SOUTH)==msg)) {
                this.remove(msg);
                this.revalidate();
                this.repaint();
            }
        });
        this.setVisible(true);

    }
}


class mysectioncard extends JPanel{

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);

    public mysectioncard(String code,String title,Main mw,Statevariables statevar){

        JButton viewstats=new JButton("View stats");
        JButton upgrades=new JButton("Enter Scores");
        viewstats.setBackground(PURPLE);
        upgrades.setBackground(PURPLE);
        viewstats.setFocusable(false);
        upgrades.setFocusable(false);

        upgrades.addActionListener(e->{
            if(statevar.getmaintainencecheck()){
                errorinfodialog msg=new errorinfodialog(Messages.NO_GRADES_UPLOAD);
                return;
            }
            System.out.println(code);
            try {
                String coursecode= GET.coursecode(code);
                String credits=GET.credits(coursecode);
                GradeUploader.uploadGrades(code,coursecode,credits,title);
            } catch (SQLException ex) {
                errorinfodialog msg=new errorinfodialog(Messages.TAB_OPEN_ERROR);
            }
        });

        viewstats.addActionListener(e->{
            try {
                boolean output= GET.isgraded(code);
                if(!output){
                    throw new InvalidOperationException(Messages.NOT_GRADED_ERROR);
                }
                String coursecode= GET.coursecode(code);
                String coursetitle=GET.coursetitle(code);
                ArrayList<StudentGrade> output1=GET.allmarks(code);
                Gradestats stats=new Gradestats(output1);
                System.out.println(stats.averageMark);
                System.out.println(stats.gradeACount);
                System.out.println(stats.totalStudents);
                new ChartWindow(stats,coursecode);


            } catch (SQLException ex) {
                errorinfodialog msg1=new errorinfodialog(Messages.TAB_OPEN_ERROR);
            } catch (InvalidOperationException ex) {
                errorinfodialog msg=new errorinfodialog(ex.getMessage());
            }
        });

        JPanel right=new JPanel();
        right.setPreferredSize(new Dimension(300,80));
        right.setLayout(new FlowLayout(FlowLayout.CENTER,10,35));
        right.setBackground(DARK);
        right.add(viewstats);
        right.add(upgrades);




        JLabel coursecode=new JLabel("Section Code: "+code);
        coursecode.setPreferredSize(new Dimension(400,40));
        coursecode.setHorizontalAlignment(SwingConstants.CENTER);
        coursecode.setFont(new Font("Montserrat",Font.PLAIN,15));
        JLabel coursetitle=new JLabel("Course Title: "+title);
        coursetitle.setPreferredSize(new Dimension(400,40));
        coursetitle.setHorizontalAlignment(SwingConstants.CENTER);
        coursetitle.setFont(new Font("Montserrat",Font.PLAIN,15));

        JPanel left=new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.CENTER));
        left.setPreferredSize(new Dimension(400,80));
        left.setBackground(DARK);
        left.add(coursecode);
        left.add(coursetitle);

        this.setBorder(new ArcBorder(Color.WHITE,1,10));
        this.setLayout(new BorderLayout(10,0));
        this.setPreferredSize(new Dimension(1150,100));
        this.setMaximumSize(new Dimension(1150,100));
        this.setBackground(DARK);
        this.add(left, BorderLayout.WEST);
        this.add(right,BorderLayout.EAST);
    }
}


class mysecdemo{

//    public static void main(String[] args){
//        mysections demo=new mysections();
//    }
}

