package com.example.lab_1.repositories;


import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.example.lab_1.entities.User;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.couchbase.client.java.query.QueryResult;
import com.example.lab_1.repositories.Interfaces.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class CouchbaseUserRepository implements UserRepository {
    private final Bucket bucket;
    private final Collection collection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CouchbaseUserRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        //this.collection = bucket.defaultCollection();
        this.collection = bucket.scope("_default").collection("users");
    }


    @Override
    public void save(User user) {
        try {
            collection.upsert(String.valueOf(user.getId()), user);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getUserBanks(String id){
        List<Integer> banks = new ArrayList<>();
        String query = "SELECT user_bank.* FROM `Lab_1`.`_default`.`user_bank` WHERE user_id = $id";

        QueryResult result = CouchbaseConnection.getCluster().query(query,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("id", id)));

        result.rowsAsObject().forEach(row -> {
            int bank_id = row.getInt("bank_id");
            banks.add(bank_id);
        });

        return banks;
    }

    @Override
    public String getUserPassword(String phone) {
        String query = "SELECT users.* FROM `Lab_1`.`_default`.`users` where phone = $phone";

        QueryResult result = CouchbaseConnection.getCluster().query(query,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("phone", phone)));

        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getString("password"))
                .orElse(null);
    }

    @Override
    public boolean isUserExistByPassport(String passport){
        String query = "SELECT COUNT(*) AS user_count FROM `Lab_1`.`_default`.`users` WHERE passport = $passport";;
        QueryResult result = CouchbaseConnection.getCluster().query(query,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("passport", passport)));

        int user_counter = result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getInt("user_count"))
                .orElse(0);

        return user_counter != 0;
    }

    @Override
    public boolean isUserExistByPhone(String phone) {
        String query = "SELECT COUNT(*) AS user_count FROM `Lab_1`.`_default`.`users` WHERE phone = $phone";;
        QueryResult result = CouchbaseConnection.getCluster().query(query,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("phone", phone)));

        int user_counter = result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getInt("user_count"))
                .orElse(0);

        return user_counter != 0;
    }

    @Override
    public Optional<User> getUserByPhoneNumber(String phone) {
        List<User> users = new ArrayList<>();
        String query = "SELECT users.* FROM `Lab_1`.`_default`.`users` WHERE phone = $phone";;
        QueryResult result = CouchbaseConnection.getCluster().query(query,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("phone", phone)));

        result.rowsAsObject().forEach(row -> {
            try {
                User user = objectMapper.readValue(row.toString(), User.class);
                users.add(user);
                }
            catch (Exception e) {
                e.printStackTrace();
            }

        });

        return users.stream().findFirst();
    }

    @Override
    public Optional<User> findById(String id) {
        List<User> users = new ArrayList<>();

        try {
            String query = "Select users.* FROM `Lab_1`.`_default`.`users` where id = " + id;

            QueryResult result = CouchbaseConnection.getCluster().query(query,
                    QueryOptions.queryOptions().parameters(JsonObject.create().put("id", id)));

            result.rowsAsObject().forEach(row -> {
                try {
                    User user = objectMapper.readValue(row.toString(), User.class);
                    users.add(user);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });

            return users.stream().findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT users.* FROM `Lab_1`.`_default`.`users`";

        try {
            QueryResult result = CouchbaseConnection.getCluster().query(query);

            result.rowsAsObject().forEach(row -> {
                try {
                    String input = row.toString();

                    User user = objectMapper.readValue(input, User.class);
                    users.add(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void delete(String id) {
        try {
            collection.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getMaxUserId() {
        String query = "SELECT MAX(u.id) AS maxId FROM `Lab_1`.`_default`.`users` u";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getInt("maxId"))
                .orElse(0);
    }
}
