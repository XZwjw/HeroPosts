package first.nestedsliding.modle;

/**
 * Created by dell on 2016/10/26.
 * 英雄属性分析,单方面属性最大为10
 */
public class Info {
    private String attack;  //物理攻击
    private String defense;  //魔法攻击
    private String magic;  //防御能力
    private String difficulty;  //上手难度

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
