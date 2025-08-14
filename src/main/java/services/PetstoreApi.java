package services;

import dto.create_pet.request.CreatePetRequest;
import dto.create_user.request.CreateUserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetstoreApi {

    private RequestSpecification spec;
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USER = "/user";
    private static final String PET = "/pet";

    public PetstoreApi() {
        spec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }

    public ValidatableResponse createUser(CreateUserRequest userDTO) {

        return given(spec)
                .body(userDTO)
                .when()
                .post(USER)
                .then()
                .log().all();
    }

    public ValidatableResponse createPet(CreatePetRequest userDTO) {

        return given(spec)
                .body(userDTO)
                .when()
                .post(PET)
                .then()
                .log().all();
    }

    public ValidatableResponse getPetById(Long petId) {

        return given(spec)
                .when()
                .get(PET + "/{id}", petId)
                .then()
                .log().all();
    }
}
