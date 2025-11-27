package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.DELETE;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.*;
import edu.univ.erp.domain.Courseinfo;
import edu.univ.erp.service.POST;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.OverloadException;
import edu.univ.erp.util.simpmsgdialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Courses extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    int lastclickebutton=0;


    navlab label1,label2,label3,label4,label5;
    navlab[] arrayofbutts=new navlab[5];
    String[] options={"Fees","Academics","Disciplinary conduct","Hostel&Mess","Other"};
    String[] colheaders={"Course Code","Course Title","Pre-requisite(s)","Credits"};
    String[][] coursedata={
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"},
            {"CSE121","Machine Learning","NULL","4"}
    };
    public Courses(Main mw, Statevariables statevar){

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

        addcourses addtablepane=new addcourses(coursedata,colheaders,mw,statevar);

        dropcourses droptablepane=new dropcourses(coursedata,colheaders,mw,statevar);



        this.setSize(500,500);
        this.setLayout(new BorderLayout(0,10));
        this.setBackground(DARK);

        Navbar navpanel=new Navbar(1,mw,statevar);

        JPanel coursbuttpanel=new JPanel();
        coursbuttpanel.setBackground(DARK);
        coursbuttpanel.setPreferredSize(new Dimension(840,80));
        coursbuttpanel.setLayout(new FlowLayout(FlowLayout.LEFT,40,5));

        confpanel confpanel=new confpanel("Confirm");
        confpanel.confbutton.addActionListener(e -> {
            if(!statevar.getmaintainencecheck()){
                if (addtablepane.courscatlog.isEditing()) {
                    addtablepane.courscatlog.getCellEditor().stopCellEditing();
                }

                int count = 0;
                ArrayList<Integer> selectedindices = new ArrayList<>();
                for (int row = 0; row < addtablepane.courscatlog.getRowCount(); row++) {
                    Boolean isselected = (Boolean) addtablepane.courscatlog.getValueAt(row, 0);
                    System.out.println(isselected);
                    if (isselected != null && isselected) {
                        if (count + 1 > 5) {
                            errorinfodialog msg = new errorinfodialog(Messages.TOO_MANY_COURSES);
                        }
                        selectedindices.add(row);
                        count++;
                    }
                }
                if (selectedindices.size() != 0) {
                    try {
                        POST.selectedcourses(selectedindices, statevar.getListofaddables(), statevar.getId(), statevar);
                        simpmsgdialog msg = new simpmsgdialog(Messages.SAVED_SUCCESFULLY);
                    } catch (Exception exc) {
                        errorinfodialog ms = new errorinfodialog(exc.getMessage());
                    }

                } else {
                    errorinfodialog msg = new errorinfodialog(Messages.NO_SELECTED_COURSE);
                }
            }else{
                errorinfodialog msg=new errorinfodialog(Messages.NOT_ALLOWED);
            }

        });

        confdroppanel confdroppanel=new confdroppanel("Confirm");
        confdroppanel.confbutton.addActionListener(e -> {
            if(statevar.getmaintainencecheck()){
                errorinfodialog msg=new errorinfodialog(Messages.NOT_ALLOWED);
                return;
            }
            if(droptablepane.courscatlog.isEditing()) {
                droptablepane.courscatlog.getCellEditor().stopCellEditing();
            }
                int count = 0;
                ArrayList<Integer> selectedindices = new ArrayList<>();
                for (int row = 0; row < droptablepane.courscatlog.getRowCount(); row++) {
                    Boolean isselected = (Boolean) droptablepane.courscatlog.getValueAt(row, 0);
                    System.out.println(isselected);
                    if (isselected != null && isselected) {
                        selectedindices.add(row);
                        count++;
                    }
                }

                if(count==0){
                    errorinfodialog msg=new errorinfodialog(Messages.NO_SELECTED_COURSES);
                }else{
                    if(statevar.now.isAfter(statevar.expirydate)){
                        errorinfodialog msg=new errorinfodialog(Messages.DEADLINE_EXPIRED);
                    }else{
                        try {
                            DELETE.courses(selectedindices,statevar.getDropcourses(),statevar);
                            droptablepane.deleterow(selectedindices);
                        } catch (SQLException ex) {
                            errorinfodialog ms=new errorinfodialog(ex.getMessage());
                        }
                        ;
                    }
                }

        });


        JPanel mycourseNavpanel=new JPanel();
        mycourseNavpanel.setLayout(new BorderLayout(20,0));
        mycourseNavpanel.setPreferredSize(new Dimension(120,80));
        mycourseNavpanel.setBackground(DARK);
        mycourseNavpanel.add(coursbuttpanel,BorderLayout.WEST);

        label1=new navlab("Course Directory");
        arrayofbutts[0]=label1;
        label2=new navlab("My Courses");
        arrayofbutts[1]=label2;
        label3=new navlab("Add Courses");
        arrayofbutts[2]=label3;
        label4=new navlab("Drop Courses");
        arrayofbutts[3]=label4;
        label5=new navlab("File a grievance");
        arrayofbutts[4]=label5;
        label5.setPreferredSize(new Dimension(116,70));

        arrayofbutts[lastclickebutton].setForeground(PURPLE);

        JPanel[] arrayofpan=new JPanel[5];


        //course directory and
        JScrollPane tablepane=new coursedir(coursedata,colheaders,statevar,mw);
        tablepane.getViewport().setBackground(DARK);
        JPanel coursedirpan=new holderpanel(tablepane);
        arrayofpan[0]=coursedirpan;
        //my courses
        JScrollPane tabpane=new mycourses(coursedata,colheaders,statevar,mw);
        JPanel mycoursepan=new holderpanel(tabpane);
        arrayofpan[1]=mycoursepan;

        //add
        combinedaddpan add=new combinedaddpan(addtablepane,confpanel);
        arrayofpan[2]=add;
        //drop
        combineddroppan drop=new combineddroppan(droptablepane,confdroppanel);
        arrayofpan[3]=drop;
        //grievances
        grievance subpanel=new grievance(options,mw,statevar);
        arrayofpan[4]=subpanel;




        coursbuttpanel.add(label1);
        coursbuttpanel.add(label2);
        coursbuttpanel.add(label3);
        coursbuttpanel.add(label4);
        coursbuttpanel.add(label5);



        JPanel maincontpanel=new JPanel();
        maincontpanel.setLayout(new BorderLayout());
        maincontpanel.setBackground(DARK);
        maincontpanel.add(mycourseNavpanel,BorderLayout.NORTH);

        for(int i=0;i<5;i++){
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
                    maincontpanel.add(arrayofpan[index],BorderLayout.CENTER);
                    arrayofbutts[index].repaint();
                    mycourseNavpanel.revalidate();
                    mycourseNavpanel.repaint();
                    maincontpanel.revalidate();
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




        maincontpanel.add(coursedirpan,BorderLayout.CENTER);

        //for grievances and shit

//        maincontpanel.add(subpanel,BorderLayout.CENTER);








        maintainencemsg msg=new maintainencemsg("Maintainence Underway! Please Cooperate");

        this.add(navpanel,BorderLayout.NORTH);
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


class holderpanel extends JPanel{
    public holderpanel(JScrollPane input){
        this.setLayout(new BorderLayout());
        this.add(input);
    }
}


class combinedaddpan extends JPanel{
    public combinedaddpan(addcourses input1,confpanel inpu2){
        this.setLayout(new BorderLayout());
        this.add(input1,BorderLayout.NORTH);
        this.add(inpu2,BorderLayout.SOUTH);
        input1.setPreferredSize(new Dimension(1000,400));
    }
}

class combineddroppan extends JPanel{
    public combineddroppan(dropcourses input1,confdroppanel inpu2){
        this.setLayout(new BorderLayout());
        this.add(input1,BorderLayout.NORTH);
        this.add(inpu2,BorderLayout.SOUTH);
        input1.setPreferredSize(new Dimension(1000,400));
    }
}




class textfield extends JPanel{
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    public JTextField useridtxt=new JTextField("");
    public JComboBox useridtxt1;
    JTextField useridtxt2=new JTextField("");
    public textfield(String text){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1000,60));
        this.setBackground(DARK);
        JLabel useridlabtitl=new JLabel(text);
        useridlabtitl.setFont(new Font("Montserrat",Font.BOLD,16));
        useridtxt.setPreferredSize(new Dimension(120,50));
        this.add(useridlabtitl,BorderLayout.NORTH);
        this.add(useridtxt,BorderLayout.CENTER);
    }
    public textfield(String text,int width,int height){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(DARK);
        JLabel useridlabtitl=new JLabel(text);
        useridlabtitl.setFont(new Font("Montserrat",Font.BOLD,16));
        useridtxt2.setPreferredSize(new Dimension(120,50));
        this.add(useridlabtitl,BorderLayout.NORTH);
        this.add(useridtxt2,BorderLayout.CENTER);
    }

    public textfield(String text,String type,String[] options){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1000,60));
        this.setBackground(DARK);
        JLabel useridlabtitl=new JLabel(text);
        useridlabtitl.setFont(new Font("Montserrat",Font.BOLD,16));
        useridtxt1=new JComboBox(options);
        useridtxt1.setPreferredSize(new Dimension(120,50));
        this.add(useridlabtitl,BorderLayout.NORTH);
        this.add(useridtxt1,BorderLayout.CENTER);
    }
}


