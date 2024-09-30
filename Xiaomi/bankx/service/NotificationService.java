package arup.Xiaomi.bankx.service;

import arup.Xiaomi.bankx.entity.Customer;
import arup.Xiaomi.bankx.entity.Notification;
import arup.Xiaomi.bankx.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendNotification(Customer customer, String message) {
        Notification notification = Notification.builder()
                .message(message)
                .status("PENDING")
                .timestamp(LocalDateTime.now())
                .customer(customer)
                .build();

        notificationRepository.save(notification);

        notification.setStatus("SENT");
        notificationRepository.save(notification);
    }
}