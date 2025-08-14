package create_user;

import base_test.BaseTest;
import dto.request.create_user.CreateUserRequest;
import dto.response.create_user.CreateUserResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class CreateUserTest extends BaseTest {

    @Test
    public void createUserTest() {
        CreateUserRequest request = CreateUserRequest.builder()
                .id(400L)
                .firstName("FirstName")
                .lastName("LastName")
                .email("mail@mail.ru")
                .userStatus(505L)
                .build();

        petstoreApi.createUser(request)
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));

        CreateUserResponse response = petstoreApi.createUser(request)
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(CreateUserResponse.class);
    }
}
