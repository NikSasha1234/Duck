package autotests;
import autotests.payloads.Duck;
import autotests.payloads.DuckProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
public class Main {
    public static void main(String[] args) {

        //Создаем объект duckUpdate класса Duck
        Duck duckUpdate = new Duck()
                .id(1)
                .color("red")
                .height(3)
                .material("wood")
                .sound("crya-crya")
                .wingsState("FIXED");

        //Создаем объект duckProperties класса DuckProperties
        DuckProperties duckProperties = new DuckProperties()
                .color("yellow")
                .height(5)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        //Создаем объект createDuck класса DuckProperties
        DuckProperties createDuck = new DuckProperties()
                .color("silver")
                .height(10)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        //Передаем объект в метод преобразования объекта класса в Json
        convertAndPrintToJson(duckUpdate);
        convertAndPrintToJson(duckProperties);
        convertAndPrintToJson(createDuck);
    }


    //метод для преобразования объекта класса в Json
    public static void convertAndPrintToJson(Object jsonBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            String json = objectWriter.writeValueAsString(jsonBody);

            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
