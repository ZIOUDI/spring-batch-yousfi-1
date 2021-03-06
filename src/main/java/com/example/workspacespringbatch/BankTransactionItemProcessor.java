package com.example.workspacespringbatch;

import com.example.workspacespringbatch.model.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

// @Component : enlever Component -> pas instancié
// -> instancier ce Processor directement dans la class Config

public class BankTransactionItemProcessor
        implements ItemProcessor<BankTransaction , BankTransaction> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        // dateString --> date (Date) ==> je la retourne
        bankTransaction.setTransactionDate(simpleDateFormat.parse(bankTransaction.getStrTransactionDate()));
        return bankTransaction;
    }
}
