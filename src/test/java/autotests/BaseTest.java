package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.dsl.JsonPathSupport;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import com.consol.citrus.http.client.HttpClient;
import autotests.EndpointConfig;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})

public class BaseTest extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient yellowDuckService;
    @Autowired
    protected SingleConnectionDataSource testDb;

    protected void databaseSQL(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb).statement(sql));
    }

    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("select * from duck where ID = " + id + ";")
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }
    protected void deleteDuckFinally(TestCaseRunner runner, String sql) {
        runner.$(doFinally().actions(runner.$(sql(testDb).statement(sql))));
    }
    protected void deleteDuckFinally(TestCaseRunner runner, HttpClient URL, String path, String id) {
        runner.$(doFinally().actions(http().client(URL)
                .send()
                .delete(path)
                .queryParam("id", id)));
    }

    protected void sendGetRequestWithParam(TestCaseRunner runner, HttpClient URL, String path, String nameQueryParam, String valueQueryParam) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path)
                .queryParam(nameQueryParam, valueQueryParam));
    }

    protected void sendGetRequest(TestCaseRunner runner, HttpClient URL, String path) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path));
    }

    protected void sendPostRequestString(TestCaseRunner runner, HttpClient URL, String path, String body) {
        runner.$(http()
                .client(URL)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body));
    }

    protected void sendPostRequestResources(TestCaseRunner runner, HttpClient URL, String path, String body) {
        runner.$(http()
                .client(URL)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(body)));
    }

    protected void sendPostRequestPayload(TestCaseRunner runner, HttpClient URL, String path, Object body) {
        runner.$(http()
                .client(URL)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient URL, String path, String nameQueryParam, String valueQueryParam) {
        runner.$(http()
                .client(URL)
                .send()
                .delete(path)
                .queryParam(nameQueryParam, valueQueryParam));
    }

    protected void validateResponseString(TestCaseRunner runner, HttpClient URL, String body, HttpStatus status) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(body));
    }

    protected void validateResponseResources(TestCaseRunner runner, HttpClient URL, String body, HttpStatus status) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(body)));
    }

    protected void validateResponsePayload(TestCaseRunner runner, HttpClient URL, Object body, HttpStatus status) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    protected void validateResponseJsonPath(TestCaseRunner runner, HttpClient URL, JsonPathSupport body, HttpStatus status) {
        runner.$(http().client(URL)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .validate(body));
    }
    protected void receiveListDuckId (TestCaseRunner runner, HttpClient URL, HttpStatus status){
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message());
    }

    protected void receiveDuckId(TestCaseRunner runner, HttpClient URL, HttpStatus status, String pathValue, String variableName) {
        runner.$(http().client(URL)
                .receive()
                .response(status)
                .message()
                .extract(fromBody().expression(pathValue, variableName)
                ));
    }
}
