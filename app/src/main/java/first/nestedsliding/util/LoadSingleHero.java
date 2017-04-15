package first.nestedsliding.util;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import first.nestedsliding.activity.DisConnectionActivity;
import first.nestedsliding.fragment.lol.LOlFragment;
import first.nestedsliding.itf.SingleHeroCallBack;
import first.nestedsliding.modle.Info;
import first.nestedsliding.modle.LOLHero;
import first.nestedsliding.modle.Passive;
import first.nestedsliding.modle.Skill;
import first.nestedsliding.modle.Skin;
import first.nestedsliding.modle.Spell;

/**
 * Created by dell on 2016/10/26.
 * 加载单个英雄的详细信息
 *
 * 单个英雄界面：http://lol.qq.com/biz/hero/Aatrox.js

 英雄购买与英雄视频：http://lol.qq.com/web201310/js/herovideo.js

 皮肤大图片:http://ossweb-img.qq.com/images/lol/web201310/skin/big266000.jpg
 皮肤小图片:http://ossweb-img.qq.com/images/lol/web201310/skin/small266000.jpg
 主动技能图片:http://ossweb-img.qq.com/images/lol/img/spell/AatroxQ.png
 被动技能图片:http://ossweb-img.qq.com/images/lol/img/passive/Aatrox_Passive.png
 */
public class LoadSingleHero extends AsyncTask<Void,Void,Void>{

    private String mHeroUrl = "http://lol.qq.com/biz/hero/";    //后面加上英雄.js即可
    private String skinUrl = "http://ossweb-img.qq.com/images/lol/web201310/skin/";  //后面加上id.jpg即可
    private String spellUrl = "http://ossweb-img.qq.com/images/lol/img/spell/";
    private String passiveUrl = "http://ossweb-img.qq.com/images/lol/img/passive/";

    private String mVideoUrl = "http://lol.qq.com/web201310/js/herovideo.js";

    private LOLHero mLOLHero = new LOLHero();
    private SingleHeroCallBack mCallBack;
    public static String id;

