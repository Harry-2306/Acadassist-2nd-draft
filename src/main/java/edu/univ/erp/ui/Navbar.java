package edu.univ.erp.ui;

import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.*;
import edu.univ.erp.domain.Courseinfo;
import edu.univ.erp.service.AuthOps;
import edu.univ.erp.service.GET;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.unsucessfulretrievalexception;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

class Navbar extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private int swidth=1280;
    int lastclickedbutton;
    NavButton navbutt0,navbutt1,navbutt2,navbutt3,navbutt4;
    NavButton[] arrayofbutts=new NavButton[5];
    JLabel Navtitle;
    String id="";


    public Navbar(int input,Main mw,Statevariables statevar){
        lastclickedbutton=input;
        this.setPreferredSize(new Dimension(swidth,100));
        this.setBackground(TRANSPARENT);
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(0,20,0,20));
        this.setBackground(DARK);

        Navtitle=new JLabel("Acadassist");
        Navtitle.setPreferredSize(new Dimension(230,60));
        Navtitle.setFont(new Font("Montserrat",Font.BOLD,41));
        Navtitle.setForeground(new Color(0x7C4DFF));
        Navtitle.setBackground(TRANSPARENT);
        Navtitle.setOpaque(true);

        JPanel Buttpanel=new JPanel();
        Buttpanel.setLayout(new FlowLayout(FlowLayout.CENTER,60,40));
        Buttpanel.setPreferredSize(new Dimension(600,60));
        Buttpanel.setBackground(TRANSPARENT);

        navbutt0=new NavButton("Home");
        navbutt1=new NavButton("Courses");
        navbutt2=new NavButton("Grades");
        navbutt3=new NavButton("MyProfile");
        navbutt4=new NavButton("LogOut");

        navbutt4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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

        arrayofbutts[0]=navbutt0;
        arrayofbutts[1]=navbutt1;
        arrayofbutts[2]=navbutt2;
        arrayofbutts[3]=navbutt3;
        arrayofbutts[4]=navbutt4;


        statevar.addListener(()->id= statevar.getId());


        //event listener for logout
        navbutt4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //log out
                AuthOps.updatestatus(id,false);
                //
                mw.showPage("Login");
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

        //event listener for myprofile
        navbutt3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try{
                    Student output=GET.stu_details(statevar.getId());
                    statevar.setProgram(output.program);
                    statevar.setDOB(output.DOB);
                    statevar.setYear(output.year);
                    statevar.setRollno(output.Rollno);

                    mw.showPage("stuprofile");
                } catch (unsucessfulretrievalexception ex) {
                    errorinfodialog msg=new errorinfodialog(ex.getMessage());
                }

                //show pages

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

        //event listener for home
        navbutt0.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mw.showPage("Home");
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

        //event listener for courses
        navbutt1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                        Courseinfo[] array = GET.courselist();
                        statevar.setCourselist(array);
                        System.out.println("cached");

                    sectioninfo object=statevar.getMycourseinfo()[0];
                    if(object.isempty()){
                        sectioninfo[] arrayi=GET.mycourses(statevar.getId());
                        statevar.setMycourseinfo(arrayi);
                    }
                    ArrayList<addablecourseinfo> newoutput=GET.addblcourses();
                    System.out.println(newoutput.size());
                    statevar.setListofaddables(newoutput);
                    addablecourseinfo[] output=GET.addablecourses();
                    statevar.setarrayofaddblcourses(output);
                    System.out.println("value set");
                    statevar.setDropcourses(GET.details(statevar.getId()));
                    if(output.length==0){
                        throw new Exception(Messages.NO_ADDABLE_COURSES);
                    }
                    mw.showPage("StuCourses");
                    //add for my courses as well
                } catch (SQLException ex) {
                    errorinfodialog msg=new errorinfodialog(Messages.TAB_OPEN_ERROR);
                } catch (Exception ex) {
                    errorinfodialog msg=new errorinfodialog(ex.getMessage());
                }

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

        //event listener for grades
        navbutt2.addMouseListener(new MouseListener() {




            @Override
            public void mouseClicked(MouseEvent e) {
                if(!statevar.getmaintainencecheck()){
                    try {
                        statevar.setgrades(GET.grades(statevar.getId()));
                    } catch (SQLException ex) {
                        errorinfodialog errdialog = new errorinfodialog(Messages.TAB_OPEN_ERROR);
                    }
                    mw.showPage("Grades");
                }else{
                    errorinfodialog ms=new errorinfodialog(Messages.NO_GRADES_MAINT);
                }
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



        arrayofbutts[lastclickedbutton].setForeground(new Color(0x7C4DFF));

        Buttpanel.add(navbutt0);
        Buttpanel.add(navbutt1);
        Buttpanel.add(navbutt2);
        Buttpanel.add(navbutt3);
        Buttpanel.add(navbutt4);


        JPanel instance=this;
//        for(int i=0;i<5;i++){
//            final int indice=i;
//            arrayofbutts[i].addMouseListener(new MouseListener() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    if(e.getSource()==arrayofbutts[indice]){
//                        arrayofbutts[lastclickedbutton].setForeground(Color.WHITE);
//                        arrayofbutts[lastclickedbutton].repaint();
//
//                    }
//                    lastclickedbutton=indice;
//                    arrayofbutts[indice].setForeground(new Color(0x7C4DFF));
//                    arrayofbutts[indice].repaint();
//                    instance.revalidate();
//                    instance.repaint();
//
//                }
//
//                @Override
//                public void mousePressed(MouseEvent e) {
//
//                }
//                @Override
//                public void mouseReleased(MouseEvent e) {
//
//                }
//                @Override
//                public void mouseEntered(MouseEvent e) {
//
//                }
//                @Override
//                public void mouseExited(MouseEvent e) {
//
//                }
//            });
//        }






        this.add(Navtitle,BorderLayout.WEST);
        this.add(Buttpanel,BorderLayout.EAST);

        JPanel darkmodepanel=new JPanel();
        darkmodepanel.setBackground(Color.BLUE);
        darkmodepanel.setLayout(new BorderLayout());
        darkmodepanel.setOpaque(true);

        this.add(darkmodepanel,BorderLayout.SOUTH);

    }

    public void setdark(){
        Color comp=new Color(0x7C4DFF);
        for(NavButton button: arrayofbutts){
            if(!(button.getForeground().equals(comp))){
                button.setForeground(Color.WHITE);

            }
        }
        Navtitle.setForeground(Color.WHITE);
//        this.repaint();
    }

    public void setlight(){
        Color comp=new Color(0x7C4DFF);
        for(NavButton button: arrayofbutts){
            if(!(button.getForeground().equals(comp))){
                button.setForeground(Color.BLACK);

            }

        }
        Navtitle.setForeground(Color.BLACK);
//         this.repaint();
    }


}
class NavButton extends JLabel{

    public NavButton(String title){
        this.setSize(50,50);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(true);
        this.setText(title);
        this.setFont(new Font("Montserrat",Font.BOLD,14));
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
    }

}
