package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.dsl.JsonPathSupport;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionsClient extends BaseTest {
    @Step("Создание уточки через строку")
    public void createDuckString(TestCaseRunner runner, String body) {
        sendPostRequestString(runner, yellowDuckService, "/api/duck/create", body);
    }

    @Step("Создание уточки через resources")
    public void createDuckResources(TestCaseRunner runner, String body) {
        sendPostRequestResources(runner, yellowDuckService, "/api/duck/create", body);
    }

    @Step("Создание уточки через payload")
    public void createDuckPayload(TestCaseRunner runner, Object body) {
        sendPostRequestPayload(runner, yellowDuckService, "/api/duck/create", body);
    }

    @Step("Валидация ответа через строку")
    public void validateResponseStr(TestCaseRunner runner, String body, HttpStatus status) {
        validateResponseString(runner, yellowDuckService, body, status);
    }

    @Step("Валидация ответа через resources")
    public void validateResponseRes(TestCaseRunner runner, String body, HttpStatus status) {
        validateResponseResources(runner, yellowDuckService, body, status);
    }

    @Step("Валидация ответа через payload")
    public void validateResponsePay(TestCaseRunner runner, Object body, HttpStatus status) {
        validateResponsePayload(runner, yellowDuckService, body, status);
    }

    @Step("Валидация ответа по JsonPath")
    public void validateResponseJP(TestCaseRunner runner, JsonPathSupport body, HttpStatus status) {
        validateResponseJsonPath(runner, yellowDuckService, body, status);
    }

    @Step("Уточка летит")
    public void duckFly(TestCaseRunner runner, String id) {
        sendGetRequestWithParam(runner, yellowDuckService, "/api/duck/action/fly", "id", id);
    }

    @Step("Уточка показывает характеристики")
    public void duckProperties(TestCaseRunner runner, String id) {
        sendGetRequestWithParam(runner, yellowDuckService, "/api/duck/action/properties", "id", id);
    }

    @Step("Уточка плывет")
    public void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequestWithParam(runner, yellowDuckService, "/api/duck/action/swim", "id", id);
    }

    @Step("Уточка крякает")
    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    @Step("Получение id уточки")
    public void duckIdExtract(TestCaseRunner runner) {
        receiveDuckId(runner, yellowDuckService, HttpStatus.OK, "$.id", "duckId");
    }

    @Step("Удаление уточки через блок Finally")
    public void deleteDuckFinal(TestCaseRunner runner, String id) {
        deleteDuckFinally(runner, yellowDuckService, "/api/duck/delete", id);
    }
}