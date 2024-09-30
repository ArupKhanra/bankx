package arup.Xiaomi.bankx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long customerId;

        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Account> accounts;

    }

