package coverageJsonFilesUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultGsonType {

    @SerializedName("result")
    @Expose
    private List<UrlGsonType> result;

    @SerializedName("timestamp")
    @Expose
    private double timestamp;

    public ResultGsonType(){

    }

    public List<UrlGsonType> getResult() {
        return result;
    }

    public void setResult(List<UrlGsonType> result) {
        this.result = result;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}
