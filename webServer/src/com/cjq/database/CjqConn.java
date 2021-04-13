package com.cjq.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CjqConn {
    //private String dbname;
    private String url;
    private String username;
    private String password;
    private Connection conn;
    private Statement stmt;

    public CjqConn(String aUrl,String aUsername,String aPassword){
        //dbname = aDbname;
        url = aUrl;
        username = aUsername;
        password = aPassword;

        try {//注册JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("数据库驱动没有安装");
            e.printStackTrace();
        }

        try{//连接数据库
            conn = DriverManager.getConnection(url,username,password);
            if (!conn.isClosed())
                System.out.println("数据库连接成功");
        }catch (SQLException ex){
            System.out.println("数据库连接失败");
            ex.printStackTrace();
        }

        try{//创建Statement对象
            stmt = conn.createStatement();
        }catch (SQLException ex){
            System.out.println("Statement创建失败");
            ex.printStackTrace();
        }

    }

    public void createDatabase(String dbname)
    {
        String sql = "CREATE DATABASE " + dbname;
        try{
            stmt.executeUpdate(sql);
        }catch (SQLException ex){
            System.out.println("数据库创建失败");
            ex.printStackTrace();
        }
    }
    /*
    CREATE TABLE 表名称
    (
    列名称1 数据类型,
    列名称2 数据类型,
    列名称3 数据类型,
    ....
    )
     */
    public void createTable(String tbname,String parameter)
    {
        String sql = "CREATE TABLE " + tbname + parameter;
        try{
            stmt.executeUpdate(sql);
        }catch (SQLException ex){
            System.out.println("数据表创建失败");
            ex.printStackTrace();
        }
    }
    /*
    INSERT INTO 表名称 VALUES (值1, 值2,....)
    INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
    */
    public void insertData(String tbname,String parameter){
        String sql = "INSERT INTO "+tbname+parameter;
        try{
            stmt.executeUpdate(sql);
        }catch (SQLException ex){
            System.out.println("数据插入失败");
            ex.printStackTrace();
        }
    }

    /*
    UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
     */
    public void updateData(String tbname,String parameterSet,String parameterWhere){
        String sql = "UPDATE " + tbname + " SET " + parameterSet + " WHERE " + parameterWhere;
        try{
            stmt.executeUpdate(sql);
        }catch (SQLException ex){
            System.out.println("数据更新失败");
            ex.printStackTrace();
        }
    }
    /*
    DELETE FROM 表名称 WHERE 列名称 = 值
    DELETE FROM table_name
     */
    public void deleteData(String tbname,String parameterWhere){
        String sql = "DELETE FROM " + tbname + " WHERE " + parameterWhere;
        try{
            stmt.executeUpdate(sql);
        }catch (SQLException ex){
            System.out.println("数据删除失败");
            ex.printStackTrace();
        }
    }

    /*
    SELECT * FROM 表名称
     */
    public void listData(String tbname,String[] columnName,int columnNum){
        String sql = "SELECT * FROM " + tbname;
        try{
            ResultSet rst = stmt.executeQuery(sql);
            System.out.println("表" + tbname);
            for(int i = 0;i < columnNum;i++){
                if(i == 0)
                    System.out.printf("|");
                System.out.printf("%-10s | ",columnName[i]);
            }
            System.out.print("\n");
            while(rst.next()){
                for(int i = 0;i<columnNum;i++){
                    if(i == 0)
                        System.out.printf(" ");
                    System.out.printf("%-10s   ",rst.getString(columnName[i]));
                }
                System.out.printf("\n");
            }
            System.out.print("\n");
            rst.close();
        }catch (SQLException ex){
            System.out.println("数据查询失败");
            ex.printStackTrace();
        }
    }

    public List<User> listUserTB(String tbname){

        String sql = "SELECT * FROM " + tbname;
        List<User> ansList = new ArrayList<>();
        try {
            ResultSet rst = stmt.executeQuery(sql);
            while(rst.next()){
                User user0= new User(rst.getString("username"),rst.getString("pass"));
                ansList.add(user0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ansList;
    }

    public List<Person> listPersonTB(String tbname){

        String sql = "SELECT * FROM " + tbname;
        List<Person> ansList = new ArrayList<>();
        try {
            ResultSet rst = stmt.executeQuery(sql);
            while(rst.next()){
                String username0=rst.getString("username");
                String name0=rst.getString("name");
                String age=rst.getString("age");
                String teleno0=rst.getString("teleno");
                int age0;
                if(age==null)
                    age0=-1;
                else
                    age0=Integer.parseInt(age);

                if(teleno0==null)
                    teleno0="";

                Person person0= new Person(username0,name0,age0,teleno0);
                ansList.add(person0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ansList;
    }
    /*
    DROP TABLE 表名称
     */
    public void deleteTable(String tbname){
        String sql = "DROP TABLE " + tbname;
        try{
            stmt.executeUpdate(sql);
            System.out.printf("数据表%s删除成功\n",tbname);
        }catch (SQLException ex){
            System.out.printf("数据表%s删除失败\n",tbname);
            ex.printStackTrace();
        }
    }

    /*
    判断表tbname中是否存在某条数据
     */
    public boolean CjqDataExist(String tbname,String parameterWhere){
        String sql = "SELECT COUNT(*) FROM " + tbname + " WHERE " + parameterWhere;
        ResultSet rst = null;
        try{
            rst = stmt.executeQuery(sql);
            int count = 0;
            while(rst.next()){
                count = rst.getInt(1);
            }
            if(count >= 1)
                return true;
            else
                return false;
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public void releaseConn(){
        /*
        try{
            if(!stmt.isClosed())
                stmt.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        try{
            conn.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        */

        /*
        if(!stmt.isClosed())
            stmt.close();
        if(!conn.isClosed())
            conn.close();
         */

        try {
            if(!stmt.isClosed())
                stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            if(!conn.isClosed())
                conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Statement,Connection已释放");
    }

    public int CjqExecuteUpdate(String para){
        try{
            int a=0;
            a = stmt.executeUpdate(para);
            return a;
        }catch (SQLException ex){
            System.out.println("sql输入错误");
            ex.printStackTrace();
            return 0;
        }
    }

    public ResultSet CjqExecuteQuery(String para){
        ResultSet rst;
        try{
            rst = stmt.executeQuery(para);
            return rst;
        }catch (SQLException ex){
            System.out.println("sql输入错误");
            ex.printStackTrace();
            return null;
        }
    }

    public boolean CjqExecute(String para){
        try{
            boolean a = stmt.execute(para);
            return a;
        }catch (SQLException ex){
            System.out.println("sql输入错误");
            ex.printStackTrace();
            return false;
        }
    }
}
