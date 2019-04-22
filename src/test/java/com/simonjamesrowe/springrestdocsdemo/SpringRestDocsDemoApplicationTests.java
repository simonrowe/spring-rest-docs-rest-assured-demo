package com.simonjamesrowe.springrestdocsdemo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
public class SpringRestDocsDemoApplicationTests {

    private static final FieldDescriptor[] DEMO_RESPONSE_ENTITY_FIELDS = {fieldWithPath("[].id").description("The id of the record"),
            fieldWithPath("[].a_string").description("The A String meaning blah blah blah"),
            fieldWithPath("[].values").description(" A list of random values")};

    @LocalServerPort
    private int port;

    @Autowired
    private RequestSpecification documentationSpec;

    @Before
    public void before() {
        RestAssured.baseURI = "http://localhost:" + port;
        documentationSpec.port(port);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAll() throws Exception {
        given(this.documentationSpec)
                .filter(document("{ClassName}/{methodName}",
                        preprocessRequest(modifyUris().scheme("https").host("www.simonjamesrowe.com").removePort()),
                        preprocessResponse(prettyPrint()),
                        responseFields(DEMO_RESPONSE_ENTITY_FIELDS)))
                .when()
                .get("/api/v1/entity/")
                .then()
                .assertThat().statusCode(is(200));
    }

    @Test
    public void testSimilarEntities() throws Exception {
        given(this.documentationSpec)
                .filter(document("{ClassName}/{methodName}",
                        preprocessRequest(modifyUris().scheme("https").host("www.simonjamesrowe.com").removePort()),
                        preprocessResponse(prettyPrint()),
                        requestFields(fieldWithPath("id").description("The id of the record"),
                                fieldWithPath("a_string").description("The A String meaning blah blah blah"),
                                fieldWithPath(".values").description(" A list of random values")),
                        responseFields(DEMO_RESPONSE_ENTITY_FIELDS)))
                .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_JSON.toString(), ContentType.JSON)))
                .when()
                .contentType(MediaType.APPLICATION_JSON_UTF8.toString())
                .body("{\n" +
                        "\t\"id\" : 1,\n" +
                        "\t\"a_string\" : \"Some simple string\",\n" +
                        "\t\"values\" : [\"a\", \"c\", \"d\"]\n" +
                        "}")
                .post("/api/v1/entity/similar")
                .then()
                .assertThat().statusCode(is(200));

    }


}
