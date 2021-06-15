package com.example.workspacespringbatch;

import com.example.workspacespringbatch.model.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

    /*
        L'objectif du Job :
            - il va lire le fichier data.csv
            - il va reer un objet de type BankTransaction + trsf(date)
            - l'enregistrer dans la BDD
     */

@Configuration // classe de configuration
@EnableBatchProcessing // activer Spring.Batch
public class SpringBatchConfig {
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;
    @Autowired private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired private ItemWriter<BankTransaction>  bankTransactionItemWriter;
    @Autowired private ItemProcessor<BankTransaction,BankTransaction> bankTransactionBankTransactionItemProcessor;

    @Bean
    public Job bankJob(){
        Step step1 = stepBuilderFactory.get("step-load-data")
                .<BankTransaction,BankTransaction>chunk(100)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionBankTransactionItemProcessor)
                .writer(bankTransactionItemWriter)
                .build();

        return jobBuilderFactory.get("bank-data-loader-job")
                .start(step1)
                .build();
    }

    @Bean // un bean qui dervait etre creer au demarrage de l'application
    public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") Resource inputFile){
        FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("FFIR1");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;

    }

    @Bean
    public LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        delimitedLineTokenizer.setDelimiter(","); // (540300 , 10025436 , 17/10/2018-09:44,D,10000.5 )
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id","accountID" , "strTransactionDate" , "transactionType" , "amount");
        lineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<BankTransaction> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return lineMapper;
    }

  /*  @Bean
    public  ItemProcessor<BankTransaction , BankTransaction> itemProcessor(){
        return new ItemProcessor<BankTransaction, BankTransaction>() {
            @Override
            public BankTransaction process(BankTransaction bankTransaction) throws Exception {
                // dateString --> date (Date) ==> je la retourne
                return null;
            }
        }
    } */
}
