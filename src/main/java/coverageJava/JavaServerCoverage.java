package coverageJava;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JavaServerCoverage {

    public static boolean serverCoverageActive = false;
    public static int serverCoverageTime = 2000;
    public static String[] threadWsUrl = {""};
    public static String[] threadCondition = {""};
    public static String[] threadActive = {""};
    public static Thread websocketThread;
    public static int serverCoverage = 0;
    public static String nodeLocation = "/usr/local/opt/node@12/bin/node";
    public static String userDir = System.getProperty("user.dir");

    public static void start() throws IOException, FileNotFoundException {

        /**
         TODO: serverCoverage     jacoco api getCoverage
         */

        try {
            createCoverageFile(0, "Stop");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread mainServerCoverageThread = new Thread() {
            public void run() {

                while(true) {

                    if (serverCoverageActive) {

                        startWs();
                        try {
                            createCoverageFile(0, "Starting");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int i = 0;
                        while (!threadActive[0].equals("FinishCoverage")){

                            if (!threadWsUrl[0].equals("")) {

                                try {
                                    ServerCoverageResponse.authorizaton();
                                    ServerCoverageResponse.getResponseBody();
                                    break;
                                } catch (Exception e) {
                                    if (i == 30){
                                        e.printStackTrace();
                                        break;
                                    }
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    }
                                    i++;
                                }
                            }

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        while (!threadActive[0].equals("FinishCoverage") && (i != 30) ){


                            try {
                                serverCoverage = (int) Math.round(Double.parseDouble(ServerCoverageResponse.getCoverage()));
                            }catch (Exception e){

                            }
                            try {
                                createCoverageFile(serverCoverage,"Start");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                Thread.sleep(serverCoverageTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(!serverCoverageActive) {
                                if (websocketThread.isAlive()) {
                                    websocketThread.stop();
                                }
                                Coverage.threadActive[0] = "FinishCoverage";
                            }
                        }

                        try {
                            createCoverageFile(0, "Stop");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        serverCoverageActive = false;
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mainServerCoverageThread.start();
    }

    public static void startWs(){

        threadWsUrl = new String[]{""};
        threadCondition = new String[]{""};
        threadActive = new String[]{""}
        ;
        websocketThread = new Thread(){
            public void run(){
                try {
                    System.out.println("Thread Running");
                    String[] cmd = {nodeLocation, userDir + "/src/main/resources/websocketUrl2.js"
                            , userDir + "/src/main/resources/"};
                    Process process = Runtime.getRuntime().exec(cmd);
                    InputStream stdout = process.getInputStream();
                    InputStream stdError = process.getErrorStream();
                    OutputStream outputStream = process.getOutputStream();

                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
                    BufferedReader readerError = new BufferedReader(new InputStreamReader(stdError, StandardCharsets.UTF_8));

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                    while((line = reader.readLine()) != null) {
                        //System.out.println("Stdout: " + line);
                        if(line.contains("url:")){
                            threadWsUrl[0] = line.split(" ")[1].trim();
                            System.out.println(threadWsUrl[0]);
                        }

                        if(line.contains("condition:")){
                            // threadCondition[0] = line.replace("condition:","").trim();
                            // System.out.println(threadCondition[0]);
                            threadCondition[0] = line.split(" ")[1].trim();
                            if(threadCondition[0].equals("false")){
                                threadActive[0] = "FinishCoverage";

                                if (websocketThread.isAlive()) {
                                    websocketThread.stop();
                                }
                            }
                            //System.out.println(threadCondition[0]);
                        }
                    }

                    while ((line = readerError.readLine()) != null) {
                        System.out.println("Stdout: " + line);
                    }
                    process.waitFor();
                    System.out.println("exit: " + process.exitValue());
                    bufferedWriter.close();
                    process.destroy();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        websocketThread.start();
    }

    public static void createCoverageFile(int coverage, String condition) throws IOException {

        /**Map<String, Object> map = new HashMap<String, Object>();
         map.put("coverage", 50);
         map.put("condition", "Start");*/
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(userDir + "/target/classes/static/js/serverCoverage.json"
                        , false), StandardCharsets.UTF_8));
        //FileWriter("/Users/testinium/Desktop/MBTCover-master/coverage-display/target/classes/static/js/coverage.json");
        //"/Users/testinium/Desktop/MBTCover-master/coverage-display/src/main/resources/static/js/coverage.json");

        CoverageValue coverageValue = new CoverageValue(coverage, condition);
        Gson gson = new Gson();
        gson.toJson(coverageValue, writer);
        writer.close();
    }

}
