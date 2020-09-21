package coverageJsonFilesUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FunctionGsonType {

    @SerializedName("functionName")
    @Expose
    private String functionName;

    @SerializedName("isBlockCoverage")
    @Expose
    private boolean isBlockCoverage;

    @SerializedName("ranges")
    @Expose
    private List<RangesGsonType> ranges;

    public FunctionGsonType(){

    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public boolean getIsBlockCoverage() {
        return isBlockCoverage;
    }

    public void setIsBlockCoverage(boolean isBlockCoverage) {
        this.isBlockCoverage = isBlockCoverage;
    }

    public List<RangesGsonType> getRanges() {
        return ranges;
    }

    public void setRanges(List<RangesGsonType> ranges) {
        this.ranges = ranges;
    }
}
