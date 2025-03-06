package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;

import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.json.JsonObject;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.UserBankRepository;

public class CouchbaseUserBankRepository implements UserBankRepository {
    private final Collection collection;

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
    public void saveRole(String user_id, String bank_id, String role) {
        JsonObject json = JsonObject.create()
                .put("user_id", Integer.parseInt(user_id))
                .put("bank_id", Integer.parseInt(bank_id))
                .put("role", role);

        String new_id = user_id + "_" + bank_id;
        collection.upsert(new_id, json);
    }
}
