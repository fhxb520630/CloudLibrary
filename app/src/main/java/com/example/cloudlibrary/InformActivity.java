package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.cloudlibrary.util.Comment;
import com.example.cloudlibrary.util.CommentAdapter;
import com.example.cloudlibrary.util.Communication;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class InformActivity extends AppCompatActivity {
    private ArrayList<Comment> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ListView informList = findViewById(R.id.activity_inform_list);
        CLApplication cl = (CLApplication) getApplication();
        JSONArray jsonArray = new Communication().getAuthorReply(cl.getUserName());
        for(int i = 0;i < jsonArray.length();i++){
            try{
                Comment temp = new Comment(jsonArray.getJSONObject(i).get("text").toString());
                temp.setDate(jsonArray.getJSONObject(i).get("time").toString().replace("T"," "));
                temp.setAuthor(jsonArray.getJSONObject(i).get("name").toString());
                data.add(temp);
            }catch (JSONException e){
                Log.d("context", String.valueOf(e));
            }
        }
        CommentAdapter adapter = new CommentAdapter(this,R.layout.item_comment,data);
        informList.setAdapter(adapter);
    }
}