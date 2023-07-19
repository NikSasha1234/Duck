package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})

public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient yellowDuckService;

    @Description("Эндпоинт, заставляющий уточку лететь")
    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    @Description("Эндпоинт, показывающий характеристики уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    @Description("Эндпоинт, заставляющий уточку крякать")
    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    @Description("Эндпоинт, заставляющий уточку плыть")
    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    @Description("Валидация полученного ответа для метода, заставляющего уточку лететь, с передачей ожидаемого тела строкой")
    public void validateResponseOfDuckFly(TestCaseRunner runner) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        "  \"message\": \"I am flying :)\"\n" +
                        "}"));
    }
    @Description("Валидация полученного ответа для метода, заставляющего уточку крякать, с передачей ожидаемого тела из папки resources")
    public void validateResponseOfDuckQuack(TestCaseRunner runner) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource("getDuckQuackTest\\duckYellowQuack.json")));
    }
    @Description("Валидация полученного ответа для метода, показывающего характеристики уточки, с передачей ожидаемого тела из папки Payload")
    public void validateResponseOfDuckProperties(TestCaseRunner runner, DuckProperties duckProperties) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(duckProperties, new ObjectMapper())));
    }

}
