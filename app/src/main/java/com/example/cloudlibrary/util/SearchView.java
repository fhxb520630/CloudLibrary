package com.example.cloudlibrary.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.R;


public class SearchView extends LinearLayout {

    private Context context;

    public EditText getEtSearch() {
        return etSearch;
    }


    private EditText etSearch;

    public TextView getTvClear() {
        return tvClear;
    }

    private TextView tvClear;
    private ImageView searchBack;


    public SearchListView getListView() {
        return listView;
    }

    private SearchListView listView;

    private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase db;

    private  ICallBack mCallBack;
    private BCallBack bCallBack;

    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    private int searchBlockHeight;
    private int searchBlockColor;

    public SearchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Search_View);

        textSizeSearch = typedArray.getDimension(R.styleable.Search_View_textSizeSearch, 20);

        int defaultColor = 0x9b9b9b;
        textColorSearch = typedArray.getColor(R.styleable.Search_View_textColorSearch, defaultColor);

        textHintSearch = typedArray.getString(R.styleable.Search_View_textHintSearch);

        searchBlockHeight = typedArray.getInteger(R.styleable.Search_View_searchBlockHeight, 150);

        int defaultColor2 = 0xffffff;
        searchBlockColor = typedArray.getColor(R.styleable.Search_View_searchBlockColor, defaultColor2);

        typedArray.recycle();
    }


    private void init(){

        initView();

        helper = new RecordSQLiteOpenHelper(context);

        queryData("");

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    if (mCallBack != null){
                        mCallBack.searchAciton(etSearch.getText().toString());
                    }

                    boolean hasData = hasData(etSearch.getText().toString().trim());
                    if (!hasData) {
                        insertData(etSearch.getText().toString().trim());
                        queryData("");
                    }
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //set empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //set empty
            }

            @Override
            public void afterTextChanged(Editable s) {

                String tempName = etSearch.getText().toString();
                queryData(tempName);

            }
        });

        etSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(VISIBLE);
                tvClear.setVisibility(VISIBLE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                etSearch.setText(name);
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
            }
        });

        searchBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bCallBack != null){
                    bCallBack.backAciton();
                }

                Toast.makeText(context, "返回到上一页", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void initView(){

        LayoutInflater.from(context).inflate(R.layout.search_layout,this);


        etSearch = (EditText) findViewById(R.id.et_search);
        etSearch.setTextSize(textSizeSearch);
        etSearch.setTextColor(textColorSearch);
        etSearch.setHint(textHintSearch);


        LinearLayout searchBlock = (LinearLayout) findViewById(R.id.search_block);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) searchBlock.getLayoutParams();
        params.height = searchBlockHeight;
        searchBlock.setBackgroundColor(searchBlockColor);
        searchBlock.setLayoutParams(params);


        listView = (SearchListView) findViewById(R.id.listView);

        tvClear = (TextView) findViewById(R.id.tv_clear);
        tvClear.setVisibility(INVISIBLE);

        searchBack = (ImageView) findViewById(R.id.search_back);

    }

    private void queryData(String tempName) {

        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        BaseAdapter adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (tempName.equals("") && cursor.getCount() != 0){
            tvClear.setVisibility(VISIBLE);
        }else{
            tvClear.setVisibility(INVISIBLE);
        }
    }

    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        tvClear.setVisibility(INVISIBLE);
    }

    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        return cursor.moveToNext();
    }

    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    public void setOnClickSearch(ICallBack mCallBack){
        this.mCallBack = mCallBack;

    }

    public void setOnClickBack(BCallBack bCallBack){
        this.bCallBack = bCallBack;
    }

    public void setInit() {
        tvClear.setVisibility(GONE);
        listView.setVisibility(GONE);
    }
}
