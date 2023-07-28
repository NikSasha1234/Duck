package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Message;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тестовый класс с действиями из duck-controller")
public class DuckRUDTest extends DuckClient {

    @Description("Проверка получения списка id уточек из пустой БД")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: getAllIds", priority = 0)
    public void successfulListDuckId1(@Optional @CitrusResource TestCaseRunner runner) {
        // Очистка БД
        deleteDuckFinally(runner, "delete from duck");

        listDuckId(runner);
        validateResponsePay(runner, "[]", HttpStatus.OK);
    }

    @Description("Проверка получения списка id уточек с 1 уточкой")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: getAllIds", priority = 0)
    public void successfulListDuckId2(@Optional @CitrusResource TestCaseRunner runner) {
        // Очистка БД
        deleteDuckFinally(runner, "delete from duck");

        deleteDuckFinal(runner, "${duckId}");

        createDuckString(runner, "{\n" +
                "  \"color\": \"bright-yellow\",\n" +
                "  \"height\": 100.0,\n" +
                "  \"material\": \"man-made fibres\",\n" +
                "  \"sound\": \"quack-quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
        duckIdExtract(runner);
        listDuckId(runner);
        validateResponsePay(runner, "[\n" + "  ${duckId}\n" + "]", HttpStatus.OK);
    }

    @Description("Проверка получения списка id уточек с 2 уточками")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: getAllIds", priority = 0)
    public void successfulListDuckId3(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        // Очистка БД
        deleteDuckFinally(runner, "delete from duck");

        deleteDuckFinal(runner, "${id1}");
        deleteDuckFinal(runner, "${id2}");

        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");

        duckIdExtract(runner);
        runner.variable("id1", "${duckId}");
        Integer id1 = Integer.valueOf(context.getVariable("id1"));


        createDuckResources(runner, "getCreateDuckTest/CreateRedDuck.json");

        duckIdExtract(runner);
        runner.variable("id2", "${duckId}");
        Integer id2 = Integer.valueOf(context.getVariable("id2"));

        listDuckId(runner);

        validateResponsePay(runner, "[\n" +
                "  " + id1 + "," + "\n" +
                "  " + id2 + "\n" +
                "]", HttpStatus.OK);
    }

    @Description("Проверка обновления уточки")
    @CitrusTest
    @Test(description = "Метод запроса: PUT; Действие: update", priority = 1)
    public void successfulUpdate1(@Optional @CitrusResource TestCaseRunner runner) {
        Message message = new Message().message("Duck with id = " + "${duckId}" + " is updated");

        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest/CreateLightGreenDuck.json");
        duckIdExtract(runner);
        updateDuck(runner, "yellow", "0.01", "${duckId}", "rubber", "quack", "ACTIVE");
        validateResponsePay(runner, message, HttpStatus.OK);
    }

    @Description("Проверка обновления характиристик уточки с установкой недопустимого значения поля sound")
    @CitrusTest
    @Test(description = "Метод запроса: PUT; Действие: update", priority = 1)
    @Flaky
    public void successfulUpdate2(@Optional @CitrusResource TestCaseRunner runner) {
        Message message = new Message().message("Incorrect sound value");

        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest/CreateLightGreenDuck.json");
        duckIdExtract(runner);
        updateDuck(runner, "yellow", "0.01", "${duckId}", "rubber", "meow", "ACTIVE");
        validateResponsePay(runner, message, HttpStatus.BAD_REQUEST);
    }

    @Description("Проверка удаления уточки")
    @CitrusTest
    @Test(description = "Метод запроса: DELETE; Действие: delete", priority = 2)
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");
        duckIdExtract(runner);
        deleteDuck(runner, "${duckId}");
        validateResponsePay(runner, "{\n" +
                "  \"message\": \"Duck is deleted\"\n" +
                "}", HttpStatus.OK);
    }
}