package com.mycompany.hotdog;

import android.app.Activity;

import java.sql.ResultSet;

public class Test extends Activity{
    public void Test(){
        DBConnector db = new DBConnector();
        ResultSet s = db.makeQuery("Select * from Bruker;").getResultSet();
        System.out.println(s);
        System.out.println("hei");
    }

    public void main(String[] args){
        Test t = new Test();
    }
}