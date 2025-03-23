package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.UserBankEnterpriseRepository;

public class CouchBaseUserBankEnterpriseRepository implements UserBankEnterpriseRepository {
    private final Collection collection;

    public CouchBaseUserBankEnterpriseRepository() {
        Bucket bucket = CouchbaseConnection.getBucket();
        this.collection = bucket.scope("_default").collection("user_bank_enterprise");
    }

    @Override
    public void saveInfo(String userId, String bankId, String enterpriseId) {
        JsonObject json = JsonObject.create()
                .put("userId", Integer.parseInt(userId))
                .put("bankId", Integer.parseInt(bankId))
                .put("enterpriseId", Integer.parseInt(enterpriseId));

        String new_id = userId + "_" + bankId + "_" + enterpriseId;
        collection.upsert(new_id, json);
    }

    @Override
    public int getEnterpriseByUserAndBankId(String userId, String bankId) {
        String query = "select user_bank_enterprise.* from `Lab_1`.`_default`.`user_bank_enterprise`" +
                " where userId = " + userId + " and bankId = " + bankId;
        var result = CouchbaseConnection.getCluster().query(query);

        return result.rowsAsObject().stream()
                .findFirst()
                .map(row -> row.getInt("enterpriseId"))
                .orElse(0);
    }
}
