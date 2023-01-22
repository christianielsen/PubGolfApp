
package aston.cs3mdd.pubgolf.ui.map.directionsmodels;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//Created using https://www.jsonschema2pojo.org/
public class Polyline {

    @SerializedName("points")
    @Expose
    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

}
