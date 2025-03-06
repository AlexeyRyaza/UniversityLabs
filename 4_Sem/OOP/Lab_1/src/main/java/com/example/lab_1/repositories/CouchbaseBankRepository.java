package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.example.lab_1.repositories.Interfaces.BankRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lab_1.entities.Bank;
import com.example.lab_1.infrastructure.CouchbaseConnection;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class CouchbaseBankRepository implements BankRepository {
    private final Bucket bucket;
    private final Collection collection;

    public CouchbaseBankRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        this.collection = bucket.scope("_default").collection("banks");
    }

    @Override
    public void save(Bank bank) {
        collection.upsert(String.valueOf(bank.getId()), bank.toJson());
    }

    @Override
    public Optional<Bank> findById(String id) {
        try {
            GetResult result = collection.get(id);
            String json = result.contentAs(String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Bank bank = objectMapper.readValue(json, Bank.class);
            return Optional.of(bank);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<>();
        String query = "SELECT banks.* FROM `Lab_1`.`_default`.`banks`";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        ObjectMapper objectMapper = new ObjectMapper();
        result.rowsAsObject().forEach(row -> {
            try {
                Bank bank = objectMapper.readValue(row.toString(), Bank.class);
                banks.add(bank);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return banks;
    }

    @Override
    public void delete(String id) {
        collection.remove(id);
    }
}
