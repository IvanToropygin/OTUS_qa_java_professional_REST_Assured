package pet_tests;

import base_test.BaseTest;
import dto.commons.Category;
import dto.commons.Tag;
import dto.create_pet.request.CreatePetRequest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreatePetTest extends BaseTest {

    private Long createdPetId;

    /*
    Тест на эндпойнт POST /pet.
    Отправлен валидный запрос.
    Проверки:
    1. статус-код == 200
    2. тело ответа идентично отправленному, но с присвоенным id
     */
    @Order(1)
    @Test
    public void createPetSuccessTest() {
        CreatePetRequest request = CreatePetRequest.builder()
                .id(0L)
                .category(new Category(0, "Category"))
                .name("Name1")
                .photoUrls(new ArrayList<>(List.of("someUrl")))
                .tags(new ArrayList<>(List.of(new Tag(0, "tagId"))))
                .status("available")
                .build();

        CreatePetRequest response = petstoreApi.createPet(request)
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(CreatePetRequest.class);

        Long createdId = response.getId();
        createdPetId = createdId;
        request.id = createdId;

        assertAll(
                () -> assertTrue(response.getId() != 0, "id не должно быть 0"),
                () -> assertEquals(request, response)
        );
    }

    /*
    Тест на эндпойнт GET /pet.
    Отправлен валидный запрос.
    Проверки:
    1. статус-код == 200
    2. состав полей тела ответа через JsonSchemaValidator
     */
    @Order(2)
    @Test
    public void getCreatedPet() {
        Assertions.assertNotNull(createdPetId, "Pet ID должен быть задан");

        petstoreApi.getPetById(createdPetId)
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/GetPet.json"));
    }

    /*
    Тест на эндпойнт POST /pet.
    Отправлен НЕвалидный запрос (не переданы обязательные поля "name" и "photoUrls").
    Проверки:
    1. статус-код == 405
     */
    @Test
    public void createPetBodyWithoutNecessaryFieldsTest() {
        CreatePetRequest request = CreatePetRequest.builder()
                .id(0L)
                .category(new Category(0, "Category"))
                .tags(new ArrayList<>(List.of(new Tag(0, "tagId"))))
                .status("available")
                .build();

        petstoreApi.createPet(request)
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}
