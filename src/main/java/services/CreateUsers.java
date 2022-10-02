package services;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import common.users.User;
import common.users.UsersAPIResponse;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import sun.rmi.runtime.Log;
import utils.APIConstants;
import utils.ConfigReader;
import utils.LogUtil;

public class CreateUsers {
    RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();

    public CreateUsers() {
        RestAssured.baseURI = ConfigReader.getApplicationProperties().getProperty("petstore.base.url");
        System.out.println("base URI is : " + RestAssured.baseURI);
    }

    public UsersAPIResponse createUsersAPICall(User[] usersData) {
        RequestSpecification request = RestAssured.given().config(config);
        request.header("Content-Type", "application/json");
        request.header("accept", "application/json");
        request.body(usersData);

        Response response = request.post(APIConstants.CREATE_MULTIPLE_USERS_WITH_ARRAY);
        LogUtil.getLogger().debug(response.prettyPrint());

        UsersAPIResponse usersAPIResponse = response.as(UsersAPIResponse.class);
        return usersAPIResponse;
    }

    public void validateCreateUserResponse(UsersAPIResponse usersAPIResponse){
        LogUtil.getLogger().info("Verifying the create users API response - ");
        Assert.assertEquals("code mismatched in the API Response",200, usersAPIResponse.getCode());
        Assert.assertEquals("Message verification failed", "ok", usersAPIResponse.getMessage());
        Assert.assertEquals("Type mismatched in the response", "unknown", usersAPIResponse.getType());
        LogUtil.getLogger().info("Verification passed for create users API");
    }

}
