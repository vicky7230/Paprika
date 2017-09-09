
package com.vicky7230.eatit.data.network.model.imagga.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Uploaded {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("filename")
    @Expose
    private String filename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
