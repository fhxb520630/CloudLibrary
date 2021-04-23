package com.example.cloudlibrary.util;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.cloudlibrary.CLApplication;
import com.example.cloudlibrary.R;


import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class SweetDialogUsage {
    public void adapter(Button button, Context mcontext, Comment comment, int type){
        button.setOnClickListener(view -> {
            SweetAlertDialog dialog = new SweetAlertDialog(mcontext,SweetAlertDialog.WARNING_TYPE);
            dialog.setTitleText("提示");
            if(type == 0){
                dialog.setContentText("确认要删除这条回复吗？ ");
            }else{
                dialog.setContentText("确认要删除这条留言吗？ ");
            }
            dialog.setCancelable(true);
            dialog.setConfirmText("确认");
            dialog.setConfirmClickListener(sDialog -> {
                if(type == 0){
                    new Communication().deleteReply(comment);
                }else{
                    new Communication().deleteComment(comment);
                }
                SweetAlertDialog dialog2 = new SweetAlertDialog(mcontext,SweetAlertDialog.SUCCESS_TYPE);
                dialog2.setTitleText("提示");
                dialog2.setContentText("已删除");
                dialog2.setCancelable(true);
                dialog2.setConfirmText("确认");
                dialog2.setConfirmClickListener(sDialog1 -> sDialog1.dismissWithAnimation());
                dialog2.show();
                sDialog.dismissWithAnimation();
            });
            dialog.setCancelText("取消");
            dialog.setCancelClickListener(sDialog -> sDialog.dismissWithAnimation());

            dialog.show();
        });
        button.setFocusable(false);
    }
    public void listAdd(Button button, Context mcontext, TextView mcomment, CLApplication clApplication, String type,Comment fatherComment){
        button.setOnClickListener(view -> {
            String title = mcomment.getText().toString();
            if (title.length() == 0) {
                SweetAlertDialog dialog = new SweetAlertDialog(mcontext, SweetAlertDialog.ERROR_TYPE);
                dialog.setTitleText("提示");
                dialog.setContentText(type+"不能为空");
                dialog.setCancelable(true);
                dialog.setConfirmText("确认");
                dialog.setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation());
                dialog.show();
            }else{
                if(clApplication.getNowInfo() != 0) {
                    final Comment comment = new Comment(title,clApplication);
                    comment.setAuthor(clApplication.getUserName());
                    Thread t = null;
                    if(type.equals("评论")){
                        t = new Thread(() -> new Communication().addComment(comment));
                    }else {
                        if(clApplication.getNowInfo() == 2){
                            t = new Thread(() -> new Communication().addReply(fatherComment,comment,"1"));

                        }else{
                            t = new Thread(() -> new Communication().addReply(fatherComment,comment,"0"));
                        }
                    }
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SweetAlertDialog dialog = new SweetAlertDialog(mcontext,SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitleText("提示");
                    dialog.setContentText(type+"发表成功");
                    dialog.setCancelable(true);
                    dialog.setConfirmText("确认");
                    dialog.setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation());
                    dialog.show();
                    mcomment.setText("");
                }else{
                    SweetAlertDialog dialog = new SweetAlertDialog(mcontext,SweetAlertDialog.ERROR_TYPE);
                    dialog.setTitleText("提示");
                    dialog.setContentText("游客无法"+type);
                    dialog.setCancelable(true);
                    dialog.setConfirmText("确认");
                    dialog.setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation());
                    dialog.show();
                }
            }
        });
    }
    public void warnCom(Context mcontext,String type){
        SweetAlertDialog dialog = new SweetAlertDialog(mcontext,SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("提示");
        dialog.setContentText("已加载所有"+type);
        dialog.setCancelable(true);
        dialog.setConfirmText("确认");
        dialog.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
        dialog.show();
    }
    public FallObject falling(Context mcontext,FallObject.Builder builder){
        int r = (new Random().nextInt());
        if(r % 4 == 0){
            builder = new FallObject.Builder(getDrawable(mcontext,R.drawable.sun));
        }else if(r % 4 == 1){
            builder = new FallObject.Builder(getDrawable(mcontext,R.drawable.leaf));
        }else if(r%4 == 2){
            builder = new FallObject.Builder(getDrawable(mcontext,R.drawable.aut));
        }else{
            builder = new FallObject.Builder(getDrawable(mcontext,R.drawable.snow));
        }

        FallObject fallObject = builder
                .setSpeed(7, true)
                .setSize(150, 150, true)
                .setWind(true, false)
                .build();
        return  fallObject;
    }

}
