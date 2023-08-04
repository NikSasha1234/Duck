package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
@Epic("Тестовый класс, проверящий создание уточки")
public class DuckCreateTest extends DuckClient {
    Duck duck1 = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState("FIXED");
    Duck duck2 = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState("FIXED");
    Duck duck3 = new Duck().color("light green").height(100.0).material("").sound("quack").wingsState("ACTIVE");
    Duck duck4 = new Duck().color("").height(50.3).material("WOODEN").sound("quack").wingsState("ACTIVE");
    Duck duck5 = new Duck().color("RED").height(0.5).material("STEEL").sound("").wingsState("FIXED");
    Duck duck6 = new Duck().color("bright-yellow").height(100.0).material("man-made fibres").sound("quack-quack").wingsState("ACTIVE");
    @Description("Параметризированный тест для проверки создания уточки")
    @Test(dataProvider = "duckList", description = "Метод запроса: POST; Действие: create")
    @CitrusTest
    @CitrusParameters({"payload", "response", "HttpStatus", "runner"})
    @Flaky
    public void createDuckList(Object payload, String response, HttpStatus status, @Optional @CitrusResource TestCaseRunner runner) {
        createDuckPayload(runner, payload);
        validateResponseRes(runner, response, status);
    }
    @DataProvider(name = "duckList")
    public Object[][] DataProvider() {
        return new Object[][]{
                {duck1, "getCreateDuckTest/CreateYellowDuck.json", HttpStatus.OK, null},
                {duck2, "getCreateDuckTest/CreateYellowDuck.json", HttpStatus.OK, null},
                {duck3, "getCreateDuckTest/CreateLightGreenDuck.json", HttpStatus.OK, null},
                {duck4, "getCreateDuckTest/CreateEmptyColorDuck.json", HttpStatus.OK, null},
                {duck5, "getCreateDuckTest/CreateRedDuck.json", HttpStatus.OK, null},
                {duck6, "getCreateDuckTest/CreateBrightYellowDuck.json", HttpStatus.BAD_REQUEST, null}
        };
    }
}
