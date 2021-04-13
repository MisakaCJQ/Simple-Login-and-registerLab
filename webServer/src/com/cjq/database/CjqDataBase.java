package com.cjq.database;

import java.util.List;

public class CjqDataBase extends CjqConn {

    private final String[] usersColumnName = {"username","pass"};
    private final String[] personColumnName = {"username","name","age","teleno"};

    public CjqDataBase(String aUrl, String aUsername, String aPassword) {
        super(aUrl, aUsername, aPassword);
    }

    public void createUserTable() {
        createTable("users"," (username varchar(10) NOT NULL,pass varchar(12) NOT NULL," +
                "PRIMARY KEY (username)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
    }

    public void createPersonTable() {
        createTable("person"," (username varchar(10) NOT NULL,name varchar(20) NOT NULL,age int,teleno char(11)," +
                "PRIMARY KEY (name),FOREIGN KEY (username) REFERENCES users(username)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
    }

    public void insertUser(User aUser) {
        String username0=aUser.getUsername();
        String password0=aUser.getPassword();
        insertData("users"," VALUES ('" + username0 + "','" + password0 + "')");
    }

    public int insertPerson(Person aPerson,String password) {
        String username0=aPerson.getUsername();
        String name0=aPerson.getName();
        int age0=aPerson.getAge();
        String teleno0=aPerson.getTeleno();
        int paraType0=aPerson.getParaType();
        int insertType=0;
        System.out.println(paraType0);
        if(paraType0==0){
            //包含username,name,age,teleno
            if(CjqDataExist("users","username = '"+ username0 +"'")){
                //检查是否已存在于user表中
                if(CjqDataExist("person","username = '" + username0 + "'")){
                    //检查是否已经存在于person表中
                    updateData("person","name = '"+ name0 +"',age = '"+ age0 +"',teleno = '"+ teleno0 +"'","username = '"+ username0 +"'");
                    insertType=1;
                }
                else
                    insertData("person"," (username,name,age,teleno) VALUES ('"+ username0 +"','"+ name0 +"','"+ age0 +"','"+ teleno0 +"')");
            }else{
                insertData("users"," (username,pass) VALUES ('"+ username0 +"','"+ password +"')");
                insertData("person"," (username,name,age,teleno) VALUES ('"+ username0 +"','"+ name0 +"','"+ age0 +"','"+ teleno0 +"')");
            }
        }else if (paraType0==1){
            //包含username,name,age
            if(CjqDataExist("users","username = '"+ username0 +"'")){
                //检查是否已存在于user表中
                if(CjqDataExist("person","username = '" + username0 + "'")){
                    //检查是否已经存在于person表中
                    updateData("person","name = '"+ name0 +"',age = '"+ age0 +"'","username = '"+ username0 +"'");
                    insertType=1;
                }
                else
                    insertData("person"," (username,name,age) VALUES ('"+ username0 +"','"+ name0 +"','"+ age0 +"')");
            }else{
                insertData("users"," (username,pass) VALUES ('"+ username0 +"','"+ password +"')");
                insertData("person"," (username,name,age) VALUES ('"+ username0 +"','"+ name0 +"','"+ age0 +"')");
            }
        }else if (paraType0==2){
            //包含username,name,teleno
            if(CjqDataExist("users","username = '"+ username0 +"'")){
                //检查是否已存在于user表中
                if(CjqDataExist("person","username = '" + username0 + "'")){
                    //检查是否已经存在于person表中
                    updateData("person","name = '"+ name0 +"',teleno = '"+ teleno0 +"'","username = '"+ username0 +"'");
                    insertType=1;
                }
                else
                    insertData("person"," (username,name,teleno) VALUES ('"+ username0 +"','"+ name0 +"','"+ teleno0 +"')");
            }else{
                insertData("users"," (username,pass) VALUES ('"+ username0 +"','"+ password +"')");
                insertData("person"," (username,name,teleno) VALUES ('"+ username0 +"','"+ name0 +"','"+ teleno0 +"')");
            }
        }else if(paraType0==3){
            //包含username,name
            if(CjqDataExist("users","username = '"+ username0 +"'")){
                //检查是否已存在于user表中
                if(CjqDataExist("person","username = '" + username0 + "'")){
                    //检查是否已经存在于person表中
                    updateData("person","name = '"+ name0 +"'","username = '"+ username0 +"'");
                    insertType=1;
                }
                else
                    insertData("person"," (username,name) VALUES ('"+ username0 +"','"+ name0 +"')");
            }else{
                insertData("users"," (username,pass) VALUES ('"+ username0 +"','"+ password +"')");
                insertData("person"," (username,name) VALUES ('"+ username0 +"','"+ name0 +"')");
            }
        }
        return insertType;
    }

    public void deleteUser(String usernameFeature){
        deleteData("users","username LIKE '"+ usernameFeature +"'");
    }

    public void deletePerson(String usernameFeature){
        deleteData("person","username LIKE '"+ usernameFeature +"'");
    }

    public void deleteUserTable(){
        deleteTable("users");
    }

    public void deletePersonTable(){
        deleteTable("person");
    }

    public void printUserTable(){
        listData("users",usersColumnName,usersColumnName.length);
    }

    public void printPersonTable(){
        listData("person",personColumnName,personColumnName.length);
    }

    public List<User> listUserTable(){
        return listUserTB("users");
    }

    public List<Person> listPersonTable(){
        return listPersonTB("person");
    }

    public void release(){
        releaseConn();
    }
}
