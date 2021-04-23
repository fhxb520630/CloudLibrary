package com.example.cloudlibrary.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cloudlibrary.R;
import com.example.cloudlibrary.util.Communication;
import com.example.cloudlibrary.util.FallObject;
import com.example.cloudlibrary.util.FallingView;
import com.example.cloudlibrary.util.Selector;
import com.example.cloudlibrary.util.SelectorGroup;
import com.example.cloudlibrary.util.SweetDialogUsage;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeSelect0Fragment extends Fragment {
    Selector.OnSelectorStateListener selectorStateListener;

    public HomeSelect0Fragment(Selector.OnSelectorStateListener s){
        selectorStateListener = s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_select0,container,false);
        TextView textView=view.findViewById(R.id.fragment_home_select0_textview);
        textView.setMovementMethod(new ScrollingMovementMethod());
        try{
            JSONObject jsonObject=new Communication().getInformation("西馆");
            textView.setText(jsonObject.getString("describe"));
        } catch (JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        SelectorGroup selectorGroup = new SelectorGroup();
        Selector f1 = view.findViewById(R.id.selector_10);
        Selector f2 = view.findViewById(R.id.selector_20);
        Selector f3 = view.findViewById(R.id.selector_30);
        Selector f4 = view.findViewById(R.id.selector_40);

        f1.setOnSelectorStateListener(selectorStateListener).setSelectorGroup(selectorGroup);
        f2.setOnSelectorStateListener(selectorStateListener).setSelectorGroup(selectorGroup);
        f3.setOnSelectorStateListener(selectorStateListener).setSelectorGroup(selectorGroup);
        f4.setOnSelectorStateListener(selectorStateListener).setSelectorGroup(selectorGroup);

        FallObject.Builder builder = null;
        FallObject fallObject = new SweetDialogUsage().falling(getContext(),builder);
        FallingView fallingView = view.findViewById(R.id.fallingView2);
        fallingView.addFallObject(fallObject, 15);
        fallingView.bringToFront();

        return view;
    }
}
