package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.TimetableTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class timetable extends JPanel {



    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    public timetable(Main mw, Statevariables statevar){

        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setSize(500,500);
        this.setBackground(DARK);
        this.setLayout(new BorderLayout(0,10));
        
        Navbar navbar=new Navbar(0,mw,statevar);


        JLabel tttitlepan=new JLabel("Your Time Table");
        tttitlepan.setPreferredSize(new Dimension(100,80));
        tttitlepan.setHorizontalAlignment(SwingConstants.CENTER);
        tttitlepan.setBackground(DARK);
        tttitlepan.setFont(new Font("Montserrat",Font.BOLD,40));
        tttitlepan.setForeground(Color.WHITE);


        String[] colheaders={"Course Title","Venue","Timings"};
        String[][] data={
                {"Machine Learning","DA10","9:30-10:30"},
                {"Parallel Runtime for Modern Processors","DA10","9:30-10:30"},
                {"Dev Ops","A10","9:30-10:30"},
                {"Discrete Mathematics","DA10","9:30-10:30"},
                {"Multivariate Calculus","DA10","9:30-10:30"}
        };

        TimetableTableModel model=new TimetableTableModel(statevar.gettt());
        JTable timetable=new JTable(model);

        tablecellrenderer myrend=new tablecellrenderer();
        statevar.addListener(()->{
            model.setRows(statevar.gettt());
            for(int i=0;i<timetable.getColumnCount();i++){
                timetable.getColumnModel().getColumn(i).setCellRenderer(myrend);
            }

        });
        timetable.getTableHeader().setBackground(DARK);
        timetable.setFocusable(false);
        myrend.setHorizontalAlignment(SwingConstants.CENTER);
        timetable.setRowHeight(80);
        for(int i=0;i<timetable.getColumnCount();i++){
            timetable.getColumnModel().getColumn(i).setCellRenderer(myrend);
        }


        JScrollPane ttpane=new JScrollPane(timetable);



        JPanel mainpan=new JPanel();
        mainpan.setBackground(DARK);
        mainpan.setLayout(new BorderLayout());
        mainpan.add(tttitlepan,BorderLayout.NORTH);
        mainpan.add(ttpane,BorderLayout.CENTER);

        
        
        
        
        
        maintainencemsg msg=new maintainencemsg("Maintainence underway!");
        
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

class timetabledemo{
    public static void main(String[] args){
//        timetable tt=new timetable();
    }
}