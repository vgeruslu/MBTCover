package frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import coverage.ReportGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Controller
/**
 * This is the controller class for the web application where the majority of the logic lays.
 */
public class ApplicationController {
    /**
     *
     * @param clientCoverage - percentage of coverage of back-end code from most recent graphwalker run
     * @param serverCoverage - percentage of coverage of front-end code from most recent graphwalker run
     * @param model - allows us to add values to thymeleaf
     * @return - returns a given webpage from the templates folder located in our resources
     */
    //Mapping depending on 'coverage' routing
    @GetMapping("")
    public String index(@RequestParam(value="percent", required=false, defaultValue="0") String clientCoverage, String serverCoverage, Model model) {
        //Retrieve values of client and server-side code coverage
        clientCoverage = "0";//ReportGenerator.getClientCoverage();
        serverCoverage = "0";//ReportGenerator.getServerCoverage();
        //Adds values to thymeleaf so that they can be sent to the webpage
        //model.addAttribute("clientPercent", clientCoverage);

       // model.addAttribute("clientPercent", 10);
       // model.addAttribute("serverPercent", serverCoverage);
        //Checking if values are ok - if not, return error page.
        if ( ((clientCoverage == null) || (clientCoverage == "NaN")) || ((serverCoverage == null) || (serverCoverage == "NaN")) ) {
            return "Error";
        } else {
            return "Index";
        }
    }

    /**
     *
     * @param reqCoverage - percentage of the requirements that were covered within the most recent graphwalker run
     * @param model - allows us to add values to thymeleaf to be sent to webpage
     * @return - returns a given webpage from the templates folder located in our resources
     */
    @GetMapping("/requirements")
    public String req(@RequestParam(value="requirements", required=false, defaultValue="0") String reqCoverage, Model model) {
        //Retrieve value from backend
        reqCoverage = "0";//ReportGenerator.getRequirementCoverage();
        //Add value to thymeleaf model
        // model.addAttribute("requirements", reqCoverage);
       // model.addAttribute("teemo", "0");
        //Error checking
        if ((reqCoverage == null) || (reqCoverage == "NaN")) {
            return "Error";
        } else {
            return "Requirements";
        }
    }

    @GetMapping("/serverCoverage")
    public String serverCoverage(@RequestParam(value="serverCoverage", required=false, defaultValue="0") String serverCoverage, Model model) {
        //Retrieve value from backend
        serverCoverage = "0";
        //Add value to thymeleaf model

        //Error checking
        if ((serverCoverage == null) || (serverCoverage == "NaN")) {
            return "Error";
        } else {
            return "ServerCoverage";
        }
    }

}