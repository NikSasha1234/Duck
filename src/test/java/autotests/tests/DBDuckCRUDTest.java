package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Message;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
@Epic("Тестовый класс с действиями из duck-controller и проверкой записей в БД")
public class DBDuckCRUDTest extends DuckClient {
    @Description("Проверка создания уточки")
    @CitrusTest
    @Test(description = "Метод запроса: POST; Действие: create", enabled = true)
    public void createDuckDB(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        // Очистка БД
         deleteDuckFinally(runner, "delete from duck");

        // Создание 1 объекта
        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");

        // Валидация ответа и извлечение переменных
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId"))
                .extract(fromBody().expression("$.color", "duckColor"))
                .extract(fromBody().expression("$.height", "duckHeight"))
                .extract(fromBody().expression("$.material", "duckMaterial"))
                .extract(fromBody().expression("$.sound", "duckSound"))
                .extract(fromBody().expression("$.wingsState", "duckWings"))
                .body("{\n" +
                        "  \"id\": ${duckId},\n" +
                        "  \"color\": \"yellow\",\n" +
                        "  \"height\": 1.0,\n" +
                        "  \"material\": \"rubber\",\n" +
                        "  \"sound\": \"quack\",\n" +
                        "  \"wingsState\": \"FIXED\"\n" +
                        "}"));
        // Валидация созданного объекта в БД
        validateDuckInDatabase(runner, "${duckId}", "${duckColor}", "${duckHeight}", "${duckMaterial}", "${duckSound}", "${duckWings}");
    }
    @Description("Проверка обновления уточки")
    @CitrusTest
    @Test(description = "Метод запроса: PUT; Действие: update", enabled = true)
    public void updateDuckDB(@Optional @CitrusResource TestCaseRunner runner) {
        Message message = new Message().message("Duck with id = " + "${duckId}" + " is updated");

        deleteDuckFinally(runner, "delete from duck");

        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");
        duckIdExtract(runner);
        validateDuckInDatabase(runner, "${duckId}", "yellow", "1.0", "rubber", "quack", "FIXED");

        updateDuck(runner, "black", "50.0", "${duckId}", "metallic", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "black", "50.0", "metallic", "quack", "ACTIVE");
        validateResponsePay(runner, message, HttpStatus.OK);
    }
    @Description("Проверка получения списка id уточек с 1 уточкой")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: getAllIds", enabled = true)
    public void listIdDuckDB1(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteDuckFinally(runner, "delete from duck;");

        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");

        duckIdExtract(runner);

        runner.variable("id1", "${duckId}");
        Integer id1 = Integer.valueOf(context.getVariable("id1"));

        listDuckId(runner);

        validateResponsePay(runner, "[\n" +
                "  " + id1 + "\n" +
                "]", HttpStatus.OK);
    }
    @Description("Проверка получения списка id уточек из пустой БД")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: getAllIds", enabled = true)
    public void listIdDuckDB2(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteDuckFinally(runner, "delete from duck;");
        listDuckId(runner);
        validateResponsePay(runner, "[]", HttpStatus.OK);
    }
}
