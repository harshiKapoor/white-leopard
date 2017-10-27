Feature: Post Code nearby stations request
  Scenario Outline: Send request with postcode to get nearby stations
    Given I have access to API
    When I send <postcode> in my request
    Then I get <message> in response
    Examples:
      | postcode | message |
       #happy path
      | AB107AP  | Success |
       #postcode in lowercase
      | ab107ap  | Failure |
        #reversing the postcode - invalid postcode
      | AP701AB  | Failure |
        #postcode in mixed case
      | Ab107aP  | Failure |
         #invalid characters
      | AB1@7AP  | Failure |
         #assuming postcode must have 7 characters, invalid post code with 6 characters
      | AB17AP   | Failure |
        # special keywords in postcode
      | null     | Failure |
      | undefined | Failure |
        #using boolean in postcode
      | true      | Failure |
      | 1         | Failure |
        #empty request
      |           | Failure |
      | ''        | Failure |
        # assuming postcode must start with alphabet, invalid post code starting with number
      | 1B17AP    | Failure |
       # too long postcode
      | AB107APAB107AP | Failure |
       # using space in postcode
      | A B107 AP       | Failure |
       # using suburb in postcode
      | Boston          | Failure |
       #using country name in postcode
      | usa             | Failure |
      # using response statuc code in postcode
      | 404             | Failure |
      |500              | Failure |




    Feature: Post Code nearby stations response
      Scenario: valid postcode nearby stations
        Given I have access to API
        When  I send valid postcode in my request
          # response size
        Then  I get postcode with <number> nearby stations in my response
          # message success
        And   I should get message success in my response
          # no duplicate content
        And   Response should contain 1 CRS code with value STN
          # checking distance units is not empty
        And   Distance units 13.91
          # checking response time for performance
        And  I get response in 1ms




