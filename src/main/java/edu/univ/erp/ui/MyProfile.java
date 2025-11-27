package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.icons.FlatWindowAbstractIcon;
import com.google.protobuf.Message;
import com.sun.source.tree.WhileLoopTree;
import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.service.GET;
import edu.univ.erp.service.Transcriptwriter;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.Nogradesexception;
import edu.univ.erp.util.infodialog;
import edu.univ.erp.util.simpmsgdialog;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class MyProfile extends JPanel {
    //color palette
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    //ends here

    //event handlers and shi
    public JLabel persprof,transcpt,fees;
    private JLabel[] arrayofbutts=new JLabel[3];
    private int lastclickebutton=0;
    int currentpan=0;
    JPanel[] arrayofpans;
    public MyProfile(Main mw, Statevariables statevar){

        try{
            UIManager.put("Component.focusColor", new Color(0x7C4DFF));  // Pink accent
            UIManager.put("Button.focusColor", new Color(0x7C4DFF));
            UIManager.put("ToggleButton.focusColor", new Color(0x7C4DFF));
            UIManager.put("ProgressBar.selectionForeground", new Color(0x7C4DFF));
            UIManager.put("TextComponent.arc", 95);
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setBackground(DARK);
        this.setSize(500,500);
        this.setLayout(new BorderLayout(60,20));


        Navbar navpanel=new Navbar(3,mw,statevar);

        //panels for padding
        JPanel westpadpan=new JPanel();
        westpadpan.setBackground(TRANSPARENT);

        JPanel eastpadpan=new JPanel();
        eastpadpan.setBackground(TRANSPARENT);
        //padding panels fin

        JPanel myprofpan=new JPanel();
        myprofpan.setLayout(new BorderLayout(10,0));
        myprofpan.setBackground(DARK);
        myprofpan.setBorder(new ArcBorder(Color.GRAY,2,12));


        JPanel myprofNavpanel=new JPanel();
        myprofNavpanel.setLayout(new BorderLayout());
        myprofNavpanel.setPreferredSize(new Dimension(120,50));
        myprofNavpanel.setBackground(DARK);

        JPanel profbuttpanel=new JPanel();
        profbuttpanel.setBackground(TRANSPARENT);
        profbuttpanel.setPreferredSize(new Dimension(500,100));
        profbuttpanel.setLayout(new FlowLayout(FlowLayout.LEFT,60,15));





        persprof=new JLabel("Personal Info");
        persprof.setFont(new Font("Montserrat",Font.BOLD,14));
        persprof.setForeground(Color.WHITE);
        profbuttpanel.add(persprof);

        fees=new JLabel("Fee Dues");
        fees.setFont(new Font("Montserrat",Font.BOLD,14));
        fees.setForeground(Color.WHITE);
        profbuttpanel.add(fees);

        transcpt=new JLabel("Transcript");
        transcpt.setFont(new Font("Montserrat",Font.BOLD,14));
        transcpt.setForeground(Color.WHITE);
        profbuttpanel.add(transcpt);



        arrayofbutts[0]=persprof;
        arrayofbutts[1]=fees;
        arrayofbutts[2]=transcpt;

        arrayofbutts[lastclickebutton].setForeground(PURPLE);


        for(int i=0;i<3;i++){
            final int index=i;
            arrayofbutts[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getSource()==arrayofbutts[index]){
                        arrayofbutts[lastclickebutton].setForeground(Color.WHITE);
                        arrayofbutts[lastclickebutton].repaint();
                    }
                    lastclickebutton=index;
                    arrayofbutts[index].setForeground(PURPLE);
                    arrayofbutts[index].repaint();
                    myprofNavpanel.revalidate();
                    myprofNavpanel.repaint();
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

        myprofNavpanel.add(profbuttpanel,BorderLayout.WEST);

        //diff options, different panels
        myprofinfopanel myprofinfopanel=new myprofinfopanel(mw,statevar);

        feeduepanel feeduepan=new feeduepanel();

        transcpt transcpt1=new transcpt(mw,statevar);

        arrayofpans=new JPanel[3];
        arrayofpans[0]=myprofinfopanel;arrayofpans[1]=feeduepan;arrayofpans[2]=transcpt1;





        myprofpan.add(myprofNavpanel,BorderLayout.NORTH);
        myprofpan.add(myprofinfopanel,BorderLayout.CENTER);
//        myprofpan.add(feeduepan,BorderLayout.CENTER);
//        myprofpan.add(transcpt,BorderLayout.CENTER);

        myprofpan.setBackground(DARK);


        fees.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myprofpan.remove(arrayofpans[currentpan]);

                myprofpan.add(feeduepan,BorderLayout.CENTER);
                myprofpan.revalidate();
                myprofpan.repaint();
                currentpan=1;
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
        persprof.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myprofpan.remove(arrayofpans[currentpan]);
                myprofpan.add(myprofinfopanel,BorderLayout.CENTER);
                myprofpan.revalidate();
                myprofpan.repaint();
                currentpan=0;
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
        transcpt.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myprofpan.remove(arrayofpans[currentpan]);
                myprofpan.add(transcpt1,BorderLayout.CENTER);
                myprofpan.revalidate();
                myprofpan.repaint();
                currentpan=2;
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



        maintainencemsg msg=new maintainencemsg("Maintainence Underway! Please Cooperate");


        this.add(navpanel,BorderLayout.NORTH);
        this.add(myprofpan,BorderLayout.CENTER);
        this.add(westpadpan,BorderLayout.WEST);
        this.add(eastpadpan,BorderLayout.EAST);
//        this.add(maintainence,BorderLayout.SOUTH);
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




class transcpt extends JPanel{

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    edu.univ.erp.domain.Grades grades=new edu.univ.erp.domain.Grades();

    public transcpt(Main mw,Statevariables statevar){
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,90));
        this.setBackground(TRANSPARENT);
        JPanel pdfpanel=new JPanel();
        pdfpanel.setPreferredSize(new Dimension(1100,60));
        pdfpanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton pdfbutton=new JButton("Download");
        pdfbutton.setFocusable(false);

        //listener to update the grades variable
        statevar.addListener(()->{grades=statevar.getgrades();});

        JLabel pdfmainlab=new JLabel("Download transcript.pdf : ");
        pdfmainlab.setFont(new Font("Montserrat",Font.BOLD,25));
        pdfmainlab.setForeground(Color.WHITE);


        pdfpanel.add(pdfmainlab);
        pdfpanel.add(pdfbutton);
        JPanel csvpanel=new JPanel();


        JButton pdfbutton2=new JButton("Download");
        pdfbutton2.setFocusable(false);

        pdfbutton2.addActionListener(e-> {

            try {
                if (grades.isempty()) {
                    statevar.setgrades(GET.grades(statevar.getId()));
                    if (grades.isempty()) {
                        throw new Nogradesexception(Messages.NO_GRADES_ERROR);
                    }
                }
                Transcriptwriter.writeTranscriptCSV(grades, statevar.getUsername() + "_transcript.csv");
                simpmsgdialog msg=new simpmsgdialog(Messages.DOWNLOAD_COMPLETE);
                System.out.println(grades.semester1);

            }catch (Exception ex) {
                errorinfodialog errmsg=new errorinfodialog(ex.getMessage());
            }
        });

        pdfbutton.addActionListener(e->{
            try {
                if (grades.isempty()) {
                    statevar.setgrades(GET.grades(statevar.getId()));
                    if (grades.isempty()) {
                        throw new Nogradesexception(Messages.NO_GRADES_ERROR);
                    }
                }
                String filename="C:/Users/scd24/OneDrive/Desktop/dev/Acadassist/"+statevar.getUsername()+"_transcript.pdf";
                Transcriptwriter.writeTranscriptPDF(grades,filename);
                simpmsgdialog msg=new simpmsgdialog(Messages.DOWNLOAD_COMPLETE);
                System.out.println(grades.semester1);

            }catch (Exception ex) {
                errorinfodialog errmsg=new errorinfodialog(ex.getMessage());
            }

        });

        JLabel csvmainlab=new JLabel("Download transcript.csv : ");
        csvmainlab.setFont(new Font("Montserrat",Font.BOLD,25));
        csvmainlab.setForeground(Color.WHITE);
        csvpanel.add(csvmainlab);
        csvpanel.add(pdfbutton2);


        csvpanel.setPreferredSize(new Dimension(1100,60));
        pdfpanel.setBackground(DARK);
        csvpanel.setBackground(DARK);
        this.add(pdfpanel);
        this.add(csvpanel);


    }
}

class myprofinfopanel extends JPanel{
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);

    JLabel name,dob,Roll_no,Residentaddress,Permanentaddress;

    public myprofinfopanel(Main mw,Statevariables statevar){
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

        name=new JLabel("Name: lorem");
        name.setForeground(Color.WHITE);
        dob=new JLabel("DOB: DD/MM/YYYY");
        dob.setForeground(Color.WHITE);
        Roll_no=new JLabel("Roll no: XXXXXX");
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

        JLabel Residendettxt=new JLabel("Academic Details");
        Residendettxt.setForeground(Color.WHITE);
        Residendettxt.setFont(new Font("Montserrat",Font.BOLD,24));
        Residentdetpan.add(Residendettxt);

        JPanel basdet2=new JPanel();
        basdet2.setPreferredSize(new Dimension(1100,180));
        basdet2.setLayout(new FlowLayout(FlowLayout.LEFT,30,50));

        Residentaddress=new JLabel("Name of Program: AAAAAAABBBBBBBBCCCCCCCCCDDDDDDDDD");
        Residentaddress.setBackground(DARK);
        Residentaddress.setOpaque(true);
        Residentaddress.setFont(new Font("Montserrat",Font.PLAIN,16));
        Residentaddress.setForeground(Color.WHITE);
        Residentaddress.setPreferredSize(new Dimension(1100,40));

        Permanentaddress=new JLabel("Year of Study: EEEEEEEEFFFFFFFFGGGGGGGGGHHHHHHHHH");
        Permanentaddress.setBackground(DARK);
        Permanentaddress.setOpaque(true);
        Permanentaddress.setFont(new Font("Montserrat",Font.PLAIN,18));
        Permanentaddress.setForeground(Color.WHITE);
        Permanentaddress.setPreferredSize(new Dimension(1100,40));

        statevar.addListener(()->{
            dob.setText("DOB: "+statevar.getDOB());
            Residentaddress.setText("Name of Program: "+statevar.getProgram());
            Permanentaddress.setText("Year of Study: "+statevar.getYear());
            name.setText("Name: "+statevar.getUsername());
            Roll_no.setText("Roll no: "+statevar.getRollno());

        });

        basdet2.add(Residentaddress);
        basdet2.add(Permanentaddress);
        basdet2.setBackground(DARK);


        this.add(basdet1);
        this.add(Residentdetpan);
        this.add(basdet2);


        persinftitlpan.add(persinftitle,BorderLayout.WEST);
    }
}

class feeduepanel extends JPanel{
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    public feeduepanel(){
        this.setBackground(DARK);

        JLabel feeduemsg=new JLabel("No Dues Remaining");
        feeduemsg.setBackground(DARK);
        feeduemsg.setPreferredSize(new Dimension(500,80));
        feeduemsg.setFont(new Font("Montserrat",Font.BOLD,40));
        feeduemsg.setHorizontalTextPosition(SwingConstants.CENTER);
        feeduemsg.setForeground(Color.WHITE);
        feeduemsg.setOpaque(true);
        this.add(feeduemsg,BorderLayout.CENTER);
    }
}



class profdemo{
    public static void main(String[] args){
//        MyProfile demo=new MyProfile();
    }
}