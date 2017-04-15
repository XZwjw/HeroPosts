package first.nestedsliding.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.fragment.lol.SearchHeroFragment;
import first.nestedsliding.fragment.lol.SearchItemFragment;
import first.nestedsliding.modle.LOLItem;

/**
 * Created by dell on 2016/10/24.
 * 通过id,key,name查找
 */
public class SearchActivity extends AppCompatActivity{

    private final static String TAG = "SearchActivity";

    private Toolbar mToolbar;
    private EditText mEditText;
    private ViewPager mViewPager;
    private TextView mHeroText,mItemText;
    private ImageView mHeroLineImage,mItemLineImage;    //英雄下划线、物品下划线

    private int LIST_STATE = 0;             //当前显示列表，0:英雄、1:物品

    /**
     * 英雄与物品列表数据
     */
    private List<String> mHeroIdList;
    private List<String> mHeroKeyList;
    private List<String> mHeroNameList;
    public static HashMap<String,String> mHeroMap;  //通过name获取id
    private List<String> mItemNameList;
    public static HashMap<String,LOLItem> mItemMap;


    /**
     *英雄与物品匹配列表数据
     */
    public static List<String> mHeroMatchingList;
    public static List<String> mItemMatchingList;
    public static List<String> mBackHeroMatchingList;
    public static List<String> mBackItemMatchingList;

    /**
     *viewPager的fragment列表
     */
    private List<Fragment> mFragmentList;
    private SearchHeroFragment mHeroFragment;
    private SearchItemFragment mItemFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        init();
        Bundle bundle = getIntent().getExtras();
        mHeroIdList = bundle.getStringArrayList("heroIdList");
        mHeroKeyList = bundle.getStringArrayList("heroKeyList");
        mHeroNameList = bundle.getStringArrayList("heroNameList");
        mItemNameList = bundle.getStringArrayList("itemNameList");
        mHeroMap = (HashMap<String, String>) bundle.getSerializable("heroMap");
        mItemMap = (HashMap<String, LOLItem>) bundle.getSerializable("itemMap");
        setMatchingData();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mHeroFragment = new SearchHeroFragment();
        mItemFragment = new SearchItemFragment();
        mFragmentList.add(mHeroFragment);
        mFragmentList.add(mItemFragment);

        addViewPagerAdapter();
        pageChangeEvent();
        queryListener();
        setTextClickEvent();

    }

    /**
     * 查询监听时间
     */
    private void queryListener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(s.toString())) {
                    mHeroMatchingList = mBackHeroMatchingList;
                    mItemMatchingList = mBackItemMatchingList;

                } else {
                    mHeroMatchingList = new ArrayList<>();
                    for (String str : mBackHeroMatchingList) {
                        if (str.startsWith(s.toString())||str.contains(s.toString())||str.endsWith(s.toString())) {
                            Log.d(TAG,str);
                            mHeroMatchingList.add(str);
                        }
                    }
                    if(mHeroMatchingList.size() == 0) {
                        mHeroMatchingList.add("主人,没找到该英雄");
                    }
                    mItemMatchingList = new ArrayList<>();
                    for (String str : mBackItemMatchingList) {
                        if (str.startsWith(s.toString())||str.contains(s.toString())||str.endsWith(s.toString())) {
                            mItemMatchingList.add(str);
                            Log.d(TAG, str);
                        }
                    }
                    if(mItemMatchingList.size() == 0) {
                        mItemMatchingList.add("主人,没找到该物品");
                    }
                }
                Log.d(TAG,"mHeroSize:"+mHeroMatchingList.size());
                Log.d(TAG,"mItemSize:"+mItemMatchingList.size());
                mHeroFragment.changeList(mHeroMatchingList);
                mItemFragment.changeList(mItemMatchingList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * viewpager换页监听事件
     */
    private void pageChangeEvent() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    //查询英雄
                    case 0:
                        mHeroText.setAlpha(1.0f);
                        mItemText.setAlpha(0.5f);
                        mHeroLineImage.setVisibility(View.INVISIBLE);
                        mItemLineImage.setVisibility(View.VISIBLE);
                        break;
                    //查询物品
                    case 1:
                        mHeroText.setAlpha(0.5f);
                        mItemText.setAlpha(1.0f);
                        mHeroLineImage.setVisibility(View.VISIBLE);
                        mItemLineImage.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addViewPagerAdapter() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }
        });
    }


    /**
     * mHeroText,mItemText点击切换fragment
     */
    private void setTextClickEvent() {
        mHeroText.setClickable(true);
        mHeroText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        mItemText.setClickable(true);
        mItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(mHeroFragment.mListView != null) {
//            mHeroFragment.mListView.setAdapter(SearchHeroFragment.mAdapter);
//            mHeroFragment.mAdapter.notifyDataSetChanged();
//        }
//        if(mItemFragment.mListView !=null) {
//            mItemFragment.mListView.setAdapter(SearchItemFragment.mAdapter);
//            mItemFragment.mAdapter.notifyDataSetChanged();
//        }
//
//    }

    /**
     * 获取英雄物品匹配信息列表
     */
    private void setMatchingData() {
        if(mHeroKeyList == null) {
            mHeroKeyList = new ArrayList<>();
        }
        if(mHeroNameList == null) {
            mHeroNameList = new ArrayList<>();
        }
        if(mItemNameList == null) {
            mItemNameList = new ArrayList<>();
        }

//        mBackHeroMatchingList.addAll(mHeroKeyList);
        mBackHeroMatchingList.addAll(mHeroNameList);
        mBackItemMatchingList.addAll(mItemNameList);
//        mHeroMatchingList.addAll(mHeroKeyList);
        mHeroMatchingList.addAll(mHeroNameList);
        mItemMatchingList.addAll(mItemNameList);

    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_search);
        mEditText = (EditText) findViewById(R.id.search_text);
        mViewPager = (ViewPager) findViewById(R.id.viewPager_search);
        mHeroText = (TextView) findViewById(R.id.hero_search);
        mItemText = (TextView) findViewById(R.id.item_search);
        mHeroLineImage = (ImageView) findViewById(R.id.heroLine_search);
        mItemLineImage = (ImageView) findViewById(R.id.itemLine_search);
        mFragmentList = new ArrayList<>();
        mHeroMatchingList = new ArrayList<>();
        mItemMatchingList = new ArrayList<>();
        mBackHeroMatchingList = new ArrayList<>();
        mBackItemMatchingList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
