package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.json.JsonObject;
import com.example.lab_1.entities.Credit;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.CreditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouchbaseCreditRepository implements CreditRepository {
    private final Bucket bucket;
    private final Collection collection;
    private final ObjectMapper mapper = new ObjectMapper();

    public CouchbaseCreditRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        // Предположим, что коллекция для кредитов называется "credits"
        this.collection = bucket.scope("_default").collection("credits");
    }

    @Override
    public int getMaxCreditId(){
        String query = "SELECT MAX(id) as maxId FROM `Lab_1`.`_default`.`credits`";

        var result = CouchbaseConnection.getCluster().query(query);
        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getInt("maxId"))
                .orElse(0);
    }

    @Override
    public void save(Credit credit) {
        collection.upsert(String.valueOf(credit.getId()), credit);
    }

    @Override
    public Optional<Credit> findById(String id) {
        try {
            GetResult result = collection.get(id);
            String json = result.contentAs(String.class);
            Credit credit = mapper.readValue(json, Credit.class);
            return Optional.of(credit);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Credit> findAll() {
        List<Credit> credits = new ArrayList<>();
        String query = "SELECT credits.* FROM `Lab_1`.`_default`.`credits` credits";
        QueryResult result = CouchbaseConnection.getCluster().query(query);
        result.rowsAsObject().forEach(row -> {
            try {
                Credit credit = mapper.readValue(row.toString(), Credit.class);
                credits.add(credit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return credits;
    }

    @Override
    public void delete(String id) {
        collection.remove(id);
    }

    @Override
    public List<Credit> findByUserId(String userId) {
        List<Credit> credits = new ArrayList<>();
        String query = "SELECT credits.* FROM `Lab_1`.`_default`.`credits` credits WHERE credits.userId = $userId";
        QueryResult result = CouchbaseConnection.getCluster().query(
                query,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("userId", Integer.valueOf(userId)))
        );
        result.rowsAsObject().forEach(row -> {
            try {
                Credit credit = mapper.readValue(row.toString(), Credit.class);
                credits.add(credit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return credits;
    }
}
