package coverageJava;

import java.util.List;

public class ModelCoverageValue {

    public String modelsCoverage = "0/0";

    public String edgeCoverage = "0/0=%0";

    public String edgeStepNumber = "0";

    public String vertexCoverage = "0/0=%0";

    public String vertexStepNumber = "0";

    public String testTime = "00:00:00.000";

    public String condition = "Stop";

    public ModelCoverageValue(String modelsCoverage, String edgeCoverage, String edgeStepNumber
            , String vertexCoverage, String vertexStepNumber, String testTime, String condition){

        this.modelsCoverage = modelsCoverage;
        this.edgeCoverage = edgeCoverage;
        this.edgeStepNumber = edgeStepNumber;
        this.vertexCoverage = vertexCoverage;
        this.vertexStepNumber = vertexStepNumber;
        this.testTime = testTime;
        this.condition = condition;
    }

    public ModelCoverageValue(List<String> modelCoverageList, String condition){
        this.modelsCoverage = modelCoverageList.get(0);
        this.edgeCoverage = modelCoverageList.get(1);
        this.edgeStepNumber = modelCoverageList.get(2);
        this.vertexCoverage = modelCoverageList.get(3);
        this.vertexStepNumber = modelCoverageList.get(4);
        this.testTime = modelCoverageList.get(5);
        this.condition = condition;

    }

}
