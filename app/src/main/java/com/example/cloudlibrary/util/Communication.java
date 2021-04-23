package com.example.cloudlibrary.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Communication {
    private static final String CODE = "application/x-www-form-urlencoded";
    private static final String TYPE = "Content-Type";
    private static final String LENGTH = "Content-Length";
    private static final String INDEX = "http://166.111.226.244:11352/comment/index/";
    private static final String TYPE_EQUAL = "?type=";
    private static final String START_EQUAL = "&start=";
    private static final String END_EQUAL = "&end=";
    private static final String FLOOR_EQUAL = "&floor=";
    private static final String NET = "http://166.111.226.244:11352/";
    private static final String UTF = "utf-8";
    private int testCode=204;

    public int getTestCode(){
        return testCode;
    }

    public JSONObject getUrl(URL url) throws JSONException {
        final JSONObject[] json = new JSONObject[1];
        Thread temp = new Thread(() -> {

            try{

                testCode = 204;
                HttpURLConnection urlConn= (HttpURLConnection) url .openConnection();
                InputStreamReader in=new InputStreamReader (urlConn.getInputStream()) ;
                BufferedReader buffer=new BufferedReader (in) ;
                String inputLine;
                StringBuilder resultData = new StringBuilder();
                while (((inputLine=buffer.readLine()) !=null)) {
                    resultData.append(inputLine).append("\n");
                }
                int responseCode = urlConn.getResponseCode();
                testCode = responseCode;
                in.close ();
                urlConn.disconnect();
                if(responseCode != 204){

                    json[0] = new JSONObject(resultData.toString());

                }

            } catch (IOException | JSONException e) {
                Log.d("context", String.valueOf(e));
            }
        });
        temp.start();
        try{
            temp.join();
        } catch (InterruptedException e) {
            Log.d("context", String.valueOf(e));
        }
        if (json[0] == null){
            json[0] = new JSONObject();
            json[0].put("data", new JSONArray());
        }
        return json[0];
    }

    public int postUrl(URL url, Map<String,String> param){
        final int[] re = {0};
        Thread t = new Thread(() -> {
            try {
                testCode = 204;
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");//大写
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);
                urlConnection.setDoOutput(true);
                StringBuilder buffer = new StringBuilder();
                if(!param.isEmpty()){
                    for(Map.Entry<String, String> entry : param.entrySet()){
                        buffer.append(entry.getKey()).append("=").
                                append(URLEncoder.encode(entry.getValue(),UTF)).
                                append("&");
                    }
                }

                //删除最后一个字符&，多了一个;主体设置完毕
                buffer.deleteCharAt(buffer.length()-1);
                byte[] mydata = buffer.toString().getBytes();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setDoInput(true);//表示从服务器获取数据
                connection.setDoOutput(true);//表示向服务器写数据

                connection.setRequestMethod("POST");
                //是否使用缓存
                connection.setUseCaches(false);
                //表示设置请求体的类型是文本类型
                connection.setRequestProperty(TYPE, CODE);

                connection.setRequestProperty(LENGTH, String.valueOf(mydata.length));
                connection.connect();   //连接，不写也可以。。？？有待了解

                //获得输出流，向服务器输出数据
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(mydata,0,mydata.length);
                //获得服务器响应的结果和状态码
                re[0] = connection.getResponseCode();
                testCode = re[0];
            } catch (IOException ex) {
                Log.d("context", String.valueOf(ex));
            }
        });
        try{
            t.start();
            t.join();
        }catch (Exception e){
            Log.d("context", String.valueOf(e));
        }
        return re[0];
    }

    public void addComment(final Comment comment){
        try {
            URL url = new URL(NET + "comment/save/");
            Map<String,String> param = new HashMap<>();
            param.put("type","create_comment");
            param.put("name",comment.getAuthor());
            param.put("email","???");
            param.put("text",comment.getTitle());
            postUrl(url,param);
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }
    }
    public void deleteComment(final Comment comment){

        try {
            URL url = new URL(INDEX);
            Map<String,String> param = new HashMap<>();

            param.put("type","delete_comment");
            param.put("name",comment.getAuthor());
            param.put("text",comment.getTitle());
            param.put("time",comment.getDate());

            testCode = postUrl(url,param);
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }




    }
    public void deleteReply(final Comment comment){


        try {
            URL url = new URL(INDEX);
            Map<String,String> param = new HashMap<>();

            param.put("type","delete_reply");
            param.put("name",comment.getAuthor());
            param.put("text",comment.getTitle());

            postUrl(url,param);
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }

    }
    public void addReply(final Comment comment,final Comment reply,final String admin){


        try {
            URL url = new URL("http://166.111.226.244:11352/comment/save/");
            Map<String,String> param = new HashMap<>();
            param.put("type","create_reply");
            param.put("father_name",comment.getAuthor());
            param.put("name",reply.getAuthor());
            param.put("text",reply.getTitle());
            param.put("father_time",comment.getDate());
            param.put("father_text",comment.getTitle());
            param.put("admin",admin);
            param.put("email","!!!");
            postUrl(url,param);
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }
    }
    public JSONArray getIndex(int start,int end){


        JSONArray jsonArray = null;
        try {
            URL url = new URL(INDEX + TYPE_EQUAL +"get_comment"+ START_EQUAL + start + END_EQUAL +end);
            JSONObject jsonObject = getUrl(url);
            jsonArray = jsonObject.getJSONArray("data");

        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        return jsonArray;
    }
   
    public boolean  confirmAdmin(String account,String password){
        final String a = account;
        final String p = password;
        final boolean[] confirmed = new boolean[1];
        Thread temp = new Thread(() -> {
            try{
                URL url = new URL("http://166.111.226.244:11352/accounts/verify/"+ TYPE_EQUAL +"verify"+"&account="+a+"&password="+p);
                HttpURLConnection urlConn= (HttpURLConnection) url .openConnection();
                InputStreamReader in=new InputStreamReader (urlConn.getInputStream()) ;
                int responseCode = urlConn.getResponseCode();
                in.close ();
                urlConn.disconnect();
                confirmed[0] = responseCode == 200;
                testCode = responseCode;
            } catch (IOException e) {
                Log.d("context", String.valueOf(e));
            }
        });
        temp.start();
        try{
            temp.join();
        } catch (InterruptedException e) {
            Log.d("context", String.valueOf(e));
        }

        return confirmed[0];
    }

    public JSONArray getIndexReply(int start, int end, final Comment father){
        JSONArray jsonArray = null;
        try {
            URL url = new URL(INDEX + TYPE_EQUAL +"get_reply"+ START_EQUAL +start+ END_EQUAL +end+"&father_name="+father.getAuthor()+"&father_time="+father.getDate());
            JSONObject jsonObject = getUrl(url);
            jsonArray = jsonObject.getJSONArray("data");
        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }

        return jsonArray;

    }
    public JSONArray getAuthorReply(String fatherName){
        JSONArray jsonArray = null;

        try {
            URL url = new URL(INDEX + TYPE_EQUAL +"admin_reply"+"&father_name="+fatherName);
            jsonArray = getUrl(url).getJSONArray("data");
        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }


        return jsonArray;

    }
    public JSONArray getIndexNews(int start, int end){
        JSONArray jsonArray = null;

        try {
            URL url = new URL("http://166.111.226.244:11352/article/"+ TYPE_EQUAL +"get_title"+ START_EQUAL +start+ END_EQUAL +end);
            jsonArray = getUrl(url).getJSONArray("data");
        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }


        return jsonArray;

    }
    public int getVisited (String title){
        int re = 0;

        try {
            URL url = new URL("http://166.111.226.244:11352/visit/"+ TYPE_EQUAL +"get_visit"+"&position="+title);
            re = getUrl(url).getInt("data");
        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }

        return re;

    }
    public String getNewsUrl(String title){
        String t = "";
        try {
            URL url = new URL("http://166.111.226.244:11352/article/"+ TYPE_EQUAL +"get_url"+"&title="+title);
            t = getUrl(url).getString("data");
        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        return t;
    }
    public JSONObject getInformation(String pos) throws JSONException {
        JSONObject jsonObject = null;

        try {
            URL url = new URL("http://166.111.226.244:11352/detail/point/?type=get_info&pos="+pos);
            jsonObject = getUrl(url).getJSONObject("data");
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }

        return jsonObject;

    }
    public JSONArray getFloorPoint(String floor,String pos){
        JSONArray jsonArray = null;
        try {
            URL url = new URL("http://166.111.226.244:11352/detail/point/?type=get_axis&library="+pos+ FLOOR_EQUAL + floor);
            jsonArray = getUrl(url).getJSONArray("data");
        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }

        return jsonArray;

    }
    public String getFloorFullImage(String pos,String floor) throws JSONException {
        String urls = NET;

        try {
            URL url = new URL("http://166.111.226.244:11352/detail/floor/?type=get_floor&library="+ pos + FLOOR_EQUAL + floor);
            urls += getUrl(url).getJSONObject("data").getString("full");
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }
        return urls;


    }

    public String getFloorImage(String floor,String pos) throws JSONException {
        String urls = NET;
        try {
            URL url = new URL("http://166.111.226.244:11352/detail/floor/?type=get_floor&library="+ pos + FLOOR_EQUAL +floor);
            urls += getUrl(url).getJSONObject("data").getString("image");
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }

        return urls;


    }
    public String getWelcomeImage() throws JSONException {
        String urls = NET;
        try {
            URL url = new URL("http://166.111.226.244:11352/welcome/?type=get_welcome");
            urls += getUrl(url).getJSONArray("data").getJSONObject(0).getString("image");
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }
        return urls;


    }
    public void addGrade(final String grade){

        try {
            URL url = new URL("http://166.111.226.244:11352/grade/");
            Map<String,String> param = new HashMap<>();
            param.put("type","new_grade");
            param.put("star",grade);
            postUrl(url,param);
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }



    }
    public void addVisited(final String position){

        try {
            URL url = new URL("http://166.111.226.244:11352/visit/");
            Map<String,String> param = new HashMap<>();
            param.put("type","new_visit");
            param.put("position",position);
            postUrl(url,param);
        } catch (MalformedURLException e) {
            Log.d("context", String.valueOf(e));
        }



    }
    public JSONArray getGrade(){
        JSONArray jsonArray = null;
        try {
            URL url = new URL("http://166.111.226.244:11352/grade/?type=get_grade");
            jsonArray = getUrl(url).getJSONArray("data");
        } catch (MalformedURLException | JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        return jsonArray;
    }


}
