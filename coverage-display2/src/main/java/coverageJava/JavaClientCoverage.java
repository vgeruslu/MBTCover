package coverageJava;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import coverageJsonFilesUtils.FunctionGsonType;
import coverageJsonFilesUtils.RangesGsonType;
import coverageJsonFilesUtils.ResultGsonType;
import coverageJsonFilesUtils.UrlGsonType;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JavaClientCoverage {

    public static String currentUrl = "";
    public static boolean coverageActive = false;
    public static int coverageTime = 2000;
    public static ConcurrentHashMap<String, CoverageMap> currentUrlMap;
    public static List<String> currentUrlList;
    public static List<String> modelCoverageList = new ArrayList<String>(Arrays
            .asList("0/0,0/0=%0,0,0/0=%0,0,00:00:00.000".split(",")));
    public static String userDir = System.getProperty("user.dir") + "/coverage-display2";


    public static void start() throws IOException, FileNotFoundException {

        try {
            createCoverageFile(0, "Stop");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            createCoverageFile("",0, "Stop");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            createModelCoverageFile(modelCoverageList,"Stop");
        }catch (IOException e){
            e.printStackTrace();
        }
      Thread mainThread = new Thread(){
          public void run(){

              /**try {
                  createCoverageFile(0, "Starting");
              } catch (IOException e) {
                  e.printStackTrace();
              }*/
              while(true) {
                  if (coverageActive) {

                      try {
                          createCoverageFile(0, "Starting");
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                      try {
                          createCoverageFile("",0, "Starting");
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                      ConcurrentHashMap<String, CoverageMap> map = new ConcurrentHashMap<String, CoverageMap>();
                      currentUrlMap = new ConcurrentHashMap<String, CoverageMap>();
                      List<String> urlList = new ArrayList<String>();
                      currentUrlList = new ArrayList<String>();
                      Coverage.startCoverage();
                      MBTModelCoverageSocket.startSocket();
                      System.out.println("deneme");
                      int i = 1;
                      String currentPageUrl = "";
                      while (!Coverage.threadActive[0].equals("FinishCoverage")){

                          if(Integer.parseInt(Coverage.threadCoverageFileCount[0]) >= i){

                              String pageUrl = String.valueOf(Coverage.urlMap.get(String.valueOf(i)));
                              System.out.println("myURL: " + Coverage.urlMap.get(String.valueOf(i)));
                              String fileName = userDir + "/src/main" +
                                      "/resources/coverageJsFiles/coverageJs" + i + ".json";
                              try {
                                  Thread.sleep(100);
                                  readCoverageFile(fileName, map, urlList, pageUrl, currentPageUrl);
                              } catch (FileNotFoundException e) {
                                  System.out.println(fileName + " coverage hesaplanamadı");
                                  e.printStackTrace();
                              } catch (InterruptedException e) {
                                  e.printStackTrace();
                              }
                              try {
                                  createCoverageFile((int) calculateCoverage(map, urlList),"Start");
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }

                              try {
                                  System.out.println("deneme: " + i);
                                  createCoverageFile(pageUrl, (int) calculateCoverage(currentUrlMap, currentUrlList),"Start");
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }

                              try {
                                  File file = new File(fileName);
                                  System.out.println("" + i + ": " + fileName);
                                  Thread.sleep(100);
                                  if (file.exists()) {
                                      file.delete();
                                      System.out.println("file delete: " + i);
                                  }
                              }catch (InterruptedException e) {
                                  e.printStackTrace();
                              }

                              currentPageUrl = pageUrl;
                              i++;
                          }

                          try {
                              Thread.sleep(100);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }


                          /**
                           Coverage Active
                           */
                          if(!coverageActive) {
                              if (Coverage.wsThread.isAlive()) {
                                  Coverage.wsThread.stop();
                              }
                              if (Coverage.thread.isAlive()) {
                                  Coverage.thread.stop();
                              }
                              if (MBTModelCoverageSocket.modelCoverageThread.isAlive()) {
                                  MBTModelCoverageSocket.modelCoverageThread.stop();
                              }
                              Coverage.threadActive[0] = "FinishCoverage";
                          }

                      }
                      try {
                          createCoverageFile(0,"Stop");
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                      try {
                          createCoverageFile("",0,"Stop");
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                      if (MBTModelCoverageSocket.modelCoverageThread.isAlive()) {
                          MBTModelCoverageSocket.modelCoverageThread.stop();
                      }
                      try {
                          createModelCoverageFile(modelCoverageList,"Stop");
                      }catch (IOException e){
                          e.printStackTrace();
                      }
                      coverageActive = false;
                  }
                  try {
                      Thread.sleep(200);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
      };

      mainThread.start();

    }

    public static void createCoverageFile(int coverage, String condition) throws IOException {

        /**Map<String, Object> map = new HashMap<String, Object>();
        map.put("coverage", 50);
        map.put("condition", "Start");*/
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(userDir + "/target/classes/static/js/coverage.json"
                        , false), StandardCharsets.UTF_8));
                //FileWriter("/Users/testinium/Desktop/MBTCover-master/coverage-display/target/classes/static/js/coverage.json");
                //"/Users/testinium/Desktop/MBTCover-master/coverage-display/src/main/resources/static/js/coverage.json");

        CoverageValue coverageValue = new CoverageValue(coverage, condition);
        Gson gson = new Gson();
        gson.toJson(coverageValue, writer);
        writer.close();
    }

    public static void createCoverageFile(String pageUrl, int coverage, String condition) throws IOException {

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(userDir + "/target/classes/static/js/coverageForUrl.json"
                        , false), StandardCharsets.UTF_8));
        //"/Users/testinium/Desktop/MBTCover-master/coverage-display/src/main/resources/static/js/coverage.json");

        System.out.println("coverageFile url: " + pageUrl + " coverage: " + coverage + " condition " + condition);
        CoverageValue coverageValue = new CoverageValue(pageUrl, coverage, condition);
        Gson gson = new Gson();
        gson.toJson(coverageValue, writer);
        writer.close();
    }

    public static long calculateCoverage(ConcurrentHashMap<String, CoverageMap> map, List<String> urlList){

        int total = 0;
        int used = 0;
        for (String url: urlList){
            System.out.println(url);
            System.out.println(map.get(url).getScriptByteLength());
            System.out.println(map.get(url).getUsedByteSet().size());
            if(map.get(url).getScriptByteLength() != 0){
                total += map.get(url).getScriptByteLength();
                used += map.get(url).getUsedByteSet().size();
            }
        }

        double value;
        if(used == 0 || total == 0) {

            value = 0.0;
        }else {

            value = ((double) used) / ((double) total);
        }

        long coveragePercent = Math.round(value*100);
        System.out.println(coveragePercent);
        return coveragePercent;
    }

    public static void readCoverageFile(String fileName, ConcurrentHashMap<String, CoverageMap> map
          , List<String> urlList, String pageUrl, String currentPageUrl) throws FileNotFoundException {

        System.out.println("readCoverage: " + currentPageUrl + " " + pageUrl);
        if(!currentPageUrl.equals(pageUrl)) {

            System.out.println("url farklı");
            currentUrlMap = new ConcurrentHashMap<String, CoverageMap>();
            currentUrlList = new ArrayList<String>();
        }

        File file = new File(fileName);
        Type elementType = new TypeToken<ResultGsonType>(){}.getType();
        Gson gson = new Gson();
        ResultGsonType resultGsonType = gson.fromJson(new FileReader(file), elementType);

        List<UrlGsonType> urlGsonTypeList = resultGsonType.getResult();

        for (UrlGsonType urlGsonType: urlGsonTypeList){

            Set<Integer> unusedSet = new HashSet<Integer>();
            Set<Integer> usedSet = new HashSet<Integer>();
            Set<Integer> usedSetBefore = new HashSet<Integer>();
            String url = urlGsonType.getUrl();
            if (!url.equals("") && !url.contains("__puppeteer_evaluation_script__") && url.endsWith(".js")) {

                if(!map.containsKey(url)){

                    urlList.add(url);
                }
                List<FunctionGsonType> functionGsonTypeList = urlGsonType.getFunctions();
                boolean first = true;
                boolean coverageMode = false;
                int coverageScriptLength = 0;
                for (FunctionGsonType functionGsonType : functionGsonTypeList) {

                    List<RangesGsonType> rangesGsonTypeList = functionGsonType.getRanges();
                    for (RangesGsonType rangesGsonType : rangesGsonTypeList) {
                        if (first) {
                            if (rangesGsonType.getStartOffset() == 0) {
                                coverageScriptLength = rangesGsonType.getEndOffset();
                            }

                            first = false;
                        }
                        if (rangesGsonType.getCount() == 0 && !functionGsonType.getIsBlockCoverage()) {
                            if (coverageScriptLength > 0) {
                                coverageMode = coverageScriptLength >= rangesGsonType.getEndOffset();
                            }
                            for (int i = rangesGsonType.getStartOffset(); i < rangesGsonType.getEndOffset(); i++) {
                                unusedSet.add(i);
                            }

                        }
                        if (functionGsonType.getIsBlockCoverage() && rangesGsonType.getCount() != 0) {
                            if (coverageScriptLength > 0) {
                                coverageMode = coverageScriptLength >= rangesGsonType.getEndOffset();
                            }
                            for (int t = rangesGsonType.getStartOffset(); t < rangesGsonType.getEndOffset(); t++) {
                                usedSetBefore.add(t);
                            }
                        }
                    }
                }

                if (coverageMode) {

                    for (int k = 0; k < coverageScriptLength; k++) {
                        if (!unusedSet.contains(k)) {
                            usedSet.add(k);
                        }
                    }

                 /**   System.out.println("url: " + url);
                    System.out.println("coverageMode: " + coverageMode);
                    System.out.println("Script length: " + coverageScriptLength);
                    System.out.println("Used Size: " + usedSet.size());
                  */

                }else {

                    for (Integer integer: unusedSet) {
                        usedSetBefore.remove(integer);
                    }
                    usedSet.addAll(usedSetBefore);

                   /** System.out.println("url: " + url);
                    System.out.println("coverageMode: " + coverageMode);
                    System.out.println("Script length: " + coverageScriptLength);
                    System.out.println("Used Size: " + usedSet.size());
                    */
                }

                if(map.containsKey(url)) {

                    CoverageMap coverageMap = map.get(url);
                    if (coverageMode) {
                        coverageMap.setScriptByteLength(coverageScriptLength);
                    }
                    coverageMap.setUsedByteSet(usedSet);
                }else {
                    CoverageMap coverageMap = new CoverageMap();
                    coverageMap.setJsUrl(url);
                    if (coverageMode) {
                        coverageMap.setScriptByteLength(coverageScriptLength);
                    }
                    coverageMap.setUsedByteSet(usedSet);
                    map.put(url, coverageMap);
                }


                    if (currentUrlMap.containsKey(url)) {

                        CoverageMap coverageMap = currentUrlMap.get(url);
                        /**
                        if (coverageMode) {
                            coverageMap.setScriptByteLength(coverageScriptLength);
                        }
                         */
                        coverageMap.setScriptByteLength(map.get(url).getScriptByteLength());
                        coverageMap.setUsedByteSet(usedSet);
                    } else {
                        currentUrlList.add(url);
                        CoverageMap coverageMap = new CoverageMap();
                        coverageMap.setJsUrl(url);
                        /**
                        if (coverageMode) {
                            coverageMap.setScriptByteLength(coverageScriptLength);
                        }
                         */
                        coverageMap.setScriptByteLength(map.get(url).getScriptByteLength());
                        coverageMap.setUsedByteSet(usedSet);
                        currentUrlMap.put(url, coverageMap);
                    }

            }
        }
    }

    public static void createModelCoverageFile(List<String> modelCoverageList, String condition) throws IOException {

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream( userDir + "/target/classes/static/js/modelCoverage.json"
                        , false), StandardCharsets.UTF_8));

        ModelCoverageValue modelCoverageValue = new ModelCoverageValue(modelCoverageList, condition);
        Gson gson = new Gson();
        gson.toJson(modelCoverageValue, writer);
        writer.close();
    }

}
