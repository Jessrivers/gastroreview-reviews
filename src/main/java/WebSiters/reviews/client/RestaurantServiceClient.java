package WebSiters.reviews.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "restaurants-service")
public interface RestaurantServiceClient {

    @GetMapping("/restaurants/{id}")
    RestaurantDto getRestaurantById(@PathVariable UUID id);
}
