package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.util.Messages;

import javax.swing.*;
import java.awt.*;

public class instmyprofile extends JPanel {

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);

    public instmyprofile(Main mw, Statevariables statevar){

        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setBackground(DARK);
        this.setSize(500,500);
        this.setLayout(new BorderLayout(60,20));

        instnavbar navpanel=new instnavbar(3,mw,statevar);
        //panels for padding
        JPanel westpadpan=new JPanel();
        westpadpan.setBackground(TRANSPARENT);

        JPanel eastpadpan=new JPanel();
        eastpadpan.setBackground(TRANSPARENT);
        //padding panels fin


        instinfopanel instprof=new instinfopanel(mw,statevar);

        JPanel myprofpan=new JPanel();
        myprofpan.setLayout(new BorderLayout(10,0));
        myprofpan.setBackground(DARK);
        myprofpan.setBorder(new ArcBorder(Color.GRAY,2,12));
        myprofpan.add(instprof,BorderLayout.CENTER);

        maintainencemsg msg=new maintainencemsg(Messages.INST_MAINT_MSG);

        this.add(navpanel,BorderLayout.NORTH);
        this.add(myprofpan,BorderLayout.CENTER);
        this.add(westpadpan,BorderLayout.WEST);
        this.add(eastpadpan,BorderLayout.EAST);
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



class instinfopanel extends JPanel{
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    JLabel name=new JLabel("Name: lorem");
    JLabel dob=new JLabel("DOB: DD/MM/YYYY");
    JLabel Roll_no=new JLabel("Occupation: XXXXXX");
    JLabel Residentaddress=new JLabel("Department: AAAAAAABBBBBBBBCCCCCCCCCDDDDDDDDD");

    public instinfopanel(Main mw,Statevariables statevar){
        this.setBackground(DARK);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));

        JPanel persinftitlpan=new JPanel();
        persinftitlpan.setPreferredSize(new Dimension(1100,40));
        persinftitlpan.setBackground(DARK);
        persinftitlpan.setLayout(new BorderLayout());
        this.add(persinftitlpan);

        JLabel persinftitle=new JLabel("Basic Details");
        persinftitle.setForeground(Color.WHITE);
        persinftitle.setFont(new Font("Montserrat",Font.BOLD,24));


        JPanel basdet1=new JPanel();
        basdet1.setPreferredSize(new Dimension(1100,40));
        basdet1.setLayout(new FlowLayout(FlowLayout.LEFT,30,10));
        basdet1.setBackground(TRANSPARENT);

        statevar.addListener(()->{
            name.setText("Name: "+statevar.getInstprofile().name);
            dob.setText("DOB: "+statevar.getInstprofile().dob);
            Roll_no.setText("Occupation : "+statevar.getInstprofile().occupation);
            Residentaddress.setText("Department: "+statevar.getInstprofile().department);
        });


        name.setForeground(Color.WHITE);
        dob.setForeground(Color.WHITE);
        Roll_no.setForeground(Color.WHITE);
        name.setFont(new Font("Montserrat",Font.PLAIN,18));
        dob.setFont(new Font("Montserrat",Font.PLAIN,18));
        Roll_no.setFont(new Font("Montserrat",Font.PLAIN,18));
        basdet1.add(name);
        basdet1.add(dob);
        basdet1.add(Roll_no);

        JPanel Residentdetpan=new JPanel();
        Residentdetpan.setPreferredSize(new Dimension(1100,40));
        Residentdetpan.setBackground(DARK);
        Residentdetpan.setLayout(new BorderLayout());

        JLabel Residendettxt=new JLabel("Residential Details&department");
        Residendettxt.setForeground(Color.WHITE);
        Residendettxt.setFont(new Font("Montserrat",Font.BOLD,24));
        Residentdetpan.add(Residendettxt);

        JPanel basdet2=new JPanel();
        basdet2.setPreferredSize(new Dimension(1100,180));
        basdet2.setLayout(new FlowLayout(FlowLayout.LEFT,30,50));

        Residentaddress.setBackground(DARK);
        Residentaddress.setOpaque(true);
        Residentaddress.setFont(new Font("Montserrat",Font.PLAIN,16));
        Residentaddress.setForeground(Color.WHITE);
        Residentaddress.setPreferredSize(new Dimension(1100,40));

        JLabel Permanentaddress=new JLabel("Residential address: EEEEEEEEFFFFFFFFGGGGGGGGGHHHHHHHHH");
        Permanentaddress.setBackground(DARK);
        Permanentaddress.setOpaque(true);
        Permanentaddress.setFont(new Font("Montserrat",Font.PLAIN,18));
        Permanentaddress.setForeground(Color.WHITE);
        Permanentaddress.setPreferredSize(new Dimension(1100,40));

        basdet2.add(Residentaddress);
        basdet2.add(Permanentaddress);
        basdet2.setBackground(DARK);


        this.add(basdet1);
        this.add(Residentdetpan);
        this.add(basdet2);


        persinftitlpan.add(persinftitle,BorderLayout.WEST);
    }
}











class profiledemo{
//    public static void main(String[] args){
//        instmyprofile demo=new instmyprofile();
//    }
}