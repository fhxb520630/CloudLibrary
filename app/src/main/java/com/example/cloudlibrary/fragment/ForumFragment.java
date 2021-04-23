package com.example.cloudlibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cloudlibrary.CLApplication;
import com.example.cloudlibrary.CommentContentActivity;
import com.example.cloudlibrary.util.CommentAdapter;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.util.Comment;
import com.example.cloudlibrary.util.Communication;
import com.example.cloudlibrary.util.SweetDialogUsage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForumFragment extends Fragment {
    Context mcontext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Comment> data;
    private CommentAdapter adapter;


    public ForumFragment(Context context){
        mcontext = context;
        data = new ArrayList<>();
        JSONArray jsonArray = new Communication().getIndex(0,20);
        for(int i = 0 ; i < jsonArray.length(); i++){
            try {
                Comment temp = new Comment(jsonArray.getJSONObject(i).get("text").toString());
                temp.setDate(jsonArray.getJSONObject(i).get("time").toString().replace("T"," "));
                temp.setAuthor(jsonArray.getJSONObject(i).get("name").toString());
                data.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter= new CommentAdapter(mcontext,R.layout.item_comment,data);
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_forum,container,false);
        ListView discussionList = view.findViewById(R.id.discussion_list);
        final TextView mcomment = view.findViewById(R.id.mycomment);
        discussionList.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        Button button = view.findViewById(R.id.push);
        new SweetDialogUsage().listAdd(button,mcontext,mcomment,(CLApplication) getActivity().getApplication(),"评论",null);
        discussionList.setOnItemClickListener((adapterView, view1, i, l) -> {
            String result = adapter.getItem(i).getTitle();
            Bundle bundle = new Bundle();
            bundle.putString("title",result);
            Intent intent=new Intent(getActivity(), CommentContentActivity.class);
            intent.putExtra("title",adapter.getItem(i).getTitle());
            intent.putExtra("author",adapter.getItem(i).getAuthor());
            intent.putExtra("time",adapter.getItem(i).getDate());
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            JSONArray jsonArray = new Communication().getIndex(0,20);
            adapter.clear();
            for(int i = 0 ; i < jsonArray.length(); i++){
                try {
                    Comment temp = new Comment(jsonArray.getJSONObject(i).get("text").toString());
                    temp.setDate(jsonArray.getJSONObject(i).get("time").toString().replace("T"," "));
                    temp.setAuthor(jsonArray.getJSONObject(i).get("name").toString());

                    adapter.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            adapter.notifyDataSetChanged();

        });
        discussionList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == SCROLL_STATE_IDLE) {
                    JSONArray jsonArray = new Communication().getIndex(adapter.getCount(),adapter.getCount()+3);
                    if(jsonArray.length() != 0){
                        for(int j = 0 ; j < jsonArray.length(); j++){
                            try {

                                Comment temp = new Comment(jsonArray.getJSONObject(j).get("text").toString());
                                temp.setDate(jsonArray.getJSONObject(j).get("time").toString().replace("T"," "));
                                temp.setAuthor(jsonArray.getJSONObject(j).get("name").toString());
                                adapter.add(temp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(jsonArray.length() == 0){
                        SweetAlertDialog dialog = new SweetAlertDialog(mcontext,SweetAlertDialog.WARNING_TYPE);
                        dialog.setTitleText("提示");
                        dialog.setContentText("已加载所有评论");
                        dialog.setCancelable(true);
                        dialog.setConfirmText("确认");
                        dialog.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
                        dialog.show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //重载
            }
        });
        return view;
    }
}
