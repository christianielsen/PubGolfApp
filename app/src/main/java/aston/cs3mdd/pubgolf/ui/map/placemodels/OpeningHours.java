
package aston.cs3mdd.pubgolf.ui.map.placemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//Created using https://www.jsonschema2pojo.org/
public class OpeningHours {

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

}