class navlab extends JLabel{
    public navlab(String title){
        this.setSize(50,70);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(true);
        this.setText(title);
        this.setFont(new Font("Montserrat",Font.BOLD,14));
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
    }
}

class grievance extends JPanel{
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    public grievance(String[] options,Main mw,Statevariables statevar){
        textfield pan1=new textfield("User Id");
        textfield pan2=new textfield("What is your issue related to?","combobox",options);
        textfield pan3=new textfield("Message",1000,100);


        JButton submitbutton=new JButton("Submit");
        submitbutton.setPreferredSize(new Dimension(400,50));
        submitbutton.setFocusable(false);
        submitbutton.setBackground(PURPLE);
        submitbutton.setFont(new Font("Montserrat",Font.BOLD,12));

        submitbutton.addActionListener(e->{
            if(statevar.getmaintainencecheck()){
                errorinfodialog msg=new errorinfodialog(Messages.NO_GRIEVANCE);
                return;
            }
            try{
                String value=(String)pan1.useridtxt.getText();
                String val2=(String)pan3.useridtxt2.getText();
                String id=statevar.getId();
                String type=(String)pan2.useridtxt1.getSelectedItem();
                if(val2.isEmpty()||value.isEmpty()){
                    throw new Exception(Messages.EMPTY_FIELD);
                }
                try {
                    POST.message(id, type, val2);
                    simpmsgdialog msg=new simpmsgdialog(Messages.MESSAGE_LOGGED);
                } catch (SQLException ex) {
                    throw new Exception(ex.getMessage());
                }

            }catch (Exception ex){
                errorinfodialog msg=new errorinfodialog(ex.getMessage());
            }
        });


        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
        this.setBackground(DARK);
        this.add(pan1);
        this.add(pan2);
        this.add(pan3);
        this.add(submitbutton);
    }
}

