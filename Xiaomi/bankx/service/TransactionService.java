package arup.Xiaomi.bankx.service;

import arup.Xiaomi.bankx.appConstant.AccountType;
import arup.Xiaomi.bankx.appConstant.TransactionType;
import arup.Xiaomi.bankx.entity.Account;

import arup.Xiaomi.bankx.entity.BankTransaction;
import arup.Xiaomi.bankx.repository.TransactionRepository;
import arup.Xiaomi.bankx.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void recordTransaction(Account account, BigDecimal amount, TransactionType transactionType) {
        BankTransaction transaction = BankTransaction.builder()
                .amount(amount)
                .transactionType(transactionType)
                .transactionDate(LocalDateTime.now())
                .account(account)
                .build();

        transactionRepository.save(transaction);

        if (transactionType == TransactionType.CREDIT && account.getAccountType() == AccountType.SAVINGS) {
            BigDecimal interest = account.getBalance().multiply(new BigDecimal("0.005")); // 0.5% interest
            account.setBalance(account.getBalance().add(interest));
            accountRepository.save(account);
        }
    }
}