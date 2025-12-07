package WebSiters.reviews.service;

import WebSiters.reviews.client.RestaurantServiceClient;
import WebSiters.reviews.client.UserServiceClient;
import WebSiters.reviews.model.Review;
import WebSiters.reviews.model.ReviewImage;
import WebSiters.reviews.repository.ReviewImageRepository;
import WebSiters.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;

    @Autowired
    private AzureTextAnalyticsService textAnalyticsService;

    @Autowired
    private AzureContentModeratorService contentModeratorService;

    public List<Review> getReviewsByRestaurant(UUID restaurantId) {
        if (restaurantId == null) {
            return reviewRepository.findAll();
        }
        return reviewRepository.findByRestaurantIdOrderByCreatedAtDesc(restaurantId);
    }

    public Review getReviewById(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Transactional
    public Review createReview(UUID userId, UUID restaurantId, Integer rating, String comment) {
        // Analyze sentiment using Azure Text Analytics
        AzureTextAnalyticsService.SentimentResult sentiment =
            textAnalyticsService.analyzeSentiment(comment);

        Review review = Review.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .restaurantId(restaurantId)
                .rating(rating)
                .comment(comment)
                .sentimentScore(sentiment.getScore())
                .sentimentLabel(sentiment.getLabel())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public ReviewImage addReviewImage(UUID reviewId, String imageUrl) {
        Review review = getReviewById(reviewId);

        // Moderate image using Azure Content Moderator (Cognitive Service #2)
        AzureContentModeratorService.ModerationResult moderation =
            contentModeratorService.moderateImage(imageUrl);

        ReviewImage reviewImage = ReviewImage.builder()
                .id(UUID.randomUUID())
                .reviewId(reviewId)
                .imageUrl(imageUrl)
                .moderationStatus(moderation.getStatus())
                .build();

        return reviewImageRepository.save(reviewImage);
    }

    public List<ReviewImage> getReviewImages(UUID reviewId) {
        return reviewImageRepository.findByReviewId(reviewId);
    }
}
