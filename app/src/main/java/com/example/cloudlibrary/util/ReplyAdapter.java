package com.example.cloudlibrary.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cloudlibrary.CLApplication;
import com.example.cloudlibrary.R;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;
public class ReplyAdapter extends ArrayAdapter<Comment> {
    private int resourceId;
    private Context mcontext;

    public ReplyAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        mcontext = context;
        resourceId = resource;
    }

    @Override
    public View getView(final int position, View converView, ViewGroup parent) {
        final Comment comment = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        Button button = view.findViewById(R.id.delete1);
        BubbleTextView bubbleTextView =  view.findViewById(R.id.btv1);
        bubbleTextView.setText(comment.getTitle());
        TextView date =  view.findViewById(R.id.date1);
        date.setText(comment.getDate());
        TextView author =  view.findViewById(R.id.author1);
        author.setText("by" + comment.getAuthor());
        if (comment.getPower() == 1) {
            author.setTextColor(Color.parseColor("#D34040"));
        }
        CLApplication clapplication = (CLApplication) getContext().getApplicationContext();
        button.setVisibility(View.GONE);
        if (clapplication.getNowInfo() == 2) {
            new SweetDialogUsage().adapter(button, mcontext, comment, 0);
            button.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
