package com.example.cloudlibrary.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cloudlibrary.R;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;


public class NewsAdapter extends ArrayAdapter<Comment> {
    private int resourceId;

    public NewsAdapter(Context context, int resource,List<Comment> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }
    @Override
    public View getView(final int position, View converView, ViewGroup parent){
        final Comment comment = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        BubbleTextView btv = (BubbleTextView) view.findViewById(R.id.news_title);

        TextView tv2 = (TextView) view.findViewById(R.id.news_visited);
        btv.setText(comment.getTitle());
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, btv.getPaint().getTextSize(), Color.GRAY, Color.BLACK, Shader.TileMode.CLAMP);
        btv.getPaint().setShader(mLinearGradient);
        tv2.setText("访问次数:" + comment.getVisited());
        return view;
    }

}
