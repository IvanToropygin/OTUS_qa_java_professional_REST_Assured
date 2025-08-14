package user_tests.create_user;

import base_test.BaseTest;
import dto.create_user.request.CreateUserRequest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class CreateUserTest extends BaseTest {

    /*
    Тест на эндпойнт POST /user.
    Отправлен валидный запрос.
    Проверки:
    1. статус-код == 200
    2. проверка тела ответа через JsonSchemaValidator
     */
    @Test
    public void createUserSuccessTest() {
        CreateUserRequest request = CreateUserRequest.builder()
                .id(400)
                .firstName("FirstName")
                .lastName("LastName")
                .email("mail@mail.ru")
                .userStatus(505)
                .build();

        petstoreApi.createUser(request)
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));
    }

    /*
    Тест на эндпойнт POST /user.
    Отправлен НЕвалидный запрос (не предано тело запроса).
    Проверки:
    1. статус-код == 400
     */
    @Test
    public void createUserEmptyBodyTest() {
        petstoreApi.createUser(CreateUserRequest.builder().build())
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
