package shared.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.time.LocalDateTime;

import static play.libs.Json.toJson;
import static play.mvc.Http.Status.BAD_REQUEST;

@Getter
public final class BadRequestException extends RuntimeException {

    private BadRequestPayload badRequestPayload;

    public BadRequestException(String message) {
        super(message);

        badRequestPayload = BadRequestPayload.builder()
                .status(BAD_REQUEST)
                .timestamp(LocalDateTime.now().toString())
                .message(message)
                .build();
    }

    public JsonNode asJson() {
        return toJson(badRequestPayload);
    }

}
