Feature: CRUD customer
  To allow a user to create, read, update and delete a customer in the system.
 
  Scenario: Create and read a customer
    Given a customer with the name 'Jaziel Fernando Leandro', address '37 Megan ave' and phone '+642950648'
    When the user searches for a customer with id 1
    Then customer should have the name 'Jaziel Fernando Leandro'
      And customer should have the address '37 Megan ave'
      
  Scenario: update a user
    Given the user searches for a customer with id 1
    When the user updates the customer's name to 'Fernando Leandro Jaziel'
    Then the customer with id 1 should have the name 'Fernando Leandro Jaziel'
    
  Scenario: delete a user
    Given the user searches for a customer with id 1
    When the user deletes the customer with id 1
    Then the user cannot find a user with id 1
