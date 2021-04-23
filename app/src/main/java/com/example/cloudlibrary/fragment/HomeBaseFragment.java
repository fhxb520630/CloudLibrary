package com.example.cloudlibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.example.cloudlibrary.R;

public class HomeBaseFragment extends Fragment {
    View.OnClickListener onClickListener;

    public HomeBaseFragment(View.OnClickListener l){
        onClickListener=l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_base,container,false);
        Button select0=(Button) view.findViewById(R.id.fragment_home_base_select0);
        Button select1=(Button) view.findViewById(R.id.fragment_home_base_select1);
        select0.setOnClickListener(onClickListener);
        select1.setOnClickListener(onClickListener);
        return view;
    }
}
