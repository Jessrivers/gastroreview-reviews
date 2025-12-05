package WebSiters.reviews.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "review_images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewImage {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "review_id", nullable = false)
    private UUID reviewId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "moderation_status")
    private String moderationStatus;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private OffsetDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}
