package com.cjq.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.cjq.database.CjqDataBase;
import com.cjq.database.Person;
import com.cjq.database.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        System.out.println(username);
        System.out.println(password);
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
        List<User> userList=myDataBase.listUserTable();//读取UserList
        List<Person> personList=myDataBase.listPersonTable();//读取personList
        myDataBase.release();
        int checkResult = 0;//0，用户名不正确。1，用户名正确，密码不正确。2，用户名密码均正确
        for(User user:userList){
            if(username.equals(user.getUsername())){
                checkResult=1;
                if(password.equals(user.getPassword()))
                    checkResult=2;
                break;
            }
        }
        //设定json
        PrintWriter out = response.getWriter();
        Map<String,String> paras = new HashMap<>();
        JSONObject jsonObj = new JSONObject();
        if(checkResult==0){
            paras.put("Result","UserDoesNotExist");
        }else if(checkResult==1){
            paras.put("Result","WrongPassword");
        }else if (checkResult==2){
            Person person0 = null;
            for(Person person:personList){
                if(username.equals(person.getUsername())){
                    person0=person;
                    break;
                }
            }
            paras.put("Result","LoginSuccess");
            assert person0 != null;
            paras.put("name",person0.getName());
            paras.put("age",person0.getAgeStr());
            paras.put("phone",person0.getTeleno());
        }

        jsonObj.put("paras",paras);
        out.write(jsonObj.toJSONString());
        System.out.println(jsonObj.toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
