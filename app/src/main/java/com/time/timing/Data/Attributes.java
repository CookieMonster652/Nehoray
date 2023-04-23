
package com.time.timing.Data;

import com.google.gson.annotations.SerializedName;


public class Attributes {

    @SerializedName("age")
    private Long mAge;
    @SerializedName("body")
    private String mBody;
    @SerializedName("created")
    private String mCreated;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("name")
    private String mName;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updated")
    private String mUpdated;

    public Long getAge() {
        return mAge;
    }

    public void setAge(Long age) {
        mAge = age;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

}
