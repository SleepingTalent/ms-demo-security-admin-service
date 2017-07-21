@permission
Feature: Permission Features

  Scenario: find all permissions returns expected permissions
    Given permissions exist
    When get all "permissions" is called
    Then "3" permissions are returned
