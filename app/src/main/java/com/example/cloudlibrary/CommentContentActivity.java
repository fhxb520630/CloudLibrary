package com.example.cloudlibrary;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cloudlibrary.util.Comment;
import com.example.cloudlibrary.util.Communication;
import com.example.cloudlibrary.util.ReplyAdapter;
import com.example.cloudlibrary.util.SweetDialogUsage;
import com.github.library.bubbleview.BubbleTextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CommentContentActivity extends AppCompatActivity {
    Context mcontext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Comment> data = new ArrayList<>();
    private ReplyAdapter adapter;
    private Comment fatherComment;
    private ListView discussionList;

    public void init(String admin){
        adapter= new ReplyAdapter(this,R.layout.item_reply,data);
        setContentView(R.layout.activity_content);
        discussionList = findViewById(R.id.reply_list);
        discussionList.setAdapter(adapter);
        swipeRefreshLayout = findViewById(R.id.RefreshLayout);
        mcontext = this;
        fatherComment = new Comment();
        fatherComment.setAuthor(getIntent().getStringExtra("author"));
        fatherComment.setDate(getIntent().getStringExtra("time"));
        fatherComment.setTitle(getIntent().getStringExtra("title"));
        data = new ArrayList<>();
        Thread s = new Thread(() -> {
            final JSONArray jsonArray = new Communication().getIndexReply(0,20,fatherComment);
            for(int i = 0 ; i < jsonArray.length(); i++){
                try {
                    Comment temp = new Comment(jsonArray.getJSONObject(i).get("text").toString());
                    temp.setDate(jsonArray.getJSONObject(i).get("time").toString().replace("T"," "));
                    temp.setAuthor(jsonArray.getJSONObject(i).get("name").toString());
                    temp.setPower(Integer.parseInt(jsonArray.getJSONObject(i).get(admin).toString()));
                    adapter.add(temp);
                } catch (JSONException e) {
                    Log.d("context", String.valueOf(e));
                }
            }
        });
        s.start();
        try {
            s.join();
        } catch (Exception e) {
            Log.d("context", String.valueOf(e));
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        String admin = "admin";
        init(admin);
        super.onCreate(savedInstanceState);
        final TextView mcomment = findViewById(R.id.myreply);
        BubbleTextView bubbleTextView =  findViewById(R.id.btv2);
        bubbleTextView.setText(fatherComment.getTitle());
        TextView date = findViewById(R.id.date2);
        date.setText(fatherComment.getDate());
        TextView author = findViewById(R.id.author2);
        if(fatherComment.getPower() == 1){
            author.setTextColor(Color.parseColor("#D34040"));
        }
        Button button2 =  findViewById(R.id.delete2);
        Button hide_button = findViewById(R.id.hide2);
        button2.bringToFront();
        hide_button.bringToFront();
        CLApplication clapplication = (CLApplication) getApplicationContext();

        button2.setVisibility(View.GONE);
        hide_button.setVisibility(View.GONE);
        if(clapplication.getNowInfo() == 2){
            button2.setVisibility(View.VISIBLE);
            button2.setOnClickListener(view -> {
                SweetAlertDialog dialog = new SweetAlertDialog(mcontext,SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("提示");
                dialog.setContentText("确认要删除这条留言吗？ ");
                dialog.setCancelable(true);
                dialog.setConfirmText("确认");
                dialog.setConfirmClickListener(sDialog -> {
                    new Communication().deleteComment(fatherComment);
                    SweetAlertDialog dialog2 = new SweetAlertDialog(mcontext,SweetAlertDialog.SUCCESS_TYPE);
                    dialog2.setTitleText("提示");
                    dialog2.setContentText("已删除");
                    dialog2.setCancelable(true);
                    dialog2.setConfirmText("确认");
                    dialog2.setConfirmClickListener(sDialog1 -> {
                        sDialog1.dismissWithAnimation();
                        finish();
                    });
                    dialog2.show();
                    sDialog.dismissWithAnimation();
                });
                dialog.setCancelText("取消");
                dialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
                dialog.show();
            });
            button2.setFocusable(false);
            hide_button.setVisibility(View.VISIBLE);
            hide_button.setOnClickListener(view -> {
                SweetAlertDialog dialog = new SweetAlertDialog(mcontext,SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("提示");
                dialog.setContentText("确认要隐藏这条留言吗？ ");
                dialog.setCancelable(true);
                dialog.setConfirmText("确认");
                dialog.setConfirmClickListener(sDialog -> {
                    new Communication().deleteComment(fatherComment);
                    SweetAlertDialog dialog2 = new SweetAlertDialog(mcontext,SweetAlertDialog.SUCCESS_TYPE);
                    dialog2.setTitleText("提示");
                    dialog2.setContentText("已隐藏");
                    dialog2.setCancelable(true);
                    dialog2.setConfirmText("确认");
                    dialog2.setConfirmClickListener(sDialog1 -> {
                        sDialog1.dismissWithAnimation();
                        finish();
                    });
                    dialog2.show();
                    sDialog.dismissWithAnimation();
                });
                dialog.setCancelText("取消");
                dialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
                dialog.show();
            });
            hide_button.setFocusable(false);
        }
        adapter.notifyDataSetChanged();

        Button button = findViewById(R.id.replypush);
        new SweetDialogUsage().listAdd(button,mcontext,mcomment, (CLApplication) getApplication(),"回复",fatherComment);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            JSONArray jsonArray = new Communication().getIndexReply(0,20,fatherComment);
            adapter.clear();
            for(int i = 0 ; i < jsonArray.length(); i++){
                try {
                    Comment temp = new Comment(jsonArray.getJSONObject(i).get("text").toString());
                    temp.setDate(jsonArray.getJSONObject(i).get("time").toString().replace("T"," "));
                    temp.setAuthor(jsonArray.getJSONObject(i).get("name").toString());
                    temp.setPower(Integer.parseInt(jsonArray.getJSONObject(i).get(admin).toString()));
                    adapter.add(temp);
                } catch (JSONException e) {
                    Log.d("context", String.valueOf(e));
                }
            }
            adapter.notifyDataSetChanged();

        });
        discussionList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == SCROLL_STATE_IDLE) {
                    JSONArray jsonArray = new Communication().getIndexReply(adapter.getCount(),adapter.getCount()+3,fatherComment);
                    if(jsonArray.length() != 0){
                        for(int j = 0 ; j < jsonArray.length(); j++){
                            try {

                                Comment temp = new Comment(jsonArray.getJSONObject(j).get("text").toString());
                                temp.setDate(jsonArray.getJSONObject(j).get("time").toString().replace("T"," "));
                                temp.setAuthor(jsonArray.getJSONObject(j).get("name").toString());
                                temp.setPower(Integer.parseInt(jsonArray.getJSONObject(j).get(admin).toString()));
                                adapter.add(temp);
                            } catch (JSONException e) {
                                Log.d("c", String.valueOf(e));
                            }
                        }
                    }
                    if(jsonArray.length() == 0){
                        new SweetDialogUsage().warnCom(mcontext,"回复");
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //重载
            }
        });
    }
}