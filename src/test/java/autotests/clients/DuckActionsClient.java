package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.dsl.JsonPathSupport;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionsClient extends BaseTest {

    @Description("Эндпоинт, создающий уточку")
    public void createDuckString(TestCaseRunner runner, String body) {
        sendPostRequestString(runner, yellowDuckService, "/api/duck/create", body);
    }

    @Description("Метод отправки запроса на создание уточки с использованием файла в папке resources")
    public void createDuckResources(TestCaseRunner runner, String body) {
        sendPostRequestResources(runner, yellowDuckService, "/api/duck/create", body);
    }

    @Description("Метод отправки запроса на создание уточки с использованием файла в папке resources")
    public void createDuckPayload(TestCaseRunner runner, Object body) {
        sendPostRequestPayload(runner, yellowDuckService, "/api/duck/create", body);
    }

    @Description("Валидация полученного ответа с передачей ожидаемого тела строкой")
    public void validateResponseStr(TestCaseRunner runner, String body, HttpStatus status) {
        validateResponseString(runner, yellowDuckService, body, status);
    }

    @Description("Валидация полученного ответа с передачей ожидаемого тела из папки resources")
    public void validateResponseRes(TestCaseRunner runner, String body, HttpStatus status) {
        validateResponseResources(runner, yellowDuckService, body, status);
    }

    @Description("Валидация полученного ответа с передачей ожидаемого тела из папки payload")
    public void validateResponsePay(TestCaseRunner runner, Object body, HttpStatus status) {
        validateResponsePayload(runner, yellowDuckService, body, status);
    }

    @Description("Валидация по JsonPath")
    public void validateResponseJP(TestCaseRunner runner, JsonPathSupport body, HttpStatus status) {
        validateResponseJsonPath(runner, yellowDuckService, body, status);
    }

    @Description("Эндпоинт, заставляющий уточку лететь")
    public void duckFly(TestCaseRunner runner, String id) {
        sendGetRequestWithParam(runner, yellowDuckService, "/api/duck/action/fly", "id", id);
    }

    @Description("Эндпоинт, показывающий характеристики уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        sendGetRequestWithParam(runner, yellowDuckService, "/api/duck/action/properties", "id", id);
    }

    @Description("Эндпоинт, заставляющий уточку плыть")
    public void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequestWithParam(runner, yellowDuckService, "/api/duck/action/swim", "id", id);
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
    @Description("Эндпоинт, получающий id уточки")
    public void duckIdExtract(TestCaseRunner runner) {
        receiveDuckId(runner, yellowDuckService, HttpStatus.OK, "$.id", "duckId");
    }
    @Description("Эндпоинт, удаляющий уточку в блоке Finally")
    public void deleteDuckFinal(TestCaseRunner runner, String id) {
        deleteDuckFinally(runner, yellowDuckService, "/api/duck/delete", id);
    }
}