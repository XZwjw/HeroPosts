package first.nestedsliding.fragment.lol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.activity.MainActivity;
import first.nestedsliding.activity.SearchActivity;
import first.nestedsliding.activity.SingleHeroActivity;
import first.nestedsliding.adapter.FragmentAdapter;
import first.nestedsliding.itf.CallBack;
import first.nestedsliding.modle.LOLItem;
import first.nestedsliding.util.LoadHero;
import first.nestedsliding.util.LoadItem;

/**
 * Created by dell on 2016/10/7.
 */
public class LOlFragment extends Fragment  implements CallBack {

    private static final int REQUEST_CODE_SEARCHACTIVITY = 1;

    private Toolbar toolbar;
    private FloatingActionButton right;
    private TabLayout tabLayout;
    private static List<String> mHeroTitles;    //英雄标题
    private static List<String> mItemTitles;    //物品标题
    private ViewPager mViewPager;
    private static FragmentAdapter mFragmentAdapter; //fragment切换适配器
    private List<Fragment> mFragmentList;
    public static int i;
    private static boolean STATE = false;      //是否处于打开物品界面状态

    public static boolean HAVE_NETWORK;   //是否有网
    private IntentFilter intentFilter;
    private NetWorkChangeReceiver netWorkChangeReceiver;

    private ProgressBar mProgressBar;       //加载

    private static FragmentManager mFragmentManager;
//    private TextView mReload;                //重新加载


    /**
     * SearchActivity所需数据
     */
    private List<String> mHeroIdList;
    private List<String> mHeroKeyList;
    private List<String> mHeroNameList;
    private HashMap<String,String> mHeroMap;
    private List<String> mItemNameList;

    private HashMap<String,LOLItem> mItemMap;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.content_lol, container, false);
        init(view);
        Log.d("TAG1", "LOLFragment  onCreateView");

        toolbar.setTitle("LOL");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.side_navigation);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.mNavigationOpened) {
                    MainActivity.closeNavigation();
                } else {
                    MainActivity.openNavigation();
                }

            }
        });

//        if (!STATE ) {
        LoadHero loadHtml = new LoadHero(LOlFragment.this);
        loadHtml.execute();
//        } else {
        LoadItem loadItem = new LoadItem(LOlFragment.this);
        loadItem.execute();
