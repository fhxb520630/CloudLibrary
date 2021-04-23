package com.example.cloudlibrary;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import androidx.viewpager2.widget.ViewPager2;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.example.cloudlibrary.fragment.ForumFragment;
import com.example.cloudlibrary.fragment.HomeFragment;
import com.example.cloudlibrary.fragment.InfoFragment;
import com.example.cloudlibrary.fragment.NewsFragment;
import com.example.cloudlibrary.util.DepthPageTransformer;
import com.example.cloudlibrary.util.MyFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    List<Fragment> mFragments;

    private HomeFragment homeFragment;
    private InfoFragment infoFragment;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 10) {
                infoFragment.update();
            }
        }
    };

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem()==0){
            if(!homeFragment.onBack()){
                super.onBackPressed();
            }
        }else{
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        CLApplication clApplication=(CLApplication)getApplication();
        clApplication.setHandler(handler);
        setContentView(R.layout.activity_main);
        infoFragment=new InfoFragment();
        homeFragment=new HomeFragment();
        ForumFragment forumFragment=new ForumFragment(this);
        NewsFragment newsFragment=new NewsFragment(this);

        mFragments = new ArrayList<>();
        List<String> tabTitle = new ArrayList<>();
        mFragments.add(homeFragment);
        mFragments.add(newsFragment);
        mFragments.add(forumFragment);
        mFragments.add(infoFragment);
        tabTitle.add("游览");
        tabTitle.add("服务");
        tabTitle.add("评论");
        tabTitle.add("我的");
        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(this, mFragments);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(new DepthPageTransformer());
        new TabLayoutMediator(tabLayout, viewPager, (tab, pos) -> {
            View view = getLayoutInflater().inflate(R.layout.customtab, null);
            int iconRes;
            switch (pos) {
                case 0:
                    iconRes = R.drawable.tour;
                    break;
                case 1:
                    iconRes = R.drawable.service;
                    break;
                case 2:
                    iconRes = R.drawable.comment;
                    break;
                case 3:
                    iconRes = R.drawable.info;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + pos);
            }
            view.findViewById(R.id.icon).setBackgroundResource(iconRes);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            view.setLayoutParams(params);
            tab.setCustomView(view);
        }).attach();


        SweetAlertDialog dialog = new SweetAlertDialog(this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        dialog.setTitleText("tips");
        dialog.setContentText("!关于图书馆电子资源的校外访问：" +
                "在" +
                "图书馆主页右下角“校外访问”                                     \n" +
                "概括如下：                                                         \n" +
                "1) 不同的数据库提供校外访问的方式不同，所以电子资源的访问方式取决于文献所属数据库。读者可以通过访问数据库说明页（在图书馆主页，点击“数据库”，进入数据库导航系统，可以检索到数据库，进入说明页阅览）获取。                                                        \n" +
                "2) 数据库访问出现任何问题，均可给数据库说明页底部的“责任馆员”邮箱发送邮件咨询。(网页连接在服务中已给出）                   ");
        //dialog.setContentText("网络连接异常！\n请检查自身网络连接状态或询问管理员。\n");
        dialog.setCancelable(true);
        dialog.setCustomImage(R.drawable.tip);
        dialog.setConfirmText("确认");
        dialog.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        });
        dialog.show();
    }
}