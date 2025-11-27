package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.Student;
import edu.univ.erp.service.GET;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.unsucessfulretrievalexception;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.SQLException;

public class Home extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    int lastclickedbutton=0;
    JLabel welcommsg;
    JLabel totcredtxt;
    String name;
    public Home(Main mw, Statevariables statevariables){

        int swidth=Toolkit.getDefaultToolkit().getScreenSize().width;

        name=statevariables.getUsername();
        statevariables.addListener(()->welcommsg.setText("Welcome "+statevariables.getUsername()+"! You are logged in as a student."));


        System.out.println(name);

        this.setLayout(new BorderLayout());
        this.setSize(500,500);
        this.setBackground(DARK);

        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JPanel navpanel=new Navbar(0,mw,statevariables);

        JPanel contentpanel=new JPanel();
        contentpanel.setBackground(TRANSPARENT);
        contentpanel.setLayout(new BorderLayout());
        contentpanel.setBorder(new EmptyBorder(20,20,0,0));


        welcommsg=new JLabel("Welcome "+name+"! You are logged in as a student.");
        welcommsg.setFont(new Font("Montserrat",Font.BOLD,30));
        welcommsg.setForeground(Color.WHITE);
        welcommsg.setBackground(TRANSPARENT);
        welcommsg.setOpaque(true);


        JPanel subcontpanel=new JPanel();
        subcontpanel.setBackground(TRANSPARENT);
        subcontpanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,20));

        JPanel credcomppan=new JPanel();
        credcomppan.setBackground(TRANSPARENT);
        credcomppan.setPreferredSize(new Dimension(swidth-80,120));
        Border padding=new EmptyBorder(0,20,0,20);
        credcomppan.setBorder(new CompoundBorder(new ArcBorder(Color.GRAY,1,18),padding));
