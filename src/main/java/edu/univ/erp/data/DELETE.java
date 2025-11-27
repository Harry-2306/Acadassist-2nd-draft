package edu.univ.erp.data;

import edu.univ.erp.domain.droppablecourseinfo;
import edu.univ.erp.service.GET;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DELETE {

    public static void courses( ArrayList<Integer> indices, ArrayList<droppablecourseinfo> input,Statevariables statevar) throws SQLException {
        Connection connection=DatabasePool.geterpdbConnection();
        String query="Delete from enrollments where userid=? and courseid=?";
        PreparedStatement ps=connection.prepareStatement(query);
        for(int i=0;i<indices.size();i++){
            droppablecourseinfo info=input.get(indices.get(i));
            ps.setString(1, statevar.getId());
            ps.setString(2,info.courseCode);
            ps.addBatch();
        }
        ps.executeBatch();
        connection.close();


        Connection connection2= DatabasePool.geterpdbConnection();
        String newquery="update sections set currcap=currcap-1 where instname=? and coursecode=? ;";
        PreparedStatement ps1=connection2.prepareStatement(newquery);
        for(int i=0;i<indices.size();i++){
            droppablecourseinfo info=input.get(indices.get(i));
            ps1.setString(1,info.courseTitle);
            ps1.setString(2,info.courseCode);
            ps1.addBatch();
        }
        ps1.executeBatch();
        connection2.close();

        statevar.setTt(GET.timetable(statevar.getId()));
        statevar.setMycourseinfo(GET.mycourses(statevar.getId()));
    }
}
