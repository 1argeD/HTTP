package com.example.http;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpSession;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

interface AccountRepository extends JpaRepository<Account, Long> {

}

@Entity
class Account {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    public Account() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }


}

@Component
class Message {
    private Log log = LogFactory.getLog(getClass());

    @JmsListener(destination = "accounts")
    public void onMessage(String content){
          log.info("-------> "+content);
    }
}

@Service
@Transactional
class AccountService{
    private JmsTemplate jmsTemplate;

    private AccountRepository accountRepository;

    public void createAccountAndNotify(String username) {
        this.jmsTemplate.convertAndSend("account",username);
        this.accountRepository.save(new Account(username));
        if("error".equals(username)) {
            throw new RuntimeException("Simulate Error");
        }
    }
}