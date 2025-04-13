Feature: API Testing for JSONPlaceholder

  Scenario: Create a new post
    Given I have the post data
    When I send a POST request to create a new post
    Then The response should contain the correct data and match the schema

  Scenario: Get all posts
    When I send a GET request to retrieve all posts
    Then The response should contain posts with valid ids and match the schema

  Scenario: Delete a post
    When I send a DELETE request
    Then The response should indicate successful deletion and match the schema

  Scenario: Update a post
    When I send a PUT request to update post with certain id
    Then The response should contain the updated data and match the schema
