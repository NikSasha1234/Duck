package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.dsl.JsonPathSupport;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckClient extends BaseTest {
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

    @Step("Удаление уточки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", id);
    }

    @Step("Удаление уточки через блок Finally")
    public void deleteDuckFinal(TestCaseRunner runner, String id) {
        deleteDuckFinally(runner, yellowDuckService, "/api/duck/delete", id);
    }

    @Step("Получение списка id уточек")
    public void listDuckId(TestCaseRunner runner) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/getAllIds");
    }

    @Step("Обновление характеристик уточки")
    public void updateDuck(TestCaseRunner runner, String color, String height, String id, String material, String sound, String wingsState) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState)
        );
    }

    @Step("Получение id уточки")
    public void duckIdExtract(TestCaseRunner runner) {
        receiveDuckId(runner, yellowDuckService, HttpStatus.OK, "$.id", "duckId");
    }
}
