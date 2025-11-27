package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.admninfo;
import edu.univ.erp.domain.message;
import edu.univ.erp.domain.sectionlogistics;
import edu.univ.erp.service.AuthOps;
import edu.univ.erp.service.GET;
import edu.univ.erp.util.Messages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class adminhome extends JPanel {

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    final private int swidth=1280;
    JLabel welcommsg;
    public adminhome(Main mw, Statevariables statevar){

        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setSize(500,500);
        this.setBackground(DARK);
        this.setLayout(new BorderLayout());

        admNavbar navbar=new admNavbar(0,mw,statevar);


        welcommsg=new JLabel("Welcome sir/ma'am! You are logged in as an admin.");
        welcommsg.setFont(new Font("Montserrat",Font.BOLD,30));
        welcommsg.setForeground(Color.WHITE);
        welcommsg.setBackground(TRANSPARENT);
        welcommsg.setOpaque(true);

        statevar.addListener(()-> welcommsg.setText("Welcome "+statevar.getUsername()+"! You are logged in as an admin."));

        JLabel quiactlab=new JLabel("Quick Actions");
        quiactlab.setForeground(Color.WHITE);
        quiactlab.setPreferredSize(new Dimension(swidth-100,40));
        quiactlab.setHorizontalTextPosition(SwingConstants.LEFT);
        quiactlab.setVerticalTextPosition(SwingConstants.CENTER);
        quiactlab.setFont(new Font("Montserrat",Font.BOLD,30));
        quiactlab.setBackground(DARK);

        quickactioncard togglemaint=new quickactioncard("Toggle Maintainence",mw,statevar,6);
        quickactioncard addpeople=new quickactioncard("Add People",mw,statevar,7);
        quickactioncard inbox=new quickactioncard("Check Inbox",mw,statevar,8);

        togglemaint.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ArrayList<message> output=GET.messages();
                    statevar.setMessageArrayList(output);
                    mw.showPage("maintain");
                } catch (SQLException ex) {
                    errorinfodialog ms=new errorinfodialog(Messages.GENERIC_ERROR);
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

        addpeople.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ArrayList<message> output=GET.messages();
                    statevar.setMessageArrayList(output);
                    mw.showPage("maintain");
                } catch (SQLException ex) {
                    errorinfodialog ms=new errorinfodialog(Messages.GENERIC_ERROR);
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


        inbox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ArrayList<message> output=GET.messages();
                    statevar.setMessageArrayList(output);
                    mw.showPage("maintain");
                } catch (SQLException ex) {
                    errorinfodialog ms=new errorinfodialog(Messages.GENERIC_ERROR);
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

        JPanel quiactpan=new JPanel();
        quiactpan.setPreferredSize(new Dimension(swidth-100,250));
        quiactpan.setBackground(TRANSPARENT);
        quiactpan.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
        quiactpan.add(togglemaint);quiactpan.add(addpeople);quiactpan.add(inbox);





        JPanel subcontpanel=new JPanel();
        subcontpanel.setBackground(TRANSPARENT);
        subcontpanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));
        subcontpanel.setBackground(DARK);
        subcontpanel.add(quiactlab);
        subcontpanel.add(quiactpan);

        JPanel contentpanel=new JPanel();
        contentpanel.setBackground(TRANSPARENT);
        contentpanel.setLayout(new BorderLayout());
        contentpanel.setBorder(new EmptyBorder(20,20,0,0));
        contentpanel.add(welcommsg,BorderLayout.NORTH);
        contentpanel.add(subcontpanel,BorderLayout.CENTER);


        maintainencemsg msg=new maintainencemsg("Maintainence underway bozo :p");
        this.add(navbar,BorderLayout.NORTH);
        this.add(contentpanel,BorderLayout.CENTER);
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

class admNavbar extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private int swidth=1280;
    int lastclickedbutton;
    NavButton navbutt0,navbutt1,navbutt2,navbutt3,navbutt4;
    NavButton[] arrayofbutts=new NavButton[5];
    JLabel Navtitle;
    String id="";
    public admNavbar(int input,Main mw,Statevariables statevar){
        lastclickedbutton=input;
        this.setPreferredSize(new Dimension(swidth,100));
        this.setBackground(TRANSPARENT);
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(0,20,0,20));
        this.setBackground(DARK);

        statevar.addListener(()->{
            id=statevar.getId();
        });

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
        navbutt1=new NavButton("Maintain");
        navbutt2=new NavButton("Courses");
        navbutt3=new NavButton("MyProfile");
        navbutt4=new NavButton("LogOut");


        arrayofbutts[0]=navbutt0;
        arrayofbutts[1]=navbutt1;
        arrayofbutts[2]=navbutt2;
        arrayofbutts[3]=navbutt3;
        arrayofbutts[4]=navbutt4;

        arrayofbutts[lastclickedbutton].setForeground(new Color(0x7C4DFF));

        Buttpanel.add(navbutt0);
        Buttpanel.add(navbutt1);
        Buttpanel.add(navbutt2);
        Buttpanel.add(navbutt3);
        Buttpanel.add(navbutt4);


        navbutt4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                AuthOps.updatestatus(id, false);
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


        navbutt3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getSource()==navbutt3){
                    try {
                        System.out.println(statevar.getId());
                        admninfo object= GET.admin(statevar.getId());
                        statevar.setAdmobj(object);

                        mw.showPage("Admprofile");
                    } catch (SQLException ex) {
                        errorinfodialog msg=new errorinfodialog(Messages.TAB_OPEN_ERROR);
                    }

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


        navbutt2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ArrayList<sectionlogistics> output=GET.seclog();
                    statevar.setSections(output);
                    mw.showPage("admcourses");
                } catch (SQLException ex) {
                    errorinfodialog msg=new errorinfodialog(Messages.TAB_OPEN_ERROR);
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


        navbutt1.addMouseListener(new MouseListener() {
            @Override

            public void mouseClicked(MouseEvent e) {
                // fetch messages
                try {
                    ArrayList<message> output=GET.messages();
                    statevar.setMessageArrayList(output);
                    mw.showPage("maintain");
                } catch (SQLException ex) {
                    errorinfodialog ms=new errorinfodialog(Messages.GENERIC_ERROR);
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


        navbutt0.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mw.showPage("AdminHome");
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

class admdemo{
    public static void main(String[] args){
//        adminhome demo=new adminhome();
    }
}