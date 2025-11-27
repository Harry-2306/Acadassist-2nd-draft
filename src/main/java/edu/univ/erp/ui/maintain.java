package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.message;
import edu.univ.erp.service.GET;
import edu.univ.erp.service.PUT;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.simpmsgdialog;
import edu.univ.erp.service.POST;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class maintain extends JPanel {

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    final private int swidth=1280;

    navlab label1,label2,label3;
    navlab[] arrayofbutts=new navlab[3];
    int lastclickebutton=0;


    public maintain(Main mw, Statevariables statevar){

        adduserpan addpeeps=new adduserpan();

        togglemaint maint=new togglemaint(mw,statevar);

        grievancepan grieve=new grievancepan(mw,statevar);

        JPanel[] arrayofpan=new JPanel[3];
        arrayofpan[0]=maint;
        arrayofpan[1]=addpeeps;
        arrayofpan[2]=grieve;

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
        this.setLayout(new BorderLayout());
        this.setBackground(DARK);



        admNavbar navbar=new admNavbar(1,mw,statevar);


        lastclickebutton=0;

        label1=new navlab("Toggle Maintainence Mode");
        arrayofbutts[0]=label1;
        label2=new navlab("Add People");
        arrayofbutts[1]=label2;
        arrayofbutts[lastclickebutton].setForeground(PURPLE);
        label3=new navlab("Grievance Inbox");
        arrayofbutts[2]=label3;

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




        //all option panels go here



        //end here
        JPanel maincontpanel=new JPanel();
        maincontpanel.setLayout(new BorderLayout());
        maincontpanel.setBackground(Color.RED);
        maincontpanel.add(mycourseNavpanel,BorderLayout.NORTH);
        maincontpanel.add(maint,BorderLayout.CENTER);


        for(int i=0;i<3;i++){
            final int index=i;
            arrayofbutts[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getSource()==arrayofbutts[index]){
                        arrayofbutts[lastclickebutton].setForeground(Color.WHITE);
                        maincontpanel.remove(arrayofpan[lastclickebutton]);
                        arrayofbutts[lastclickebutton].repaint();
                        maincontpanel.repaint();
                    }
                    lastclickebutton=index;
                    arrayofbutts[index].setForeground(PURPLE);
                    maincontpanel.add(arrayofpan[index],BorderLayout.CENTER);
                    arrayofbutts[index].repaint();
                    maincontpanel.revalidate();
                    maincontpanel.repaint();
                    mycourseNavpanel.revalidate();
                    mycourseNavpanel.repaint();
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



        maintainencemsg msg=new maintainencemsg("Maintainence Underway Bozo :p");

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


class grievancebar extends JPanel{

    final private Color DARK=new Color(0x050D1F);

    public JLabel userid=new JLabel("Userid: ");
    public JLabel problemtype=new JLabel("Subject: ");
    public JLabel rightpan=new JLabel("<html>I would like to report a problem I have been experiencing. It has caused some difficulty, and I would appreciate it if someone could look into it and help resolve it.\n</html>" +
            "Thanks in advance");

    public grievancebar(){
        this.setLayout(new BorderLayout());
        this.setBackground(DARK);
        this.setPreferredSize(new Dimension(1000,100));
        this.setMaximumSize(new Dimension(1000,100));
        JPanel leftpan=new JPanel();

        rightpan.setBackground(DARK);
        rightpan.setOpaque(true);
        this.add(leftpan,BorderLayout.WEST);
        leftpan.setPreferredSize(new Dimension(280,100));

        this.add(rightpan,BorderLayout.EAST);
        rightpan.setPreferredSize(new Dimension(700,100));
        rightpan.setVerticalAlignment(SwingConstants.CENTER);
        rightpan.setHorizontalAlignment(SwingConstants.CENTER);
        rightpan.setFont(new Font("Montserrat",Font.PLAIN,18));
        this.setBorder(new ArcBorder(Color.GRAY,2,10));

        leftpan.setLayout(new BorderLayout());


        userid.setPreferredSize(new Dimension(280,50));
        userid.setFont(new Font("Montserrat",Font.PLAIN,14));

        problemtype.setPreferredSize(new Dimension(280,50));
        problemtype.setFont(new Font("Montserrat",Font.PLAIN,14));

        leftpan.add(userid,BorderLayout.NORTH);
        leftpan.add(problemtype,BorderLayout.SOUTH);
        leftpan.setBackground(DARK);




    }
}






class grievancepan extends JPanel{
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    final private int swidth=1280;

    public grievancepan(Main mw,Statevariables statevar){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLUE);

        JPanel grievancecontainerrpan=new JPanel();
//        grievancecontainerrpan.setLayout(new BoxLayout(grievancecontainerrpan,BoxLayout.Y_AXIS));
        grievancecontainerrpan.setLayout(new BoxLayout(grievancecontainerrpan,BoxLayout.Y_AXIS));
//        grievancecontainerrpan.setPreferredSize(new Dimension(1240,800));
        grievancebar bar1=new grievancebar();
        grievancebar bar2=new grievancebar();

//        grievancecontainerrpan.add(bar1);
//        grievancecontainerrpan.add(Box.createRigidArea(new Dimension(0,20)));
//        grievancecontainerrpan.add(bar2);
//        grievancecontainerrpan.add(Box.createRigidArea(new Dimension(0,20)));
//        grievancecontainerrpan.add(new grievancebar());
//        grievancecontainerrpan.add(Box.createRigidArea(new Dimension(0,10)));
//        grievancecontainerrpan.add(new grievancebar());
//        grievancecontainerrpan.add(Box.createRigidArea(new Dimension(0,10)));
//        grievancecontainerrpan.add(new grievancebar());
//        grievancecontainerrpan.add(Box.createRigidArea(new Dimension(0,10)));

        statevar.addListener(()->{
            ArrayList<message> output=statevar.getMessageArrayList();
            for(message griev: output){
                String message=griev.msg;
                String userid=griev.id;
                String type=griev.type;
                grievancebar input=new grievancebar();
                input.problemtype.setText("Problem type: "+type);
                input.userid.setText("User id: "+userid);
                input.rightpan.setText("<html>"+message+"</html>");
                grievancecontainerrpan.add(input);
                grievancecontainerrpan.add(Box.createRigidArea(new Dimension(0,20)));
                grievancecontainerrpan.revalidate();
                grievancecontainerrpan.repaint();
            }



        });

//        grievancecontainerrpan.add(new grievancebar());
//        grievancecontainerrpan.add(new grievancebar());
//        grievancecontainerrpan.add(new grievancebar());
//        grievancecontainerrpan.add(new grievancebar());






        JScrollPane containerofcontainers=new JScrollPane();
        containerofcontainers.setViewportView(grievancecontainerrpan);
        containerofcontainers.setFocusable(false);
        containerofcontainers.setBorder(new EmptyBorder(0,0,0,0));
        containerofcontainers.getViewport().setBackground(DARK);
        grievancecontainerrpan.setBackground(DARK);
        this.add(containerofcontainers,BorderLayout.CENTER);



    }
}

class togglemaint extends JPanel{

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    final private int swidth=1280;

    public togglemaint(Main mw,Statevariables statevar){
        this.setLayout(new BorderLayout());
        this.setBackground(DARK);


        JLabel text=new JLabel("Toggle Maintainence Mode : ");
        text.setFont(new Font("Montserrat",Font.BOLD,40));
        text.setBackground(DARK);
        text.setOpaque(true);

        String[] options={"On","Off"};

        JComboBox box=new JComboBox(options);
        if(statevar.getmaintainencecheck()){
            box.setSelectedItem(options[0]);
        }else{
            box.setSelectedItem(options[1]);
        }
        box.setFocusable(false);

        JPanel subpanel=new JPanel();
        subpanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,150));
        subpanel.setBackground(DARK);
        subpanel.add(text);
        subpanel.add(box);


        JButton savebutton=new JButton("Save Changes");
        savebutton.setFocusable(false);
        savebutton.setForeground(Color.WHITE);
        savebutton.setBackground(PURPLE);

        savebutton.addActionListener(e->{
            String option=(String)box.getSelectedItem();
            boolean actualoption=option.contentEquals("Off")?false:true;
            System.out.println(actualoption);
            try {
                PUT.maintainenceval(actualoption);
                statevar.setMaintainencecheck(actualoption);
                simpmsgdialog msg=new simpmsgdialog(Messages.SAVED_SUCCESFULLY);

            } catch (SQLException ex) {
                errorinfodialog msg=new errorinfodialog(Messages.FAILED_SAVE_ERROR);
            }
        });

        JPanel buttpan=new JPanel();
        buttpan.setBackground(DARK);
        buttpan.setPreferredSize(new Dimension(40,40));
        buttpan.add(savebutton);


        this.add(subpanel,BorderLayout.CENTER);
        this.add(buttpan,BorderLayout.SOUTH);
    }
}

