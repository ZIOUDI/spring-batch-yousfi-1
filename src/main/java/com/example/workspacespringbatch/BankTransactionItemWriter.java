package com.example.workspacespringbatch;

import com.example.workspacespringbatch.model.BankTransaction;
import com.example.workspacespringbatch.repository.BankTransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
// la meth write() recoit une liste d'objet BankTransaction ? à enregistrer dans la BDD
//
@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
    @Autowired private BankTransactionRepository bankTransactionRepository;
    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
        bankTransactionRepository.saveAll(list);

    }
}
