package pets;

import common.Constants;
import common.pets.Pet;
import common.users.User;
import common.users.UsersAPIResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.PetAPIHelper;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.Map;

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

        Pet updatePetDetailsAPIResponse = petAPIHelper.updatePetAPICall(petData);
        petAPIHelper.comparePetDetails(petData, updatePetDetailsAPIResponse);

        Pet actualPetDetailsAPIResponse = petAPIHelper.getPetAPICall(petData.getId());
        petAPIHelper.comparePetDetails (petData, actualPetDetailsAPIResponse);
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
