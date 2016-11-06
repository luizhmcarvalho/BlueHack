package travelfi.com.net.bluehack.models;

import android.content.Context;

import java.util.HashMap;

import travelfi.com.net.bluehack.helpers.bluemix.cloudant.CloudantAuth;

/**
 * Created by luiz on 05/11/16.
 */

public class UserProfile {

    private static UserProfile _sharedInstance;
    private long id;
    private String userName;
    private String name;
    private String email;
    private String city;
    private String birthDate;


    public static UserProfile getInstance() {
        if(_sharedInstance == null)
            _sharedInstance = new UserProfile();
        return _sharedInstance;
    }

    public boolean saveToCloudant(Context context) {
        try {
            CloudantAuth.getInstance(context).saveObject("user_profile", this.getMap());
            return true;
        }
        catch (Exception ex){
            return  false;
        }
    }

    private HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", getId());
        map.put("userName", getUserName());
        map.put("name", getName());
        map.put("city",getCity());
        map.put("birthDate",getBirthDate());
        return map;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