//        }

        /**
         * 设置tabs
         */
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        //悬浮button监听器
        right = (FloatingActionButton) view.findViewById(R.id.right);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //快速置顶
                LOlFragment.rapidTop();
                //置顶后提醒信息
                Snackbar.make(v, "up了", Snackbar.LENGTH_LONG).setAction("down", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //快速置底
                        LOlFragment.rapidBottom();
                    }
                }).show();

            }
        });
        return view;
    }


    private void init(View view) {
//        mReload = (TextView) view.findViewById(R.id.reload_lol);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar_lol);
        toolbar = (Toolbar) view.findViewById(R.id.toolBar_lol);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mHeroTitles = new ArrayList<>();
        mItemTitles = new ArrayList<>();
        mFragmentManager = getActivity().getSupportFragmentManager();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        getActivity().registerReceiver(netWorkChangeReceiver, intentFilter);
    }


    //快速置顶
    public static void rapidTop(){
        //STATE为true时，当前为物品界面，false时，当前为英雄界面

        if(STATE) {
          ItemFragment.rapidTop();
        } else {
           HeroFragment.rapidTop();
        }
    }

    //快速置底
    public static void rapidBottom(){
        //STATE为true时，当前为物品界面，false时，当前为英雄界面

        if(STATE) {
            ItemFragment.rapidBottom();
        } else {
            HeroFragment.rapidBottom();
        }
    }


    /**
     * 调用该方法时，表明已获得英雄数据
     * @param mList
     */
    @Override
    public void solveHeroTitle(List<String> mList) {

        if(mList !=null) {
            mHeroTitles = mList;
            Log.d("TAG", "Hero STATE :" + STATE + "HAVE_NETWORK:" + HAVE_NETWORK);
            if(!STATE) {
                setHeroInterface();
            }
        }

    }

    /**
     * 调用该方法时，表明已获得物品数据
     * @param mTitleList
     */
    @Override
    public void solveItemTitle(List<String> mTitleList) {
        if(mTitleList !=null) {
            mItemTitles = mTitleList;
            Log.d("TAG", "Item STATE :" + STATE + "HAVE_NETWORK:" + HAVE_NETWORK);
            if(STATE) {
                setItemInterface();
            }
        }
    }

    @Override
    public void solveSearchHeroList(List<String> idList, List<String> keyList, List<String> nameList) {
        mHeroIdList = idList;
        mHeroKeyList = keyList;
        mHeroNameList = nameList;
    }

    @Override
    public void solveSearchItemList(List<String> nameList) {

        mItemNameList = nameList;
    }

    @Override
    public void solveHeroId(HashMap<String, String> map) {
        mHeroMap = map;
    }

    @Override
    public void solveItemMap(HashMap<String, LOLItem> map) {
        mItemMap = map;
    }


    /**
     * 英雄界面
     */
    private void setHeroInterface() {


        if(mHeroTitles.size() == 0 &&!HAVE_NETWORK) {

            overLoading();
            Toast.makeText(getContext(),"无网，请检查网络连接。",Toast.LENGTH_SHORT).show();
            return;
        }

        if(mHeroTitles.size() == 0) {

            STATE = false;
            LoadHero loadHero = new LoadHero(LOlFragment.this);
            loadHero.execute();
            return;
        }

        mFragmentList = new ArrayList<>();
        for(int i = 0;i < mHeroTitles.size();i++) {
            HeroFragment heroFragment = new HeroFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("i", i);
            heroFragment.setArguments(bundle);
            mFragmentList.add(heroFragment);
        }
        Log.d("TAG", "mFragmentList.size():" + mFragmentList.size());
        if(mFragmentList.size() != 0) {
            setInterface(mFragmentList, mHeroTitles);
        }

    }

    /**
     * 物品界面
     */
    private void setItemInterface() {
        /**
         * 添加Fragment
         */

        if(mItemTitles.size() == 0 && !HAVE_NETWORK) {

            overLoading();
            Toast.makeText(getContext(),"无网，请检查网络连接。",Toast.LENGTH_SHORT).show();
//            mReload.setVisibility(View.VISIBLE);
            return;
        }

        if(mItemTitles.size() == 0) {

            Log.d("TAG", "重新加载LoadItem");
            STATE = true;
            LoadItem loadItem = new LoadItem(LOlFragment.this);
            loadItem.execute();

            return;
        }
        Log.d("TAG", "加载Item中....");
        Log.d("TAG1111 LoadItem.mLists.size()"," "+LoadItem.mLists.size());
        mFragmentList = new ArrayList<>();
        for(int i = 0;i < mItemTitles.size();i++) {
            ItemFragment itemFragment = new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("which",i);
            itemFragment.setArguments(bundle);
            Log.d("TAG", "加一个Fragment");
            mFragmentList.add(itemFragment);
        }
        Log.d("TAG", "mFragmentList.size():" + mFragmentList.size());
        if(mFragmentList.size() != 0) {
            setInterface(mFragmentList, mItemTitles);
        }
    }

    /**
     * 搜索界面
     */
    private void changeSearchInterface() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("heroIdList", (ArrayList<String>) mHeroIdList);
        bundle.putStringArrayList("heroKeyList", (ArrayList<String>) mHeroKeyList);
        bundle.putStringArrayList("heroNameList", (ArrayList<String>) mHeroNameList);
        bundle.putStringArrayList("itemNameList", (ArrayList<String>) mItemNameList);
        bundle.putSerializable("heroMap",mHeroMap);
        bundle.putSerializable("itemMap", mItemMap);
        intent.putExtras(bundle);
//        startActivity(intent);
        startActivityForResult(intent,REQUEST_CODE_SEARCHACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SEARCHACTIVITY:
                if(resultCode == 1) {
                    Bundle bundle = data.getExtras();
                    Intent intent = new Intent(getActivity(), SingleHeroActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }

    //设置界面
    private void setInterface(List<Fragment> mFragmentList, List<String> mTitles) {
        /**
         * mViewPager添加适配器
         */
        mFragmentAdapter = new FragmentAdapter(mFragmentManager,mFragmentList, mTitles);
        mViewPager.setAdapter(mFragmentAdapter);

        /**
         * 适配mPagerAdapter
         */
        tabLayout.setupWithViewPager(mViewPager);

        /**
         * 加载完成,取消加载框
         */
        overLoading();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lol, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean beforeState = STATE;

        switch (item.getItemId())  {
            case R.id.hero:
                /**
                 * 判断当前是否为英雄界面,将当前界面转换到英雄界面
                 */
                STATE = false;
                if(beforeState) {
                    startLoading();
                    setHeroInterface();
                }

                break;
            case R.id.item:
                /**
                 * 判断当前是否为物品界面，将当前界面转换到物品界面
                 */
                STATE = true;
                if(!beforeState) {
                    startLoading();
                    setItemInterface();
                }

                break;
            case R.id.search_menu:
                /**
                 * 将当前界面转换到搜索界面
                 */
                changeSearchInterface();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    public class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager;
            if(getActivity() != null) {
                manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isAvailable()) {
                    /**
                     * 网络存在
                     */
                    HAVE_NETWORK = true;

                    if(STATE && mItemTitles.size() == 0) {
                        startLoading();
                        setItemInterface();
                    } else if(!STATE && mHeroTitles.size() == 0) {
                        startLoading();
                        setHeroInterface();
                    }
//                mReload.setVisibility(View.GONE);
                } else {
                    /**
                     * 网络不存在
                     */
                    HAVE_NETWORK = false;
                }
            }

        }
    }



    private void startLoading(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void overLoading() {
        mProgressBar.setVisibility(View.GONE);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().unregisterReceiver(netWorkChangeReceiver);
    }
}
