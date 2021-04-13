package com.example.myclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsername = (EditText) findViewById(R.id.usernameLogin);
        edtPassword = (EditText) findViewById(R.id.passwordLogin);
    }

    public void onClickSignUp(View v){
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickLogIn(View v) throws IOException {
        String username=edtUsername.getText().toString();
        String password=edtPassword.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(MainActivity.this,"请输入用户名！",Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){
            Toast.makeText(MainActivity.this,"请输入密码！",Toast.LENGTH_SHORT).show();
        }else{
            logIn(username,password);
        }
    }

    public void logIn(String username,String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.137.1:8080/webTest_war_exploded/LoginServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    String message = "username="+ URLEncoder.encode(username,"UTF-8")+
                            "&password="+URLEncoder.encode(password,"UTF-8");
                    OutputStream os = connection.getOutputStream();
                    os.write(message.getBytes());
                    Looper.prepare();
                    //System.out.println(connection.getResponseCode());
                    if(connection.getResponseCode()==200){
                        //获取返回数据
                        InputStream is = connection.getInputStream();
                        StringBuffer sb=new StringBuffer();
                        BufferedReader responseReader=new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                        String readLine;
                        while ( (readLine = responseReader.readLine())!=null){
                            sb.append(readLine).append("\n");
                        }
                        responseReader.close();
                        String responseString = sb.toString();
                        System.out.println(responseString);
                        //将json字符串转为jsonobject对象
                        JSONObject jsonObj = (JSONObject) new JSONObject(responseString).get("paras");
                        String result = jsonObj.getString("Result");
                        System.out.println(result);
                        if(result.equals("UserDoesNotExist")){
                            Toast.makeText(MainActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                        }else if(result.equals("WrongPassword")){
                            Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                        }else if(result.equals("LoginSuccess")){
                            //获取用户信息转至欢迎界面
                            String name=jsonObj.getString("name");
                            String age=jsonObj.getString("age");
                            String phone=jsonObj.getString("phone");
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("name",name);
                            intent.putExtra("age",age);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                        }

                    }else{
                        Toast.makeText(MainActivity.this,"连接服务器出错",Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();

        //Toast.makeText(MainActivity.this,username,Toast.LENGTH_SHORT).show();
    }

}

