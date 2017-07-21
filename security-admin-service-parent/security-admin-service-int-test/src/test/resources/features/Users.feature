@user
Feature: User Features

  Scenario: find all users returns expected users
    Given users exist
    When get all "users" is called
    Then "3" users are returned