//        credcomppan.setBorder(new LineBorder(Color.BLACK));
        credcomppan.putClientProperty(FlatClientProperties.STYLE, "arc: 12;");
        credcomppan.setLayout(new BorderLayout(30,40));


        JLabel Credcomptxt=new JLabel("Credits Completed");
        Credcomptxt.setFont(new Font("Montserrat",Font.BOLD,20));
        Credcomptxt.setBackground(TRANSPARENT);
        Credcomptxt.setForeground(Color.WHITE);
        Credcomptxt.setOpaque(true);

        totcredtxt=new JLabel("000/300");
        totcredtxt.setFont(new Font("Montserrat",Font.PLAIN,20));
        totcredtxt.setBackground(TRANSPARENT);
        totcredtxt.setForeground(PURPLE);
        totcredtxt.setOpaque(true);

        statevariables.addListener(()->totcredtxt.setText(statevariables.getCredits()+"/300"));

        credcomppan.add(Credcomptxt,BorderLayout.WEST);
        credcomppan.add(totcredtxt,BorderLayout.EAST);

        JLabel quiactlab=new JLabel("Quick Actions");
        quiactlab.setForeground(Color.WHITE);
        quiactlab.setPreferredSize(new Dimension(swidth-100,40));
        quiactlab.setHorizontalTextPosition(SwingConstants.LEFT);
        quiactlab.setVerticalTextPosition(SwingConstants.CENTER);
        quiactlab.setFont(new Font("Montserrat",Font.BOLD,30));
        quiactlab.setBackground(DARK);


        JPanel quiactpan=new JPanel();
        quiactpan.setPreferredSize(new Dimension(swidth-100,250));
        quiactpan.setBackground(TRANSPARENT);
        quiactpan.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));

        //quick action card starts here
        quickactioncard transcript=new quickactioncard("View Transcript",mw,statevariables,0);
        quickactioncard fees=new quickactioncard("Fee Details",mw,statevariables,1);
        quickactioncard timetable=new quickactioncard("View Time table",mw,statevariables,2);

        transcript.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                        Student output = GET.stu_details(statevariables.getId());
                        statevariables.setProgram(output.program);
                        statevariables.setDOB(output.DOB);
                        statevariables.setYear(output.year);
                        statevariables.setRollno(output.Rollno);
                    } catch (unsucessfulretrievalexception ex) {
                        errorinfodialog msg=new errorinfodialog(Messages.TAB_OPEN_ERROR);
                    }

                mw.showPage("stuprofile");
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

        timetable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //fetch data ops
                try {
                    statevariables.setTt(GET.timetable(statevariables.getId()));
                    mw.showPage("timetable");
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

        fees.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Student output = GET.stu_details(statevariables.getId());
                    statevariables.setProgram(output.program);
                    statevariables.setDOB(output.DOB);
                    statevariables.setYear(output.year);
                    statevariables.setRollno(output.Rollno);
                    mw.showPage("stuprofile");
                } catch (unsucessfulretrievalexception ex) {
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

        quiactpan.add(transcript);
        quiactpan.add(fees);
        quiactpan.add(timetable);
        subcontpanel.add(credcomppan);
        subcontpanel.add(quiactlab);
        subcontpanel.add(quiactpan);




        contentpanel.add(welcommsg,BorderLayout.NORTH);
        contentpanel.add(subcontpanel,BorderLayout.CENTER);


        maintainencemsg msg=new maintainencemsg("Maintainence Underway! You cannot add/drop any new courses at the moment. Kindly contact admin for more information.");

        JLabel maintcheck=new JLabel("Maintainence");
        maintcheck.setBackground(PURPLE);
        maintcheck.setPreferredSize(new Dimension(swidth,30));
        maintcheck.setFont(new Font("Montserrat",Font.PLAIN,16));
        maintcheck.setHorizontalTextPosition(SwingConstants.CENTER);
        maintcheck.setOpaque(true);



        this.add(navpanel,BorderLayout.NORTH);
        this.add(contentpanel,BorderLayout.CENTER);
        if(statevariables.getmaintainencecheck()){
            this.add(msg,BorderLayout.SOUTH);
        }

        statevariables.addListener(()->{
            BorderLayout layout=(BorderLayout) this.getLayout();
            if(statevariables.getmaintainencecheck()&&(layout.getLayoutComponent(BorderLayout.SOUTH)!=msg)){
                this.add(msg,BorderLayout.SOUTH);
                this.revalidate();
                this.repaint();
            } else if ((!statevariables.getmaintainencecheck())&&(layout.getLayoutComponent(BorderLayout.SOUTH)==msg)) {
                this.remove(msg);
                this.revalidate();
                this.repaint();
            }
        });
        this.setVisible(true);

    }
}


class quickactioncard extends JLabel{
    final String[] array={"/images/transcript.png","/images/feedetails.png","/images/timetable.png","/images/something.png","/images/grievance.png","/images/section.png","/images/maintain.png","/images/section.png","/images/maintain.png","/images/add.png","/images/inbox.png"};
    public quickactioncard(String option,Main mw,Statevariables statevar,int i){
        this.setText(option);
        this.setFont(new Font("Montserrat", Font.BOLD,14));
        this.setPreferredSize(new Dimension(350,225));
        this.setForeground(Color.WHITE);
        URL url = getClass().getResource(array[i]);
        ImageIcon cardicon= new ImageIcon(url);

//        Image img= cardicon.getImage();
//        img=img.getScaledInstance(8,8,Image.SCALE_SMOOTH);
//        cardicon=new ImageIcon(img);
        this.setIcon(cardicon);
//        this.text
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setHorizontalAlignment(CENTER);
        this.setIconTextGap(8);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setBorder(new ArcBorder(Color.GRAY,1,20));
        this.setBackground(new Color(0,0,0,0));
        this.setOpaque(true);

    }
}



//class hometest{
//    public static void main(String[] args){
//        Home demo=new Home(0);
//    }
//}
