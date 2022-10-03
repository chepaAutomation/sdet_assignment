package pets;

import common.Constants;
import common.pets.Pet;
import common.users.User;
import common.users.UsersAPIResponse;
import io.restassured.response.Response;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.PetAPIHelper;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Purpose : Create a single pet, update the pet details and get the pet details
 */

public class UpdatePetsDetailsTest {

    ExcelUtils excelUtils = new ExcelUtils();
    String testDataFileLocation = Constants.PET_STORE_TEST_DATA_FILE;
    String createSinglePetTestDataSheetName = Constants.CREATE_SINGLE_PET_SHEET_NAME;
    String updatePetDetailsTestDataSheetName = Constants.UPDATE_PET_DETAILS_SHEET_NAME;

    Pet originalUserData = new Pet();
    CreateSinglePet createSinglePet = new CreateSinglePet();
    Pet petData = new Pet();
    PetAPIHelper petAPIHelper = new PetAPIHelper();


    @Test(dataProvider = "createSinglePetTestData")
    public void createSinglePetDataFlow(Map inputTestData){
        originalUserData = createSinglePet.createSinglePet(inputTestData);
    }

    @Test (dataProvider = "updatePetDetailsTestData", dependsOnMethods = "createSinglePetDataFlow")
    public void updatePetDetailsFlow (Map inputTestData){
        petData = petAPIHelper.fillPetData(inputTestData);

        // Update the pet details
        Pet updatePetDetailsAPIResponse = petAPIHelper.updatePetAPICall(petData);
        petAPIHelper.comparePetDetails(petData, updatePetDetailsAPIResponse);

        // Compare the pet details with the get Pet details API (expected (from excel data) vs actual pet details shown by get pet details API)
        System.out.println("Pet id integer value is : "+ (int)petData.getId());
        Pet actualPetDetailsAPIResponse = petAPIHelper.getPetAPICall((int)petData.getId());
        petAPIHelper.comparePetDetails (petData, actualPetDetailsAPIResponse);

        // Call the findByStatusPet API call to get all pets with status mentioned in testData
        Pet[] findByStatusAPIResponse = petAPIHelper.findByStatusPetAPICall(petData.getStatus());
        petAPIHelper.validateFindByStatusPetAPI(findByStatusAPIResponse, petData.getId(), petData.getStatus());

    }

    // input all the statuses and validate the API response
    @Test(dependsOnMethods = "updatePetDetailsFlow")
    public void findByStatusFlow(){
        String petStatuses = "sold,available,pending";
        Pet[] findByStatusAPIResponse = petAPIHelper.findByStatusPetAPICall(petStatuses);
        petAPIHelper.validateFindByStatusPetAPI(findByStatusAPIResponse, petData.getId(), petData.getStatus());
    }


    @DataProvider(name = "createSinglePetTestData")
    public Object[][] createSinglePetTestData() throws IOException, InvalidFormatException {
        Object[][] testData = excelUtils.dataReaderInMap(testDataFileLocation, createSinglePetTestDataSheetName);
        return testData;
    }

    @DataProvider(name = "updatePetDetailsTestData")
    public Object[][] updatePetDetailsTestData() throws IOException, InvalidFormatException {
        Object[][] testData = excelUtils.dataReaderInMap(testDataFileLocation, updatePetDetailsTestDataSheetName);
        return testData;
    }


}
