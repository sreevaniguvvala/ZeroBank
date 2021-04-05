Feature: Zero Bank

  Scenario: To Transfer Funds

    Given the user logins ZeroBank
    When the user is on ZeroBank Home Page
    Then the user navigates to Transfer Funds
    Then the user Account details to transfer
    And the user clicks on Continue Button
    Then the user verifies the data and Submit
    And the user verifies transcation message
    And the user logs out


  Scenario: To Transfer Funds

    Given the user logins ZeroBank
    When the user is on ZeroBank Home Page
    Then the user navigates to PayBill
    And the user clicks on Add New Payee Tab
    And the user fills all the details
    And the user verifies successful message
    And the user logs out
