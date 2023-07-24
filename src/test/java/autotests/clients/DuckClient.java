package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestAction;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.dsl.JsonPathSupport;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckClient extends BaseTest {
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
        validateResponseResources(runner, yellowDuckService, body, status);
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

    @Description("Эндпоинт, удаляющий уточку")
    public void deleteDuck(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", id);
    }

    @Description("Эндпоинт для получения списка id всех уточек")
    public void listDuckId(TestCaseRunner runner) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/getAllIds");
    }

    @Description("Эндпоинт для обновления характеристик уточки")
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

    @Description("Эндпоинт, получающий id уточки")
    public void duckIdExtract(TestCaseRunner runner) {
        receiveDuckId(runner, yellowDuckService, HttpStatus.OK, "$.id", "duckId");
    }
}
