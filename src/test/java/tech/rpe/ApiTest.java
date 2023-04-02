package tech.rpe;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tech.rpe.entity.UserRequest;
import tech.rpe.entity.UserResponse;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void createUser() {
        Gson gson = new Gson();
        UserRequest userRequest = new UserRequest("Matheus", "QA");
        UserResponse userResponse = given()
                .contentType(ContentType.JSON).log().all()
                .body(gson.toJson(userRequest))
                .when().post("api/users")
                .then().statusCode(HttpStatus.SC_CREATED).log().all()
                .and().extract().response().as(UserResponse.class);

        assertEquals("Matheus", userResponse.getName());
        assertEquals("QA", userResponse.getJob());
        assertNotNull(userResponse.getId());
    }

}
