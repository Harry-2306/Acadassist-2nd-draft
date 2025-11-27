package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.mysql.cj.protocol.MessageSender;
import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.SectionTableModel;
import edu.univ.erp.domain.sectionlogistics;
import edu.univ.erp.service.GET;
import edu.univ.erp.service.POST;
import edu.univ.erp.service.PUT;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.emptyfieldexception;
import edu.univ.erp.util.simpmsgdialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class admcourses extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    final private int swidth=1280;
    int lastclickebutton;



    String[] headers = {"coursecode", "title", "Instructor", "Credits", "Pre-Requisite","Course Capacity"};
    String[][] courses = {
            {"CS101", "Introduction to Programming", "Dr. Alice Bennett", "3", "None","150"},
            {"CS202", "Data Structures", "Prof. Mark Lee", "4", "CS101","150"},
            {"CS310", "Operating Systems", "Dr. Priya Sharma", "4", "CS202","150"},
            {"CS450", "Database Systems", "Dr. Omar Haddad", "3", "CS202","150"},
            {"CS499", "Capstone Project", "Prof. Elaine Zhu", "6", "CS310","150"}
    };



    navlab label1,label2,label3;
    navlab[] arrayofbutts=new navlab[3];

    createassign create=new createassign();
    createcourse add=new createcourse();
    JPanel[] arrayofpan=new JPanel[3];


    public admcourses(Main mw, Statevariables statevar){
        editcourses edit=new editcourses(mw,statevar);
        arrayofpan[0]=create;
        arrayofpan[1]=edit;
        arrayofpan[2]=add;

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

        admNavbar navbar=new admNavbar(2,mw,statevar);

        lastclickebutton=0;

        label1=new navlab("Create&assign section");
        arrayofbutts[0]=label1;
        label2=new navlab("Edit Section logistics");
        arrayofbutts[1]=label2;
        label3=new navlab("Create course");
        arrayofbutts[2]=label3;
        arrayofbutts[lastclickebutton].setForeground(PURPLE);

        JPanel coursbuttpanel=new JPanel();
        coursbuttpanel.setBackground(DARK);
        coursbuttpanel.setPreferredSize(new Dimension(840,80));
        coursbuttpanel.setLayout(new FlowLayout(FlowLayout.LEFT,40,30));
        coursbuttpanel.add(label1);
        coursbuttpanel.add(label2);
        coursbuttpanel.add(label3);

        JPanel mycourseNavpanel=new JPanel();
        mycourseNavpanel.setLayout(new BorderLayout(20,0));
        mycourseNavpanel.setPreferredSize(new Dimension(120,60));
        mycourseNavpanel.setBackground(DARK);
        mycourseNavpanel.add(coursbuttpanel,BorderLayout.WEST);






        JPanel maincontpanel=new JPanel();
        maincontpanel.setLayout(new BorderLayout());
        maincontpanel.setBackground(DARK);
        maincontpanel.add(mycourseNavpanel,BorderLayout.NORTH);
        maincontpanel.add(create,BorderLayout.CENTER);


        for(int i=0;i<3;i++){
            final int index=i;
            arrayofbutts[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getSource()==arrayofbutts[index]){
                        arrayofbutts[lastclickebutton].setForeground(Color.WHITE);
                        maincontpanel.remove(arrayofpan[lastclickebutton]);
                        arrayofbutts[lastclickebutton].repaint();
                        maincontpanel.revalidate();
                        maincontpanel.repaint();
                    }
                    lastclickebutton=index;
                    arrayofbutts[index].setForeground(PURPLE);
                    arrayofbutts[index].repaint();
                    maincontpanel.add(arrayofpan[index]);
                    mycourseNavpanel.revalidate();
                    maincontpanel.revalidate();
                    mycourseNavpanel.repaint();
                    maincontpanel.repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }



        maintainencemsg msg=new maintainencemsg("Maintainence underway bozo :p");

        this.add(navbar,BorderLayout.NORTH);
        this.add(maincontpanel,BorderLayout.CENTER);
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


class createassign extends JPanel{
    final private Color PURPLE=new Color(0x7C4DFF);
    final private Color DARK=new Color(0x050D1F);

    public createassign(){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

        JPanel inputpan=new JPanel();
        inputpan.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputpan.setPreferredSize(new Dimension(600,60));
        JLabel txtlab=new JLabel("Course Code");
        txtlab.setPreferredSize(new Dimension(600,30));
        JTextField txtinput=new JTextField("");
        txtinput.setPreferredSize(new Dimension(600,30));
        inputpan.add(txtlab);
        inputpan.add(txtinput);

        inputpan inp1=new inputpan("Course Code");
        inputpan inp2=new inputpan("Instructor_id");
        inputpan inp3=new inputpan("Section_id");
        inputpan inp4=new inputpan("venue");
        inputpan inp5=new inputpan("timings");
        inputpan inp6=new inputpan("capacity");

        JPanel leftpan=new JPanel();
        leftpan.setBackground(DARK);
        leftpan.setPreferredSize(new Dimension(640,80));
        leftpan.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
        leftpan.add(inp1);
        leftpan.add(inp2);
        leftpan.add(inp3);


        JPanel rightpan=new JPanel();
        rightpan.setBackground(DARK);
        rightpan.setPreferredSize(new Dimension(640,80));
        rightpan.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
        rightpan.add(inp4);
        rightpan.add(inp5);
        rightpan.add(inp6);

        JButton button=new JButton("Create section");
        button.setFocusable(false);
        button.setBackground(PURPLE);

        button.addActionListener(e -> {
            try{
                if(inp1.txtinput.getText().isEmpty()||inp2.txtinput.getText().isEmpty()||inp3.txtinput.getText().isEmpty()||inp4.txtinput.getText().isEmpty()||inp5.txtinput.getText().isEmpty()||inp6.txtinput.getText().isEmpty()){
                    throw new Exception(Messages.EMPTY_FIELD);
                }
                if(Fetch.coursecodeexists(inp1.txtinput.getText())&&Fetch.sectionidexists(inp3.txtinput.getText())&&Fetch.instructorexists(inp2.txtinput.getText())){
                    throw new Exception(Messages.UNIQUE_VALUE_ERR);
                }
                int nums=Integer.parseInt(inp6.txtinput.getText());

                String courseid=inp1.txtinput.getText();
                String sectionid=inp3.txtinput.getText();
                String instructorid=inp2.txtinput.getText();
                String venue=inp4.txtinput.getText();
                String timings=inp5.txtinput.getText();
                String capacity=inp6.txtinput.getText();
                String coursetitle=Fetch.coursetitlefromcourse(courseid);
                String instname=Fetch.instructorfrominst(instructorid);

                POST.section(courseid,sectionid,coursetitle,instructorid,instname,venue,timings,capacity);
                simpmsgdialog ms=new simpmsgdialog(Messages.SAVED_SUCCESFULLY);

            } catch(NumberFormatException e1){
                errorinfodialog ms=new errorinfodialog(Messages.NON_SENSICAL_VALUE);
            }catch (Exception ex) {
                errorinfodialog ms=new errorinfodialog(ex.getMessage());
            }
        });

        JPanel buttpan=new JPanel();
        buttpan.setBackground(DARK);
        buttpan.setPreferredSize(new Dimension(40,40));
        buttpan.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        buttpan.add(button);

        this.add(leftpan,BorderLayout.WEST);
        this.add(rightpan,BorderLayout.EAST);
        this.add(buttpan,BorderLayout.SOUTH);
    }
}


class editcourses extends JPanel{
    final private Color PURPLE=new Color(0x7C4DFF);
    final private Color DARK=new Color(0x050D1F);

    public editcourses(Main mw,Statevariables statevar){

        this.setLayout(new BorderLayout());

        SectionTableModel model=new SectionTableModel();

        JTable table=new JTable(model);
        table.setFocusable(false);
        table.getTableHeader().setBackground(DARK);
        table.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,16));
        table.setRowHeight(84);
        tablecellrenderer myrend=new tablecellrenderer();

        statevar.addListener(()->{
            model.setData(statevar.getSections());
            for(int i=0;i<table.getColumnCount();i++){
                table.getColumnModel().getColumn(i).setCellRenderer(myrend);
            }
        });

        myrend.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(myrend);
        }


        JScrollPane tablepane=new JScrollPane(table);
        tablepane.setBorder(new EmptyBorder(0,0,0,0));
        tablepane.getViewport().setBackground(DARK);

        JButton savechanges=new JButton("Save Changes");
        savechanges.setBackground(PURPLE);

        savechanges.addActionListener(e -> {

            for (int j = 0; j < model.getRowCount(); j++) {
                try{
                    String sectionId = (String) model.getValueAt(j, 0);
                    String courseCode = (String) model.getValueAt(j, 1);

                    int currCap = (int) model.getValueAt(j, 2);

                    Object maxCapObj = model.getValueAt(j, 3);
                    Object venueObj = model.getValueAt(j, 4);
                    Object timeObj = model.getValueAt(j, 5);


                    if (maxCapObj == null || venueObj == null || timeObj == null || maxCapObj.toString().trim().isEmpty() || venueObj.toString().trim().isEmpty() || timeObj.toString().trim().isEmpty()) {
                        throw new Exception("Table fields must not be left empty");
                    }

                    int maxCap;

                    try {
                        maxCap = Integer.parseInt(maxCapObj.toString().trim());
                    } catch (NumberFormatException ex) {
                        throw new Exception(Messages.NON_SENSICAL_VALUE);
                    }

                    if (maxCap < 0) {
                        throw new Exception("Maximum capacity cannot be negative");
                    }
                    if (maxCap < currCap) {
                        throw new Exception("Max capacity must be greater than or equal to the current capacity at all times.");
                    }
                } catch (Exception ex) {
                    errorinfodialog msg=new errorinfodialog(ex.getMessage());
                    return;
                }
            }
            ArrayList<sectionlogistics> input=new ArrayList<>();
            for(int i=0;i<model.getRowCount();i++){
                String sectionId = (String) model.getValueAt(i, 0);
                String coursecode = (String) model.getValueAt(i, 1);
                int currCap = (int) model.getValueAt(i, 2);
                int maxcap=(int)model.getValueAt(i,3);
                String venue=(String)model.getValueAt(i,4);
                String time=(String)model.getValueAt(i,5);
                input.add(new sectionlogistics(coursecode,venue,time,sectionId,maxcap,currCap));
            }

            try {
                PUT.secinfo(input);
                simpmsgdialog ms=new simpmsgdialog(Messages.SAVED_SUCCESFULLY);
            }catch (IllegalArgumentException ex1){
                errorinfodialog ms=new errorinfodialog(Messages.WRONG_TIME_FORMAT);
            }catch (SQLException ex) {
                errorinfodialog ms=new errorinfodialog(ex.getMessage());
            }

        });


        JPanel buttpan=new JPanel();
        buttpan.setBackground(DARK);
        buttpan.setPreferredSize(new Dimension(40,40));
        buttpan.setLayout(new FlowLayout(FlowLayout.CENTER,0,8));
        buttpan.add(savechanges);


        this.add(tablepane,BorderLayout.CENTER);
        this.add(buttpan,BorderLayout.SOUTH);
    }
}

