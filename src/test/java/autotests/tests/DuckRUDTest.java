package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Message;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
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
        createDuckString(runner, "{\n" +
                "  \"color\": \"bright-yellow\",\n" +
                "  \"height\": 100.0,\n" +
                "  \"material\": \"man-made fibres\",\n" +
                "  \"sound\": \"quack-quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
        listDuckId(runner);
        validateResponseStr(runner, "[\n" + "  29\n" + "]", HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка вывода списка id уточек с 3 уточками", priority = 0)
    public void successfulListDuckId3(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");
        createDuckResources(runner, "getCreateDuckTest/CreateRedDuck.json");
        listDuckId(runner);
        validateResponsePay(runner, "[\n" +
                "  1,\n" +
                "  2,\n" +
                "  3\n" +
                "]", HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка обновления характиристик уточки", priority = 1)
    public void successfulUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckResources(runner, "getCreateDuckTest/CreateLightGreenDuck.json");
        duckIdExtract(runner);
        updateDuck(runner, "yellow", "0.01", "${duckId}", "rubber", "quack", "ACTIVE");
        Message message = new Message().message("Duck with id = " + "${duckId}" + " is updated");
        validateResponsePay(runner, message, HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка обновления характиристик уточки с установкой недопустимого значения поля sound", priority = 1)
    public void successfulUpdate2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckResources(runner, "getCreateDuckTest/CreateLightGreenDuck.json");
        duckIdExtract(runner);
        updateDuck(runner, "yellow", "0.01", "${duckId}", "rubber", "meow", "ACTIVE");
        Message message = new Message().message("Incorrect sound value");
        validateResponsePay(runner, message, HttpStatus.BAD_REQUEST);
    }

    @CitrusTest
    @Test(description = "Проверка удаления уточки", priority = 2)
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");
        duckIdExtract(runner);
        deleteDuck(runner, "${duckId}");
        validateResponsePay(runner, "{\n" +
                "  \"message\": \"Duck is deleted\"\n" +
                "}", HttpStatus.OK);
    }
}