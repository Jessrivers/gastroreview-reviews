package WebSiters.reviews.client;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String name;
}
