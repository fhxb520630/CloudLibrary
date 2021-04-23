package com.example.cloudlibrary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.cloudlibrary.CLApplication;
import com.example.cloudlibrary.GradeActivity;
import com.example.cloudlibrary.GradeManagerActivity;
import com.example.cloudlibrary.InformActivity;
import com.example.cloudlibrary.LogActivity;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.WebActivity;
import com.example.cloudlibrary.util.Power;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoFragment extends Fragment implements View.OnClickListener{
	private CLApplication app;
    private ImageView image;
    private TextView name;
    private TextView nowInfo;
    private TextView email;
    private TextView gender;
    private TextView stuId;
    private TextView department;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private FloatingActionButton fab4;
    private FloatingActionButton fab5;
    private FloatingActionButton fab6;
    private FloatingActionButton fab7;
    private FloatingActionButton fab8;
    private boolean menuOpen;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_user,container,false);
        app = (CLApplication) getActivity().getApplication();
        image = (ImageView) view.findViewById(R.id.fragment_user_image);
        name = (TextView) view.findViewById(R.id.fragment_user_name);
        nowInfo = (TextView) view.findViewById(R.id.fragment_user_nowinfo);
        email = (TextView) view.findViewById(R.id.fragment_user_email);
        gender = (TextView) view.findViewById(R.id.fragment_user_gender);
        stuId = (TextView) view.findViewById(R.id.fragment_user_stu_id);
        department = (TextView) view.findViewById(R.id.fragment_user_department);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton)view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)view.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)view.findViewById(R.id.fab3);
        fab4 = (FloatingActionButton)view.findViewById(R.id.fab4);
        fab5 = (FloatingActionButton)view.findViewById(R.id.fab5);
        fab6 = (FloatingActionButton)view.findViewById(R.id.fab6);
        fab7 = (FloatingActionButton)view.findViewById(R.id.fab7);
        fab8 = (FloatingActionButton)view.findViewById(R.id.fab8);
        menuOpen = false;
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);
        fab6.setOnClickListener(this);
        fab7.setOnClickListener(this);
        fab8.setOnClickListener(this);
        update();
        return view;
    }
    private void showOutMenu(){
        menuOpen = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }
    private void closeOutMenu(){
        menuOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
    }
    private void showInMenu(){
        menuOpen = true;
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab5.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        fab6.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
    }
    private void closeInMenu(){
        menuOpen = false;
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
        fab5.animate().translationY(0);
        fab6.animate().translationY(0);

    }
    private void showmInMenu(){
        menuOpen = true;
        fab7.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab8.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }
    private void closemInMenu(){
        menuOpen = false;
        fab7.animate().translationY(0);
        fab8.animate().translationY(0);
    }
    public void update(){
        name.setText(app.getUserName());
        String secret = "secret";
        switch (app.getNowInfo()){
            case Power.POWER_VISITOR:
                nowInfo.setText("tourist");
                image.setImageResource(R.drawable.default_user);
                email.setText(secret);
                gender.setText(secret);
                stuId.setText(secret);
                department.setText(secret);
                break;
            case Power.POWER_STUDENT:
                nowInfo.setText("student");
                image.setImageResource(R.drawable.student);
                email.setText(app.getStudentInfo().getEmail());
                gender.setText(secret);
                stuId.setText(app.getStudentInfo().getStuNumber());
                department.setText(app.getStudentInfo().getFaculty());
                break;
            case Power.POWER_MANAGER:
                nowInfo.setText("manager");
                image.setImageResource(R.drawable.ic_user);
                email.setText(secret);
                gender.setText(secret);
                stuId.setText(secret);
                department.setText(secret);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:{
                if (app.getNowInfo() == 0){
                    if (!menuOpen){
                        showOutMenu();
                    }else{
                        closeOutMenu();
                    }
                }else if(app.getNowInfo() == 1){
                    if (!menuOpen){
                        showInMenu();
                    }else{
                        closeInMenu();
                    }
                }else{
                    if(!menuOpen){
                        showmInMenu();
                    }else{
                        closemInMenu();
                    }
                }
                break;
            }
            case R.id.fab1:{
                closeOutMenu();
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("category","login");
                startActivity(intent);
                break;
            }
            case R.id.fab2:{
                closeOutMenu();
                Intent intent = new Intent(getActivity(), LogActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.fab5:{
                closeInMenu();
                Intent intent = new Intent(getActivity(), InformActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.fab3:{
                closeInMenu();
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("category","profile/change");
                startActivity(intent);
                break;
            }
            case R.id.fab6:{
                closeInMenu();
                Intent intent=new Intent(getActivity(), GradeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.fab8:{
                closemInMenu();
                Intent intent=new Intent(getActivity(), GradeManagerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.fab4:{
                closeInMenu();
                app.setUserName("未登录");
                app.setStudentInfo(null);
                app.setNowInfo(0);
                update();
                break;
            }
            case R.id.fab7:{
                closemInMenu();
                app.setUserName("未登录");
                app.setStudentInfo(null);
                app.setNowInfo(0);
                update();
                break;
            }
            default:
                break;
        }
    }
}
