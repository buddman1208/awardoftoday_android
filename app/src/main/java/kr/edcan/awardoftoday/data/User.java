package kr.edcan.awardoftoday.data;

/**
 * Created by Junseok on 2015-10-11.
 */
public class User {
    public String id;
    public String password;
    public String name;
    public String apikey;
    public String targetApikey;
    public boolean isParent;
    public User(String id, String password, String name, String apikey, String targetApikey,  boolean isParent){
        this.id =id;
        this.password = password;
        this.name = name;
        this.apikey = apikey;
        this.targetApikey = targetApikey;
        this.isParent = isParent;
    }
}
