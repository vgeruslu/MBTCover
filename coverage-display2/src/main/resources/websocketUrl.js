const puppeteer = require('puppeteer');
// Include to be able to export files w/ node
const fs = require('fs');

(async () => {
    const fileLocation = process.argv[2];
    const browser = await puppeteer.launch();
    const page = await browser.newPage();

    var testStart = false;
    // Begin collecting CSS coverage data

    // Visit desired page
    for(var i = 0; i < 30; i++){
    try{
        await page.goto('http://127.0.0.1:9222/json/version');
        testStart = true;
        break;
    }catch(e){

        testStart = false;
    }
        await page.waitFor(1000);
    }

    if(testStart){
    const wsInfo = await page.$x("//html/body/pre");
    const getTheProperty = await wsInfo[0].getProperty(
      'textContent'
    );
    const getRecord = getTheProperty._remoteObject.value;
    console.info(getRecord);
    var url = JSON.parse(getRecord).webSocketDebuggerUrl;
    console.info("url: " + url);
    const fileName = fileLocation +
    'websocketUrl.json';
    fs.writeFile(fileName, '{"url":"' + url + '"}', function(err) {
        if(err) {
            return console.log(err);
        }
        console.log("The file was saved!");
    });

    while(true){
            try{
                    await page.goto('http://127.0.0.1:9222/json/version');
                    testStart = true;
                    console.info("condition: " + testStart);
                }catch(e){

                    testStart = false;
                    break;
                }
                    await page.waitFor(100);
                }
                }
    console.info("condition: " + testStart);
    await browser.close();
})();