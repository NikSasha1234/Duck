package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Message;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckRUDTest extends DuckClient {

    // Пробовала сделать по-другому, чтобы не передавать в теле ответ, ничего не сработало. Тест получился "одноразовый". Оставила так.
    @CitrusTest
    @Test(description = "Проверка вывода списка id уточек из пустой БД", priority = 0)
    public void successfulListDuckId1(@Optional @CitrusResource TestCaseRunner runner) {
        listDuckId(runner);
        validateResponsePay(runner, "[]", HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка вывода списка id уточек с 1 уточкой", priority = 0)
    public void successfulListDuckId2(@Optional @CitrusResource TestCaseRunner runner) {
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

    @CitrusTest
    @Test(description = "Проверка вывода списка id уточек с 2 уточками", priority = 0)
    public void successfulListDuckId3(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
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

    @CitrusTest
    @Test(description = "Проверка обновления характиристик уточки", priority = 1)
    public void successfulUpdate1(@Optional @CitrusResource TestCaseRunner runner) {
        Message message = new Message().message("Duck with id = " + "${duckId}" + " is updated");

        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest/CreateLightGreenDuck.json");
        duckIdExtract(runner);
        updateDuck(runner, "yellow", "0.01", "${duckId}", "rubber", "quack", "ACTIVE");
        validateResponsePay(runner, message, HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка обновления характиристик уточки с установкой недопустимого значения поля sound", priority = 1)
    public void successfulUpdate2(@Optional @CitrusResource TestCaseRunner runner) {
        Message message = new Message().message("Incorrect sound value");

        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest/CreateLightGreenDuck.json");
        duckIdExtract(runner);
        updateDuck(runner, "yellow", "0.01", "${duckId}", "rubber", "meow", "ACTIVE");
        validateResponsePay(runner, message, HttpStatus.BAD_REQUEST);
    }

    @CitrusTest
    @Test(description = "Проверка удаления уточки", priority = 2)
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