package kr.edcan.awardoftoday.data;

/**
 * Created by Junseok on 2015-10-18.
 */
public class DeveloperInfo {
    private String title;
    private String description;

    public DeveloperInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
}
