package qub.ac.uk.models;

import qub.ac.uk.helper.Helper;
import qub.ac.uk.FindOwnersSharedState;

import org.graphwalker.java.annotation.AfterElement;
import org.graphwalker.java.annotation.BeforeElement;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@GraphWalker(value = "random(edge_coverage(100))")
public class FindOwners extends ExecutionContext implements FindOwnersSharedState {

    @Override
    public void v_Owners() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Owners"));
        WebElement table = Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.id("owners")));
        org.junit.Assert.assertTrue(table.findElements(By.xpath("id('owners')/tbody/tr")).size() >= 10);
    }

    @Override
    public void e_AddOwner() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.linkText("Add Owner"))).click();
    }

    @Override
    public void v_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Find Owners"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_Search() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();
    }

    @Override
    public void e_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-search"))).click();
    }

    @Override
    public void v_NewOwner() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "New Owner"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/table/tbody/tr/td[2]/img")));
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
