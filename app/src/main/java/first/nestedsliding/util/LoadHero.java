package first.nestedsliding.util;

import android.os.AsyncTask;

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
import first.nestedsliding.modle.LOLHero;

/**
 * Created by dell on 2016/9/28.
 * LOL英雄加载类(各个英雄的简单信息)
 */
public class LoadHero extends AsyncTask<String,String,String> {
    private final static String url = "http://lol.qq.com/web201310/info-heros.shtml#Navi";   //获取标题的url;
    private final static String heroUrl = "http://lol.qq.com/biz/hero/champion.js"; //英雄头像接口


    /**
     * 用于英雄查询界面的数据
     */
    private List<String> mIdList = new ArrayList<>();     //存放英雄Id列表
    private List<String> mKeyList = new ArrayList<>();     //存放英雄Key列表
    private List<String> mNameList = new ArrayList<>();     //存放英雄Name列表
    private HashMap<String,String> mGetIdMap = new HashMap<>(); //获取id的HashMap


    Document doc;
    CallBack callBack;



    private List<String> mListTitle = new ArrayList<>();         //标题列表
    private List<LOLHero> mAllHeroList = new ArrayList<>();      //所有英雄列表
    private List<LOLHero> mWarriorList = new ArrayList<>();      //战士英雄列表
    private List<LOLHero> mMasterHeroList = new ArrayList<>();   //法师英雄列表
    private List<LOLHero> mAssassinHeroList = new ArrayList<>(); //刺客英雄列表
    private List<LOLHero> mTankHeroList = new ArrayList<>();     //坦克英雄列表
    private List<LOLHero> mShooterList = new ArrayList<>();      //射手英雄列表
    private List<LOLHero> mAuxiliaryList = new ArrayList<>();    //辅助英雄列表

    public static List<List<LOLHero>> lists = new ArrayList<>();



    public LoadHero(CallBack callBack) {

        this.callBack = callBack;
    }


    @Override
    protected String doInBackground(String... params) {


        //获取标题
        try {
            doc = Jsoup.connect(url).timeout(5000).post();
            Document document = Jsoup.parse(doc.toString());
            Elements element = document.select("#seleteChecklist");
            Document document1 = Jsoup.parse(element.toString());
            Elements elements = document1.getElementsByTag("li");

            for(Element links : elements) {

                String title = links.getElementsByTag("label").text();
                mListTitle.add(title);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取英雄
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(heroUrl).openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                int i = 0;
                while((i = is.read()) != -1  ) {
                    sb.append((char)i);
                }
                String string = "if(!LOLherojs)var LOLherojs={};LOLherojs.champion=";   //JSON数据多余的字符
                sb.delete(0, string.length());
                try {


                    JSONObject jsonObject = new JSONObject(sb.toString());
                    String str;
                    str = jsonObject.getString("data");         //得到所有英雄数据
                    JSONObject jsonObject1 = new JSONObject(str);       //所有英雄的JSON数据
                    Iterator<String> it = jsonObject1.keys();           //创建迭代器迭代所有键
                    if(it != null) {
                        while(it.hasNext()) {
                            String str1 = jsonObject1.getString(it.next());
                            JSONObject jsonObject2 = new JSONObject(str1);      //单个英雄的JSON数据
                            /**
                             * 根据key获取想要的属性值。。。。。。。。。。。。。。。
                             * id "Aatrox"
                             * image ...
                             * key "266"
                             * name "暗裔剑魔"
                             * tags ...
                             * title "亚托克斯"
                             */


                            String photoString = jsonObject2.getString("image");
                            JSONObject jsonObject3 = new JSONObject(photoString);
                            String photo = jsonObject3.getString("full");

                            String id = jsonObject2.getString("id");
                            mIdList.add(id);
                            String key = jsonObject2.getString("key");
                            mKeyList.add(key);
                            String name = jsonObject2.getString("name");
                            mNameList.add(name);
                            mGetIdMap.put(key,id);
                            mGetIdMap.put(name,id);
                            String title = jsonObject2.getString("title");

                            /**
                             * 英雄分类标签
                             */
                            String tags = jsonObject2.getString("tags");
                            JSONArray array = new JSONArray(tags);
                            StringBuilder sb3 = new StringBuilder();
                            for(int j=0; j < array.length() - 1;j++) {
                                if(j + 1 == array.length()) {
                                    sb3.append(array.getString(j));
                                } else {
                                    sb3.append(array.getString(j) +"、");
                                }
                            }
                            String tag = sb3.toString();

                            LOLHero lolHero = new LOLHero();
                            lolHero.setId(id);
                            lolHero.setName(name);
                            lolHero.setTitle(title);
                            lolHero.setKey(key);
                            lolHero.setPhotoUri("http://ossweb-img.qq.com/images/lol/img/champion/" + photo);
                            lolHero.setTag(tag);


                            /**
                             *
                             */

                            mAllHeroList.add(lolHero);
                            for(int k = 0; k <array.length();k++) {

                                switch ((String)array.get(k)) {
                                    case "Tank":
                                        mTankHeroList.add(lolHero);     //坦克
                                        break;
                                    case "Mage":
                                        mMasterHeroList.add(lolHero);   //法师
                                        break;
                                    case "Support":
                                        mAuxiliaryList.add(lolHero);    //辅助
                                        break;
                                    case "Fighter":
                                        mWarriorList.add(lolHero);      //战士
                                        break;
                                    case "Assassin":
                                        mAssassinHeroList.add(lolHero); //刺客
                                        break;
                                    case "Marksman":
                                        mShooterList.add(lolHero);      //射手
                                        break;
                                }
                            }

                        }
                    }

                    lists.add(mAllHeroList);
                    lists.add(mWarriorList);
                    lists.add(mMasterHeroList);
                    lists.add(mAssassinHeroList);
                    lists.add(mTankHeroList);
                    lists.add(mShooterList);
                    lists.add(mAuxiliaryList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callBack.solveHeroId(mGetIdMap);
        callBack.solveHeroTitle(mListTitle);
        callBack.solveSearchHeroList(mIdList,mKeyList,mNameList);
    }

}
