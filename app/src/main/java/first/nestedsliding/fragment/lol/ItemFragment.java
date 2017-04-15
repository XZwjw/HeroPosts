package first.nestedsliding.fragment.lol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.adapter.ItemRecycleViewAadpter;
import first.nestedsliding.modle.LOLItem;
import first.nestedsliding.shap.ItemDivider;
import first.nestedsliding.util.LoadItem;

/**
 * Created by dell on 2016/10/20.
 */
public class ItemFragment extends Fragment{

    private  ScrollView mScrollView;
    private static List<ScrollView> mScrollViewList = new ArrayList<>();   //ItemFragment的ScrollView列表

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;

    private List<RecyclerView> mRecycleViewList;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;

    private List<TextView> mTextViewList;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    private volatile List<List<LOLItem>> mLists;
    private String[] mSubTitles;
    private int which;
    private ItemRecycleViewAadpter mItemRecycleViewAadpter;
    private ProgressBar mProgressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment,container,false);
        init(view);
        beginLoading();
        /**
         * LOlFragment传过来的参数,which
         * which :  0:加载第一个itemFragment ,1:加载第二个itemFragment,2:.........
         */
        which = getArguments().getInt("which");

        ItemSolve();
        return view;
    }

    private void beginLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void overLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void ItemSolve() {

        //获取该物品类型分类数据
        mLists = getLists(which);

        mSubTitles = getTitleLists(which);

        for(int i=0;i< mLists.size();i++) {
            mItemRecycleViewAadpter = new ItemRecycleViewAadpter(getContext(), (ArrayList<LOLItem>) mLists.get(i));
            RecyclerView recyclerView = mRecycleViewList.get(i);
            recyclerView.setAdapter(mItemRecycleViewAadpter);
            ItemDivider itemDivider = new ItemDivider(getContext(),R.drawable.item_divider);
            recyclerView.addItemDecoration(itemDivider);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            mTextViewList.get(i).setText(mSubTitles[i]);
        }
        overLoading();

    }

    private List<List<LOLItem>> getLists(int which) {
        List<List<LOLItem>> lists = null;
        if(LoadItem.mLists.get(which) != null) {
           lists  = LoadItem.mLists.get(which);
        }
        return lists;

    }



    private void init(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar_item);
        mScrollView = (ScrollView) view.findViewById(R.id.item_fragment_scrollView);
        mScrollViewList.add(mScrollView);

        linearLayout1 = (LinearLayout) view.findViewById(R.id.item_fragment_include_1);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.item_fragment_include_2);
        linearLayout3 = (LinearLayout) view.findViewById(R.id.item_fragment_include_3);
        linearLayout4 = (LinearLayout) view.findViewById(R.id.item_fragment_include_4);

        mRecycleViewList = new ArrayList<>();
        recyclerView1 = (RecyclerView) linearLayout1.findViewById(R.id.sub_item_recycleView);
        recyclerView2 = (RecyclerView) linearLayout2.findViewById(R.id.sub_item_recycleView);
        recyclerView3 = (RecyclerView) linearLayout3.findViewById(R.id.sub_item_recycleView);
        recyclerView4 = (RecyclerView) linearLayout4.findViewById(R.id.sub_item_recycleView);
        mRecycleViewList.add(recyclerView1);
        mRecycleViewList.add(recyclerView2);
        mRecycleViewList.add(recyclerView3);
        mRecycleViewList.add(recyclerView4);

        mTextViewList = new ArrayList<>();
        textView1 = (TextView) linearLayout1.findViewById(R.id.sub_item_text);
        textView2 = (TextView) linearLayout2.findViewById(R.id.sub_item_text);
        textView3 = (TextView) linearLayout3.findViewById(R.id.sub_item_text);
        textView4 = (TextView) linearLayout4.findViewById(R.id.sub_item_text);
        mTextViewList.add(textView1);
        mTextViewList.add(textView2);
        mTextViewList.add(textView3);
        mTextViewList.add(textView4);

        mLists = new ArrayList<>();
    }


    public String[] getTitleLists(int i) {
        String[] strings = null;
        strings = LoadItem.mTitleClassificationList.get(i);
        return strings;
    }

    //快速置顶
    public static void rapidTop(){
        for(int i = 0;i < mScrollViewList.size();i++) {
            if(mScrollViewList.get(i) != null) {
                mScrollViewList.get(i).fullScroll(ScrollView.FOCUS_UP);
            }
        }

    };

    //快速置底
    public static void rapidBottom(){
        for(int i = 0;i < mScrollViewList.size();i++) {
            if(mScrollViewList.get(i) != null) {
                mScrollViewList.get(i).fullScroll(ScrollView.FOCUS_DOWN);
            }
        }

    };
}
