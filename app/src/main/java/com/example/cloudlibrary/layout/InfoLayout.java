package com.example.cloudlibrary.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.util.StudentInfo;

public class InfoLayout extends LinearLayout {
    private Context mc;
    private int nowInfo;

    public InfoLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        mc=context;
        nowInfo=-1;
    }

    private void setStudentInfo(StudentInfo studentInfo){
        ViewGroup child=(ViewGroup) getChildAt(0);
        TextView faculty=(TextView) child.getChildAt(0);
        TextView stuNumber=(TextView) child.getChildAt(1);
        child=(ViewGroup) child.getChildAt(2);
        TextView email=(TextView) child.getChildAt(1);
        faculty.setText(studentInfo.getFaculty());
        stuNumber.setText(studentInfo.getStuNumber());
        email.setText(studentInfo.getEmail());
    }

    private void setButtonOnClickListener(OnClickListener p){
        if(nowInfo==0){//visitor
            ViewGroup child=(ViewGroup) getChildAt(0);
            Button studentButton=(Button) child.getChildAt(1);
            Button managerButton=(Button) child.getChildAt(2);
            studentButton.setOnClickListener(p);
            managerButton.setOnClickListener(p);
        }else if(nowInfo==1){//student
            ViewGroup child=(ViewGroup) getChildAt(  0);
            Button changeButton=(Button) child.getChildAt(3);
            Button outButton=(Button) child.getChildAt(4);
            Button informButton = (Button) child.getChildAt(5);
            Button gradeButton = (Button) child.getChildAt(6);
            changeButton.setOnClickListener(p);
            outButton.setOnClickListener(p);
            informButton.setOnClickListener(p);
            gradeButton.setOnClickListener(p);
        }else if(nowInfo==2){
            ViewGroup child=(ViewGroup) getChildAt(0);
            Button outButton=(Button) child.getChildAt(1);
            Button gradeButton = (Button) child.getChildAt(2);
            outButton.setOnClickListener(p);
            gradeButton.setOnClickListener(p);
        }
    }

    public void infoForVisitor(OnClickListener p){
    	if(nowInfo==0) return;
    	if(nowInfo!=-1) removeAllViews();
    	LayoutInflater.from(mc).inflate(R.layout.infolayout_visitor,this);
    	nowInfo=0;
        setButtonOnClickListener(p);
    }
    
    public void infoForStudent(OnClickListener p, StudentInfo studentInfo){
    	if(nowInfo==1) return;
    	if(nowInfo!=-1) removeAllViews();
    	LayoutInflater.from(mc).inflate(R.layout.infolayout_student,this);
    	nowInfo=1;
        if(studentInfo!=null) setStudentInfo(studentInfo);
        setButtonOnClickListener(p);
    }
    
    public void infoForManager(OnClickListener p){
    	if(nowInfo==2) return;
    	if(nowInfo!=-1) removeAllViews();
    	LayoutInflater.from(mc).inflate(R.layout.infolayout_manager,this);
    	nowInfo=2;
        setButtonOnClickListener(p);
    }
}
