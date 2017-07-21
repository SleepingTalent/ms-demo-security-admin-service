@role
Feature: Role Features

  Scenario: find all roles returns expected roles
    Given roles exist
    When get all "roles" is called
    Then "3" roles are returned