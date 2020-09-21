package coverageJava;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoverageMap {

    private String jsUrl = "";

    private int scriptByteLength = 0;

    private Set<Integer> usedByteSet = new HashSet<Integer>();

    public CoverageMap(){

    }

    public String getJsUrl() {
        return jsUrl;
    }

    public void setJsUrl(String jsUrl) {
        this.jsUrl = jsUrl;
    }

    public int getScriptByteLength() {
        return scriptByteLength;
    }

    public void setScriptByteLength(int scriptByteLength) {
        this.scriptByteLength = scriptByteLength;
    }

    public Set<Integer> getUsedByteSet() {
        return usedByteSet;
    }

    public void setUsedByteSet(Set<Integer> usedByteSet) {
        this.usedByteSet.addAll(usedByteSet);
    }
}
