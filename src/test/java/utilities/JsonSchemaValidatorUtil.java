package utilities;

import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

public class JsonSchemaValidatorUtil {
    public static void validateSchema(Response response, String schemaFileName) {
        File schema = new File("src/test/resources/schemas/" + schemaFileName);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }
}
