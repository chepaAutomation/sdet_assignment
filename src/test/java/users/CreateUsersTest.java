package users;

import common.Constants;
import common.users.User;
import common.users.UsersAPIResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.UserAPIHelper;
import utils.ExcelUtils;
import utils.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Description : To test the flow for createUsersWithArray API
 *      Test : prepareUsersDataArray - To combine all the input data and store it inside an array
 *      Test : createUsersFlow - Calling an API to create users and validate on the response
 */

public class CreateUsersTest {

    ExcelUtils excelUtils = new ExcelUtils();
    String testDataFileLocation = Constants.PET_STORE_TEST_DATA_FILE;
    String createUsersTestDataSheetName = Constants.CREATE_MULTI_USERS_SHEET_NAME;
    public User userData = null;
    UserAPIHelper usersAPI = new UserAPIHelper();

    private static List<User> usersList = new ArrayList<>();
    private User[] userArray = null;

    @Test(dataProvider = "createUsersTestData")
    public void prepareUsersDataArray(Map inputTestData) {
        userData = new User();
        userData = usersAPI.fillUsersData(inputTestData, userData);
        usersList.add(userData);
        LogUtil.getLogger().info("Total users added : " + usersList.size());
    }

    @Test(dependsOnMethods = "prepareUsersDataArray")
    public void createUsersFlow() {
        LogUtil.getLogger().info("## Running the test for create users with Array :");
        userArray = new User[usersList.size()];
        userArray = usersList.toArray(userArray);
        LogUtil.getLogger().info("Total users in the inputs request are : "+ userArray.length);

        UsersAPIResponse usersAPIResponse = usersAPI.createUsersAPICall(userArray);
        usersAPI.validateCreateUserResponse(usersAPIResponse);

    }

    @DataProvider(name = "createUsersTestData")
    public Object[][] createUsersTestData() throws IOException, InvalidFormatException {
        Object[][] testData = excelUtils.dataReaderInMap(testDataFileLocation, createUsersTestDataSheetName);
        return testData;
    }

}
