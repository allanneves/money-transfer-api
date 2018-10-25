package shared.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
final class BadRequestPayload {

    private String timestamp;
    private Integer status;
    private String message;
}
