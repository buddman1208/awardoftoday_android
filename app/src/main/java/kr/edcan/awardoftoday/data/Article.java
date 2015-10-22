package kr.edcan.awardoftoday.data;

/**
 * Created by Junseok on 2015-10-21.
 */
public class Article {
    private String title;
    private dateType dateType;
    private String content;
    private int sticker;
    private String articleKey;
    private boolean waiting;
    private String status;

    public Article(String title, dateType dateType, String content, int sticker, String articleKey, boolean waiting, String status) {
        this.title = title;
        this.content = content;
        this.dateType = dateType;
        this.sticker = sticker;
        this.waiting = waiting;
        this.articleKey = articleKey;
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public int getSticker() {
        return this.sticker;
    }

    public String getArticleKey() {
        return this.articleKey;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean getWaiting(){
        return this.waiting;
    }
    public String getDate() {
        return dateType.year + "년 " + dateType.month + "월 " + dateType.day + "일 " + dateType.hour + ":" + dateType.minute;
    }

}