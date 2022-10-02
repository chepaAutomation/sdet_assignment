package pets;

import common.Constants;
import common.pets.Pet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.PetAPIHelper;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.Map;

/***
 * Description : To test the flow for createPet API
 *      Test : createPetFlow - Calling an API to create pet and validate on the response
 */

public class CreatePetsTest {

    ExcelUtils excelUtils = new ExcelUtils();
    String testDataFileLocation = Constants.PET_STORE_TEST_DATA_FILE;
    String createPetTestDataSheetName = Constants.CREATE_PET_SHEET_NAME;
    PetAPIHelper petAPIHelper = new PetAPIHelper();
    Pet petsData = new Pet();


    @Test(dataProvider = "createPetTestData")
    public void createPetFlow(Map inputTestData){
        petsData = petAPIHelper.fillPetData(inputTestData);
        Pet petsActualData = petAPIHelper.createPetAPICall(petsData);
        petAPIHelper.comparePetDetails(petsData, petsActualData);

    }

    @DataProvider(name = "createPetTestData")
    public Object[][] createPetTestData() throws IOException, InvalidFormatException {
        Object[][] testData = excelUtils.dataReaderInMap(testDataFileLocation, createPetTestDataSheetName);
        return testData;
    }


}
