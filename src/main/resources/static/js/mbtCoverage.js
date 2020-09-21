(function () {
    "use strict";
    // this function is strict...
}());

var modelCoverage = "0/0";
var edgeCoverage = "0/0=%0";
var edgeStepNumber = "0";
var vertexCoverage = "0/0=%0";
var vertexStepNumber = "0";
var testTime = "00:00:00.000";
var conditionnn = "Stop";

function getMbtCoverageCondition() {

           readJsonFileee('./js/modelCoverage.json');
           console.log(conditionnn);
           if(conditionnn == "Start"){

                 return true;
           }else{
                return false;
           }

}

function modelsCoverageF(){

    console.log(modelCoverage);
    return modelCoverage;
}

function edgeCoverageF(){

    return edgeCoverage;
}

function edgeStepNumberF(){

    return edgeStepNumber;
}

function vertexCoverageF(){

    return vertexCoverage;
}

function vertexStepNumberF(){

    return vertexStepNumber;
}

function testTimeF(){

    return testTime;
}

function readJsonFileee(fileLocation){

            $.getJSON( fileLocation, function( json ) {

                modelCoverage = json.modelsCoverage;
                edgeCoverage = json.edgeCoverage;
                edgeStepNumber = json.edgeStepNumber;
                vertexCoverage = json.vertexCoverage;
                vertexStepNumber = json.vertexStepNumber;
                testTime = json.testTime;
                conditionnn = json.condition;
            });

             return modelsCoverage;
        }