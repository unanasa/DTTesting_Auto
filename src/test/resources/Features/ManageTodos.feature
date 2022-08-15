#Author: Sachitha Nanayakkara
#Date: 15/08/2022

Feature: In order to remember the things I want to do, as a ToDo MVC user, I want to manage my todo list

  Background: 
    Given an empty Todo list

  @smoke
  Scenario Outline: New item can be added to the empty list
    When MVC User adds a Todo for "<todo>"
    Then only that item is listed
    And the list summary is "<summary>"
    And the list’s filter is set to All with Completed and Active are unset
    And Clear Completed filter is unavailable

    Examples: 
      | todo        | summary     |
      | Go shopping | 1 item left |

  @smoke
  Scenario Outline: Two new items can be added to the empty list
    When MVC User adds Todos for "<todo1>" and "<todo2>"
    Then only those two items are listed
    And the list summary is "<summary>"
    And the list’s filter is set to All with Completed and Active are unset
    And Clear Completed filter is unavailable

    Examples: 
      | todo1       | todo2           | summary      |
      | Go shopping | Clean the house | 2 items left |

  @smoke
  Scenario Outline: Added items can be cleared
    Given a Todo list with items "<todo1>" and "<todo2>"
    And the first item is completed
    When ‘Clear completed’ is clicked
    Then only the second item is listed
    And the list summary is "<summary>"

    Examples: 
      | todo1       | todo2           | summary     |
      | Go shopping | Clean the house | 1 item left |

  @regression
  Scenario Outline: Added items can be removed from the list
    Given a Todo list with item "<todo>"
    When the item is removed
    Then nothing is listed

    Examples: 
      | todo        |
      | Go shopping |
