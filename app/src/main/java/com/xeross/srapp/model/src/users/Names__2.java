
package com.xeross.srapp.model.src.users;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Names__2 {

    @SerializedName("international")
    @Expose
    private String international;
    @SerializedName("japanese")
    @Expose
    private Object japanese;

    public String getInternational() {
        return international;
    }

    public void setInternational(String international) {
        this.international = international;
    }

    public Object getJapanese() {
        return japanese;
    }

    public void setJapanese(Object japanese) {
        this.japanese = japanese;
    }

}
