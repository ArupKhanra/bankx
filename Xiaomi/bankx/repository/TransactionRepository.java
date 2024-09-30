package arup.Xiaomi.bankx.repository;

import arup.Xiaomi.bankx.appConstant.TransactionType;
import arup.Xiaomi.bankx.entity.Account;
import arup.Xiaomi.bankx.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<BankTransaction, Long> {

    List<BankTransaction> findByAccount(Account account);

    List<BankTransaction> findByTransactionType(TransactionType transactionType);
}