class createcourse extends JPanel{
    final private Color PURPLE=new Color(0x7C4DFF);
    final private Color DARK=new Color(0x050D1F);
    public createcourse(){
        this.setBackground(DARK);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,30,50));
        inputpan inp1=new inputpan("Enter the courseid");
        inputpan inp2=new inputpan("Enter the course title");
        inputpan inp3=new inputpan("Enter credits");
        inputpan inp4=new inputpan("Enter pre-requisite. Enter NA if none");

        confpanel button=new confpanel("Add course");
        this.add(inp1);
        this.add(inp2);
        this.add(inp3);
        this.add(inp4);
        this.add(button);
        button.confbutton.addActionListener(e -> {

            try {
                if (inp1.txtinput.getText().isEmpty() || inp2.txtinput.getText().isEmpty() || inp3.txtinput.getText().isEmpty() || inp4.txtinput.getText().isEmpty()) {
                    throw new Exception(Messages.EMPTY_FIELD);
                }
                if(GET.checkcoursecodeexistence(inp1.txtinput.getText())){
                    throw new Exception(Messages.DUPLICATE_VALUE);
                }
                if(!(inp4.txtinput.getText().contentEquals("NA"))&&(!GET.checkcoursecodeexistence(inp4.txtinput.getText()))){
                    throw new Exception(Messages.NON_EXISTENT_PREREQ);
                }

                String coursecode=inp1.txtinput.getText();
                String coursetitle=inp2.txtinput.getText();
                String prerequisite=inp4.txtinput.getText();
                int credits = Integer.parseInt(inp3.txtinput.getText());
                //write to table
                POST.course(coursecode,coursetitle,credits,prerequisite);
                simpmsgdialog msg=new simpmsgdialog(Messages.SAVED_SUCCESFULLY);
            }catch(NumberFormatException ex){
                errorinfodialog msg= new errorinfodialog(Messages.NON_SENSICAL_VALUE);
            }catch (Exception e1){
                errorinfodialog msg=new errorinfodialog(e1.getMessage());
            }

        });
    }
}




class inputpan extends JPanel{
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    public JTextField txtinput=new JTextField("");
    public inputpan(String header){
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setPreferredSize(new Dimension(600,100));
        JLabel txtlab=new JLabel(header);
        txtlab.setFont(new Font("Montserrat",Font.PLAIN,12));
        txtlab.setPreferredSize(new Dimension(600,40));

        txtinput.setPreferredSize(new Dimension(600,40));
        this.setBackground(DARK);
        this.add(txtlab);
        this.add(txtinput);
    }
}

class maintdemo{
    public static void main(String[] args){
//        admcourses demo=new admcourses();
    }
}