package stepDefinitions;

import Pojo.Data;
import Pojo.Services;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import resources.APIResources;
import resources.Utils;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ServicesStepDefinition extends Utils {
    RequestSpecification serviceSpecifications;
    Response serviceResponse;
    Services services;
    List<Data> serviceDataList;
    int specificServiceID;

    @Given("user calls {string} using {string} http request")
    public void userCallsUsingHttpRequest(String resource, String method) throws IOException {
        APIResources resourceAPI = APIResources.valueOf(resource);
        serviceSpecifications = given().log().all().spec(requestSpecification());

        if (method.equalsIgnoreCase("POST"))
            serviceResponse = serviceSpecifications.when().post(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            serviceResponse = serviceSpecifications.when().get(resourceAPI.getResource());
    }

    @Then("the API call will return with status code {int}")
    public void theAPICallWillReturnWithStatusCode(int statusCode) {
        Assert.assertEquals(statusCode, serviceResponse.statusCode());
    }

    @And("first service will be {string} and total number of services will be {int}")
    public void firstServiceWillBeAndTotalNumberOfServicesWillBe(String firstServiceName, int numOfServices) {
        services = serviceResponse.as(Services.class);
        serviceDataList = services.getData();
        Assert.assertEquals(firstServiceName,
                serviceDataList.get(0)
                        .getName());
        Assert.assertEquals(numOfServices, serviceDataList.size());
    }


    @When("user get {string} of service {string}")
    public void userGetIdOfService(String key, String serviceName) throws IOException {
        services = serviceResponse.as(Services.class);
        serviceDataList = services.getData();
        for (Data dataObject : serviceDataList) {
            if (dataObject.getName().equals(serviceName)) {
                specificServiceID = dataObject.getId();
                break;
            }
        }
        serviceSpecifications = given().log().all().spec(requestSpecification())
                .queryParam(key, specificServiceID);
    }

    @And("search for that id using {string} and {string}")
    public void searchForThatIdUsingAnd(String resource, String method) {
        APIResources resourceAPI = APIResources.valueOf(resource);
        if (method.equalsIgnoreCase("GET")) {
            serviceResponse = serviceSpecifications.when().get(resourceAPI.getResource());
        }
    }

    @And("response body should contain fetched data of {string}")
    public void responseBodyShouldContainFetchedDataOf(String serviceName) {
        services = serviceResponse.as(Services.class);
        serviceDataList = services.getData();
        Assert.assertEquals(specificServiceID, serviceDataList.get(0).getId());
        Assert.assertEquals(serviceName, serviceDataList.get(0).getName());
    }

    @Given("user calls {string} using {string} http request and {int} using {string}")
    public void userCallsUsingHttpRequestAndId(String resource, String method, int id, String key) throws IOException {
        APIResources resourceAPI = APIResources.valueOf(resource);
        serviceSpecifications = given().log().all().spec(requestSpecification())
                .queryParam(key, id);

        if (method.equalsIgnoreCase("GET")) {
            serviceResponse = serviceSpecifications.when().get(resourceAPI.getResource());
        }
    }

    @Then("response body should contains empty data")
    public void responseBodyShouldContainsEmptyData() {
        services = serviceResponse.as(Services.class);
        serviceDataList = services.getData();
        Assert.assertTrue(serviceDataList.isEmpty());
    }
}
