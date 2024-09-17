package hlmg.users.data;

import hlmg.users.ui.model.AlbumResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "albums-ws")
public interface AlbumServiceClient {

    @Retry(name = "albums-ws")
    @CircuitBreaker(name = "albums-ws", fallbackMethod = "getAlbumsFallback")
    @GetMapping("/users/{id}/albums")
    List<AlbumResponse> getAlbums(@PathVariable String id);

    default List<AlbumResponse> getAlbumsFallback(String id, Throwable exception) {
        System.out.println("Param =  " + id);
        System.out.println("Exception took place: " + exception.getMessage());
        return Collections.emptyList();
    }
}
