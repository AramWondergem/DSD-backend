package com.example.demo.util.Ngrok;

import com.ngrok.Session;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Getter
@Service
@Profile("dev")
public class NgrokService {
    private String ngrokUrl;
    @Value("${ngrokAuthToken}")
    String ngrokAuthToken;

    @PostConstruct
    public void startNgrok() throws IOException {
        if (ngrokAuthToken == null || ngrokAuthToken.isEmpty()) {
            throw new IllegalStateException("NGROK_AUTHTOKEN is not set!");
        }

        var sessionBuilder = Session.withAuthtoken(ngrokAuthToken)
                .metadata("Spring Boot Ngrok Session");

        try (var session = sessionBuilder.connect()) {
            var listenerBuilder = session.httpEndpoint().metadata("Spring Boot Listener");

            try (var listener = listenerBuilder.listen()) {
                this.ngrokUrl = listener.getUrl();
                log.info("Ngrok Tunnel URL: {}", ngrokUrl);
            }
        }
    }
}
