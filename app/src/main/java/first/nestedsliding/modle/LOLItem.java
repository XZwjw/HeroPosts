package first.nestedsliding.modle;

import java.io.Serializable;

/**
 * Created by dell on 2016/10/13.
 * LOL物品类
 */
public class LOLItem implements Serializable{
    private String name;    //名称
    private String description; //描述
    private String plaintext;   //明文
    private String[] into;      //可合成的物品对应的键（单个物品LOLItem对应的键,其值为这个物品的所有数据）
    private String imageUrl;    //图片url
    private String price;        //价格
    private String sell;        //出售价格
    private String[] tags;         //类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String[] getInto() {
        return into;
    }

    public void setInto(String[] into) {
        this.into = into;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
