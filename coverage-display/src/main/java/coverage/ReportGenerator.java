package coverage;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This class holds all of the utility methods to retrieve coverage statistics from other components of system
 */
public class ReportGenerator {

    /**
     * This method retrieves the percentage of code covered on the client-side by the most recent graphwalker run
     * @return - percentage of code coverage on client-side
     */
    public static String getClientCoverage() {
        //Initialise
        String percent = null;
        //Open file
        File coverage = new File("../graphwalker-petclinic/java-petclinic/client_coverage.txt");
        try {
            //Read
            FileInputStream fis = new FileInputStream(coverage);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                percent = line;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Post-processing
        double dPercent = Double.parseDouble(percent);
        double rounded = Math.round(dPercent);
        int iPercent = (int) rounded;
        percent = Integer.toString(iPercent);
        return percent;
    }

    /**
     * This method retrieves the percentage of code covered on the server-side by the most recent graphwalker run
     * @return - percentage of code covered on server-side
     */
    public static String getRequirementCoverage() {
        //Initialise
        String requirements = null;
        //Open file
        File result = new File("../graphwalker-petclinic/java-petclinic/log.txt");
        try{
            //Read
            FileInputStream fis = new FileInputStream(result);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            //Look for specified line
            while((line = br.readLine()) != null){
                requirements = line;
                if (requirements.contains("requirementCoverage")) {
                    break;
                }
            }
            //Post-processing
            String[] aRequirements = requirements.split(":");
            requirements = aRequirements[1].replace(" ", "").replace(",", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requirements;
    }

    /**
     * This method retrieves the percentage of requirements covered in the most recent graphwalker run
     * @return - percentage of requirements covered
     */
    public static String getServerCoverage() {
        //Initialise
        String coverage = "";
        try {
            //Open file
            File input = new File("../spring-petclinic/coverage/index.html");
            //Use JSOUP to parse the html in the file
            Document doc = Jsoup.parse(input, "UTF-8", "");
            //Strip HTML tags etc
            String text = doc.text();
            //Post-processing
            String[] content = text.split("Total");
            content = content[1].split("%");
            coverage = content[0].substring(content[0].length()-2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coverage;
    }

}
