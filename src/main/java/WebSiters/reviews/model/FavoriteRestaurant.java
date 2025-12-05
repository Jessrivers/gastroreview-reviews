package WebSiters.reviews.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "favorite_restaurants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FavoriteRestaurant {

    @EmbeddedId
    private FavoriteRestaurantId id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Embeddable
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class FavoriteRestaurantId implements java.io.Serializable {
        @JdbcTypeCode(SqlTypes.UUID)
        private UUID userId;

        @JdbcTypeCode(SqlTypes.UUID)
        private UUID restaurantId;
    }
}
