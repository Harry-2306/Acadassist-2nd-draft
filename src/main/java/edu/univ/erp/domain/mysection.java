package edu.univ.erp.domain;

public class mysection {
    public String sectionid="";
    public String coursetitle="";

    public mysection(String sectionid,String coursetitle){
        this.sectionid=sectionid;
        this.coursetitle=coursetitle;
    }

    public boolean isempty(){
        return this.sectionid.isEmpty() && this.coursetitle.isEmpty();
    }
}
