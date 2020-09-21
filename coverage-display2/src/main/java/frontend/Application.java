package frontend;

import coverageJava.JavaClientCoverage;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Ensures spring-boot application ran as main method
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {

        JavaClientCoverage.start();
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new MyApplicationListener());
        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<String, Object>();
        map.put("server.address","127.0.0.1");
        map.put("server.port", "8090");
        app.setDefaultProperties(map);
       // ConfigurableApplicationContext context =
        app.run(args);
        //context.addApplicationListener(new MyApplicationListener());
    }

}
