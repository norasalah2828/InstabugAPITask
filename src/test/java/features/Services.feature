Feature: Services


  Scenario Outline: Check that you can get all services and validate first service name
    Given user calls "<APIEndPoint>" using "<Method>" http request
    Then the API call will return with status code <statusCode>
    And first service will be "<ServiceName>" and total number of services will be <NumOfServices>

    Examples:
      | APIEndPoint | Method | statusCode | ServiceName         | NumOfServices |
      | ServicesAPI | GET    | 200        | Geek Squad Services | 10            |

  Scenario Outline: Check that you can search for specific service and fetch it's data
    Given user calls "<APIEndPoint>" using "<Method>" http request
    When user get "<queryParameterKey>" of service "<ServiceName>"
    And search for that id using "<APIEndPoint>" and "<Method>"
    Then the API call will return with status code <statusCode>
    And response body should contain fetched data of "<ServiceName>"

    Examples:
      | APIEndPoint | Method | statusCode | ServiceName         | queryParameterKey |
      | ServicesAPI | GET    | 200        | Geek Squad Services | id                |

  Scenario Outline: Check if you hit invalid endpoint it will return "Page not found 404" response
    Given user calls "<APIEndPoint>" using "<Method>" http request
    Then  the API call will return with status code <statusCode>

    Examples:
      | APIEndPoint | Method | statusCode |
      | InvalidAPI  | GET    | 404        |

  Scenario Outline: Validate if you search for not existed service the status should be 404
    Given user calls "<APIEndPoint>" using "<Method>" http request and <id> using "<queryParameterKey>"
    Then  the API call will return with status code <statusCode>
    And  response body should contains empty data


    Examples:
      | APIEndPoint | Method | id   | statusCode | queryParameterKey |
      | ServicesAPI | GET    | 7000 | 200        | id                |



