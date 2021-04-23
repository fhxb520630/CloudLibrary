package com.example.cloudlibrary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.util.ButtonInfo;
import com.example.cloudlibrary.util.Communication;
import com.example.cloudlibrary.util.Selector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, Selector.OnSelectorStateListener{
    private FragmentManager fragmentManager;
    private HomeBaseFragment homeBaseFragment;
    private HomeSelect0Fragment homeSelect0Fragment;
    private HomeSelect1Fragment homeSelect1Fragment;
    private HomeMapFragment homeMapFragment;
    private int nowFragment;//homeBaseFragment=0,homeSelect0Fragment=1,homeSelect1Fragment=2,homeMapFragment=3
    private int selected;//homeSelect0Fragment=0,homeSelect1Fragment=1



    public boolean onBack(){
        if(nowFragment == 1 || nowFragment == 2){
            nowFragment = 0;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(
                    R.anim.card_flip_right_in,
                    R.anim.card_flip_right_out,
                    R.anim.card_flip_left_in,
                    R.anim.card_flip_left_out
            );
            fragmentTransaction.replace(R.id.fragment_home_framlayout,homeBaseFragment);
            fragmentTransaction.commit();
            return true;
        }else if(nowFragment == 3){
            nowFragment = selected + 1;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(
                    R.anim.card_flip_right_in,
                    R.anim.card_flip_right_out,
                    R.anim.card_flip_left_in,
                    R.anim.card_flip_left_out
            );
            fragmentTransaction.replace(R.id.fragment_home_framlayout,(selected==1)?homeSelect1Fragment:homeSelect0Fragment);
            fragmentTransaction.commit();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        //HomeBaseFragment
        if(view.getId() == R.id.fragment_home_base_select0){
                nowFragment=1;
                selected=0;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        R.anim.card_flip_right_in,
                        R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out
                );
                fragmentTransaction.replace(R.id.fragment_home_framlayout,homeSelect0Fragment);
                fragmentTransaction.commit();
                return;
        }else if(view.getId() == R.id.fragment_home_base_select1){
                nowFragment=2;
                selected=1;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        R.anim.card_flip_right_in,
                        R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out
                );
                fragmentTransaction.replace(R.id.fragment_home_framlayout,homeSelect1Fragment);
                fragmentTransaction.commit();
                return;
        }
    }
    @Override
    public void onStateChange(Selector selector, boolean isSelect) {
        if (isSelect) {
            String floor = "";
            String pos = "";

            switch (selector.getId()){
                case R.id.selector_10:
                    pos = "西馆";
                    floor = "1";
                    break;
                case R.id.selector_20:
                    pos = "西馆";
                    floor = "2";
                    break;
                case R.id.selector_30:
                    pos = "西馆";
                    floor = "3";
                    break;
                case R.id.selector_40:
                    pos = "西馆";
                    floor = "4";
                    break;
                case R.id.selector_11:
                    pos = "北馆";
                    floor = "G";
                    break;
                case R.id.selector_21:
                    pos = "北馆";
                    floor = "1";
                    break;
                case R.id.selector_31:
                    pos = "北馆";
                    floor = "2";
                    break;
                case R.id.selector_41:
                    pos = "北馆";
                    floor = "3";
                    break;
                case R.id.selector_51:
                    pos = "北馆";
                    floor = "4";
                    break;
                case R.id.selector_61:
                    pos = "北馆";
                    floor = "5";
                    break;
                default:
                    break;
            }
            JSONArray jsonArray = new JSONArray() ;
            String mapId ="";
            try {
                mapId = new Communication().getFloorImage(floor,pos);
                jsonArray = new Communication().getFloorPoint(floor,pos);
            } catch (JSONException e) {
                Log.d("context", String.valueOf(e));
            }
            new Communication().addVisited(pos);
            List<ButtonInfo> buttonInfoList = new ArrayList<>();
            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    Log.d("context", String.valueOf(e));
                }
                try {
                    buttonInfoList.add(new ButtonInfo(jsonObject.getInt("x")/10000.,jsonObject.getInt("y")/10000.,jsonObject.getString("pos"),jsonObject.getString("order")));
                } catch (JSONException e) {
                    Log.d("context", String.valueOf(e));
                }
            }
            homeMapFragment.setButtonInfoList(buttonInfoList);
            homeMapFragment.setMapId(mapId);
            homeMapFragment.setFloor(floor);
            homeMapFragment.setPos(pos);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(
                    R.anim.card_flip_right_in,
                    R.anim.card_flip_right_out,
                    R.anim.card_flip_left_in,
                    R.anim.card_flip_left_out
            );
            fragmentTransaction.replace(R.id.fragment_home_framlayout,homeMapFragment);
            fragmentTransaction.commit();
            nowFragment = 3;
        }
    }

    public HomeFragment(){
        homeBaseFragment = new HomeBaseFragment(this);
        homeSelect0Fragment = new HomeSelect0Fragment(this);
        homeSelect1Fragment = new HomeSelect1Fragment(this);
        homeMapFragment = new HomeMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        Log.d("dedede","onCreateView");
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.card_flip_right_in,
                R.anim.card_flip_right_out,
                R.anim.card_flip_left_in,
                R.anim.card_flip_left_out
        );
        fragmentTransaction.replace(R.id.fragment_home_framlayout,homeBaseFragment);
        fragmentTransaction.commit();
        nowFragment = 0;
        return view;
    }
}
