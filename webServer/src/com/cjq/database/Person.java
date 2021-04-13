package com.cjq.database;

public class Person {
    private String username;
    private String name;
    private int age;
    private String teleno;
    private int paraType;

    public Person(String aUsername,String aName,int aAge,String aTeleno){
        username=aUsername;
        name=aName;
        age=aAge;
        teleno=aTeleno;
        paraType=0;
    }

    public Person(String aUsername,String aName,int aAge){
        username=aUsername;
        name=aName;
        age=aAge;
        teleno=null;
        paraType=1;
    }

    public Person(String aUsername,String aName,String aTeleno){
        username=aUsername;
        name=aName;
        age=-1;
        teleno=aTeleno;
        paraType=2;
    }

    public Person(String aUsername,String aName){
        username=aUsername;
        name=aName;
        age=-1;
        teleno=null;
        paraType=3;
    }

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getAgeStr(){
        if(age==-1)
            return "";
        else
            return String.valueOf(age);
    }

    public String getTeleno(){
        return teleno;
    }

    public int getParaType(){
        return paraType;
    }
}


