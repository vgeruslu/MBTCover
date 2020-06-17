package qub.ac.uk.models;

import qub.ac.uk.PetClinicSharedState;
import qub.ac.uk.helper.Helper;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterElement;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeElement;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@GraphWalker(value = "random(edge_coverage(100))", start = "e_StartBrowser")
public class PetClinic extends ExecutionContext implements PetClinicSharedState {

    @BeforeExecution
    public void setup() {
        Helper.setup();
    }

    @AfterExecution
    public void cleanup() {
        Helper.tearDown();

        //Client-side code coverage script.
        String s;
        try {
            String[] cmd = {"/usr/bin/node", "./src/coverage/coverage.js"};
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            PrintWriter writer = new PrintWriter("./src/coverage/coverage.txt", "UTF-8");
            writer.println(s = br.readLine());
            writer.close();
            System.out.println();
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void v_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Find Owners"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_HomePage() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-home"))).click();
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-home"))).click();
    }

    @Override
    public void e_Veterinarians() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-th-list"))).click();
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-th-list"))).click();
    }

    @Override
    public void v_Veterinarians() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Veterinarians"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table[last()]/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-search"))).click();
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-search"))).click();
    }

    @Override
    public void v_HomePage() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Welcome"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_StartBrowser() {
        Helper.getActiveInstance().get("http://localhost:9966/petclinic/");
    }

    @BeforeElement
    public void printBeforeElement() {
        System.out.println("Before element " + getCurrentElement().getName());
        Helper.pause(125);
    }

    @AfterElement
    public void printAfterElement() {
        System.out.println("After element " + getCurrentElement().getName());
        Helper.pause(125);
    }

}
