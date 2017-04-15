package first.nestedsliding.modle;

/**
 * Created by dell on 2016/10/26.
 * 英雄技能类
 */
public class Spell {
    private String id;          //技能id,eg:"AatroxQ"
    private String name;        //名称
    private String description; //描述
    private String imageUrl;    //英雄技能图片
    private String tooltip;     //技能提醒
    private String[] label;     //技能标签;eg:伤害、冷却时间
    private String[] effect;//伤害对应字符串、冷却时间对应字符串等等
    private String resource;    //消耗资源；eg:消耗当前10%生命值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public String[] getEffect() {
        return effect;
    }

    public void setEffect(String[] effect) {
        this.effect = effect;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
