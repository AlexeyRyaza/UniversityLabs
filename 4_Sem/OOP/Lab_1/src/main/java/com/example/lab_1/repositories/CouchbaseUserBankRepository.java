package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;

import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.json.JsonObject;
import com.example.lab_1.entities.User;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.UserBankRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CouchbaseUserBankRepository implements UserBankRepository {
    private final Collection collection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CouchbaseUserBankRepository() {
        Bucket bucket = CouchbaseConnection.getBucket();
        this.collection = bucket.scope("_default").collection("user_bank");
    }

    @Override
    public String getUserRoleByID(String user_id, String bank_id){
        String query = "Select user_bank.* from `Lab_1`.`_default`.`user_bank` where user_id = " + user_id + " and bank_id = " + bank_id;
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getString("role"))
                .orElse(null);
    }

    @Override
    public boolean IsApproved(String userId, String bankId) {
        String query = "Select user_bank.* from `Lab_1`.`_default`.`user_bank` where user_id = " + userId + " and bank_id = " + bankId;
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getBoolean("IsApproved"))
                .orElse(false);
    }

    @Override
    public void rejectUser(String userId, String bankId) {
        collection.remove(userId + "_" + bankId);
    }

    @Override
    public void approveUser(String userId, String bankId) {
        String role = getUserRoleByID(userId, bankId);
        JsonObject json = JsonObject.create()
                .put("user_id", Integer.parseInt(userId))
                .put("bank_id", Integer.parseInt(bankId))
                .put("role", role)
                .put("IsApproved", true);

        collection.replace(userId + "_" + bankId, json);
    }

    @Override
    public List<Integer> getPendingUsersIds(String bankId) {
        String query = "Select user_bank.* from `Lab_1`.`_default`.`user_bank` where bank_id = " + bankId + " and IsApproved = false";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        List<Integer> users = new ArrayList<>();
        result.rowsAsObject().forEach(row -> {
            try {
                int id = row.getInt("user_id");
                users.add(id);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        users.sort(Comparator.comparing(Integer::intValue));
        return users;
    }

    @Override
    public boolean deleteUser(String userId, String bankId) {
        try {
            collection.remove(userId + "_" + bankId);
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

    @Override
    public void saveRole(String user_id, String bank_id, String role, boolean IsApproved) {
        JsonObject json = JsonObject.create()
                .put("user_id", Integer.parseInt(user_id))
                .put("bank_id", Integer.parseInt(bank_id))
                .put("role", role)
                .put("IsApproved", IsApproved);

        String new_id = user_id + "_" + bank_id;
        collection.upsert(new_id, json);
    }
}
