package first.nestedsliding.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import first.nestedsliding.itf.CallBack;
import first.nestedsliding.modle.LOLItem;

/**
 * Created by dell on 2016/10/13.
 * LOL物品加载类
 */
public class LoadItem extends AsyncTask<Void,Void,Void>{
    //标题url
    private final static String TitleUrl = "http://lol.qq.com/web201310/info-item.shtml#Navi";
    //物品url
    private final static String itemUrl = "http://lol.qq.com/biz/hero/item.js";

    public static List<String[]> mTitleClassificationList = new ArrayList<>();
    //所有物品类型标题分类字符串
    private String[] mAllItemTitleString = {"所有物品"};
    //防御物品类型标题分类字符串
    private String[] mDefenseTitleString = {"生命值","护甲","魔抗","生命回复"};
    //攻击物品类型标题分类字符串
    private String[] mAttackTitleString = {"攻击力","暴击","攻击速度","生命偷取"};
    //法术物品类型标题分类字符串
    private String[] mSpellTitleString = {"法术强度","冷却缩减","法力值","法力回复"};
    //移动速度物品类型标题分类字符串
    private String[] mMovingTitleString = {"鞋子","其他移动速度物品"};
    //消耗品物品类型标题分类字符串
    private String[] mConsumableTitleString = {"消耗品"};


    /**
     * 用于SearchActivity物品查询界面的数据
     */
    private List<String> mNameList = new ArrayList<>();     //存放物品Name列表
    private HashMap<String,LOLItem> mGetLOLItemByNameMap = new HashMap<>();

    Document doc;
    private CallBack mCallBack;

    private List<String> mTitleList = new ArrayList<>();            //标题列表;


    public static List<List<List<LOLItem>>> mLists = new ArrayList<>();    //总系列列表(所有物品(所有物品)、防御(生命值、护甲、魔抗、生命回复)、攻击...)

    /**
     * 所有物品类系列
     */
    private List<List<LOLItem>> mAllItemList = new ArrayList<>(); //所有物品列表总类列表
    private List<LOLItem> mItemList = new ArrayList<>();   //所有物品列表

    /**
     * 防御类系列
     */
    private List<List<LOLItem>> mAllDefenseList = new ArrayList<>();         //防御物品总类列表;

    private List<LOLItem> mLifeList = new ArrayList<>();             //生命值物品列表;
    private List<LOLItem> mArmorList = new ArrayList<>();             //护甲物品列表;
    private List<LOLItem> mMagicResistanceList = new ArrayList<>(); //魔法抗性物品列表;
    private List<LOLItem> mLifeRecoveryList = new ArrayList<>();    //生命回复物品列表;

    /**
     * 攻击类系列
     */
    private List<List<LOLItem>> mAllAttackList = new ArrayList<>();             //攻击物品列表;

    private List<LOLItem> mAggressivityList = new ArrayList<>();       //攻击力物品列表;
    private List<LOLItem> mCritList = new ArrayList<>();             //暴击物品列表;
    private List<LOLItem> mAttackSpeedList = new ArrayList<>();     //攻击速度物品列表;
    private List<LOLItem> mLifeStealList = new ArrayList<>();       //生命偷取物品列表;


    /**
     * 法术类系列
     */
    private List<List<LOLItem>> mAllSpellList = new ArrayList<>();             //法术物品列表;

    private List<LOLItem> mSpellPowerList = new ArrayList<>();       //法术强度列表;
    private List<LOLItem> mCoolingReductionList = new ArrayList<>(); //冷却缩减物品列表;
    private List<LOLItem> mManaList = new ArrayList<>();            //法力值物品列表;
    private List<LOLItem> mManaRegenList = new ArrayList<>();       //法力回复物品列表;

    /**
     *移动速度类系列
     */
    private List<List<LOLItem>> mAllMovingSpeedList = new ArrayList<>();             //移动速度物品列表;

    private List<LOLItem> mShoesList = new ArrayList<>();       //鞋子物品列表;
    private List<LOLItem> mOtherMovingSpeedItemsList = new ArrayList<>(); //其他移动速度物品列表;


    /**
     *消耗品
     */
    private List<List<LOLItem>> mAllConsumablesList = new ArrayList<>();
    private List<LOLItem> mConsumablesList = new ArrayList<>();            //消耗品物品列表;




    public LoadItem(CallBack mCallBack){
        this.mCallBack = mCallBack;
        init();
    }

