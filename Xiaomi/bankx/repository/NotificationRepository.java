package arup.Xiaomi.bankx.repository;

import arup.Xiaomi.bankx.entity.Customer;
import arup.Xiaomi.bankx.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByCustomer(Customer customer);

    List<Notification> findByStatus(String status);
}