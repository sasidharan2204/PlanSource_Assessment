# PlanSource_Assessment

## Overview

This project is an automation framework designed to validate the enrollment process for an employee on the PlanSource benefits platform. It integrates both UI and API automation using Selenium WebDriver and RestAssured.

The framework is developed in Java using TestNG, following modular and scalable design practices.

Test Scenarios Covered

1. Create Employee via UI
2. Add Dependent via UI
3. Enroll Employee in Two Benefits (Medical & Voluntary) via UI
4. Enroll Employee in Dental Benefit via API
5. Download and Verify Confirmation PDF

Project Structure

src - test - java - org.example - EnrollEmployee.java
             resources - Driver - chromdriver.exe

Features Used

#Selenium WebDriver for UI automation
#RestAssured for API testing
#TestNG for test execution and annotations
#Apache PDFBox for PDF validation


Inputs to run

1. Enter the full employee details
2. Enter the dependent details
3. Paste the PDF downloading location


Use java 8 or above.

Set to Run.