    private void init() {
        /**
         * 分类标题List
         */
        mTitleClassificationList.add(mAllItemTitleString);
        mTitleClassificationList.add(mDefenseTitleString);
        mTitleClassificationList.add(mAttackTitleString);
        mTitleClassificationList.add(mSpellTitleString);
        mTitleClassificationList.add(mMovingTitleString);
        mTitleClassificationList.add(mConsumableTitleString);

    }


    @Override
    protected Void doInBackground(Void... params) {

        /**
         * 获取标题
         */
        try {
            doc = Jsoup.connect(TitleUrl).timeout(5000).post();
            Document document = Jsoup.parse(doc.toString());
            Elements mElements1 = document.select("#seleteCheckItem");
            Document document1 = Jsoup.parse(mElements1.toString());
            Elements mElements2 = document1.getElementsByTag("li");
            for(Element element: mElements2) {
                String title = element.getElementsByTag("label").text();

                mTitleList.add(title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        /**
         * 获取物品数据
         */
        try {
            URL url = new URL(itemUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                int i = 0;
                while((i = is.read()) != -1) {
                    sb.append((char)i);
                }
            }

            String other = "if(!LOLitemjs)var LOLitemjs=";
            sb.delete(0,other.length());

            try {
                JSONObject jsonObject = new JSONObject(sb.toString());
                String data = jsonObject.getString("data");
                JSONObject jsonObject1 = new JSONObject(data);
                Iterator<String> it = jsonObject1.keys();

                String name;        //名称
                String description; //描述
                String plaintext;   //明文
                String[] into;      //能合成物品的键
                String imageUrl;    //图片Url
                String price;       //价格
                String sell;        //回收价格
                String[] tags;      //标签类型

                if(it != null) {
                    while(it.hasNext()) {
                        String mItemStr = jsonObject1.getString(it.next());
                        JSONObject jsonObject2 = new JSONObject(mItemStr);
                        name = jsonObject2.getString("name");
                        mNameList.add(name);
                        description = jsonObject2.getString("description");
                        plaintext = jsonObject2.getString("plaintext");
                        into = null;
                        if(jsonObject2.has("into")) {
                            String mIntoString = jsonObject2.getString("into");
                            JSONArray jsonArray = new JSONArray(mIntoString);

                            into = new String[jsonArray.length()];
                            for(int i = 0;i < jsonArray.length();i++) {
                                into[i] = jsonArray.getString(i);
                            }
                        }

                        String mImageString = jsonObject2.getString("image");
                        JSONObject jsonObject4 = new JSONObject(mImageString);
                        imageUrl = jsonObject4.getString("full");


                        String mGoldString = jsonObject2.getString("gold");
                        JSONObject jsonObject5 = new JSONObject(mGoldString);
                        price = jsonObject5.getString("total");
                        sell = jsonObject5.getString("sell");

                        String mTagsString = jsonObject2.getString("tags");
                        JSONArray jsonArray2 = new JSONArray(mTagsString);
                        tags = new String[jsonArray2.length()];
                        for(int i = 0;i < jsonArray2.length();i++) {
                            tags[i] = jsonArray2.getString(i);
                        }

                        LOLItem item = new LOLItem();
                        item.setName(name);

                        //修改description
                        StringBuilder sb1 = new StringBuilder(description);
                        for(int i = 0;i < sb1.length();i++) {
                            char ch = sb1.charAt(i);
                            if(ch == '<') {
                                StringBuilder sb2 = new StringBuilder();
                                while (i < sb1.length() && ((ch = sb1.charAt(i)))!= '>') {
                                    sb2.append(ch);
                                    sb1.deleteCharAt(i);
                                }
                                if(sb1.charAt(i)== '>') {
                                    sb1.deleteCharAt(i);
                                    sb2.append('>');
                                    if(i > 1) {
                                        i = i -2;
                                    }
                                }
                                if(sb2.toString().equals("<br>")) {
                                    sb1.insert(i+2,'\n');
                                }
                            }

                        }
                        for(int i = 0;i < sb1.length();i++) {
                            char ch = sb1.charAt(i);
                            if(ch == '<') {
                                do {
                                    sb1.deleteCharAt(i);
                                 }while (i < sb1.length() && (sb1.charAt(i))!= '>');
                            }
                            if(sb1.charAt(i) == '>') {
                            sb1.deleteCharAt(i);
                            }

                        }
                        item.setDescription(sb1.toString());
                        item.setPlaintext(plaintext);
                        item.setImageUrl("http://ossweb-img.qq.com/images/lol/img/item/" + imageUrl);
                        item.setInto(into);
                        item.setPrice(price+"");
                        item.setSell(sell);
                        item.setTags(tags);
                        mGetLOLItemByNameMap.put(name,item);

                        /**
                         * tags":["HEALTH","ARMOR","SPELLBLOCK","HEALTHREGEN"]},{"tags":["DAMAGE","CRITICALSTRIKE","ATTACKSPEED","LIFESTEAL"]},{"header":"MAGIC","tags":["SPELLDAMAGE","COOLDOWNREDUCTION","MANA","MANAREGEN"]},{"header":"MOVEMENT","tags":["BOOTS","NONBOOTSMOVEMENT"]}
                         */
                        mItemList.add(item);
                        for(int i = 0;i < tags.length;i++) {

                            switch (tags[i]) {

                                case "Health":
                                    mLifeList.add(item);    //生命值
                                    break;
                                case "Armor":               //护甲
                                    mArmorList.add(item);
                                    break;
                                case "SpellBlock":          //魔法抗性
                                    mMagicResistanceList.add(item);
                                    break;
                                case "HealthRegen":
                                    mLifeRecoveryList.add(item);    //生命回复
                                    break;



                                case "Damage":              //攻击力
                                    mAggressivityList.add(item);
                                    break;


                                case "CriticalStrike":
                                    mCritList.add(item);    //暴击
                                    break;

                                case "AttackSpeed":
                                    mAttackSpeedList.add(item); //攻击速度
                                    break;

                                case "LifeSteal":
                                    mLifeStealList.add(item);   //生命偷取
                                    break;

                                case "SpellDamage":         //法术伤害
                                    mSpellPowerList.add(item);
                                    break;

                                case "CooldownReduction":   //冷却缩减
                                    mCoolingReductionList.add(item);
                                    break;

                                case "Mana":                //法力
                                    mManaList.add(item);
                                    break;

                                case "ManaRegen":
                                    mManaRegenList.add(item);   //法力回复
                                    break;

                                case "Boots":
                                    mShoesList.add(item);       //鞋子
                                    break;

                                case "NonbootsMovement":
                                    mOtherMovingSpeedItemsList.add(item); //其他移动速度物品列表
                                    break;

                                case "Consumable":              //消耗品
                                    mConsumablesList.add(item);
                                    break;
                                default:


                            }
                        }
                    }
                }


                /**
                 * 总类包含各种类(所有物品、攻击类、防御类。。。)
                 */
                mLists.add(mAllItemList);
                mLists.add(mAllDefenseList);
                mLists.add(mAllAttackList);
                mLists.add(mAllSpellList);
                mLists.add(mAllMovingSpeedList);
                mLists.add(mAllConsumablesList);


                /**
                 * 各种类包含各种小类
                 */

                //所有物品
                mAllItemList.add(mItemList);

                //防御
                mAllDefenseList.add(mLifeList);
                mAllDefenseList.add(mArmorList);
                mAllDefenseList.add(mMagicResistanceList);
                mAllDefenseList.add(mLifeRecoveryList);

                //攻击
                mAllAttackList.add(mAggressivityList);
                mAllAttackList.add(mCritList);
                mAllAttackList.add(mAttackSpeedList);
                mAllAttackList.add(mLifeStealList);

                //法术
                mAllSpellList.add(mSpellPowerList);
                mAllSpellList.add(mCoolingReductionList);
                mAllSpellList.add(mManaList);
                mAllSpellList.add(mManaRegenList);

                //移动速度
                mAllMovingSpeedList.add(mShoesList);
                mAllMovingSpeedList.add(mOtherMovingSpeedItemsList);

                //消耗品
                mAllConsumablesList.add(mConsumablesList);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }




    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//            StringBuilder sb = new StringBuilder();
//            sb.append("冷却缩减:" + "\n");
//            for(int i = 0;i < mCoolingReductionList.size();i++) {
//                sb.append(mCoolingReductionList.get(i).getName()+",");
//            }
//            One.textView.setText("也就那么回事" + sb.toString());
        mCallBack.solveItemMap(mGetLOLItemByNameMap);
        mCallBack.solveItemTitle(mTitleList);
        mCallBack.solveSearchItemList(mNameList);
    }
}
