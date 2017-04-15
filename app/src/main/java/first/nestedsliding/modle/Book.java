package first.nestedsliding.modle;

/**
 * Created by dell on 2016/10/8.
 */
public class Book {

    private String name;    //书名
    private String author;  //作者
    private String briefIntroduction;   //简介
    private String bookImageUri; //书图片uri

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public String getBookImageUri() {
        return bookImageUri;
    }

    public void setBookImageUri(String bookImageUri) {
        this.bookImageUri = bookImageUri;
    }
}

