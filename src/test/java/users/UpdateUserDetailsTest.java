package users;

import common.Constants;
import common.users.User;
import common.users.UsersAPIResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.UserAPIHelper;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.Map;

/***
 * Description : To test the flow for update user details API
 *      Test : createSingleUsersDataFlow - To create a single user for which we will update the details
 *      Test : updateUserDetailsFlow - Calling an API to update user details like : firstName, lastName, email, phone, password as mentioned in testData
 *      Verification : compareUserDetails - compare the expected and actual user details (actual : we get from get user details API)
 */

public class UpdateUserDetailsTest {

    ExcelUtils excelUtils = new ExcelUtils();
    String testDataFileLocation = Constants.PET_STORE_TEST_DATA_FILE;
    String createSingleUsersTestDataSheetName = Constants.CREATE_SINGLE_USERS_SHEET_NAME;
    String updateUserDetailsTestDataSheetName = Constants.UPDATE_USER_DETAILS_SHEET_NAME;

    CreateSingleUser createSingleUser = new CreateSingleUser();
    public User originalUserData = new User();
    public User userData = new User();
    UserAPIHelper userAPIHelper = new UserAPIHelper();

    @Test (dataProvider = "createSingleUsersTestData")
    public void createSingleUsersDataFlow(Map inputTestData){
        originalUserData = createSingleUser.createSingleUserFlow(inputTestData);
    }

    @Test (dataProvider = "updateUserDetailsTestData", dependsOnMethods = "createSingleUsersDataFlow")
    public void updateUserDetailsFlow (Map inputTestData){
        userData = userAPIHelper.fillUsersData(inputTestData, userData);

        UsersAPIResponse updateUserDetailsAPIResponse = userAPIHelper.updateUsersAPICall(userData);
        userAPIHelper.validateUpdateUserResponse(updateUserDetailsAPIResponse);

        User actualUserDetailsAPIResponse = userAPIHelper.getUsersAPICall(userData.getUsername());
        userAPIHelper.compareUserDetails (userData, actualUserDetailsAPIResponse);
    }


    @DataProvider(name = "createSingleUsersTestData")
    public Object[][] createSingleUsersTestData() throws IOException, InvalidFormatException {
        Object[][] testData = excelUtils.dataReaderInMap(testDataFileLocation, createSingleUsersTestDataSheetName);
        return testData;
    }


    @DataProvider(name = "updateUserDetailsTestData")
    public Object[][] updateUserDetailsTestData() throws IOException, InvalidFormatException {
        Object[][] testData = excelUtils.dataReaderInMap(testDataFileLocation, updateUserDetailsTestDataSheetName);
        return testData;
    }


}