class adduserpan extends JPanel{
    final private Color PURPLE=new Color(0x7C4DFF);
    final private Color DARK=new Color(0x050D1F);

    String[] roles={"Student","Instructor","Admin"};

    public adduserpan(){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

        JPanel inppan=new JPanel();
        inppan.setBackground(DARK);
        inppan.setLayout(new FlowLayout(FlowLayout.CENTER));
        inppan.setPreferredSize(new Dimension(600,90));
        JLabel txtlab=new JLabel("Role");
        txtlab.setPreferredSize(new Dimension(600,30));
        JComboBox txtinput=new JComboBox(roles);
        txtinput.setPreferredSize(new Dimension(600,30));
        inppan.add(txtlab);
        inppan.add(txtinput);

        inputpan inputpan1=new inputpan("User Id");
        inputpan inputpan2=new inputpan("Department/Program");
        inputpan inputpan3=new inputpan("Name");
        inputpan inputpan4=new inputpan("Password");
        inputpan inputpan5=new inputpan("Roll no.(if applicable)/years of experience(for admin)");

        JPanel leftpan=new JPanel();
        leftpan.setBackground(DARK);
        leftpan.setPreferredSize(new Dimension(640,80));
        leftpan.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
        leftpan.add(inputpan1);
        leftpan.add(inppan);
        leftpan.add(inputpan2);


        JPanel rightpan=new JPanel();
        rightpan.setBackground(DARK);
        rightpan.setPreferredSize(new Dimension(640,80));
        rightpan.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
        rightpan.add(inputpan3);
        rightpan.add(inputpan4);
        rightpan.add(inputpan5);

        JButton button=new JButton("Add Person");
        button.setFocusable(false);
        button.setBackground(PURPLE);

        button.addActionListener(e -> {
            try{
                String role = (String) txtinput.getSelectedItem();
                if (role.contentEquals("Student")) {
                    if (inputpan1.txtinput.getText().isEmpty() || inputpan2.txtinput.getText().isEmpty() || inputpan3.txtinput.getText().isEmpty() || inputpan4.txtinput.getText().isEmpty() || inputpan5.txtinput.getText().isEmpty()) {
                        throw new Exception(Messages.EMPTY_FIELD);
                    }
                    if(!(GET.checkuniqid(inputpan1.txtinput.getText())&&GET.checkuniqroll(inputpan5.txtinput.getText()))){
                        throw new Exception(Messages.UNIQUE_VALUE_ERR);
                    }
                    //write the data
                    POST.studata(inputpan1.txtinput.getText(),inputpan3.txtinput.getText(),inputpan4.txtinput.getText(),inputpan2.txtinput.getText(),inputpan5.txtinput.getText());
                    simpmsgdialog ms=new simpmsgdialog(Messages.SUCCESFULL_ADDITION);

                }else if(role.contentEquals("Instructor")){
                    if(inputpan1.txtinput.getText().isEmpty() || inputpan2.txtinput.getText().isEmpty() || inputpan3.txtinput.getText().isEmpty() || inputpan4.txtinput.getText().isEmpty()){
                        throw new Exception(Messages.EMPTY_FIELD);
                    }
                    if(!GET.checkuniqinstid(inputpan1.txtinput.getText())){
                        throw new Exception(Messages.UNIQUE_VALUE_ERR);
                    }
                    POST.instdata(inputpan1.txtinput.getText(),inputpan3.txtinput.getText(),inputpan4.txtinput.getText(),inputpan2.txtinput.getText());
                    simpmsgdialog ms=new simpmsgdialog(Messages.SUCCESFULL_ADDITION);

                }else{
                    if(inputpan1.txtinput.getText().isEmpty()  || inputpan3.txtinput.getText().isEmpty() || inputpan4.txtinput.getText().isEmpty() || inputpan5.txtinput.getText().isEmpty()){
                        throw new Exception(Messages.EMPTY_FIELD);
                    }
                    if(!GET.checkuniqadmid(inputpan1.txtinput.getText())){
                        throw new Exception(Messages.UNIQUE_VALUE_ERR);
                    }
                    POST.admdata(inputpan1.txtinput.getText(),inputpan3.txtinput.getText(),inputpan4.txtinput.getText(),inputpan5.txtinput.getText());
                    simpmsgdialog ms=new simpmsgdialog(Messages.SUCCESFULL_ADDITION);
                }
            } catch (Exception ex) {
                errorinfodialog msg=new errorinfodialog(ex.getMessage());
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

class maintainddemo{
//    public static void main(String[] args){
//        maintain demo=new maintain();
//    }
}