class coursedir extends JScrollPane{
    final private Color DARK=new Color(0x050D1F);
    Object[][] coursedata={
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"},
            {"CSE121","Machine Learning","Dan Man","NULL","4"}
    };
    tablecellrenderer defrend=new tablecellrenderer();
    public coursedir(String[][] rodata,String[] headers,Statevariables statevar,Main mw){

        String[] colheaders={"Course Code","Course Title","Pre-requisite(s)","Credits"};


        JTable courscatlog=new JTable(rodata,headers){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };

        courscatlog.setBackground(DARK);
        this.setBorder(new EmptyBorder(0,0,0,0));
        statevar.addListener(()->{
            Courseinfo[] output=statevar.getCourselist();
            Object[][] courseinf=new String[output.length][4];

            for(int i=0;i<output.length;i++){
                String[] intermediate=new String[4];
                intermediate[0]= output[i].coursecode;
                intermediate[1]= output[i].coursetitle;
                intermediate[2]= output[i].prerequisites;
                intermediate[3]= output[i].credits;
                courseinf[i]=intermediate;

            }
            // set the table again
            coursedata=courseinf;
            DefaultTableModel model = new DefaultTableModel(coursedata, colheaders);
            courscatlog.setModel(model);
            for(int i=0;i<courscatlog.getColumnCount();i++){
                courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);
            }

        });

