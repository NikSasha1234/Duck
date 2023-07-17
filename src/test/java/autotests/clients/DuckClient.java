package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})

public class DuckClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient yellowDuckService;

    @Description("Эндпоинт, создающий уточку")
    public void createDuck(TestCaseRunner runner, String color, String height, String material, String sound, String wingsState) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"color\": " + "\"" + color + "\",\n" +
                        "  \"height\": " + height + ",\n" +
                        "  \"material\": " + "\"" + material + "\",\n" +
                        "  \"sound\": " + "\"" + sound + "\",\n" +
                        "  \"wingsState\": " + "\"" + wingsState + "\"\n" +
                        "}"));
    }

    @Description("Эндпоинт, удаляющий уточку")
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    @Description("Эндпоинт для получения списка id всех уточек")
    public void listDuckId(TestCaseRunner runner) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/getAllIds"));
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
}
