package com.hendisantika.coffeshop;

import com.hendisantika.coffeshop.entity.Store;
import com.hendisantika.coffeshop.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * Project : coffeeshop
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/17
 * Time: 06.35
 * To change this template use File | Settings | File Templates.
 */

@Slf4j
@Component
public class StoreInitializer {
    @Autowired
    public StoreInitializer(StoreRepository repository, MongoOperations operations) throws Exception {

        if (repository.count() != 0) {
            return;
        }

        List<Store> stores = readStores();
        log.info("Importing {} stores into MongoDB…", stores.size());
        repository.saveAll(stores);
        log.info("Successfully imported {} stores.", repository.count());
    }

    /**
     * Reads a file {@code starbucks.csv} from the class path and parses it into {@link Store} instances about to
     * persisted.
     *
     * @return
     * @throws Exception
     */
    public static List<Store> readStores() throws Exception {

        ClassPathResource resource = new ClassPathResource("starbucks.csv");
        Scanner scanner = new Scanner(resource.getInputStream());
        String line = scanner.nextLine();
        scanner.close();

        FlatFileItemReader<Store> itemReader = new FlatFileItemReader<Store>();
        itemReader.setResource(resource);

        // DelimitedLineTokenizer defaults to comma as its delimiter
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(line.split(","));
        tokenizer.setStrict(false);

        DefaultLineMapper<Store> lineMapper = new DefaultLineMapper<Store>();
        lineMapper.setFieldSetMapper(fields -> {

            Point location = new Point(fields.readDouble("Longitude"), fields.readDouble("Latitude"));
            Store.Address address = new Store.Address(fields.readString("Street Address"), fields.readString("City"),
                    fields.readString("Zip"), location);

            return new Store(fields.readString("Name"), address);
        });

        lineMapper.setLineTokenizer(tokenizer);
        itemReader.setLineMapper(lineMapper);
        itemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
        itemReader.setLinesToSkip(1);
        itemReader.open(new ExecutionContext());

        List<Store> stores = new ArrayList<>();
        Store store = null;

        do {

            store = itemReader.read();

            if (store != null) {
                stores.add(store);
            }

        } while (store != null);

        return stores;
    }
}
