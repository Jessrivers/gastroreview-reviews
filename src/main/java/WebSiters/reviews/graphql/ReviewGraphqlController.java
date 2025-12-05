package WebSiters.reviews.graphql;

import WebSiters.reviews.client.RestaurantDto;
import WebSiters.reviews.client.RestaurantServiceClient;
import WebSiters.reviews.client.UserDto;
import WebSiters.reviews.client.UserServiceClient;
import WebSiters.reviews.model.Review;
import WebSiters.reviews.model.ReviewImage;
import WebSiters.reviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ReviewGraphqlController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;

    @QueryMapping
    public List<Review> reviews(@Argument String restaurantId) {
        return reviewService.getReviewsByRestaurant(UUID.fromString(restaurantId));
    }

    @QueryMapping
    public Review reviewById(@Argument String id) {
        return reviewService.getReviewById(UUID.fromString(id));
    }

    @MutationMapping
    public Review createReview(@Argument ReviewInput input) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.randomUUID(); // In production, extract from JWT

        return reviewService.createReview(
            userId,
            UUID.fromString(input.restaurantId()),
            input.rating(),
            input.comment()
        );
    }

    @MutationMapping
    public ReviewImage addReviewImage(@Argument String reviewId, @Argument String imageUrl) {
        return reviewService.addReviewImage(UUID.fromString(reviewId), imageUrl);
    }

    @SchemaMapping(typeName = "Review", field = "user")
    public UserDto user(Review review) {
        try {
            return userServiceClient.getUserById(review.getUserId());
        } catch (Exception e) {
            return null;
        }
    }

    @SchemaMapping(typeName = "Review", field = "restaurant")
    public RestaurantDto restaurant(Review review) {
        try {
            return restaurantServiceClient.getRestaurantById(review.getRestaurantId());
        } catch (Exception e) {
            return null;
        }
    }

    @SchemaMapping(typeName = "Review", field = "images")
    public List<ReviewImage> images(Review review) {
        return reviewService.getReviewImages(review.getId());
    }
}

record ReviewInput(String restaurantId, Integer rating, String comment) {}
