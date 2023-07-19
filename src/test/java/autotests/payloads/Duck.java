package autotests.payloads;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
@Setter //установка значения свойств
@Accessors(fluent = true) //создание методов доступа (геттеров и сеттеров)
@JsonInclude(JsonInclude.Include.NON_NULL) //игнорирование свойств объекта равных null
public class Duck {
    @JsonProperty
    private int id;

    @JsonProperty
    private String color;

    @JsonProperty
    private double height;

    @JsonProperty
    private String material;

    @JsonProperty
    private String sound;

    @JsonProperty
    private String wingsState;
}
