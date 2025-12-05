package WebSiters.reviews.client;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RestaurantDto {
    private UUID id;
    private String name;
    private String description;
    private Double rating;
}
