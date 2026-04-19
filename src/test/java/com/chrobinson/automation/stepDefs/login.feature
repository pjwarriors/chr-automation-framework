Feature: Shipment Creation
  Scenario: Create new shipment successfully
    Given User is logged in
    When User creates a shipment with valid details
    Then Shipment should appear in tracking list