package arup.Xiaomi.bankx.repository;

import arup.Xiaomi.bankx.entity.Account;
import arup.Xiaomi.bankx.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCustomer(Customer customer);

    Account findByAccountNumber(String accountNumber);
}