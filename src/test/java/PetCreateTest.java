import com.github.javafaker.Faker;
import config.SpringConfig;
import dto.Pet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import services.PetNewApi;

import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class PetCreateTest {

    /*
         кейс: отправить запрос для обновления инфы о питомце с несколькими парметрами
         проверить: статус 200ок, отправленные данные корректно создаются.
    */

    @Autowired
    private PetNewApi petNewApi;

    @Test
    public void petUpdateSomeParametres(){

        //создаем питомца
        Faker faker = new Faker();
        String randomNameBeforeUpdate = faker.name().firstName();

        Pet pet = Pet.builder()
                .id(165)
                .name(randomNameBeforeUpdate)
                .status("available")
                .build();

        petNewApi.createPet(pet);

        //проверяем данные
        petNewApi.getPetId("165")
                .statusCode(200)
                .body("id", equalTo(165))
                .body("name", equalTo(randomNameBeforeUpdate))
                .body("status", equalTo("available"));

        //обновляем данные
        String randomNameAfterUpdate = faker.name().firstName();
        Pet pet2 = Pet.builder()
                .id(165)
                .name(randomNameAfterUpdate)
                .status("available")
                .build();

        petNewApi.createPet(pet2);

        //проверяем обновление данных
        petNewApi.getPetId("165")
                .statusCode(200)
                .body("id", equalTo(165))
                .body("name", equalTo(randomNameAfterUpdate))
                .body("status", equalTo("available"));


    }

    /*
        кейс: отправить запрос для обновления инфы о питомце с одним параметром
        проверить: статус 200ок, отправленный id создался корректные, неотправленные параметры - null.
    */
    @Test
    public void petUpdateOnlyId(){
        Pet pet = Pet.builder()
                .id(165)
                .build();

        petNewApi.createPet(pet)
                .statusCode(200)
                .body("id", equalTo(165))
                .body("name", equalTo(null))
                .body("status", equalTo(null));


        //обновляем данные
        Pet pet2 = Pet.builder()
                .id(200)
                .build();

        petNewApi.createPet(pet2);

        //проверяем обновление данных
        petNewApi.getPetId("200")
                .statusCode(200)
                .body("id", equalTo(200))
                .body("name", equalTo(null))
                .body("status", equalTo(null));
    }

}
