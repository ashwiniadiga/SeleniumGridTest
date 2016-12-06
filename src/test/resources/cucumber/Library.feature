@Web
Feature: User can navigate in Library

  @FIRSTTEST
  Scenario:  User can navigate to children
    Given User opens library url
    Then User goes to children page

  @FIRSTTEST
  Scenario:  User can navigate to children Books Movies and Music Page
    Given User opens library url
    Then User goes to children page
    Then User navigates to children Books Movies and Music Page