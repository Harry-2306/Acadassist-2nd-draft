package edu.univ.erp.ui;

import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.instructorprofile;
import edu.univ.erp.domain.mysection;
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

public class Homeinst extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    JLabel Welcomelab;
    public Homeinst(Main mw, Statevariables statevar){

        this.setSize(500,500);
        this.setBackground(DARK);
        this.setLayout(new BorderLayout(0,20));

        instnavbar navbar=new instnavbar(0,mw,statevar);



        Welcomelab=new JLabel("  Welcome instructor! You are logged in as an instructor.");
        Welcomelab.setHorizontalAlignment(SwingConstants.LEFT);
        Welcomelab.setFont(new Font("Montserrat",Font.BOLD,35));
        Welcomelab.setBackground(DARK);
        Welcomelab.setForeground(Color.WHITE);
        Welcomelab.setOpaque(true);

        statevar.addListener(()->Welcomelab.setText("  Welcome "+statevar.getUsername()+"! You are logged in as an instructor."));


        quickactioncard card1=new quickactioncard("My Profile",mw,statevar,3);
        quickactioncard card2=new quickactioncard("Grievance",mw,statevar,4);
        quickactioncard card3=new quickactioncard("Manage section scores",mw,statevar,5);

        card1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    instructorprofile output = GET.instdetails(statevar.getId());
                    output.name=statevar.getUsername();
                    statevar.setInstprofile(output);

                    mw.showPage("instprofile");
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

        card2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mw.showPage("instgrievance");
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

        card3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ArrayList<mysection> output=GET.mysections(statevar.getId());
                    statevar.setMysec(output);
//                    errorinfodialog msg=new errorinfodialog("opening....");
                    if(output.isEmpty()){
                        throw new Exception();
                    }
                    mw.showPage("mysections");
                } catch (SQLException ex) {
                    errorinfodialog msg=new errorinfodialog(Messages.TAB_OPEN_ERROR);
                } catch (Exception ex) {
                    errorinfodialog msg=new errorinfodialog(Messages.NO_SECTION_ERROR);
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
        quiactpan.setLayout(new FlowLayout(FlowLayout.CENTER,50,90));
        quiactpan.setBackground(DARK);
        quiactpan.add(card1);
        quiactpan.add(card2);
        quiactpan.add(card3);

        JPanel maincontpanel=new JPanel();
        maincontpanel.setBackground(DARK);
        maincontpanel.setLayout(new BorderLayout());
        maincontpanel.add(Welcomelab,BorderLayout.NORTH);
        maincontpanel.add(quiactpan,BorderLayout.CENTER);








        //something needs to be added here



        maintainencemsg msg=new maintainencemsg(Messages.INST_MAINT_MSG);


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

class instnavbar extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private int swidth=1280;
    int lastclickedbutton;
    NavButton navbutt0,navbutt1,navbutt2,navbutt3,navbutt4;
    NavButton[] arrayofbutts=new NavButton[5];
    JLabel Navtitle;
    String id="";
    public instnavbar(int input,Main mw,Statevariables statevar) {
        lastclickedbutton = input;
        this.setPreferredSize(new Dimension(swidth, 100));
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(0, 20, 0, 20));
        this.setBackground(DARK);

        Navtitle = new JLabel("Acadassist");
        Navtitle.setPreferredSize(new Dimension(230, 60));
        Navtitle.setFont(new Font("Montserrat", Font.BOLD, 41));
        Navtitle.setForeground(new Color(0x7C4DFF));
        Navtitle.setBackground(TRANSPARENT);
        Navtitle.setOpaque(true);

        JPanel Buttpanel = new JPanel();
        Buttpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 40));
        Buttpanel.setPreferredSize(new Dimension(680, 60));
        Buttpanel.setBackground(TRANSPARENT);

        navbutt0 = new NavButton("Home");
        navbutt1 = new NavButton("My Sections");
        navbutt2 = new NavButton("Grievances");
        navbutt3 = new NavButton("My Profile");
        navbutt4 = new NavButton("LogOut");


        arrayofbutts[0] = navbutt0;
        arrayofbutts[1] = navbutt1;
        arrayofbutts[2] = navbutt2;
        arrayofbutts[3] = navbutt3;
        arrayofbutts[4] = navbutt4;





        arrayofbutts[lastclickedbutton].setForeground(new Color(0x7C4DFF));

        Buttpanel.add(navbutt0);
        Buttpanel.add(navbutt1);
        Buttpanel.add(navbutt2);
        Buttpanel.add(navbutt3);
        Buttpanel.add(navbutt4);

        statevar.addListener(()->id=statevar.getId());

        navbutt4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //log out

                AuthOps.updatestatus(id, false);
                System.out.println(id);
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

        navbutt3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try{
                    instructorprofile output = GET.instdetails(statevar.getId());
                    output.name=statevar.getUsername();
                    statevar.setInstprofile(output);

                    mw.showPage("instprofile");
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

        navbutt2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(statevar.getmaintainencecheck()){
                    errorinfodialog msg=new errorinfodialog(Messages.NO_GRIEVANCE);
                    return;
                }
                mw.showPage("instgrievance");
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
                try {
                    ArrayList<mysection> output=GET.mysections(statevar.getId());
                    statevar.setMysec(output);
//                    errorinfodialog msg=new errorinfodialog("opening....");
                    if(output.isEmpty()){
                        throw new Exception();
                    }
                    mw.showPage("mysections");
                } catch (SQLException ex) {
                    errorinfodialog msg=new errorinfodialog(Messages.TAB_OPEN_ERROR);
                } catch (Exception ex) {
                    errorinfodialog msg=new errorinfodialog(Messages.NO_SECTION_ERROR);
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
                mw.showPage("InstHome");
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

        JPanel instance = this;
//        for (int i = 0; i < 5; i++) {
//            final int indice = i;
//            arrayofbutts[i].addMouseListener(new MouseListener() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    if (e.getSource() == arrayofbutts[indice]) {
//                        arrayofbutts[lastclickedbutton].setForeground(Color.WHITE);
//                        arrayofbutts[lastclickedbutton].repaint();
//
//                    }
//                    lastclickedbutton = indice;
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
//
//                @Override
//                public void mouseReleased(MouseEvent e) {
//
//                }
//
//                @Override
//                public void mouseEntered(MouseEvent e) {
//
//                }
//
//                @Override
//                public void mouseExited(MouseEvent e) {
//
//                }
//            });
//        }

        this.add(Navtitle,BorderLayout.WEST);
        this.add(Buttpanel,BorderLayout.EAST);
    }
}













//class Homeinstdemo{
//    public static void main(String[] args){
//        Homeinst demo=new Homeinst();
//    }
//}