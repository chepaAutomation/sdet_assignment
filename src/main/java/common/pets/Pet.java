package common.pets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    private int id;
    private String name;
    private String status;

    private Category category;
    private List<String> photoUrls = null;
    private List<Tag> tags = null;

    public Pet(int id, String name, String status, Category category, List<String> photoUrls, List<Tag> tags){
        this.id = id;
        this.name = name;
        this.status = status;
        this.category = category;
        this.photoUrls = photoUrls;
        this.tags = tags;
    }

}
