package com.example.http;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class HTTPApplication {
    public static void main(String[] args) {
        SpringApplication.run(HTTPApplication.class,args);
    }
}

@RestController
class SessionController {
    private final String ip;

    @Autowired
    public SessionController(@Value("${CF_INSTANCE_IP:127.0.0.1}") String ip) {
        this.ip = ip;
    }

    @GetMapping("/hi")
    public Map<String ,String > uid(HttpSession httpSession) {
        UUID uid = Optional.ofNullable(UUID.class.cast(httpSession.getAttribute("uid"))).orElse(UUID.randomUUID());

        Map<String ,String > m = new HashMap<>();
        m.put("instance_ip", this.ip);
        m.put("uuid",uid.toString());
        return m;
    }
}