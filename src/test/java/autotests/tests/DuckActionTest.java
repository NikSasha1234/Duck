package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.context.TestContext;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
@Epic("Тестовый класс с действиями из duck-action-controller")
public class DuckActionTest extends DuckActionsClient {
    Duck duck = new Duck().color("RED").height(0.05).material("STEEL").sound("quack").wingsState("ACTIVE");
    @Description("Проверка способности уточки летать при значении поля wingsState = ACTIVE")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: fly", enabled = true)
    @Flaky
    public void successfulFly1(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest\\requestDuckCreate.json");
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckFly(runner, "${duckId}");
        validateResponseJP(runner, jsonPath().expression("$.message", "I'm flying"), HttpStatus.OK);
    }
    @Description("Проверка способности уточки летать при значении поля wingsState = FIXED")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: fly", enabled = true)
    @Flaky
    public void successfulFly2(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckFly(runner, "${duckId}");
        validateResponseJP(runner, jsonPath().expression("$.message", "I can’t fly"), HttpStatus.OK);
    }
    @Description("Проверка способности уточки показывать свойства при значении поля height = 0.0, значении поля material = rubber")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: properties", enabled = true)
    public void successfulProperties1(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteDuckFinal(runner, "${duckId}");

        runner.variable("height", "0.0");
        Double height = Double.valueOf(context.getVariable("height"));

        Duck duck = new Duck().color("yellow")
                .height(height)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuckPayload(runner, duck);
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckProperties(runner, "${duckId}");
        validateResponsePay(runner, duck, HttpStatus.OK);
    }
    @Description("Проверка способности уточки показывать свойства при значении поля material = wooden")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: properties", enabled = true)
    @Flaky
    public void successfulProperties2(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteDuckFinal(runner, "${duckId}");

        runner.variable("height", "3.0");
        Double height = Double.valueOf(context.getVariable("height"));

        Duck duck = new Duck().color("yellow")
                .height(height)
                .material("wooden")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuckPayload(runner, duck);
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckProperties(runner, "${duckId}");
        validateResponsePay(runner, duck, HttpStatus.OK);
    }
    @Description("Проверка способности уточки показывать свойства при значении поля height = 10.0")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: properties", enabled = true)
    @Flaky
    public void successfulProperties3(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteDuckFinal(runner, "${duckId}");

        runner.variable("height", "10.0");
        Double height = Double.valueOf(context.getVariable("height"));

        Duck duck = new Duck().color("red")
                .height(height)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuckPayload(runner, duck);
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckProperties(runner, "${duckId}");
        validateResponsePay(runner, duck, HttpStatus.OK);
    }
    @Description("Проверка способности уточки крякать, количество звуков = 1, количество повторений = 1")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: quack", enabled = true)
    @Flaky
    public void successfulQuack1(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckPayload(runner, duck);
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckQuack(runner, "${duckId}", "1", "1");
        validateResponseStr(runner, "{\n" +
                "  \"sound\": \"quack\"\n" +
                "}", HttpStatus.OK);
    }
    @Description("Проверка способности уточки крякать, количество звуков = 5, количество повторений = 1")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: quack", enabled = true)
    @Flaky
    public void successfulQuack2(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckPayload(runner, duck);
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckQuack(runner, "${duckId}", "5", "1");
        validateResponseStr(runner, "{\n" +
                "  \"sound\": \"quack, quack, quack, quack, quack\"\n" +
                "}", HttpStatus.OK);
    }
    @Description("Проверка способности уточки крякать, количество звуков = 1, количество повторений = 5")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: quack", enabled = true)
    @Flaky
    public void successfulQuack3(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckPayload(runner, duck);
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckQuack(runner, "${duckId}", "1", "5");
        validateResponseStr(runner, "{\n" +
                "  \"sound\": \"quack-quack-quack-quack-quack\"\n" +
                "}", HttpStatus.OK);
    }
    @Description("Проверка способности уточки крякать, при отправке пустого поля sound")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: quack", enabled = true)
    @Flaky
    public void successfulQuack4(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        Duck duck1 = new Duck().color("RED").height(0.5).material("STEEL").sound("").wingsState("FIXED");

        createDuckPayload(runner, duck1);

        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckQuack(runner, "${duckId}", "1", "1");
        validateResponseStr(runner, "{\n" +
                "  \"sound\": \"\"\n" +
                "}", HttpStatus.OK);
    }
    @Description("Проверка способности уточки плыть")
    @CitrusTest
    @Test(description = "Метод запроса: GET; Действие: swim", enabled = true)
    @Flaky
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest\\requestDuckCreate.json");
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckSwim(runner, "${duckId}");
        validateResponseRes(runner, "getCreateDuckTest\\responseMessageSwim.json", HttpStatus.OK);
    }
}

