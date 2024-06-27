package com.example.http;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SendEmail {

    private  SendGrid sendGrid;

    @RequestMapping("/email")
   Response  email(@PathVariable String message) throws IOException {
        Email sender = new Email("sender - email Address");
        String title = "e-mail title";
        Email receiver = new Email("receiver - email Address");
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(sender, title, receiver,content);

        sendGrid.addRequestHeader("X-Mock", "true");
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Request re = new Request();
        return sendGrid.api(re);
    }

}
