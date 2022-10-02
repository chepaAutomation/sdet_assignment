package services;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import common.users.User;
import common.users.UsersAPIResponse;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import sun.rmi.runtime.Log;
import utils.APIConstants;
import utils.ConfigReader;
import utils.LogUtil;

import java.util.Map;

public class UserAPIHelper {
    RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();

    public UserAPIHelper() {
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

    public User fillUsersData(Map apiInputData, User userData) {
        userData.setId(Integer.parseInt(apiInputData.get("id").toString()));
        userData.setUsername(apiInputData.get("username").toString());
        userData.setFirstName(apiInputData.get("firstName").toString());
        userData.setLastName(apiInputData.get("lastName").toString());
        userData.setEmail(apiInputData.get("email").toString());
        userData.setPassword(apiInputData.get("password").toString());
        userData.setPhone(apiInputData.get("phone").toString());
        userData.setUserStatus(Integer.parseInt(apiInputData.get("userStatus").toString()));
        return userData;
    }

    public void validateCreateUserResponse(UsersAPIResponse usersAPIResponse) {
        LogUtil.getLogger().info("### Verifying the create users API response");
        Assert.assertEquals(200, usersAPIResponse.getCode(), "code mismatched in the API Response");
        Assert.assertEquals("ok", usersAPIResponse.getMessage(), "Message verification failed");
        Assert.assertEquals("unknown", usersAPIResponse.getType(), "Type mismatched in the response");
        LogUtil.getLogger().info("### Verification passed for create users API");
    }

    public UsersAPIResponse updateUsersAPICall(User usersData) {
        RequestSpecification request = RestAssured.given().config(config);
        request.header("Content-Type", "application/json");
        request.header("accept", "application/json");
        request.body(usersData);

        Response response = request.put(APIConstants.UPDATE_USER_DETAILS + "/" + usersData.getUsername());
        LogUtil.getLogger().debug(response.prettyPrint());

        UsersAPIResponse usersAPIResponse = response.as(UsersAPIResponse.class);
        return usersAPIResponse;
    }

    public void validateUpdateUserResponse(UsersAPIResponse usersAPIResponse) {
        LogUtil.getLogger().info("### Verifying the update user details API response ");
        Assert.assertEquals(200, usersAPIResponse.getCode(), "code mismatched in the API Response");
        Assert.assertEquals("unknown", usersAPIResponse.getType(), "Type mismatched in the response");
        LogUtil.getLogger().info("### Verification passed for update users API");
    }

    public User getUsersAPICall(String userName) {
        RequestSpecification request = RestAssured.given().config(config);
        request.header("accept", "application/json");

        Response response = request.get(APIConstants.GET_USER_DETAILS + "/" + userName);
        LogUtil.getLogger().debug(response.prettyPrint());

        User getUsersAPIResponse = response.as(User.class);
        return getUsersAPIResponse;
    }

    public void compareUserDetails(User expectedUsersData, User actualUsersData) {
        LogUtil.getLogger().info("### Verifying the user details after update user details API ");
        Assert.assertEquals(expectedUsersData.getUsername(), actualUsersData.getUsername());
        Assert.assertEquals(expectedUsersData.getFirstName(), actualUsersData.getFirstName());
        Assert.assertEquals(expectedUsersData.getLastName(), actualUsersData.getLastName());
        Assert.assertEquals(expectedUsersData.getEmail(), actualUsersData.getEmail());
        Assert.assertEquals(expectedUsersData.getPassword(), actualUsersData.getPassword());
        Assert.assertEquals(expectedUsersData.getPhone(), actualUsersData.getPhone());
        Assert.assertEquals(expectedUsersData.getUserStatus(), actualUsersData.getUserStatus());
        LogUtil.getLogger().info("### Verification passed for update users Details");
    }

}
