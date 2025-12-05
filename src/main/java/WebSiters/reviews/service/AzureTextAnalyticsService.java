package WebSiters.reviews.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AzureTextAnalyticsService {

    @Value("${azure.text-analytics.key:placeholder}")
    private String apiKey;

    @Value("${azure.text-analytics.endpoint:https://placeholder.cognitiveservices.azure.com}")
    private String endpoint;

    public SentimentResult analyzeSentiment(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new SentimentResult(0.5, "NEUTRAL");
        }

        // Check if we have real Azure credentials
        if ("placeholder".equals(apiKey) || endpoint.contains("placeholder")) {
            System.out.println("Azure Text Analytics: Using fallback sentiment analysis (no credentials configured)");
            return performBasicSentimentAnalysis(text);
        }

        try {
            // In production with real credentials, this would call Azure Text Analytics API
            // For now, use fallback analysis
            return performBasicSentimentAnalysis(text);
        } catch (Exception e) {
            System.err.println("Error calling Azure Text Analytics: " + e.getMessage());
            return new SentimentResult(0.5, "NEUTRAL");
        }
    }

    private SentimentResult performBasicSentimentAnalysis(String text) {
        // Simple sentiment analysis based on keywords
        String lowerText = text.toLowerCase();

        String[] positiveWords = {"excelente", "bueno", "delicioso", "increíble", "genial", "perfecto",
                                   "maravilloso", "fantastic", "great", "excellent", "amazing", "wonderful"};
        String[] negativeWords = {"malo", "terrible", "horrible", "pésimo", "awful", "bad", "terrible",
                                   "disgusting", "poor", "worst"};

        int positiveCount = 0;
        int negativeCount = 0;

        for (String word : positiveWords) {
            if (lowerText.contains(word)) positiveCount++;
        }

        for (String word : negativeWords) {
            if (lowerText.contains(word)) negativeCount++;
        }

        if (positiveCount > negativeCount) {
            double score = Math.min(0.9, 0.6 + (positiveCount * 0.1));
            return new SentimentResult(score, "POSITIVE");
        } else if (negativeCount > positiveCount) {
            double score = Math.min(0.9, 0.6 + (negativeCount * 0.1));
            return new SentimentResult(score, "NEGATIVE");
        } else {
            return new SentimentResult(0.5, "NEUTRAL");
        }
    }

    public static class SentimentResult {
        private final double score;
        private final String label;

        public SentimentResult(double score, String label) {
            this.score = score;
            this.label = label;
        }

        public double getScore() { return score; }
        public String getLabel() { return label; }
    }
}
