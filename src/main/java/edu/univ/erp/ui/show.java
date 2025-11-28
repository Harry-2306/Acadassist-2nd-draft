package edu.univ.erp.ui;

import edu.univ.erp.data.Statevariables;
import edu.univ.erp.util.Messages;

import javax.swing.*;
import java.awt.*;

 class Main extends JFrame {
    private CardLayout layout=new CardLayout();
    private JPanel root=new JPanel(layout);

    public Main(){
        this.setTitle("Acadassist");
        this.setSize(900,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(root);

    }

    public void addPage(JPanel page, String name) {
        root.add(page, name);
    }

    public void showPage(String name) {
        layout.show(root, name);

    }

}



public class show{
    public static void main(String[] args){
        Main mw = new Main();

        try{
            Statevariables object = new Statevariables();
            mw.addPage(new Login(mw,object),"Login");
            mw.addPage(new forgotpassword(mw,object),"forgor");
            //student panels
            mw.addPage(new Home(mw,object), "Home");
            mw.addPage(new Courses(mw,object),"StuCourses");
            mw.addPage(new Grades(mw,object),"Grades");
            mw.addPage(new MyProfile(mw,object),"stuprofile");
            mw.addPage(new timetable(mw,object),"timetable");
            //instructor panels
            mw.addPage(new Homeinst(mw,object), "InstHome");
            mw.addPage(new mysections(mw,object),"mysections");
            mw.addPage(new instgrievances(mw,object),"instgrievance");
            mw.addPage(new instmyprofile(mw,object),"instprofile");
            //admin panels
            mw.addPage(new adminhome(mw,object),"AdminHome");
            mw.addPage(new admprofile(mw,object),"Admprofile");
            mw.addPage(new maintain(mw,object),"maintain");
            mw.addPage(new admcourses(mw,object),"admcourses");

            mw.setVisible(true);

            mw.showPage("Login");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            errorinfodialog msg=new errorinfodialog(Messages.BOOTUP_FAIL_ERROR);
        }

    }
}