        courscatlog.setFocusable(false);
        courscatlog.setRowHeight(80);
        courscatlog.getTableHeader().setBackground(DARK);
        courscatlog.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,16));


        defrend.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<courscatlog.getColumnCount();i++){
            courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);
        }





        this.getViewport().add(courscatlog);
        this.getViewport().setBackground(DARK);
        this.setFocusable(false);

    }
}

class addcourses extends JScrollPane{
    final private Color DARK=new Color(0x050D1F);
    Color tintA = new Color(0x131F4C);       // row tint 1
    Color tintB = new Color(0x4C5873);// row tint 2

    Object[][] coursedata={
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE}
    };
    String[] colheaders={"Course Code","Course Title","Instructor","Pre-requisite(s)","Credits","Capacity","Select"};


    addablecourseinfotablemodel model=new addablecourseinfotablemodel(new ArrayList<>());



    public JTable courscatlog=new JTable(model);
    adddroptablerenderer defrend=new adddroptablerenderer();
    public addcourses(String[][] rodata,String[] headers,Main mw,Statevariables statevar){
        courscatlog.setBackground(DARK);
        this.getViewport().setBackground(DARK);
        this.setBorder(new EmptyBorder(0,0,0,0));
        statevar.addListener(()->{

            model.setData(statevar.getListofaddables());
            for(int i=0;i<courscatlog.getColumnCount();i++){
                courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);

            }

        });

        JCheckBox checkBoxEditor = new JCheckBox();
        checkBoxEditor.setHorizontalAlignment(JCheckBox.CENTER);
        courscatlog.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(checkBoxEditor));


//
        courscatlog.setFocusable(false);
        courscatlog.setRowHeight(80);
        courscatlog.getTableHeader().setBackground(DARK);
        courscatlog.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,16));


        defrend.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<courscatlog.getColumnCount();i++){
            courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);
            if(i==6){
                courscatlog.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JCheckBox()));

            }
        }




        this.getViewport().add(courscatlog);
        this.getViewport().setBackground(DARK);
        this.setFocusable(false);

    }
}

class confpanel extends JPanel{

    public JButton confbutton=new JButton();
    final private Color PURPLE=new Color(0x7C4DFF);
    final private Color DARK=new Color(0x050D1F);
    public confpanel(String title){
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        this.setPreferredSize(new Dimension(1280,60));
        this.setBackground(DARK);
        confbutton.setText(title);
        this.add(confbutton);
        confbutton.setFocusable(false);
        confbutton.setPreferredSize(new Dimension(120,40));
        confbutton.setBackground(PURPLE);

    }
}

class mycourses extends JScrollPane{
    final private Color DARK=new Color(0x050D1F);
    Object[][] coursedata={
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"},
            {"CSE121","Machine Learning","Dan Man","NULL","4","Click here"}
    };
    tablecellrenderer defrend=new tablecellrenderer();



