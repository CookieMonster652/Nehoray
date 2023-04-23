
package com.time.timing.Data;

import com.google.gson.annotations.SerializedName;


public class Relationships {

    @SerializedName("author")
    private Author mAuthor;

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

}
