package hlmg.users.shared;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {
    private final Environment environment;

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400 -> {
                return new BadRequestException(response.reason());
            }
            case 404 -> {
                if (methodKey.contains("getAlbums")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), environment.getProperty("album.exceptions.albums-not-found"));
                } else {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
                }
            }
            default -> {
                return new Exception(response.reason());
            }
        }
    }
}
