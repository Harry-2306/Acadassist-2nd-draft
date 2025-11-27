package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.data.loginattempt;
import edu.univ.erp.service.AuthOps;
import edu.univ.erp.service.GET;
import edu.univ.erp.util.Messages;
import edu.univ.erp.util.emptyfieldexception;
import edu.univ.erp.util.incorrectcredentialsexception;
import edu.univ.erp.util.simpmsgdialog;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.sql.SQLException;
import java.util.Map;

public class forgotpassword extends JPanel {
    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    JPasswordField password;
    JTextField username;
    public int numberoftries=0;
    int isdark=0;
    public forgotpassword(Main mw, Statevariables statevariables){

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension dim=toolkit.getScreenSize();

        int width=dim.width;
        int length=dim.height;

        this.setSize(500,500);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));
        this.setBackground(new Color(0x050D1F));


        try{
            UIManager.put("Component.focusColor", new Color(0x7C4DFF));  // Pink accent
            UIManager.put("Button.focusColor", new Color(0x7C4DFF));
            UIManager.put("ToggleButton.focusColor", new Color(0x7C4DFF));
            UIManager.put("ProgressBar.selectionForeground", new Color(0x7C4DFF));
            UIManager.put("TextComponent.arc", 95);

            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        JPanel titlebar=new JPanel();

        titlebar.setPreferredSize(new Dimension(width,length/10));
        titlebar.setBackground(new Color(255, 255, 255, 0));

        JLabel text=new JLabel();
        text.setText("Acadassist");
        Font baseFont=new Font("Montserrat",Font.BOLD,60);
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) baseFont.getAttributes();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_ULTRABOLD); // or WEIGHT_BOLD, WEIGHT_LIGHT, etc.
        text.setFont(baseFont.deriveFont(attributes));
        text.setForeground(Color.WHITE);
        text.setHorizontalTextPosition(SwingConstants.CENTER);
        text.setVerticalTextPosition(SwingConstants.CENTER);
        titlebar.add(text);

        //welcome message
        JLabel titletext=new JLabel("Change your Password!",SwingConstants.CENTER);
        titletext.setPreferredSize(new Dimension(400-5,40));
        titletext.setFont(new Font("Montserrat", Font.BOLD,24));
        titletext.setForeground(Color.WHITE);



        //username dialog box




//        usernamedialog.add(username);
        ;

//        JLabel userheader=new JLabel("Enter your username");
//        userheader.setHorizontalAlignment(SwingConstants.LEFT);
//        userheader.setFont(new Font("Montserrat",Font.PLAIN,10));
//        userheader.setBackground(Color.RED);
//        userheader.setOpaque(true);




        //text label
        JLabel textlabel=new JLabel("Enter your Username");
        textlabel.setFont(new Font("Montserrat",Font.BOLD,12));
        textlabel.setPreferredSize(new Dimension(400-40,30));
        textlabel.setVerticalTextPosition(SwingConstants.CENTER);
        textlabel.setHorizontalTextPosition(SwingConstants.LEFT);
        textlabel.setForeground(Color.WHITE);

        //text field
        username=new JTextField();
        username.setText("");
        username.setPreferredSize(new Dimension(400-40,30));
        username.setHorizontalAlignment(SwingConstants.LEFT);
        username.setForeground(Color.WHITE);

        //main panel for username
        JPanel userpanel=new JPanel();
        userpanel.setBackground(DARK);
        userpanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        userpanel.setPreferredSize(new Dimension(400-40,70));
        userpanel.add(textlabel);
        userpanel.add(username);


        //password label
        JLabel passlabel=new JLabel("Enter your new Password");

        passlabel.setFont(new Font("Montserrat",Font.BOLD,12));
        passlabel.setPreferredSize(new Dimension(400-40,30));
        passlabel.setVerticalTextPosition(SwingConstants.CENTER);
        passlabel.setHorizontalTextPosition(SwingConstants.LEFT);
        //password field
        password=new JPasswordField();
        password.setText("");
        password.setPreferredSize(new Dimension(400-40,30));
        password.setHorizontalAlignment(SwingConstants.LEFT);


        //main panel for password
        JPanel passpanel=new JPanel();
        passpanel.setBackground(DARK);
        passpanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        passpanel.setPreferredSize(new Dimension(400-40,70));
        passpanel.add(passlabel);
        passpanel.add(password);

        //login sign up and toggle dark mode

        JPanel buttonbox=new JPanel();
        buttonbox.setLayout(new FlowLayout(FlowLayout.CENTER,0,8));
        buttonbox.setBackground(new Color(0,0,0,0));
        buttonbox.setPreferredSize(new Dimension(400-100,130));

        JButton Login=new LoginScreenButton("Change Password");

        JButton Signup=new LoginScreenButton("SIGN UP");

        JButton darkmode=new LoginScreenButton("Go Back To Login");

        darkmode.addActionListener(e -> {
            mw.showPage("Login");
        });


        Login.addActionListener(e->{
            String passwd=new String(password.getPassword());
            try{
                if(username.getText().isEmpty()||passwd.isEmpty()){
                    throw new emptyfieldexception(Messages.EMPTY_FIELD);
                }
                String id=username.getText();
                if(AuthOps.userexists(id,passwd)){
                    simpmsgdialog msg=new simpmsgdialog(Messages.PASS_CHANGE_SUCCESS);
                }else{
                    throw new incorrectcredentialsexception(Messages.ACC_NO_EXIST);

                }

            } catch (emptyfieldexception ex) {
                errorinfodialog message=new errorinfodialog(ex.getMessage());

            }catch (incorrectcredentialsexception ex){
                errorinfodialog message=new errorinfodialog(ex.getMessage());
                numberoftries++;
                if(numberoftries==5){
                    mw.dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        JLabel forgotlab=new JLabel("Forgot Password?");
        forgotlab.setFont(new Font("Montserrat",Font.PLAIN,13));
        forgotlab.setHorizontalTextPosition(SwingConstants.CENTER);
        forgotlab.setVerticalTextPosition(SwingConstants.CENTER);
        forgotlab.setPreferredSize(new Dimension(390,30));
        forgotlab.setForeground(new Color(0x1B64E3));
        forgotlab.setHorizontalAlignment(SwingConstants.CENTER);
        forgotlab.setVerticalAlignment(SwingConstants.CENTER);

        buttonbox.add(Login);
//        buttonbox.add(Signup);
        buttonbox.add(darkmode);





        //login box
        JPanel logindialog=new JPanel();
        logindialog.setBackground(new Color(0,0,0,0));
        logindialog.setLayout(new FlowLayout(FlowLayout.CENTER,0,25));
        logindialog.setPreferredSize(new Dimension(400,470));
        logindialog.add(titletext);
        logindialog.add(userpanel);
        logindialog.add(passpanel);
        logindialog.add(buttonbox);
//        logindialog.add(forgotpan);


        this.add(titlebar);
        this.add(logindialog);

        this.setVisible(true);

    }
}


