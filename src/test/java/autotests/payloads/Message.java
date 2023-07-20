package autotests.payloads;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
@Setter //установка значения свойств
@Accessors(fluent = true) //создание методов доступа (геттеров и сеттеров)

public class Message {
    @JsonProperty
    private String message;
}
