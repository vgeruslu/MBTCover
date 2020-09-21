package coverageJava;


public class CoverageValue {

    public String pageUrl;
    public int coverage;
    public String condition;

    public CoverageValue(int coverage, String condition){

        this.coverage = coverage;
        this.condition = condition;
    }

    public CoverageValue(String pageUrl, int coverage, String condition){

        this.pageUrl = pageUrl;
        this.coverage = coverage;
        this.condition = condition;
    }

}
