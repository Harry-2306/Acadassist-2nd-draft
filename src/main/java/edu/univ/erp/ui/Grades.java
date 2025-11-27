package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import edu.univ.erp.data.Statevariables;
import edu.univ.erp.domain.Evaluations;
import edu.univ.erp.service.GET;
import edu.univ.erp.util.unsucessfulretrievalexception;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Grades extends JPanel {

    final private Color TRANSPARENT=new Color(0,0,0,0);
    final private Color DARK=new Color(0x050D1F);
    final private Color PURPLE=new Color(0x7C4DFF);
    edu.univ.erp.domain.Grades object=new edu.univ.erp.domain.Grades();
    ArrayListTableModel model;
    JLabel titllab;
    JLabel sgpalab;
    JLabel cgpalab;
    JLabel semnlab;
    public Grades(Main mw, Statevariables statevar){

        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        this.setSize(500,500);
        this.setLayout(new BorderLayout());
        this.setBackground(DARK);

        Navbar navbar=new Navbar(2,mw,statevar);

        JPanel maincontpanel=new JPanel();
        maincontpanel.setBackground(DARK);
        maincontpanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,20));

        titllab=new JLabel("Your Current CGPA:");
        titllab.setForeground(Color.WHITE);
        titllab.setFont(new Font("Montserrat",Font.BOLD,29));
        titllab.setPreferredSize(new Dimension(1280,80));
        titllab.setHorizontalAlignment(SwingConstants.CENTER);
        titllab.setOpaque(true);
        titllab.setBackground(DARK);

        String[] animals={"Semester 1","Semester 2","Semester 3"};

        JComboBox semselector=new JComboBox(animals);
        semselector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    String item=(String) e.getItem();
                    if(item.equals("Semester 1")){
                        model.setData(object.semester1);
                        semnlab.setText("Grade Table for "+item);
                        sgpalab.setText("SGPA: "+object.sgpa1);
                    } else if (item.equals("Semester 2")) {
                        model.setData(object.semester2);
                        semnlab.setText("Grade Table for "+item);
                        sgpalab.setText("SGPA: "+object.sgpa2);
                    }else{
                        model.setData(object.semester3);
                        semnlab.setText("Grade Table for "+item);
                        sgpalab.setText("SGPA: "+object.sgpa3);
                    }
                }
            }
        });
        semselector.setFocusable(false);


        semnlab=new JLabel("Grade Table for semester 1");
        semnlab.setForeground(Color.WHITE);
        semnlab.setFont(new Font("Montserrat",Font.BOLD,20));
        semnlab.setPreferredSize(new Dimension(380,34));
        semnlab.setHorizontalAlignment(SwingConstants.CENTER);
        semnlab.setOpaque(true);
        semnlab.setBackground(DARK);




        JPanel combboxpan2=new JPanel();
        combboxpan2.setPreferredSize(new Dimension(1280,34));
        combboxpan2.setBackground(DARK);
        combboxpan2.add(semnlab);
        combboxpan2.add(semselector);

        //table addition for the shit

        statevar.addListener(()-> {
            object = statevar.getgrades();
            System.out.println("value updated");
            model.setData(object.semester1);
            System.out.println("table updated");
            titllab.setText("Your Current CGPA: "+object.cgpa);
            cgpalab.setText("CGPA: "+object.cgpa);
            sgpalab.setText("SGPA: "+object.sgpa1);
        });

        String[] columns={"Course id","Course title","Grade","GPA"};
        String[][] data={
                {"CSE101","4","A+","9"},
                {"CSE101","4","A+","9"},
                {"CSE101","4","A+","9"},
                {"CSE101","4","A+","9"},
                {"CSE101","4","A+","9"}
        };
        model=new ArrayListTableModel(object.semester1,columns);
        JTable gradtable=new JTable(model){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        gradtable.setRowHeight(80);
        gradtable.setBackground(DARK);
        gradtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    int selectedrow=gradtable.getSelectedRow();
//                    String coursetitle=(String)gradtable.getValueAt(selectedrow,1);
                    System.out.println(selectedrow);
                    if(selectedrow!=-1){
                        String course_id=(String)gradtable.getValueAt(selectedrow,0);
                        String coursetitle=(String)gradtable.getValueAt(selectedrow,1);
                        System.out.println(selectedrow);
                        System.out.println(course_id);
                        try {
                            Evaluations eval=GET.courseeval(course_id,statevar.getId());
                            new componentwisegrades(eval,coursetitle);
                        } catch (unsucessfulretrievalexception ex) {
                            errorinfodialog errmsg=new errorinfodialog(ex.getMessage());
                        }
                    }
                }
            }
        });

        tablecellrenderer customrend=new tablecellrenderer();
        customrend.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i=0;i<gradtable.getColumnCount();i++){
            gradtable.getColumnModel().getColumn(i).setCellRenderer(customrend);
        }


        gradtable.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,16));
        gradtable.getTableHeader().setBackground(new Color(0x0B1A3A));
        gradtable.setFocusable(false);

        JScrollPane tablepane=new JScrollPane(gradtable);
        tablepane.setPreferredSize(new Dimension(1280,350));
        tablepane.getViewport().setBackground(DARK);
        tablepane.setFocusable(false);
        tablepane.setBorder(new EmptyBorder(0,0,0,0));

        maincontpanel.add(titllab);
        maincontpanel.add(combboxpan2);
        maincontpanel.add(tablepane);

        JPanel totscorepan=new JPanel();
        sgpalab=new JLabel("SGPA:");
        cgpalab=new JLabel("CGPA:");
        totscorepan.setLayout(new FlowLayout(FlowLayout.CENTER,600,10));
        totscorepan.setPreferredSize(new Dimension(120,30));
        totscorepan.setBackground(DARK);
        sgpalab.setFont(new Font("Montserrat",Font.BOLD,12));
        cgpalab.setFont(new Font("Montserrat",Font.BOLD,12));
        totscorepan.add(sgpalab);
        totscorepan.add(cgpalab);



        this.add(navbar,BorderLayout.NORTH);
        this.add(maincontpanel,BorderLayout.CENTER);
        this.add(totscorepan,BorderLayout.SOUTH);
        this.setVisible(true);
    }
}

class gradetest{
    public static void main(String[] args){
//        Grades demo=new Grades();
    }
}