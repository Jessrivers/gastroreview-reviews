package WebSiters.reviews.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.Map;

@Service
public class AzureContentModeratorService {

    @Value("${azure.content-moderator.key:placeholder}")
    private String apiKey;

    @Value("${azure.content-moderator.endpoint:https://placeholder.cognitiveservices.azure.com}")
    private String endpoint;

    private final WebClient webClient;

    public AzureContentModeratorService() {
        this.webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    public ModerationResult moderateImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return new ModerationResult(true, "APPROVED", 0.0);
        }

        // Check if we have real Azure credentials
        if ("placeholder".equals(apiKey) || endpoint.contains("placeholder")) {
            System.out.println("Azure Content Moderator: Using fallback (no credentials configured)");
            return new ModerationResult(true, "APPROVED", 0.05);
        }

        try {
            // Call Azure Content Moderator API
            // POST {endpoint}/contentmoderator/moderate/v1.0/ProcessImage/Evaluate
            Map<String, Object> response = webClient.post()
                    .uri(endpoint + "/contentmoderator/moderate/v1.0/ProcessImage/Evaluate")
                    .header("Ocp-Apim-Subscription-Key", apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(Map.of("DataRepresentation", "URL", "Value", imageUrl))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();

            // Parse response
            if (response != null && response.containsKey("IsImageAdultClassified")) {
                boolean isAdult = (boolean) response.getOrDefault("IsImageAdultClassified", false);
                boolean isRacy = (boolean) response.getOrDefault("IsImageRacyClassified", false);

                if (isAdult || isRacy) {
                    return new ModerationResult(false, "REJECTED", 0.9);
                }
                return new ModerationResult(true, "APPROVED", 0.1);
            }

            return new ModerationResult(true, "APPROVED", 0.05);

        } catch (WebClientResponseException e) {
            System.err.println("Azure Content Moderator API error: " + e.getStatusCode() + " - " + e.getMessage());
            return new ModerationResult(true, "NEEDS_REVIEW", 0.5);
        } catch (Exception e) {
            System.err.println("Error calling Azure Content Moderator: " + e.getMessage());
            return new ModerationResult(true, "NEEDS_REVIEW", 0.5);
        }
    }

    public static class ModerationResult {
        private final boolean approved;
        private final String status;
        private final double score;

        public ModerationResult(boolean approved, String status, double score) {
            this.approved = approved;
            this.status = status;
            this.score = score;
        }

        public boolean isApproved() { return approved; }
        public String getStatus() { return status; }
        public double getScore() { return score; }
    }
}
