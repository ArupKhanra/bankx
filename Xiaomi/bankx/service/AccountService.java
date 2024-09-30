package arup.Xiaomi.bankx.service;

import arup.Xiaomi.bankx.appConstant.AccountType;
import arup.Xiaomi.bankx.appConstant.TransactionType;
import arup.Xiaomi.bankx.entity.Account;
import arup.Xiaomi.bankx.entity.Customer;
import arup.Xiaomi.bankx.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    public void createAccountsForCustomer(Customer customer) {
        Account currentAccount = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(AccountType.CURRENT)
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .build();
        accountRepository.save(currentAccount);

        Account savingsAccount = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(AccountType.SAVINGS)
                .balance(new BigDecimal("500.00")) // Joining bonus
                .customer(customer)
                .build();
        accountRepository.save(savingsAccount);
    }

    public void transferFunds(Long fromAccountId, Long toAccountId,
                              BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + toAccountId));

        if (fromAccount.getAccountType() == AccountType.CURRENT && fromAccount.getBalance().compareTo(amount) >= 0) {

            BigDecimal transactionFee = amount.multiply(new BigDecimal("0.0005")); // 0.05%
            BigDecimal totalDeduction = amount.add(transactionFee);

            if (fromAccount.getBalance().compareTo(totalDeduction) < 0) {
                throw new RuntimeException("Insufficient funds to cover transaction fee and transfer amount");
            }

            // Update balances
            fromAccount.setBalance(fromAccount.getBalance().subtract(totalDeduction));
            toAccount.setBalance(toAccount.getBalance().add(amount));


            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);


            transactionService.recordTransaction(fromAccount, amount, TransactionType.DEBIT);
            transactionService.recordTransaction(toAccount, amount, TransactionType.CREDIT);
        } else {
            throw new RuntimeException("Insufficient funds or invalid account type for transfer");
        }
    }

    private String generateAccountNumber() {

        return "ACCT" + System.currentTimeMillis();
    }
}
