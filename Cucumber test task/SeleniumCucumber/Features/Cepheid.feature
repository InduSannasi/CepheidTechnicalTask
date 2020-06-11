Feature: Login to the Test Application to modify Employee data

  Scenario: Login with Valid Credentials
    Given User enters Valid UserName and Password
    When Click login button
    Then login should be successful

  Scenario: Login with InValid Credentials
    Given User enters Invalid UserName or Password
    Then login should fail

  Scenario: Check logout Functionality
    Given User logs in successfully
    When User clicks logout button
    Then Navigate to homepage

  Scenario: Create a new employee profile with valid details
    Given User logs in successfully
    When Open create employee wrapper
    Then Provide details and add employee
    And Validate addition of new employee

  Scenario: Update employee profile and validate change
    Given User logs in successfully
    When Open create employee wrapper
    Then Provide details and add employee
    Then Select the added employee to update
    And Make changes to the employee profile and submit
    And Verify the changes are reflected

  Scenario: Delete employee profile and validate change
    Given User logs in successfully
    When Open create employee wrapper
    Then Provide details and add employee
    And Select the added employee profile to delete
    And Delete employee profile and validate change

