package first.nestedsliding.modle;

/**
 * Created by dell on 2016/9/23.
 *
 * 根据key获取想要的属性值。。。。。。。。。。。。。。。
 * id "Aatrox"
 * image ...
 * key "266"
 * name "暗裔剑魔"
 * tags ...
 * title "亚托克斯"
 */

public class LOLHero {
    private String id;          //英雄id
    private String key;         //英雄键
    private String name;        //姓名
    private String title;       //英雄标签
    private String price;       //价格
    private String photoUri;    //图片
    private String tag;         //类型标签
    private Spell[] spells;     //英雄技能
    private Passive passive;    //被动
    private Skin[] skins;       //皮肤
    private Info info;          //英雄属性
    private String lore;        //背景故事(全文)
    private String blurb;       //背景故事(略文)
    private Skill skill;        //使用技巧
    private String usingVideo;     //视频网址


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Spell[] getSpells() {
        return spells;
    }

    public void setSpells(Spell[] spells) {
        this.spells = spells;
    }

    public Passive getPassive() {
        return passive;
    }

    public void setPassive(Passive passive) {
        this.passive = passive;
    }

    public Skin[] getSkins() {
        return skins;
    }

    public void setSkins(Skin[] skins) {
        this.skins = skins;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }


    public String getUsingVideo() {
        return usingVideo;
    }

    public void setUsingVideo(String usingVideo) {
        this.usingVideo = usingVideo;
    }
}
