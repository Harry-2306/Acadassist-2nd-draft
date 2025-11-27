package edu.univ.erp.data;

import edu.univ.erp.domain.Person;

public class loginattempt {
    public  Person object;
    public  boolean status;
    public loginattempt(boolean status,Person object){
        this.status=status;
        this.object=object;
    }
}
