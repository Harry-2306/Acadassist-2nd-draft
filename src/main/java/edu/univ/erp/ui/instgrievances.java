package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.Statevariables;

import javax.swing.*;
import java.awt.*;

public class instgrievances extends JPanel {

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);

    public instgrievances(Main mw, Statevariables statevar){

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
        this.setLayout(new BorderLayout(0,10));

        instnavbar navbar=new instnavbar(2,mw,statevar);


        JLabel grievtitlab=new JLabel("File a Grievance");
        grievtitlab.setFont(new Font("Montserrat",Font.BOLD,44));
        grievtitlab.setForeground(Color.WHITE);
        grievtitlab.setHorizontalAlignment(SwingConstants.CENTER);

        String[] options={"Academics","Disciplinary conduct","Procedural/Bureaucratic","Other"};
        grievance grievancepan=new grievance(options,mw,statevar);



        JPanel mainpan=new JPanel();
        mainpan.setLayout(new BorderLayout());
        mainpan.add(grievtitlab,BorderLayout.NORTH);
        mainpan.setBackground(DARK);
        mainpan.add(grievancepan,BorderLayout.CENTER);

        maintainencemsg msg=new maintainencemsg("Maintainence underway");

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








class instgrievdemo{
    public static void main(String[] args){
//        instgrievances demo=new instgrievances();

    }
}