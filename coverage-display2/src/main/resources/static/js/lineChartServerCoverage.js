(function () {
    "use strict";
    // this function is strict...
}());

var coverageValue = 0;
var condition = "";
var coverage = 0;
		var config = {
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
					text: 'Server Coverage'
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
							labelString: 'Server Coverage Percent'
						}
					}]
				}
			}
		};

		function draw() {
			var ctx = document.getElementById('canvas').getContext('2d');
			window.myLine = new Chart(ctx, config);
			readJsonFile('./js/serverCoverage.json');
		};

		function randomizeData() {
			config.data.datasets.forEach(function(dataset) {
				dataset.data = dataset.data.map(function() {
					return randomScalingFactor();
				});

			});

			window.myLine.update();
		}

		var colorNames = Object.keys(window.chartColors);

		function addDataset() {
			var colorName = colorNames[config.data.datasets.length % colorNames.length];
			var newColor = window.chartColors[colorName];
			var newDataset = {
				//label: 'Dataset ' + config.data.datasets.length,
				label: 'Total Coverage',
				backgroundColor: newColor,
				borderColor: newColor,
				data: [],
				fill: false
			};

			/**for (var index = 0; index < config.data.labels.length; ++index) {
			   // if(index > 5){
				newDataset.data.push(randomScalingFactor());
			//	} else {
			//	newDataset.data.push(null);
			//	}
			}*/

			config.data.datasets.push(newDataset);
			window.myLine.update();
		}

		function addData() {
			if (config.data.datasets.length > 0) {
                readJsonFile('./js/serverCoverage.json');
                console.log(condition);
                 if(condition == "Start"){

				config.data.labels.push(//new Date(i*1000).toISOString().substr(11, 8)
				new Date().toLocaleTimeString('en-GB', { hour12: false,
                                             hour: "numeric",
                                             minute: "numeric",
                                             second: "numeric",
                                             millisecond: "numeric"})
                                             );

				config.data.datasets.forEach(function(dataset) {
				   // coverage =

				    if (isNaN(coverageValue)) {
                                coverageValue = 0;
                            } else if (coverageValue < 0) {
                                coverageValue = 0;
                            } else if (coverageValue > 100) {
                                coverageValue = 100;
                            }
					dataset.data.push(coverageValue);//randomScalingFactor());

				});

				window.myLine.update();
				}
			}
		}

		function removeDataset() {
			config.data.datasets.splice(0, 1);
			window.myLine.update();
		}

		function removeData() {
			config.data.labels.splice(-1, 1); // remove the label first

			config.data.datasets.forEach(function(dataset) {
				dataset.data.pop();
			});

			window.myLine.update();
		}

		function removeAllData() {
		console.log(config.data.labels.length);
		let t = config.data.labels.length;
		for(let j = 0; j < t; j++){
        			config.data.labels.splice(-1, 1); // remove the label first
        			console.log(config.data.labels.length);
        			}

        			config.data.datasets.forEach(function(dataset) {
        				dataset.data.pop();
        			});

                    coverageValue = 0;
                    condition = "Stop";
                    coverage = 0;
        			window.myLine.update();
        		}

		function readJsonFile(fileLocation){

            $.getJSON( fileLocation, function( json ) {

                coverageValue = json.coverage;
                condition = json.condition;
            });

             console.log( "JSON Data received: " + coverageValue);
             return coverageValue;
        }

        function readJsonFilee(fileLocation){

                    $.getJSON( fileLocation, function( json ) {

                       json.condition;
                    });

                }