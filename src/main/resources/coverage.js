const puppeteer = require('puppeteer');
//var args = process.argv.slice(2);

(async () => {
const wsChromeEndpointurl = process.argv[2];
const fileLocation = process.argv[3];
var coverageTime = parseInt(process.argv[4]);
//const fileLocation = './coverageJsFiles/';
const browser = await puppeteer.connect({
   browserWSEndpoint: wsChromeEndpointurl,
    defaultViewport: null,
    //devtools: false
});

  const readline = require('readline');
  var fs = require('fs');

    function askQuestion(query) {
          const rl = readline.createInterface({
              input: process.stdin,
              output: process.stdout,
          });

          return new Promise(resolve => rl.question(query, ans => {
              rl.close();
              resolve(ans);
          }))
      }

  const page = (await browser.pages())[0];
  //await page.setViewport({ width: 0, height: 0 });

  // Starts to gather coverage information for JS and CSS files
  //await Promise.all([page.coverage.startJSCoverage(), page.coverage.startCSSCoverage()]);
  //await page.waitForSelector('title');

  const session = await page.target().createCDPSession();
  await session.send('Profiler.enable');
  await session.send('Page.enable');
  await session.send('Profiler.startPreciseCoverage', {
                                                             callCount: true,
                                                             detailed: true,
                                                             allowTriggeredUpdates: false,
                                                            });
  await session.send('Debugger.enable');
  await session.send('Debugger.setSkipAllPauses', { skip: true });

  var i = 0;

  var condition = true;
  while(condition){

      await page.waitFor(coverageTime);
      console.info('Ready');
      const ans = await askQuestion("Are you sure?: ");
      console.info(ans);
      if(ans !== 'y'){
      condition = false;
          break;
       }
   var pageLength = (await browser.pages()).length;

     if(pageLength !== 0){

     const response = await session.send('Profiler.takePreciseCoverage');
     console.info(pageLength);
     ++i;

     console.info('url: ' + page.url());
     /**const responseResult = response.result;
     var lengthValue = responseResult.length;
     console.info(lengthValue);
     for(var j = 0; j < lengthValue; j++){
   // console.info(responseResult[j].url);
    const value = "" + responseResult[j].scriptId;
    const url = "" + responseResult[j].url;
    //console.info(value);
     if(url !== "" && url !== "__puppeteer_evaluation_script__"){
     const deneme = await session.send('Debugger.getScriptSource', {
                              scriptId: value,
                             });

                             console.info(url);
                             console.info("jsCode: " + JSON.stringify(deneme).length);
                             }
                             }*/

     const fileName = fileLocation + 'coverageJs' + i + '.json';
     fs.writeFileSync(fileName, JSON.stringify(response));
     console.info('coverageFileCount: ' + i);
     condition = true;
    }else{
             condition = false;
           }
  }

  if(condition){
    await session.send('Profiler.stopPreciseCoverage');
    await session.send('Profiler.disable');
    await session.send('Debugger.disable');
    }
  console.info("FinishCoverage");

})();