package org.example.crypto;

import org.example.crypto.data.User;
import org.example.crypto.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeEventConsumer implements ApplicationListener<PriceChangeEvent> {

    private final UserRepository userRepository;
    private final TelegramSender telegramSender;

    @Autowired
    public PriceChangeEventConsumer(UserRepository userRepository, TelegramSender telegramSender) {
        this.userRepository = userRepository;
        this.telegramSender = telegramSender;
    }

    @Override
    public void onApplicationEvent(PriceChangeEvent event) {

        var activeUsers = userRepository.findAllActive();
        var recipientNumbers = activeUsers.stream().map(User::getNumber).toList();

        var action = event.getOldPrice() > event.getNewPrice() ? "decreased" : "increased";
        var message = String.format("Crypto %s %s price!, Old price: %s, new price: %s", event.getSymbol(), action, event.getOldPrice(), event.getNewPrice());
        telegramSender.sendMessage(recipientNumbers, message);
    }
}
