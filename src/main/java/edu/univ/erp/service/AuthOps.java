package edu.univ.erp.service;

import edu.univ.erp.data.Fetch;
import edu.univ.erp.data.Update;
import edu.univ.erp.data.loginattempt;
import edu.univ.erp.domain.Person;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;


public class AuthOps {

    public static boolean userexists(String username,String passwd) throws SQLException {
        if(Fetch.userexists(username)){
            //write that shit
            PUT.password(passwd,username);
            return true;
        }
        else{
            return false;
        }
    }

    public static loginattempt isValid(String username, String password){

        //check the database for the hashedpswd and the username and return true or false accordingly
        Person output= Fetch.authdetails(username,password);
        if(output.name.isEmpty()||output.role.isEmpty()){
            return new loginattempt(false,output);
        }
        return new loginattempt(true,output);
    }

    public static void updatestatus(String id,boolean status) {
        Update.loginstatus(id,status);
    }

    public static void main(String[] args){
        System.out.println(BCrypt.hashpw("stu2",BCrypt.gensalt()));
    }


}

