package pets;

import common.pets.Pet;
import services.PetAPIHelper;

import java.util.Map;

public class CreateSinglePet {

    Pet petsData = new Pet();
    PetAPIHelper petAPIHelper = new PetAPIHelper();

    public Pet createSinglePet(Map inputTestData){
        petsData = petAPIHelper.fillPetData(inputTestData);
        Pet petsActualData = petAPIHelper.createPetAPICall(petsData);
        petAPIHelper.comparePetDetails(petsData, petsActualData);
        return petsData;
    }
}