    public LoadSingleHero(SingleHeroCallBack callBack,String id){
        this.id = id;
        mLOLHero.setId(id);
        mHeroUrl += (id+".js");
        mCallBack = callBack;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {


            HttpURLConnection connection = (HttpURLConnection) new URL(mHeroUrl).openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                int i = 0;
                while ((i = reader.read()) != -1) {
                    sb.append((char)i);
                }
                String Other = "if(!LOLherojs)var LOLherojs={champion:{}};LOLherojs.champion."+mLOLHero.getId()+"=";
                sb.delete(0,Other.length());
                try {
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    /**
                     * 解析JSON数据
                     */
                    String data = jsonObject.getString("data");
                    JSONObject jsonObject1 = new JSONObject(data);

                    //key
                    mLOLHero.setKey(jsonObject1.getString("key"));

                    //名称
                    mLOLHero.setName(jsonObject1.getString("name"));
                    //标题
                    mLOLHero.setTitle(jsonObject1.getString("title"));

                    //类别
                    String tagString = jsonObject1.getString("tags");
                    JSONArray jsonArray = new JSONArray(tagString);
                    StringBuilder sb1 = new StringBuilder();
                    for(int j = 0;j < jsonArray.length();j++) {
                        if(j == (jsonArray.length() - 1))
                            sb1.append(jsonArray.getString(j));
                        else {
                            sb1.append(jsonArray.getString(j)+"、");
                        }
                    }

                    mLOLHero.setTag(sb1.toString());
                    //图片
                    String s = jsonObject1.getString("image");
                    JSONObject jO = new JSONObject(s);
                    mLOLHero.setPhotoUri(jO.getString("full"));

                    //皮肤
                    String skins = jsonObject1.getString("skins");
                    JSONArray jsonArray1 = new JSONArray(skins);
                    Skin[] skinsArray = new Skin[jsonArray1.length()];
                    for (int j = 0;j < jsonArray1.length();j++) {
                        String str = jsonArray1.getString(j);
                        JSONObject jsonObject2 = new JSONObject(str);
                        String name = jsonObject2.getString("name");
                        if(name.equals("default")) {    //default为原始皮肤
                            name = mLOLHero.getName();
                        }
                        Skin skin = new Skin();
                        skin.setName(name);
                        skin.setBigImageUrl(skinUrl + "big" + jsonObject2.getString("id") + ".jpg");
                        skin.setSmallImageUrl(skinUrl + "small" + jsonObject2.getString("id") + ".jpg");
                        skinsArray[j] = skin;
                    }

                    mLOLHero.setSkins(skinsArray);

                    //英雄属性
                    String infoString = jsonObject1.getString("info");
                    JSONObject jsonObject2 = new JSONObject(infoString);
                    Info info = new Info();

                    info.setAttack(jsonObject2.getString("attack"));
                    info.setDefense(jsonObject2.getString("defense"));
                    info.setMagic(jsonObject2.getString("magic"));
                    info.setDifficulty(jsonObject2.getString("difficulty"));
                    mLOLHero.setInfo(info);


                    //技能
                    String spellsString = jsonObject1.getString("spells");
                    JSONArray jsonArray2 = new JSONArray(spellsString);
                    Spell[] spells = new Spell[jsonArray2.length()];
                    for(int j = 0; j < jsonArray2.length();j++) {
                        String spellString = jsonArray2.getString(j);
                        JSONObject jsonObject3 = new JSONObject(spellString);
                        Spell spell = new Spell();
                        spell.setId(jsonObject3.getString("id"));
                        spell.setName(jsonObject3.getString("name"));
                        spell.setDescription(jsonObject3.getString("description"));

                        String str = jsonObject3.getString("image");
                        JSONObject jsonObject4 = new JSONObject(str);
                        spell.setImageUrl(spellUrl+jsonObject4.getString("full"));


                       

                        //修改description
//                        StringBuilder topic = new StringBuilder(jsonObject3.getString("tooltip"));
//                        for(int k = 0;k < topic.length();k++) {
//                            char ch = topic.charAt(k);
//                            if(ch == '<') {
//                                StringBuilder sb2 = new StringBuilder();
//                                while (k < topic.length() && ((ch = topic.charAt(k)))!= '>') {
//                                    sb2.append(ch);
//                                    topic.deleteCharAt(k);
//                                }
//                                if(topic.charAt(k)== '>') {
//                                    topic.deleteCharAt(k);
//                                    sb2.append('>');
//                                    if(k > 1) {
//                                        k = k -2;
//                                    }
//                                }
//                            }
//
//                        }
                        





                        spell.setTooltip(removeSymbol(jsonObject3.getString("tooltip"),false));

                        String str2 = jsonObject3.getString("leveltip");
                        JSONObject jsonObject5 = new JSONObject(str2);
                        String str3 = jsonObject5.getString("label");
                        String str4 = jsonObject5.getString("effect");

                        JSONArray jsonArray3 = new JSONArray(str3);
                        String[] labelArray = new String[jsonArray3.length()];
                        for(int k=0;k< jsonArray3.length();k++) {
                            labelArray[k] = jsonArray3.getString(k);
                        }
                        spell.setLabel(labelArray);

                        JSONArray jsonArray4 = new JSONArray(str4);
                        String[] effectArray = new String[jsonArray4.length()];
                        for(int k=0;k< jsonArray4.length();k++) {
                            effectArray[k] = jsonArray4.getString(k);
                        }
                        spell.setEffect(effectArray);
                        spell.setResource(jsonObject3.getString("resource"));
                        spells[j] = spell;


                    }
                    mLOLHero.setSpells(spells);

                    //被动
                    String psString = jsonObject1.getString("passive");
                    JSONObject jsonObject3 = new JSONObject(psString);
                    Passive passive = new Passive();

                    passive.setName(jsonObject3.getString("name"));

                    String str = jsonObject3.getString("image");
                    JSONObject jsonObject4 = new JSONObject(str);
                    passive.setImageUrl(passiveUrl+jsonObject4.getString("full"));

                    passive.setDescription(jsonObject3.getString("description"));
                    mLOLHero.setPassive(passive);

                    //背景故事(长)
                    mLOLHero.setLore(removeSymbol(jsonObject1.getString("lore"),true));

                    //背景故事(短)
                    mLOLHero.setBlurb(removeSymbol(jsonObject1.getString("blurb"),true));

                    /**
                     * 使用技巧
                     */
                    Skill skill = new Skill();
                    //自己使用
                    String string = jsonObject1.getString("allytips");
                    JSONArray jsonArray3 = new JSONArray(string);
                    StringBuilder sb3 = new StringBuilder();
                    for(int j =0; j < jsonArray3.length();j++) {
                        sb3.append(jsonArray3.getString(j)+"\n");
                    }
                    skill.setAllytips(sb3.toString());

                    //敌人使用
                    String string1 = jsonObject1.getString("enemytips");
                    JSONArray jsonArray4 = new JSONArray(string1);
                    StringBuilder sb4 = new StringBuilder();
                    for(int j =0; j < jsonArray4.length();j++) {
                        sb4.append(jsonArray4.getString(j)+"\n");
                    }
                    skill.setEnemytips(sb4.toString());
                    mLOLHero.setSkill(skill);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            /**
             * 获取英雄视频网址
             */
            getHeroUrl(id);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void getHeroUrl(String id) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(mVideoUrl).openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                StringBuilder sb = new StringBuilder();
                int i;
                while ((i = reader.read()) != -1) {
                    sb.append((char)i);
                }
                String other = "if (!LOLherojs) var LOLherojs = {\n" +
                        "    champion: {}\n" +
                        "};\n" +
                        "LOLherojs.otherthings = ";
                sb.delete(0,other.length());
                try {
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    String data = jsonObject.getString("data");
                    JSONObject jsonObject1 = new JSONObject(data);
                    String singleData = jsonObject1.getString(id);
                    JSONObject jsonObject2 = new JSONObject(singleData);
                    String link = jsonObject2.getString("vodlink");
                    mLOLHero.setUsingVideo(link);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(mLOLHero != null && LOlFragment.HAVE_NETWORK) {

            mCallBack.assignment(mLOLHero);

        } else {
            Intent intent = new Intent((Context) mCallBack, DisConnectionActivity.class);

            ((Context) mCallBack).startActivity(intent);
        }
        super.onPostExecute(aVoid);
    }

    private String removeSymbol(String string,boolean wrap) {
        StringBuilder sb = new StringBuilder(string);
        for(int k = 0;k < sb.length();k++) {
            char ch = sb.charAt(k);
            if(ch == '<') {
                StringBuilder sb2 = new StringBuilder();
                while (k < sb.length() && ((ch = sb.charAt(k)))!= '>') {
                    sb2.append(ch);
                    sb.deleteCharAt(k);
                }
                if(sb.charAt(k)== '>') {
                    sb.deleteCharAt(k);
                    sb2.append('>');
                    if(k > 1) {
                        k = k -2;
                    }
                }
                if(sb2.toString().equals("<br>")&&wrap) {
                    sb.insert(k+2,'\n');
                }
            }

        }


        return sb.toString();
    };
}
