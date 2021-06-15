package com.example.workspacespringbatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
@Entity
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class BankTransaction {
    @Id
    private Long id;
    private long accountId;
    @Transient
    private  String strTransactionDate;
    private Date transactionDate;
    private String transactionType;
    private double amount;

}
