package com.pragma.notification_service.infrastructure.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfiguration {

    @Value("${twilio.account-sid}")
    private String accountSid;
    @Value("${twilio.auth-token}")
    private String authToken;

    @PostConstruct
    public void init() {
        com.twilio.Twilio.init(accountSid, authToken);
    }

}
