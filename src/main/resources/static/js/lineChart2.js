(function () {
    "use strict";
    // this function is strict...
}());

var coverageValuee = 0;
var conditionn = "";
var pageUrl = "";
var currentUrl = "";
var coveragee = 0;

		var configg = {
			type: 'line',
			data: {
			labels: [],
			datasets: []
			},
			options: {
			    legend: {
                             display: false,
                        },
                elements: {
                             line: {
                                       tension: 0 // disable curves
                                   }
                          },
				responsive: true,
				title: {
					display: true,
					text: 'JS Coverage % per web page'
				},
				tooltips: {
					mode: 'index',
					intersect: false,
				},
				hover: {
					mode: 'nearest',
					intersect: true
				},
				scales: {
					xAxes: [{
						display: true,
            scaleLabel: {
							display: true,
							labelString: 'Time'
						}
					}],
					yAxes: [{
						display: true,
						ticks: {
                                //beginAtZero: true,
                                min: 0,
                                steps: 10,
                                //stepValue: 5,
                                max: 100
                            },
						scaleLabel: {
							display: true,
							labelString: 'Js Coverage Percent'
						}
					}]
				}
			}
		};

		function draw2() {
			var ctx = document.getElementById('canvas2').getContext('2d');
			window.myLine2 = new Chart(ctx, configg);
			readJsonFile2('./js/coverageForUrl.json');
		};

		function randomizeDataa() {
			configg.data.datasets.forEach(function(dataset) {
				dataset.data = dataset.data.map(function() {
					return randomScalingFactor();
				});

			});

			window.myLine2.update();
		}

		var colorNamess = Object.keys(window.chartColors);

		function addDatasett(labelName) {
			var colorName = colorNamess[configg.data.datasets.length % colorNamess.length];
			var newColor = window.chartColors[colorName];
			var newDataset = {
				label: labelName,
				backgroundColor: newColor,
				borderColor: newColor,
				data: [],
				fill: false
			};

            var labelsLength = configg.data.labels.length;
			for (var index = 0; index < labelsLength; ++index) {
			    if(index == (labelsLength - 1)){
				newDataset.data.push(0);
				} else {
				newDataset.data.push(null);
				}
			}

			configg.data.datasets.push(newDataset);
			window.myLine2.update();
		}

		function addDataa() {
			if (configg.data.datasets.length > 0) {
                readJsonFile2('./js/coverageForUrl.json');

                console.log(conditionn);
                 if(conditionn == "Start"){
				//i = i+2;
				configg.data.labels.push(//new Date(i*1000).toISOString().substr(11, 8)
				new Date().toLocaleTimeString('en-GB', { hour12: false,
                                             hour: "numeric",
                                             minute: "numeric",
                                             second: "numeric",
                                             millisecond: "numeric"})
                                             );

				configg.data.datasets.forEach(function(dataset) {
				   // coverage =

				    if (isNaN(coverageValuee)) {
                                coverageValue = 0;
                            } else if (coverageValue < 0) {
                                coverageValue = 0;
                            } else if (coverageValue > 100) {
                                coverageValue = 100;
                            }
					dataset.data.push(coverageValuee);//randomScalingFactor());

				});

				window.myLine2.update();
				}
			}
		}

		function addDatasetAndData() {

        		//	if (configg.data.datasets.length > 0) {
                        readJsonFile2('./js/coverageForUrl.json');
                        console.log(currentUrl);
                        console.log(pageUrl);
                        console.log(conditionn);
                        if(conditionn == "Start"){
                        if(currentUrl !== pageUrl){
                        console.log(true);
                        addDatasett(pageUrl);
                        }

        				configg.data.labels.push(//new Date(i*1000).toISOString().substr(11, 8)
        				new Date().toLocaleTimeString('en-GB', { hour12: false,
                                                     hour: "numeric",
                                                     minute: "numeric",
                                                     second: "numeric",
                                                     millisecond: "numeric"})
                                                     );

        			//	configg.data.datasets.forEach(function(dataset) {

                    let t = configg.data.datasets.length;
                    for(let j = 0; j < t; j++){
                          if(j == (t-1)){
        				    if (isNaN(coverageValuee)) {
                                        coverageValue = 0;
                                    } else if (coverageValue < 0) {
                                        coverageValue = 0;
                                    } else if (coverageValue > 100) {
                                        coverageValue = 100;
                                    }
        					configg.data.datasets[j].data.push(coverageValuee);
        					}else{
        					configg.data.datasets[j].data.push(null);
        					}
        					}

        			//	});
                        currentUrl = pageUrl;
        				window.myLine2.update();
        				}
        			//}
        		}

		function removeDatasett() {
			configg.data.datasets.splice(0, 1);
			window.myLine2.update();
		}

		function removeAllDatasett() {

		        let t = configg.data.datasets.length;
                for(let j = 0; j < t; j++){

        		 configg.data.datasets.splice(0, 1);
        		}

        		window.myLine2.update();
        }

		function removeDataa() {
			configg.data.labels.splice(-1, 1); // remove the label first

			configg.data.datasets.forEach(function(dataset) {
				dataset.data.pop();
			});

			window.myLine2.update();
		}

		function removeAllDataa() {
		console.log(configg.data.labels.length);
		let t = configg.data.labels.length;
		for(let j = 0; j < t; j++){
        			configg.data.labels.splice(-1, 1); // remove the label first
        			console.log(configg.data.labels.length);
        			}

        			configg.data.datasets.forEach(function(dataset) {
        				dataset.data.pop();
        			});

                    coverageValuee = 0;
                    conditionn = "Stop";
                    pageUrl = "";
                    currentUrl = "";
                    coveragee = 0;
        			window.myLine2.update();
        		}

		function readJsonFile2(fileLocation){

            $.getJSON( fileLocation, function( json ) {

                pageUrl = json.pageUrl;
                coverageValuee = json.coverage;
                conditionn = json.condition;
            });

             console.log( "JSON Data received: " + coverageValuee);
             return coverageValuee;
        }