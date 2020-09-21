package coverageJava;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class Coverage {

    public static String[] threadWsUrl = {""};
    public static String[] threadCondition = {""};
    public static String[] threadCoverageFileCount = {"0"};
    public static String[] threadActive = {""};
    public static Thread wsThread;
    public static Thread thread;
    public static ConcurrentHashMap<String,Object> urlMap;
    public static String nodeLocation = "/usr/local/opt/node@12/bin/node";
    public static String userDir = System.getProperty("user.dir") + "/coverage-display2";

    public static void startCoverage(){

        urlMap = new ConcurrentHashMap<String, Object>();
        threadWsUrl = new String[]{""};
        threadCondition = new String[]{""};
        threadCoverageFileCount = new String[]{"0"};
        threadActive = new String[]{""};

    wsThread = new Thread(){
            public void run(){
       try {
            System.out.println("Thread Running");
            String[] cmd = {nodeLocation, userDir + "/src/main/resources/websocketUrl.js"
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
                          if (thread.isAlive()) {
                              thread.stop();
                          }
                          if (wsThread.isAlive()) {
                              wsThread.stop();
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

    wsThread.start();

    thread = new Thread(){
        public void run(){
            System.out.println("Thread 2 Running");
            while (wsThread.isAlive() && threadWsUrl[0].equals("")){//wsThread.isAlive()){

                /**if(threadCondition[0].equals("true")){
                    break;
                }*/

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(threadWsUrl[0].equals("")){
                threadActive[0] = "FinishCoverage";
                System.out.println("thread 2");
                System.out.println("thread 2 " + threadCondition[0]);
                if (wsThread.isAlive()) {
                    wsThread.stop();
                }
                if (thread.isAlive()) {
                    thread.stop();
                }
            }

            try {

                String[] cmdd = {nodeLocation, userDir + "/src/main/resources/coverage.js"
                        , threadWsUrl[0], userDir + "/src/main/resources/coverageJsFiles/"
                , "" + JavaClientCoverage.coverageTime};
                Process process = Runtime.getRuntime().exec(cmdd);
                InputStream stdout = process.getInputStream();
                InputStream stdError = process.getErrorStream();
                OutputStream outputStream = process.getOutputStream();

                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));

                BufferedReader readerError = new BufferedReader(new InputStreamReader(stdError, StandardCharsets.UTF_8));

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                while ((line = reader.readLine()) != null) {

                    //System.out.println("Stdout: " + line);

                    if(line.contains("Ready")){
                        threadActive[0] = "y";
                        String value = threadCondition[0].equals("true") ? "y" : "n";
                        bufferedWriter.write(value + "\n");
                        bufferedWriter.flush();
                    }
                    if (line.contains("coverageFileCount:")){

                        threadCoverageFileCount[0] = line.replace("coverageFileCount:","").trim();
                        urlMap.put("threadCoverageFileCount", threadCoverageFileCount[0]);
                    }

                    if (line.contains("url:")){

                        urlMap.put(String.valueOf(Integer.parseInt(threadCoverageFileCount[0]) + 1), line.replace("url:","").trim());
                    }

                    if(line.contains("FinishCoverage")){
                        threadActive[0] = "FinishCoverage";
                        System.out.println(urlMap.toString());
                        if (wsThread.isAlive()) {
                            wsThread.stop();
                        }
                        if (thread.isAlive()) {
                            thread.stop();
                        }
                    }
                }

                while ((line = readerError.readLine()) != null) {
                    System.out.println("Stdout: " + line);
                }
                System.out.println("deneme");
                process.waitFor();
                System.out.println("exit2: " + process.exitValue());
                bufferedWriter.close();
                process.destroy();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    thread.start();
    }


}
