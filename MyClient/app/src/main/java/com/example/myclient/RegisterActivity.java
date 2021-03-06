package com.example.myclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtName;
    EditText edtAge;
    EditText edtPhone;
    EditText edtPassword;
    EditText edtConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = (EditText) findViewById(R.id.usernameSignUp);
        edtName = (EditText) findViewById(R.id.nameSignUp);
        edtAge = (EditText) findViewById(R.id.ageSignUp);
        edtPhone = (EditText) findViewById(R.id.telenoSignUp);
        edtPassword = (EditText) findViewById(R.id.passwordSignUp);
        edtConfirmPassword = (EditText) findViewById(R.id.confirmPasswordSignUp);
    }

    public void onClickBack(View v){
        finish();
    }

    public void onClickSendSignUp(View v){
        String username=edtUsername.getText().toString();
        String name=edtName.getText().toString();
        String age=edtAge.getText().toString();
        String phone=edtPhone.getText().toString();
        String password=edtPassword.getText().toString();
        String confirmPassword=edtConfirmPassword.getText().toString();
        if(check(username,name,age,phone,password,confirmPassword)){
            new AlertDialog.Builder(this)
                    .setTitle("?????????????????????????????????")
                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(RegisterActivity.this,"?????????",Toast.LENGTH_SHORT).show();
                            signUp(username,name,age,phone,password);
                        }
                    })
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    }

    public void signUp(String username,String name,String age,String phone,String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.137.1:8080/webTest_war_exploded/RegisterServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    String message="username="+ URLEncoder.encode(username,"UTF-8")+
                            "&name="+ URLEncoder.encode(name,"UTF-8")+
                            "&age="+ URLEncoder.encode(age,"UTF-8")+
                            "&phone="+ URLEncoder.encode(phone,"UTF-8")+
                            "&password="+ URLEncoder.encode(password,"UTF-8");
                    System.out.println(message);
                    OutputStream os = connection.getOutputStream();
                    os.write(message.getBytes());
                    Looper.prepare();
                    //System.out.println(connection.getResponseCode());
                    if(connection.getResponseCode()==200){
                        //??????????????????
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
                        //???json???????????????jsonobject??????
                        JSONObject jsonObj = (JSONObject) new JSONObject(responseString).get("paras");
                        String result = jsonObj.getString("Result");
                        if(result.equals("UsernameAlreadyExists")){
                            Toast.makeText(RegisterActivity.this,"????????????????????????????????????",Toast.LENGTH_SHORT).show();
                        }else if(result.equals("NameAlreadyExists")){
                            Toast.makeText(RegisterActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                        }else if(result.equals("SignUpSuccess")){
                            Toast.makeText(RegisterActivity.this,"????????????",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean check(String username,String name,String age,String phone,String password,String confirmPassword){
        //boolean isCorrect = true;
        /*1.???????????????*/
        //?????????????????????
        String pattern1 = "^[a-zA-Z][a-zA-Z\\d_]*";//????????????????????????????????????????????????????????????????????????????????????
        String pattern2 = ".*[A-Z].*";//??????????????????????????????
        boolean match1 = Pattern.matches(pattern1, username);
        boolean match2 = Pattern.matches(pattern2, username);
        if(username.length()<5||username.length()>10){
            Toast.makeText(RegisterActivity.this,"?????????????????????5~10",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!match1){
            Toast.makeText(RegisterActivity.this,"???????????????????????????????????????????????????????????????????????????_",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!match2){
            Toast.makeText(RegisterActivity.this,"??????????????????????????????????????????",Toast.LENGTH_SHORT).show();
            return false;
        }

        /*2.????????????*/
        if(name.isEmpty()){
            Toast.makeText(RegisterActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.length()>20){
            Toast.makeText(RegisterActivity.this,"??????????????????20?????????",Toast.LENGTH_SHORT).show();
        }

        /*3.????????????*/
        if(age.isEmpty()){
            Toast.makeText(RegisterActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
            return false;
        }
        int age0=Integer.parseInt(age);
        if(age.length()>3||age0<0||age0>150){
            Toast.makeText(RegisterActivity.this,"????????????0~150",Toast.LENGTH_SHORT).show();
            return false;
        }

        /*4.???????????????*/
        if(phone.isEmpty()){
            Toast.makeText(RegisterActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(phone.length()>11){
            Toast.makeText(RegisterActivity.this,"???????????????????????????11",Toast.LENGTH_SHORT).show();
            return false;
        }

        /*5.????????????*/
        if(password.isEmpty()||confirmPassword.isEmpty()){
            Toast.makeText(RegisterActivity.this,"??????????????????????????????",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length()<6||password.length()>12){
            Toast.makeText(RegisterActivity.this,"????????????6~12???",Toast.LENGTH_SHORT).show();
            return false;
        }
        //????????????????????????????????????
        String patternPsw = "[a-zA-Z\\d_]*";
        boolean matchPsw = Pattern.matches(patternPsw,password);
        if(!matchPsw){
            Toast.makeText(RegisterActivity.this,"????????????????????????????????????????????????_",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(RegisterActivity.this,"??????????????????????????????",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}