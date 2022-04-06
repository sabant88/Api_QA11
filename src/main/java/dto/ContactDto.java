package dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor

public class ContactDto {
    String address;
    String description;
    String email;
    String lastName;
    String name;
    String phone;
    int id;
}