 public mycourses(String[][]rodata,String[] headers,Statevariables statevar,Main mw){

     String[] colheaders={"Course Code","Course Title","Instructor","Venue"};
     JTable courscatlog=new JTable(rodata,colheaders){
         @Override
         public boolean isCellEditable(int row,int column){
             return false;
         }
     };

    courscatlog.setBackground(DARK);
    this.setBorder(new EmptyBorder(0,0,0,0));

     statevar.addListener(()->{
         sectioninfo[] output=statevar.getMycourseinfo();
         Object[][] courseinf=new String[output.length][4];

         for(int i=0;i<output.length;i++){
             String[] intermediate=new String[4];
             intermediate[0]= output[i].courseid;
             intermediate[1]= output[i].coursetitle;
             intermediate[2]= output[i].Instructor;
             intermediate[3]= output[i].venue;
             courseinf[i]=intermediate;

         }
         // set the table again
         coursedata=courseinf;
         DefaultTableModel model = new DefaultTableModel(coursedata, colheaders);
         courscatlog.setModel(model);
         for(int i=0;i<courscatlog.getColumnCount();i++){
             courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);
         }

     });

     courscatlog.setFocusable(false);
     courscatlog.setRowHeight(80);
     courscatlog.getTableHeader().setBackground(DARK);
     courscatlog.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,16));


     defrend.setHorizontalAlignment(SwingConstants.CENTER);
     for(int i=0;i<courscatlog.getColumnCount();i++){
         courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);
     }


     this.getViewport().add(courscatlog);
     this.getViewport().setBackground(DARK);
     this.setFocusable(false);

 }
}


class coursedem{
    public static void main(String[] args){
//        Courses demo=new Courses();
    }
}
class confdroppanel extends JPanel{

    JButton confbutton=new JButton();
    final private Color PURPLE=new Color(0x7C4DFF);
    final private Color DARK=new Color(0x050D1F);
    public confdroppanel(String title){
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        this.setPreferredSize(new Dimension(1280,60));
        this.setBackground(DARK);
        confbutton.setText(title);
        this.add(confbutton);
        confbutton.setFocusable(false);
        confbutton.setPreferredSize(new Dimension(120,40));
        confbutton.setBackground(PURPLE);

    }
}


class dropcourses extends JScrollPane{
    final private Color DARK=new Color(0x050D1F);
    Color tintA = new Color(0x131F4C);       // row tint 1
    Color tintB = new Color(0x4C5873);// row tint 2

    Object[][] dropcourses={
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE},
            {"CSE121","Machine Learning","Prof.Lorem","NULL","4","AAA",Boolean.FALSE}
    };
    String[] colheaders={"Course Code","Course Title","Instructor","Pre-requisite(s)","Credits","Capacity","Select"};


    droppabletablemodel model=new droppabletablemodel(new ArrayList<>());



    public JTable courscatlog=new JTable(model);
    adddroptablerenderer defrend=new adddroptablerenderer();

    public void deleterow(ArrayList<Integer> indices){
        for(int i=0;i<indices.size();i++){
            model.removeRow(indices.get(i));
        }
    }


    public dropcourses(String[][] rodata,String[] headers,Main mw,Statevariables statevar){
        courscatlog.setFillsViewportHeight(true);
        courscatlog.setBackground(DARK);
        this.setBorder(new EmptyBorder(0,0,0,0));
        this.getViewport().setBackground(DARK);
        statevar.addListener(()->{

            model.setRows(statevar.getDropcourses());
            for(int i=0;i<courscatlog.getColumnCount();i++){
                courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);
            }

        });

        JCheckBox checkBoxEditor = new JCheckBox();
        checkBoxEditor.setHorizontalAlignment(JCheckBox.CENTER);
        courscatlog.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(checkBoxEditor));


//
        courscatlog.setFocusable(false);
        courscatlog.setRowHeight(80);
        courscatlog.getTableHeader().setBackground(DARK);
        courscatlog.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,16));


        defrend.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<courscatlog.getColumnCount();i++){
            courscatlog.getColumnModel().getColumn(i).setCellRenderer(defrend);
            if(i==6){
                courscatlog.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JCheckBox()));

            }
        }




        this.getViewport().add(courscatlog);
        this.getViewport().setBackground(DARK);
        this.setFocusable(false);

    }
}
