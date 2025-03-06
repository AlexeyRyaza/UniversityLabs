package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.json.JsonObject;
import com.example.lab_1.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lab_1.entities.Account;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouchbaseAccountRepository implements AccountRepository {
    private final Bucket bucket;
    private final Collection collection;
    private final ObjectMapper mapper = new ObjectMapper();

    public CouchbaseAccountRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        this.collection = bucket.scope("_default").collection("accounts");
    }

    @Override
    public void update(int sourceAccountId, int amount){
        Account sourceAccount = AccountService.getInstance().getAccountById(String.valueOf(sourceAccountId)).get();

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);

        save(sourceAccount);
    }

    @Override
    public void update(int sourceAccountId, int destinationAccountId, int amount){
        Account sourceAccount = AccountService.getInstance().getAccountById(String.valueOf(sourceAccountId)).get();
        Account destinationAccount = AccountService.getInstance().getAccountById(String.valueOf(destinationAccountId)).get();

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        save(sourceAccount);
        save(destinationAccount);
    }

    @Override
    public int getMaxAccountId(){
        String query = "SELECT MAX(accountId) AS maxId FROM `Lab_1`.`_default`.`accounts`";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getInt("maxId"))
                .orElse(0);
    }

    @Override
    public void save(Account account) {
        collection.upsert(String.valueOf(account.getAccountId()), account);
    }

    @Override
    public void delete(String id) {
        collection.remove(id);
    }

    @Override
    public Optional<Account> findById(String id) {
        List<Account> accounts = new ArrayList<>();
        try {
            String query = "SELECT accounts.* FROM `Lab_1`.`_default`.`accounts` WHERE accountId = " + id;
            QueryResult result = CouchbaseConnection.getCluster().query(query,
                    QueryOptions.queryOptions().parameters(JsonObject.create().put("id", id)));

            result.rowsAsObject().forEach(row -> {
                try {
                    Account account = mapper.readValue(row.toString(), Account.class);
                    accounts.add(account);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return accounts.stream().findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT accounts.* FROM `Lab_1`.`_default`.`accounts` accounts";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        result.rowsAsObject().forEach(row -> {
            try {
                Account account = mapper.readValue(row.toString(), Account.class);
                accounts.add(account);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return accounts;
    }

    @Override
    public List<Account> findByUserId(String userId) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT accounts.* " +
                "FROM `Lab_1`.`_default`.`accounts` accounts " +
                "WHERE accounts.userId = $userId";

        QueryResult result = CouchbaseConnection.getCluster().query(
                query,
                QueryOptions.queryOptions().parameters(
                        JsonObject.create().put("userId", Integer.valueOf(userId))
                )
        );

        result.rowsAsObject().forEach(row -> {
            try {
                Account account = mapper.readValue(row.toString(), Account.class);
                accounts.add(account);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return accounts;
    }

    @Override
    public List<Account> findByBankId(String bankId) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT accounts.* " +
                "FROM `Lab_1`.`_default`.`accounts` accounts " +
                "WHERE accounts.bankId = $bankId";

        QueryResult result = CouchbaseConnection.getCluster().query(
                query,
                QueryOptions.queryOptions().parameters(
                        JsonObject.create().put("bankId", Integer.valueOf(bankId))
                )
        );

        result.rowsAsObject().forEach(row -> {
            try {
                Account account = mapper.readValue(row.toString(), Account.class);
                accounts.add(account);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return accounts;
    }
}
