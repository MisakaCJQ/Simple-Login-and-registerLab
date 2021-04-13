package com.cjq.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjq.database.CjqDataBase;
import com.cjq.database.Person;
import jdk.internal.util.xml.impl.Input;
import netscape.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username").trim();
        String name = request.getParameter("name").trim();
        String age = request.getParameter("age").trim();
        String telenum = request.getParameter("phone").trim();
        String password = request.getParameter("password").trim();
        //构造person对象
        Person person0;
        boolean flagAge= !(age.length()<=0);
        boolean flagTel= !(telenum.length()<=0);
        if(flagAge&&!flagTel){
            int age0=Integer.parseInt(age);
            person0 = new Person(username,name,age0);
        }else if(!flagAge && flagTel){
            person0 = new Person(username,name,telenum);
        }else if(!flagAge && !flagTel){
            person0 = new Person(username,name);
        }else{
            int age0=Integer.parseInt(age);
            person0 = new Person(username,name,age0,telenum);
        }

        //读取数据库配置文件
        InputStream in = getServletContext().getResourceAsStream("/WEB-INF/cjqDataBase1.properties");
        Properties settings = new Properties();
        settings.load(in);//使用Properties对象读取配置文件
        String DBname = settings.getProperty("dbname");
        String DBcharacterEncoding = settings.getProperty("characterEncoding");
        String DBserverTimezone = settings.getProperty("serverTimezone");
        String DBUsername = settings.getProperty("username");
        String DBpassword = settings.getProperty("password");
        String url="jdbc:mysql://localhost:3306/"+DBname+"?characterEncoding="+DBcharacterEncoding
                +"&serverTimezone="+DBserverTimezone;
        //创建数据库对象
        CjqDataBase myDataBase = new CjqDataBase(url,DBUsername,DBpassword);
        List<Person> personList=myDataBase.listPersonTable();

        //检查username和name是否已存在
        boolean hasUsername = false;
        boolean hasName = false;
        for(Person person:personList){
            if(username.equals(person.getUsername())){
                hasUsername = true;
                break;
            }
        }

        for(Person person:personList){
            if(name.equals(person.getName())){
                hasName = true;
                break;
            }
        }
        //设定json
        PrintWriter out = response.getWriter();
        Map<String,String> paras = new HashMap<>();
        JSONObject jsonObj = new JSONObject();

        if(hasUsername){
            paras.put("Result","UsernameAlreadyExists");
        }else if(hasName){
            paras.put("Result","NameAlreadyExists");
        }else{
            myDataBase.insertPerson(person0,password);
            paras.put("Result","SignUpSuccess");
        }
        myDataBase.release();
        jsonObj.put("paras",paras);
        //返回
        out.write(jsonObj.toJSONString());
        System.out.println(jsonObj.toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
