package coverageJsonFilesUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UrlGsonType {

    @SerializedName("functions")
    @Expose
    private List<FunctionGsonType> functions;

    @SerializedName("scriptId")
    @Expose
    private String scriptId;

    @SerializedName("url")
    @Expose
    private String url;

    public UrlGsonType(){

    }

    public List<FunctionGsonType> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionGsonType> functions) {
        this.functions = functions;
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
