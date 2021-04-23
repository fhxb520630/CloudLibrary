package com.example.cloudlibrary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cloudlibrary.FullImageActivity;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.layout.MapLayout;
import com.example.cloudlibrary.util.ButtonInfo;
import com.example.cloudlibrary.util.Communication;

import org.json.JSONException;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeMapFragment extends Fragment {
    private List<ButtonInfo> buttonInfoList;
    private String mapUrl;
    private String pos;
    private String floor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_map,container,false);
        MapLayout mapLayout = (MapLayout) view.findViewById(R.id.maplayout);
        mapLayout.init(mapUrl,buttonInfoList);
        TextView textView = (TextView) view.findViewById(R.id.floor_text);
        textView.setText(pos+" "+floor+"层");
        Button button = (Button) view.findViewById(R.id.fragment_floor_full);
        String s = "";
        try {
            s = new Communication().getFloorFullImage(pos, floor);
            Log.d("home",s);
        } catch (JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        String finalS = s;
        button.setOnClickListener(view1 -> {

            if("http://166.111.226.244:11352/media/".equals(finalS)){
                SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("提示");
                dialog.setContentText("暂无全景图");
                dialog.setCancelable(true);
                dialog.setConfirmText("确认");
                dialog.setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation());
                dialog.show();
            }else{
                Intent intent=new Intent(getActivity(), FullImageActivity.class);
                intent.putExtra("pos",pos);
                intent.putExtra("floor",floor);
                startActivity(intent);
            }

        });
        return view;
    }

    public void setMapId(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public void setButtonInfoList(List<ButtonInfo> b){
        buttonInfoList = b;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
