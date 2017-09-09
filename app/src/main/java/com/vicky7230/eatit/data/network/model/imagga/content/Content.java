
package com.vicky7230.eatit.data.network.model.imagga.content;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("uploaded")
    @Expose
    private List<Uploaded> uploaded = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Uploaded> getUploaded() {
        return uploaded;
    }

    public void setUploaded(List<Uploaded> uploaded) {
        this.uploaded = uploaded;
    }

}
