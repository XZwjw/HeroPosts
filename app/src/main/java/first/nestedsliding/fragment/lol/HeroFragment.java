package first.nestedsliding.fragment.lol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.adapter.HeroRecycleViewAdpter;
import first.nestedsliding.modle.LOLHero;
import first.nestedsliding.shap.ItemDivider;
import first.nestedsliding.util.LoadHero;

/**
 * Created by dell on 2016/9/23.
 */
public class HeroFragment extends Fragment{



    private static RecyclerView mRecycleViewLOL;
    private static List<RecyclerView> mRecycleViewList = new ArrayList<>();
    private HeroRecycleViewAdpter mHeroRecycleViewAdapter;
    private List<LOLHero> list;     //数据列表
    private List<List<LOLHero>> lists;      //所有英雄数据的列表
    private int i;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lol_fragment, container, false);

        i = getArguments().getInt("i");
        init(view);
        heroSolve();
        return view;
    }

    private void init(View view) {
        mRecycleViewLOL = (RecyclerView) view.findViewById(R.id.recycleView_lol);
        mRecycleViewList.add(mRecycleViewLOL);
        lists = LoadHero.lists;
        if(lists.size() > 0) {
            list = lists.get(i);
        } else {
            list = new ArrayList<>();
        }
        Log.d("TAG","list.size():"+list.size());
    }

    public void heroSolve() {
        mHeroRecycleViewAdapter = new HeroRecycleViewAdpter(getContext(),list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        mRecycleViewLOL.setAdapter(mHeroRecycleViewAdapter);
        mRecycleViewLOL.setLayoutManager(gridLayoutManager);
        ItemDivider itemDivider = new ItemDivider(getContext(),R.drawable.item_divider);
        mRecycleViewLOL.addItemDecoration(itemDivider);
    }

    //快速置顶
    public static void rapidTop(){
        Log.d("TAG","HeroRapidTop");
        for(int j = 0;j < mRecycleViewList.size();j++) {
            mRecycleViewList.get(j).smoothScrollToPosition(0);
        }
    };

    //快速置底
    public static void rapidBottom() {
        Log.d("TAG", "HeroRapidBottom");
        for (int j = 0; j < mRecycleViewList.size(); j++) {
            mRecycleViewList.get(j).smoothScrollToPosition(mRecycleViewList.get(j).getAdapter().getItemCount());
        }
    };

}
