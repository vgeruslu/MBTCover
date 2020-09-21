package coverageJsonFilesUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RangesGsonType {

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("endOffset")
    @Expose
    private int endOffset;

    @SerializedName("startOffset")
    @Expose
    private int startOffset;

    public RangesGsonType(){

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }
}
