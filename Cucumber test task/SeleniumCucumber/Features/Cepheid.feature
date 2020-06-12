Feature: Login to the Test Application to modify Employee data

 Scenario: Login with Valid Credentials parameter
    Given User login with username "Luke" and password "Skywalker"
    Then login should be successful

  Scenario: Login with InValid Credentials parameter
    Given User login with username "Amy" and password "Skywalker"
    Then login should fail

  Scenario: Check logout Functionality
    Given User login with username "Luke" and password "Skywalker"
    When User clicks logout button
    Then Navigate to homepage

  Scenario: Create a new employee profile with valid details
    Given User login with username "Luke" and password "Skywalker"
    When Open create employee wrapper
    Then Provide details and add employee
    And Validate addition of new employee

  Scenario: Update employee profile and validate change
    Given User login with username "Luke" and password "Skywalker"
    When Open create employee wrapper
    Then Provide details and add employee
    Then Select the added employee to update
    And Make changes to the employee profile and submit
    And Verify the changes are reflected

  Scenario: Delete employee profile and validate change
    Given User login with username "Luke" and password "Skywalker"
    When Open create employee wrapper
    Then Provide details and add employee
    And Select the added employee profile to delete
    And Delete employee profile and validate change

  Scenario: Check Update functionality by double click on Employee Name
    Given User login with username "Luke" and password "Skywalker"
    When  DoubleClick on employee
    Then  Navigate to Update page
