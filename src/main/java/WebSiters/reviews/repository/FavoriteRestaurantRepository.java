package WebSiters.reviews.repository;

import WebSiters.reviews.model.FavoriteRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurant, FavoriteRestaurant.FavoriteRestaurantId> {
    List<FavoriteRestaurant> findByIdUserId(UUID userId);
}
