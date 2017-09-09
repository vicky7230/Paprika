
package com.vicky7230.eatit.data.network.model.imagga.tag;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("tagging_id")
    @Expose
    private String taggingId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;

    public String getTaggingId() {
        return taggingId;
    }

    public void setTaggingId(String taggingId) {
        this.taggingId = taggingId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
