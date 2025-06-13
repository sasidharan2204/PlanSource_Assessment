package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class EnrollEmployee {

    public static WebDriver driver;
    // Employee Detail
    static String firstName="Sasi";
    static String lastName="Ramadass";
    static String SSN="777-777-8899";
    static String address="No.18, Chruch Street,";
    static String city="Ohio";
    static String state="Alabama";
    static String zip="90000";
    static String country="United States";
    static String DOB="12/19/2003"; //mm/dd/YYYY
    static String gender="M"; //M or //F
    static String Marital_status="Married";
    static String hireDate="06/19/2025";
    static String employementType="F"; //F or //P
    static String currentSalary="450.00"; //0.00
    static String benefitSalary="800.00"; //0.00

    // Dependent Detail
    static String firstNameD="Angel";
    static String lastNameD="Louis";
    static String DOB_D="07/10/1993";
    static String genderD="Female"; //Female or //Male
    static String relationshipD="spouse"; //Spouse //child

    static String PDFlocation=""; //c:\\Downloads\\

    static long startTime=System.nanoTime();



    @BeforeClass
    public static void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/Driver/chromedriver.exe");

        driver=new ChromeDriver();
        driver.get("https://partner-dev-benefits.plansource.com/");
        driver.manage().window().maximize();
        System.out.println("========================Run Start Here====================");
        System.out.println("");
    }

    @Test(priority = 1)
    public void login(){
        //Username
        driver.findElement(By.id("user_name")).sendKeys("plansource_test_admin");
        //Password
        driver.findElement(By.id("password")).sendKeys("password123");
        //Click on login
        driver.findElement(By.id("logon_submit")).click();

        System.out.println("Login Successfully");
    }

    @Test(priority = 2)
    public void createEmployee(){
        driver.findElement(By.xpath("//a[@class='action'][contains(text(),'Add a New Employee')]")).click();

        driver.findElement(By.id("password")).sendKeys("Abb334gth#");
        // First Name
        driver.findElement(By.id("first_name")).sendKeys(firstName);
        // Last Name
        driver.findElement(By.id("last_name")).sendKeys(lastName);
        // SSN
        driver.findElement(By.id("ssn_text")).sendKeys(SSN);
        // Address 1
        driver.findElement(By.id("address_1")).sendKeys(address);
        // City
        driver.findElement(By.id("city")).sendKeys(city);
        // State
        driver.findElement(By.id("stateTypeahead")).sendKeys(state);
        // ZIP
        driver.findElement(By.id("zip_code")).sendKeys(zip);
        // Country
        driver.findElement(By.id("countryTypeahead")).sendKeys(country);
        // DOB
        driver.findElement(By.id("birthdate")).sendKeys(DOB);
        // Gender
        Select genderList=new Select(driver.findElement(By.id("gender")));
        genderList.selectByValue(gender);
        // Marital Status
        Select maritalList=new Select(driver.findElement(By.id("marital_status")));
        maritalList.selectByVisibleText(Marital_status);
        // Hire date
        driver.findElement(By.id("hire_date")).sendKeys(hireDate);
        // Start date
        driver.findElement(By.id("benefits_start_date")).sendKeys(hireDate);
        // Employement Type
        Select employeeTypeList=new Select(driver.findElement(By.id("employment_level")));
        employeeTypeList.selectByValue(employementType);
        // Location
        Select locationList=new Select(driver.findElement(By.id("location")));
        locationList.selectByIndex(1);
        // Current Salary
        driver.findElement(By.id("current_salary")).sendKeys(currentSalary);
        // Benefit Salary
        driver.findElement(By.id("benefit_salary")).sendKeys(benefitSalary);

        // Save btn
        driver.findElement(By.id("btn_save")).click();

        System.out.println("Employee Details Updated");

    }

    @Test(priority = 3)
    public void addDependents(){

        try {

            Thread.sleep(5000);
            driver.findElement(By.xpath("//a[contains(text(),'New Hire Enrollment')]")).click();


            // Get Started
            Thread.sleep(5000);
            driver.findElement(By.id("enrollmentStepOne")).click();


            // Next Review my family
            Thread.sleep(5000);
            driver.findElement(By.id("submit_form")).click();


            // Add Family Member
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            WebElement addFamily = driver.findElement(By.xpath("//a[contains(text(),'Add Family Member')]"));
            wait.until(ExpectedConditions.elementToBeClickable(addFamily)).click();

            wait.until(ExpectedConditions.titleIs("New Dependent - Testing Plansource Client Benefits"));
            String currentTitle = driver.getTitle();
            if (currentTitle.contains("New Dependent")) {
                driver.findElement(By.id("first_name")).sendKeys(firstNameD);
                driver.findElement(By.id("last_name")).sendKeys(lastNameD);
                Select GenderList = new Select(driver.findElement(By.id("gender")));
                GenderList.selectByVisibleText(genderD);
                driver.findElement(By.id("birthdate")).sendKeys(DOB_D);
                Select relantionship = new Select(driver.findElement(By.id("relationship")));
                relantionship.selectByValue(relationshipD);
                driver.findElement(By.id("submit_form")).click();

                System.out.println("Dependent Details Updated");


            }
        }catch (NoSuchElementException e){
            System.out.println("Element Not found in Dependent section: "+e.getMessage());
        }
        catch (Exception e) {
            Assert.fail("Unexpected Error in Dependent Section: "+e.getMessage());
        }

    }

    @Test(priority = 4)
    public void enrollBenefits(){

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

            Thread.sleep(3000);
            driver.findElement(By.xpath("//button/span[contains(text(),'Next: Shop for Benefits')]")).click();


            //Get all Shop buttons & click Voluntary Plan
            List<WebElement> totalPlans = driver.findElements(By.xpath("//a[contains(text(),'Shop Plans')]"));
            totalPlans.get(5).click();


            wait.until(ExpectedConditions.titleIs("Voluntary Employee Life - Testing Plansource Client Benefits"));

            String title = driver.getTitle();

            if (title.contains("Voluntary Employee Life - Testing Plansource Client Benefits")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 1000);");
                List<WebElement> dropDownList = driver.findElements(By.xpath("//span[contains(text(),'Select Amount')]"));
                dropDownList.get(0).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("//a/span[@class='text'][contains(text(),'$10,000.00 ($0.35)')]")).click();
                //div[@class='dropdown-menu open show']/ul/li/a/span[contains(text(),'$10,000.00 ($0.40)')]
                driver.findElement(By.id("updateCartBtn")).click();
                Thread.sleep(2000);
                driver.findElement(By.cssSelector(".animate-ripple-out")).click();
                System.out.println("Voluntary Plan Selected");
            }

            wait.until(ExpectedConditions.titleIs("Medical - Testing Plansource Client Benefits"));
            title = driver.getTitle();
            if (title.contains("Medical")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 1000);");
                WebElement uptBtn=driver.findElement(By.id("updateCartBtn"));
                wait.until(ExpectedConditions.elementToBeClickable(uptBtn)).click();
                System.out.println("Medical Plan Selected");

                wait.until(ExpectedConditions.titleIs("Survey 11 - Testing Plansource Client Benefits"));
                if (driver.getTitle().contains("Survey 11")) {
                    driver.findElement(By.xpath("//label[@class='radio']/span[contains(text(),'Yes')]")).click();
                    driver.findElement(By.id("next")).click();
                    driver.findElement(By.id("submit_form")).click();

                }
                wait.until(ExpectedConditions.titleIs("Consider enrolling in a Health Savings Account - Testing Plansource Client Benefits"));
                JavascriptExecutor js1 = (JavascriptExecutor) driver;
                js1.executeScript("window.scrollBy(0, 1000);");
                WebElement uptBtn2 = driver.findElement(By.id("updateCartBtn"));
                wait.until(ExpectedConditions.elementToBeClickable(uptBtn2)).click();
                System.out.println("HSA Updated");
            }

        }
        catch (NoSuchElementException e){
            System.out.println("Element Not found in Benefits: "+e.getMessage());
        }
        catch (ElementClickInterceptedException e){
            System.out.println("Element Not Clickable in Benefits: "+e.getMessage());
        }
        catch (StaleElementReferenceException e){
            System.out.println("Not Visible in DOM in Benefits: "+e.getMessage());
        }
        catch (Exception e){
            Assert.fail("Unexpected Error in Benefits: "+e.getMessage());
        }
    }

    @Test(priority = 5)
    public void enrollDentalviaAPI(){

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            wait.until(ExpectedConditions.titleIs("Shop for Dental Insurance - Testing Plansource Client Benefits"));

            if (driver.getTitle().contains("Dental")) {
                Cookie sessionCookie = driver.manage().getCookieNamed("_session_id");
                String session_id_value = sessionCookie.getValue();

                // Referer header value
                String referer = "https://partner-dev-benefits.plansource.com/subscriber/benefit/121137668";

                // Base URI
                RestAssured.baseURI = "https://partner-dev-benefits.plansource.com";

                // JSON Body as a Map
                Map<String, Object> jsonBody = new HashMap<>();
                Map<String, Object> election = new HashMap<>();
                election.put("coverage_level_id", 137);
                election.put("dependent_ids", new int[]{}); // empty array
                election.put("org_plan_id", 319901188);
                jsonBody.put("election", election);
                jsonBody.put("enrollment_context_type", "initial");
                jsonBody.put("include_benefits_in_response", true);
                jsonBody.put("include_related_coverage_changes", true);
                jsonBody.put("life_event_completed", false);
                jsonBody.put("org_benefit_id", 121137668);
                // String JSONBody="{'election': {'coverage_level_id': 137, 'dependent_ids': [], 'org_plan_id': 319901188},'enrollment_context_type': 'initial', 'include_benefits_in_response': true,'include_related_coverage_changes': true, 'life_event_completed': false, 'org_benefit_id':121137668}";

                Response res = given()
                        .relaxedHTTPSValidation()
                        .cookie("_session_id", session_id_value)
                        .header("Content-Type", "application/json")
                        .header("Referer", referer)
                        .body(jsonBody)
                        .when()
                        .put("/v1/self_service/coverages");

                int status = res.statusCode();


                driver.findElement(By.cssSelector(".btn-link.back-nav")).click();
                Thread.sleep(2000);
                System.out.println("Dental Plan Updated via API :" + status);
            }
        }
        catch (Exception e){
            Assert.fail("Exception during API Call: "+e.getMessage());
        }
    }


    @Test(priority = 6)
    public void downloadPDF(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

            WebElement toggleBtn = driver.findElement(By.id("toggleNavMenu"));
            wait.until(ExpectedConditions.elementToBeClickable(toggleBtn)).click();

            driver.findElement(By.xpath("//a[@data-submenu-key='admin']")).click();
            driver.findElement(By.xpath("//a[@href='/subscriber/benefits/current/review?earlyCheckout=true']")).click();
            driver.findElement(By.xpath("//button[@type='submit']/span[contains(text(),'Checkout')]")).click();
            wait.until(ExpectedConditions.titleIs("Enrollment Complete - Current Benefit Elections - Testing Plansource Client Benefits"));

            driver.findElement(By.cssSelector(".btn.btn-link.shown-while-editing")).click();
            System.out.println("Dowloaded PDF Succesfully");
            Thread.sleep(5000); // wait for PDF to download
        }
        catch (NoSuchElementException e){
            System.out.println("Element not found in Checkout: "+e.getMessage());
        }
        catch (Exception e){
            Assert.fail("Unexpected Error in Checkout: "+e.getMessage());
        }
    }

    @Test(priority = 7)
    public void verifyPDF() throws IOException {
        File file=new File(PDFlocation+"confirmation.pdf");
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        if(text.contains("Medical") && text.contains("Dental") && text.contains("Voluntary Employee Life")){
            System.out.println("Verified PDF Successfully");
        }
        else {
            System.out.println("Some Plans missed");
        }
    }


    @AfterClass
    public void tearDown(){
        driver.close();
        driver.quit();
        long endTime=System.nanoTime();
        long totalTime=TimeUnit.SECONDS.convert((endTime-startTime),TimeUnit.NANOSECONDS);
        System.out.println("Total time: "+totalTime+" in Sec");
    }

}
