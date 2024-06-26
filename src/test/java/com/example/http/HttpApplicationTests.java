package com.example.http;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HTTPApplication.class)
class HttpApplicationTests {
    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Test
    void contextLoads() {
        accountService.createAccountAndNotify("joah");
        log.info("count : " +accountRepository.count());

        try {
            accountService.createAccountAndNotify("error");
        }catch (Exception ex) {
            log.error(ex.getMessage());
        }

        log.info("count : " +accountRepository.count());
        assertEquals(accountRepository.count(), 1);
    }

}
