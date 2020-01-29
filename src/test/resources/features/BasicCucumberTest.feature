Feature: CucumberJava Example

  @regression
  Scenario: The TTC menu is displayed
    Given I am on the Google homepage
    When I open the TTC website
    Then I can see that the menu is displayed

  # This feature is ignored by the test runner
  @ignore
  Scenario: Logout functionality exists
    Given I am logged into the TTC website
    When I log out of the TTC website
    Then I see the guest user page

  @regression @release
  Scenario: The BBC leaderboard is displayed
    Given I am on the Google homepage
    When I open the BBC website
    Then I can see that the leaderboard is displayed