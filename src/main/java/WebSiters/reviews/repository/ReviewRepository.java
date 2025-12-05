package WebSiters.reviews.repository;

import WebSiters.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByRestaurantIdOrderByCreatedAtDesc(UUID restaurantId);
    List<Review> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
