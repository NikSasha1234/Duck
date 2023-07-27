package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.context.TestContext;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;

public class DuckActionTest extends DuckActionsClient {
    Duck duck = new Duck().color("RED").height(0.05).material("STEEL").sound("quack").wingsState("ACTIVE");

    @CitrusTest
    @Test(description = "Проверка того, что уточка полетела, значение поля wingsState = ACTIVE", enabled = true)
    public void successfulFly1(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest\\requestDuckCreate.json");
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckFly(runner, "${duckId}");
        validateResponseJP(runner, jsonPath().expression("$.message", "I'm flying"), HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка того, что уточка полетела, значение поля wingsState = FIXED", enabled = true)
    public void successfulFly2(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest/CreateYellowDuck.json");
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckFly(runner, "${duckId}");
        validateResponseJP(runner, jsonPath().expression("$.message", "I can’t fly"), HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка того, что показываются свойства уточки, значение поля height = 0.0, значение поля material = rubber", enabled = true)
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

    @CitrusTest
    @Test(description = "Проверка того, что показываются свойства уточки, значение поля material = wooden", enabled = true)
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

    @CitrusTest
    @Test(description = "Проверка того, что показываются свойства уточки, значение поля height = 10.0", enabled = true)
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

    @CitrusTest
    @Test(description = "Проверка способности уточки крякать, количество звуков = 1, количество повторений = 1", enabled = true)
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

    @CitrusTest
    @Test(description = "Проверка способности уточки крякать, количество звуков = 5, количество повторений = 1", enabled = true)
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

    @CitrusTest
    @Test(description = "Проверка способности уточки крякать, количество звуков = 1, количество повторений = 5", enabled = true)
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

    @CitrusTest
    @Test(description = "Проверка способности уточки крякать, при отправке пустого поля sound", enabled = true)
    public void successfulQuack4(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckPayload(runner, duck);
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckQuack(runner, "${duckId}", "1", "1");
        validateResponseStr(runner, "{\n" +
                "  \"sound\": \"\"\n" +
                "}", HttpStatus.OK);
    }

    @CitrusTest
    @Test(description = "Проверка того, что уточка поплыла", enabled = true)
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFinal(runner, "${duckId}");

        createDuckResources(runner, "getCreateDuckTest\\requestDuckCreate.json");
        duckIdExtract(runner);
        runner.$(echo("duckId: \"${duckId}\""));
        duckSwim(runner, "${duckId}");
        validateResponseRes(runner, "getCreateDuckTest\\responseMessageSwim.json", HttpStatus.OK);
    }
}

