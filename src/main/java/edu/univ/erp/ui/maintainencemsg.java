package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;

public class maintainencemsg extends JPanel{
    final private Color PURPLE=new Color(0x5126BF);
    public maintainencemsg(String message){
        this.setBackground(PURPLE);
        this.setPreferredSize(new Dimension(1280,25));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel maintmessage=new JLabel(message);
        this.add(maintmessage);
        maintmessage.setFont(new Font("SansSerif",Font.BOLD,14));
        maintmessage.setForeground(Color.WHITE);
    }
}
