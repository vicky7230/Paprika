
package com.vicky7230.eatit.data.network.model.imagga.tag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

    @SerializedName("confidence")
    @Expose
    private Float confidence;
    @SerializedName("tag")
    @Expose
    private String tag;

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
