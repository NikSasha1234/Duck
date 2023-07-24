package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckClient {
    Duck duck1 = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState("FIXED");
    Duck duck2 = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState("FIXED");
    Duck duck3 = new Duck().color("light green").height(100.0).material("").sound("quack").wingsState("ACTIVE");
    Duck duck4 = new Duck().color("").height(50.3).material("WOODEN").sound("quack").wingsState("ACTIVE");
    Duck duck5 = new Duck().color("RED").height(0.5).material("STEEL").sound("").wingsState("FIXED");
    Duck duck6 = new Duck().color("bright-yellow").height(100.0).material("man-made fibres").sound("quack-quack").wingsState("ACTIVE");

    @Test(dataProvider = "duckList1")
    @CitrusTest
    @CitrusParameters({"payload", "response", "HttpStatus", "runner"})
    public void createDuckList1(Object payload, String response, HttpStatus status, @Optional @CitrusResource TestCaseRunner runner) {
        createDuckPayload(runner, payload);
        validateResponseRes(runner, response, status);
    }
    @DataProvider(name = "duckList1")
    public Object[][] DataProvider1() {
        return new Object[][]{
                {duck1, "getCreateDuckTest/CreateYellowDuck.json", HttpStatus.OK, null},
                {duck2, "getCreateDuckTest/CreateYellowDuck.json", HttpStatus.OK, null},
                {duck3, "getCreateDuckTest/CreateLightGreenDuck.json", HttpStatus.OK, null},
                {duck4, "getCreateDuckTest/CreateEmptyColorDuck.json", HttpStatus.OK, null},
                {duck5, "getCreateDuckTest/CreateRedDuck.json", HttpStatus.OK, null}
        };
    }
    //Проверка, что при значении sound != "quack" || "" получаем BAD_REQUEST
    @Test(dataProvider = "duckList2")
    @CitrusTest
    @CitrusParameters({"payload", "response", "runner", "HttpStatus"})
    public void createDuckList2(Object payload, String response, HttpStatus status, @Optional @CitrusResource TestCaseRunner runner) {
        createDuckPayload(runner, payload);
        validateResponseRes(runner, response, status);
    }
    @DataProvider(name = "duckList2")
    public Object[][] DataProvider2() {
        return new Object[][]{
                {duck6, "getCreateDuckTest/CreateBrightYellowDuck.json", HttpStatus.BAD_REQUEST, null}
        };
    }
}
