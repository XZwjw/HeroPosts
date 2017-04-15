package first.nestedsliding.fragment.lol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.activity.SearchActivity;
import first.nestedsliding.adapter.SearchHeroListViewAdapter;
import first.nestedsliding.itf.CallBack;
import first.nestedsliding.modle.LOLItem;
import first.nestedsliding.util.LoadHero;

/**
 * Created by dell on 2016/11/29.
 */
public class SearchHeroFragment extends Fragment implements CallBack{

    public static ListView mListView;
    private List<String> mList;
    public static SearchHeroListViewAdapter mAdapter;
    private HashMap<String,String> mHeroIdMap;  //通过key、name获取id
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_hero,container,false);
        init(view);
        if(mList.size() == 0 || mHeroIdMap == null) {
            LoadHero loadHero = new LoadHero(this);
            loadHero.execute();
        } else {
            mAdapter = new SearchHeroListViewAdapter(getContext(),mList);
            mListView.setAdapter(mAdapter);
            mListView.setTextFilterEnabled(true);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view = mListView.getChildAt(position - mListView.getFirstVisiblePosition());
                TextView textView = (TextView) view.findViewById(R.id.text_search_item);
                if(!textView.getText().equals("主人,没找到该英雄")) {
                    String idString = mHeroIdMap.get(textView.getText().toString());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("id",idString);
                    intent.putExtras(bundle);
                    getActivity().setResult(1,intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }


    private void init(View view) {
        mListView = (ListView) view.findViewById(R.id.listView_search_hero);
        mList = SearchActivity.mHeroMatchingList;
        mHeroIdMap = SearchActivity.mHeroMap;
    }

    public void changeList(List<String> list){
        if(mAdapter !=null) {
            mList = list;
            mAdapter = new SearchHeroListViewAdapter(getContext(),mList);
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
//        mList.addAll(keyList);
        mList.addAll(nameList);
        SearchActivity.mBackHeroMatchingList = mList;
    }

    @Override
    public void solveSearchItemList(List<String> nameList) {

    }

    @Override
    public void solveHeroId(HashMap<String, String> map) {
        mHeroIdMap = map;
    }

    @Override
    public void solveItemMap(HashMap<String, LOLItem> map) {

    }


}
