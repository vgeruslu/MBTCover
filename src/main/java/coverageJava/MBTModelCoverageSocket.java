package coverageJava;

import com.google.gson.Gson;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MBTModelCoverageSocket {

    public static Thread modelCoverageThread;
    public static String userDir = System.getProperty("user.dir");

    public static void startSocket(){

        modelCoverageThread = new Thread() {
            public void run() {
                Socket s = null;
                for (int i = 0; i < 300; i++) {
                    try {
                        s = new Socket("localhost", 3333);
                        break;
                    } catch (Exception e) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e1) {

                        }
                    }
                }
                if (s != null) {
                    try {

                        DataInputStream din = new DataInputStream(s.getInputStream());
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

                        String str = "Get Model Coverage", str2 = "";
                        while (!str2.equals("stop") && JavaClientCoverage.coverageActive) {
                            //str = br.readLine();
                            dout.writeUTF(str);
                            dout.flush();
                            str2 = din.readUTF();
                            System.out.println("Server says: " + str2);
                            String[] coverageArray = str2.split(",");
                            List<String> list = new ArrayList<String>();
                            list.add(coverageArray[0].replace("modelsCoverage:","").trim());
                            list.add(coverageArray[1].replace("calledModelEdgeCoverage:","").trim()
                                    + "=%" + coverageArray[2].replace("calledModelEdgeCoverageDouble:","").trim());
                            list.add(coverageArray[3].replace("edgeStepNumber:","").trim());
                            list.add(coverageArray[4].replace("calledModelVertexCoverage:","").trim()
                                    + "=%" + coverageArray[5].replace("calledModelVertexCoverageDouble:","").trim());
                            list.add(coverageArray[6].replace("vertexStepNumber:","").trim());
                            list.add(coverageArray[7].replace("TestTime:","").trim());
                            createModelCoverageFile(list,"Start");

                            Thread.sleep(JavaClientCoverage.coverageTime);
                        }

                        dout.close();
                        s.close();
                    }catch (IOException | InterruptedException e){

                    }
                }

            }
        };

        modelCoverageThread.start();
        }

    public static void createModelCoverageFile(List<String> modelCoverageList, String condition) throws IOException {

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(userDir + "/target/classes/static/js/modelCoverage.json"
                        , false), StandardCharsets.UTF_8));

        ModelCoverageValue modelCoverageValue = new ModelCoverageValue(modelCoverageList, condition);
        Gson gson = new Gson();
        gson.toJson(modelCoverageValue, writer);
        writer.close();
    }
    }
