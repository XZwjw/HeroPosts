package first.nestedsliding.modle;

/**
 * Created by dell on 2016/10/26.
 * 皮肤
 */
public class Skin {
    private String bigImageUrl;      //皮肤id（大图片）
    private String name;    //皮肤名称
    private String smallImageUrl;  //小图片

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }
}
