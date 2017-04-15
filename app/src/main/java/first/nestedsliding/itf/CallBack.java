package first.nestedsliding.itf;

import java.util.HashMap;
import java.util.List;

import first.nestedsliding.modle.LOLItem;

/**
 * Created by dell on 2016/9/28.
 */
public interface CallBack {
    void solveHeroTitle(List<String> mTitleList);       //英雄标题列表
    void solveItemTitle(List<String> mTitleList);       //物品标题列表
    void solveSearchHeroList(List<String> idList,List<String> keyList,List<String> nameList);   //查询英雄数据列表
    void solveSearchItemList(List<String> nameList);    //查询物品数据列表
    void solveHeroId(HashMap<String,String> map);           //通过key、name获取Id的HashMap
    void solveItemMap(HashMap<String,LOLItem> map);     //通过name获取对应Item信息的HashMap
}
