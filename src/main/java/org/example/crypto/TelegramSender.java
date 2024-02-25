package org.example.crypto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelegramSender {

    public void sendMessage (List<String> recipientNumbers, String message) {
        // https://whatsmate.github.io/2022-06-16-send-telegram-message-java/
        System.out.println("Pending implementation to call real Telegram api to send message");
    }
}
