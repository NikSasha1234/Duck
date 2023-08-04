package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Flaky;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
@Epic("Тестовый класс с действиями из duck-action-controller и проверкой записей в БД")
public class DBDuckActionTest extends DuckActionsClient {
    @Description("Проверка способности уточки плыть")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: swim", enabled = true)
    @Flaky
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinally(runner, "delete from duck");

        runner.variable("duckId", "citrus:randomNumber(3, true)");

        databaseSQL(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" + "values (${duckId}, 'RED', '0.05', 'STEEL', 'quack', 'ACTIVE');");
        duckSwim(runner, "${duckId}");
        validateResponseRes(runner, "getCreateDuckTest\\responseMessageSwim.json", HttpStatus.OK);
    }
    @Description("Проверка способности уточки показывать свойства")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: properties", enabled = true)
    @Flaky
    public void successfulProperties(@Optional @CitrusResource TestCaseRunner runner) {
        // Очистка БД
        deleteDuckFinally(runner, "delete from duck");

        // Создание записи в БД с 1 объектом
        runner.variable("rndId", "citrus:randomNumber(3, true)");
        databaseSQL(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" + "values (${rndId}, 'yellow', '10.0', 'rubber', 'quack', 'ACTIVE');");

        // Проверка созданной записи в БД
        validateDuckInDatabase(runner, "${rndId}", "yellow", "10.0", "rubber", "quack", "ACTIVE");

        // Обращение к эндпоинту метода показа характеристик
        duckProperties(runner, "${rndId}");

        // Проверка ответа от эндпоинта метода показа характеристик
        validateResponseStr(runner, "{\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 10.0,\n" +
                "  \"material\": \"rubber\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}", HttpStatus.OK);
    }
}
