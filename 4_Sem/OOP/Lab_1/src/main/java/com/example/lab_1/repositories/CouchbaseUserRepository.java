package com.example.lab_1.repositories;

import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.example.lab_1.entities.User;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.couchbase.client.java.query.QueryResult;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class CouchbaseUserRepository implements UserRepository {
    private final Bucket bucket;
    private final Collection collection;

    public CouchbaseUserRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        //this.collection = bucket.defaultCollection();
        this.collection = bucket.scope("_default").collection("users");
    }

    @Override
    public void save(User user) {
        collection.upsert(String.valueOf(user.getId()), user.toJson());
        //collection.upsert(String.valueOf(user.getId()), user.toJson());
    }

    @Override
    public Optional<User> findById(String id) {
        try {
            GetResult result = collection.get(id);
            String json = result.contentAs(String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(json, User.class);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.* FROM `" + bucket.name() + "` u";
        QueryResult result = CouchbaseConnection.getCluster().query(query);
        result.rowsAsObject().forEach(row -> {
            // TODO parse json to user
        });
        return users;
    }

    @Override
    public void delete(String id) {
        collection.remove(id);
    }
}
