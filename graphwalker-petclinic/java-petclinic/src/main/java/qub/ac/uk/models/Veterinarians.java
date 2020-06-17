package qub.ac.uk.models;

import qub.ac.uk.VeterinariansSharedState;
import qub.ac.uk.helper.Helper;

import org.graphwalker.java.annotation.AfterElement;
import org.graphwalker.java.annotation.BeforeElement;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@GraphWalker(value = "random(edge_coverage(100))")
public class Veterinarians extends ExecutionContext implements VeterinariansSharedState {

    @Override
    public void e_Search() {
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=\"search\"]"))).clear();
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=\"search\"]"))).sendKeys("helen");
    }

    @Override
    public void v_SearchResult() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.xpath("//table[@id='vets']/tbody/tr/td"), "Helen Leary"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table[last()]/tbody/tr/td[2]/img")));
    }

    @Override
    public void v_Veterinarians() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Veterinarians"));
        WebElement table = Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.id("vets")));
        org.junit.Assert.assertTrue(table.findElements(By.xpath("id('vets')/tbody/tr")).size() >= 1);
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
