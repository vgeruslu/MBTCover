package frontend;

import coverageJava.JavaClientCoverage;
import coverageJava.JavaServerCoverage;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("event: " + event.toString());
        if(event.toString().contains("coverageTime") && event.toString().contains(".json")) {
            //System.out.println("Ready");

            if (event.toString().contains("coverageTime1")) {
                JavaClientCoverage.coverageTime = 1000;
            } else if (event.toString().contains("coverageTime2")) {
                JavaClientCoverage.coverageTime = 2000;
            } else if (event.toString().contains("coverageTime3")) {
                JavaClientCoverage.coverageTime = 3000;
            } else {
                JavaClientCoverage.coverageTime = 2000;
            }

            if (event.toString().contains("coverageTimeStop")) {
                JavaClientCoverage.coverageActive = false;
            } else //if(!JavaClientCoverage.coverageActive)
            {
                JavaClientCoverage.coverageActive = true;
            }
        }else if(event.toString().contains("serverCoverageTime") && event.toString().contains(".json")) {
                //System.out.println("Ready");

                if (event.toString().contains("serverCoverageTime2")) {
                    JavaServerCoverage.serverCoverageTime = 2000;
                } else if (event.toString().contains("serverCoverageTime3")) {
                    JavaServerCoverage.serverCoverageTime = 3000;
                } else if (event.toString().contains("serverCoverageTime4")) {
                    JavaServerCoverage.serverCoverageTime = 4000;
                } else if (event.toString().contains("serverCoverageTime5")) {
                    JavaServerCoverage.serverCoverageTime = 5000;
                } else {
                    JavaServerCoverage.serverCoverageTime = 2000;
                }

                if (event.toString().contains("serverCoverageTimeStop")) {
                    JavaServerCoverage.serverCoverageActive = false;
                } else //if(!JavaClientCoverage.coverageActive)
                {
                    JavaServerCoverage.serverCoverageActive = true;
                }
            }

    }

}
