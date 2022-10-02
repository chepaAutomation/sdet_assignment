package users;

import common.users.User;
import common.users.UsersAPIResponse;
import services.UserAPIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Purpose : Class to create a single user. This user can be used for updating the details.
 */

public class CreateSingleUser {

    public User userData = new User();
    UserAPIHelper usersAPI = new UserAPIHelper();
    private static List<User> usersList = new ArrayList<>();
    private User[] userArray = new User[1];

    public User createSingleUserFlow(Map inputTestData){
        userData = usersAPI.fillUsersData(inputTestData, userData);
        usersList.add(userData);

        userArray = usersList.toArray(userArray);

        UsersAPIResponse usersAPIResponse = usersAPI.createUsersAPICall(userArray);
        usersAPI.validateCreateUserResponse(usersAPIResponse);
        return userData;
    }

}
