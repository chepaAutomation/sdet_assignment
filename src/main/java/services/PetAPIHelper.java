package services;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import common.pets.Category;
import common.pets.Pet;
import common.pets.Tag;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetAPIHelper {

    RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();

    public PetAPIHelper() {
        RestAssured.baseURI = ConfigReader.getApplicationProperties().getProperty("petstore.base.url");
    }

    public Pet createPetAPICall(Pet petsData) {
        RequestSpecification request = RestAssured.given().config(config);
        request.header("Content-Type", "application/json");
        request.header("accept", "application/json");
        request.body(petsData);

        Response response = request.post(APIConstants.CREATE_PET);
        LogUtil.getLogger().debug(response.prettyPrint());

        Pet petsAPIResponse = response.as(Pet.class);
        return petsAPIResponse;
    }

    public Pet fillPetData(Map inputTestData) {
        Category category = new Category(Integer.parseInt(inputTestData.get("category_id").toString()), inputTestData.get("category_name").toString());

        List<String> photoUrls = new ArrayList<>();
        photoUrls.add(inputTestData.get("photoUrls").toString());

        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag(Integer.parseInt(inputTestData.get("tag_id").toString()), inputTestData.get("tag_name").toString());
        tags.add(tag);

        Pet pet = new Pet(Double.valueOf(inputTestData.get("id").toString()), inputTestData.get("name").toString(),
                inputTestData.get("status").toString(), category, photoUrls, tags);

        return pet;
    }


    public void comparePetDetails(Pet expectedPetDetails, Pet actualPetDetails) {
        LogUtil.getLogger().info("### Verifying the Pet details after hitting create pets API ");
        Assert.assertEquals(expectedPetDetails.getId(), actualPetDetails.getId(), "mismatch in pet id");
        Assert.assertEquals(expectedPetDetails.getCategory().getId(), actualPetDetails.getCategory().getId(), "mismatch in category id");
        Assert.assertEquals(expectedPetDetails.getCategory().getName(), actualPetDetails.getCategory().getName(), "mismatch in category name");
        Assert.assertEquals(expectedPetDetails.getName(), actualPetDetails.getName(), "mismatch in pet name");
        Assert.assertEquals(expectedPetDetails.getPhotoUrls(), actualPetDetails.getPhotoUrls(), "mismatch in photo url");
        Assert.assertEquals(expectedPetDetails.getStatus(), actualPetDetails.getStatus(), "mismatch in status");
        Assert.assertEquals(expectedPetDetails.getTags().get(0).getId(), actualPetDetails.getTags().get(0).getId(), "mismatch in tag id");
        Assert.assertEquals(expectedPetDetails.getTags().get(0).getName(), actualPetDetails.getTags().get(0).getName(), "mismatch in tag name");
        LogUtil.getLogger().info("### Verification passed for create Pet API");

    }

    public Pet updatePetAPICall(Pet petsData) {
        RequestSpecification request = RestAssured.given().config(config);
        request.header("Content-Type", "application/json");
        request.header("accept", "application/json");
        request.body(petsData);

        Response response = request.put(APIConstants.UPDATE_PET);
        LogUtil.getLogger().debug(response.prettyPrint());

        Pet petsAPIResponse = response.as(Pet.class);
        return petsAPIResponse;
    }

    public Pet getPetAPICall(int petId) {
        RequestSpecification request = RestAssured.given().config(config);
        request.header("accept", "application/json");

        Response response = request.get(APIConstants.GET_PET + "/" + petId);
        LogUtil.getLogger().debug(response.prettyPrint());

        Pet petsAPIResponse = response.as(Pet.class);
        return petsAPIResponse;
    }


    public Pet[] findByStatusPetAPICall(String petStatuses) {
        RequestSpecification request = RestAssured.given().config(config);
        request.header("accept", "application/json");

        petStatuses = petStatuses.replace(",", "&status=");
        petStatuses = "?status=" + petStatuses;
        System.out.println("Pet statuses value is : " + petStatuses);

        Response response = request.get(APIConstants.FIND_BY_STATUS + petStatuses);

        Pet[] actualResponse = response.as(Pet[].class);
        LogUtil.getLogger().debug(response.prettyPrint());

        return actualResponse;
    }

    public void validateFindByStatusPetAPI(Pet[] actualResponse, Double inputPetId, String petStatus) {
        for (Pet petData : actualResponse) {
            if (petData.getId()==inputPetId) {
                LogUtil.getLogger().info("## Verifying the response for find by status API for Pet for Pet Id : "+ inputPetId + ", with status : "+ petStatus);
                Assert.assertEquals(petData.getStatus(), petStatus, "pet status mismatch for input pet id : " + inputPetId);
            }
        }
        LogUtil.getLogger().info("## Validation passed for find by status API for Pet for Pet Id : "+ inputPetId);
    }
}
