package com.example.cloudlibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cloudlibrary.R;
import com.example.cloudlibrary.WebActivityNews;
import com.example.cloudlibrary.util.Comment;
import com.example.cloudlibrary.util.Communication;
import com.example.cloudlibrary.util.NewsAdapter;
import com.example.cloudlibrary.util.SearchView;
import com.example.cloudlibrary.util.SweetDialogUsage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;



public class NewsFragment extends Fragment {
    Context mcontext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Comment> data;
    private NewsAdapter adapter;
    private SearchView searchView;
    private String s = "";
    private int start = 0;


    public NewsFragment(Context context){
        mcontext = context;
        data = new ArrayList<>();
        JSONArray jsonArray = new Communication().getIndexNews(0,10);
        for(int i = 0 ; i < jsonArray.length(); i++){
            try {
                Comment temp = new Comment(jsonArray.getString(i));
                temp.setVisited(new Communication().getVisited(temp.getTitle()));
                data.add(temp);
            } catch (JSONException e) {
                Log.d("context", String.valueOf(e));
            }
        }
        adapter= new NewsAdapter(mcontext, R.layout.item_news,data);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_news,container,false);
        ListView newsList = view.findViewById(R.id.news_list);
        newsList.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_news);
        searchView = view.findViewById(R.id.search_view);
        searchView.setInit();
        searchView.setOnClickSearch(string -> {
            JSONArray jsonArray = new Communication().getIndexNews(0,20);
            adapter.clear();
            for(int i = 0 ; i < jsonArray.length(); i++){
                try {
                    Comment temp = new Comment(jsonArray.getString(i));
                    temp.setVisited(new Communication().getVisited(temp.getTitle()));
                    s = string;
                    start = 20;
                    if(temp.getTitle().contains(string))
                        adapter.add(temp);
                    searchView.getListView().setVisibility(View.GONE);
                    searchView.getTvClear().setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.d("context", String.valueOf(e));
                }
            }

            adapter.notifyDataSetChanged();
        });

        searchView.setOnClickBack(() -> {
            searchView.getListView().setVisibility(View.GONE);
            searchView.getTvClear().setVisibility(View.GONE);
            s = "";
            start = 0;
        });

        newsList.setOnItemClickListener((adapterView, view1, i, l) -> {
            String result = adapter.getItem(i).getTitle();
            new Communication().addVisited(result);
            String url = new Communication().getNewsUrl(result);
            Intent intent=new Intent(getActivity(), WebActivityNews.class);
            intent.putExtra("url",url);
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            JSONArray jsonArray = new Communication().getIndexNews(0,10);
            adapter.clear();
            for(int i = 0 ; i < jsonArray.length(); i++){
                try {
                    Comment temp = new Comment(jsonArray.getString(i));
                    temp.setVisited(new Communication().getVisited(temp.getTitle()));
                    adapter.add(temp);
                    searchView.getListView().setVisibility(View.GONE);
                    searchView.getTvClear().setVisibility(View.GONE);
                    searchView.getEtSearch().setText("");
                } catch (JSONException e) {
                    Log.d("context", String.valueOf(e));
                }
            }

            adapter.notifyDataSetChanged();

        });
        newsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == SCROLL_STATE_IDLE) {
                    int t =  (adapter.getCount() > start) ? adapter.getCount() : start;
                    JSONArray jsonArray = new Communication().getIndexNews(t,t+5);
                    if(jsonArray.length() != 0){
                        for(int j = 0 ; j < jsonArray.length(); j++){
                            try {

                                Comment temp = new Comment(jsonArray.getString(j));
                                temp.setVisited(new Communication().getVisited(temp.getTitle()));
                                if(temp.getTitle().contains(s))
                                    adapter.add(temp);
                            } catch (JSONException e) {
                                Log.d("context", String.valueOf(e));
                            }
                        }
                    }
                    if(jsonArray.length() == 0){
                        new SweetDialogUsage().warnCom(mcontext,"服务");
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
