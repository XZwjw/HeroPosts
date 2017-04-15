package first.nestedsliding.fragment.lol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.activity.SearchActivity;
import first.nestedsliding.adapter.SearchItemListViewAdapter;
import first.nestedsliding.itf.CallBack;
import first.nestedsliding.modle.LOLItem;
import first.nestedsliding.util.LoadItem;

/**
 * Created by dell on 2016/11/29.
 * 匹配物品Fragment
 */
public class SearchItemFragment extends Fragment implements CallBack{

    public static ListView mListView;
    public static SearchItemListViewAdapter mAdapter;
    private List<String> mList;                    //name列表
    private HashMap<String,LOLItem> mItemMap;  //通过name获取对应的Item



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_item,null);
        init(view);
        if(mList.size() == 0 || mItemMap == null) {
            LoadItem loadItem = new LoadItem(this);
            loadItem.execute();
        } else {

            mAdapter = new SearchItemListViewAdapter(getContext(),mList,mItemMap);
            mListView.setAdapter(mAdapter);
            mListView.setTextFilterEnabled(true);
        }

        return view;
    }

    private void init(View view) {
        mListView = (ListView) view.findViewById(R.id.listView_search_item);
        mList = SearchActivity.mItemMatchingList;
        mItemMap = SearchActivity.mItemMap;
    }



    public void changeList(List<String> list){
        if(mAdapter !=null) {
            mList = list;
            mAdapter = new SearchItemListViewAdapter(getContext(),mList,mItemMap);
            mAdapter.notifyDataSetChanged();
            mListView.setAdapter(mAdapter);
            Log.d("TAG", "mItemAdapter.getCount():" + mAdapter.getCount());
        }
    }


    @Override
    public void solveHeroTitle(List<String> mTitleList) {

    }

    @Override
    public void solveItemTitle(List<String> mTitleList) {

    }

    @Override
    public void solveSearchHeroList(List<String> idList, List<String> keyList, List<String> nameList) {

    }

    @Override
    public void solveSearchItemList(List<String> nameList) {
        SearchActivity.mBackItemMatchingList = mList;
        mList = nameList;
        mAdapter = new SearchItemListViewAdapter(getContext(),mList,mItemMap);
        mListView.setAdapter(mAdapter);
        mListView.setTextFilterEnabled(true);
    }

    @Override
    public void solveHeroId(HashMap<String, String> map) {

    }

    @Override
    public void solveItemMap(HashMap<String, LOLItem> map) {
        mItemMap = map;
    }


}
