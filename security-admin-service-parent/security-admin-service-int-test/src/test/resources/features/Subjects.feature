@subject
Feature: Subjects Features

  Scenario: find all subjects returns expected subjects
    Given subjects exist
    When get all "subjects" is called
    Then "3" subjects are returned
