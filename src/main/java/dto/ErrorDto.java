package dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor

public class ErrorDto {
    int code;
    String details;
    String message;
}
