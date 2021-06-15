package com.example.workspacespringbatch;

import com.example.workspacespringbatch.model.BankTransaction;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

// @Component : enlever Component -> pas instancié
// -> instancier ce Processor directement dans la class Config
public class BankTransactionItemAnalyticsProcessor
        implements ItemProcessor<BankTransaction , BankTransaction> {
    // Ce processor va prendre eb entrée
    // -> on va calculer le montant total montantDebit
    @Getter private double totalDebit ;
    @Getter private double totalCredit ;

     @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {

        if (bankTransaction.getTransactionType().equals("D")) totalDebit+= bankTransaction.getAmount();
        else if (bankTransaction.getTransactionType().equals("C")) totalCredit+= bankTransaction.getAmount();
        return bankTransaction;

    }
}
