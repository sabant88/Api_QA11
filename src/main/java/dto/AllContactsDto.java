package dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor

public class AllContactsDto {
    List<ContactDto> contacts;
}
