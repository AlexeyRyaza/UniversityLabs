package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.json.JsonObject;
import com.example.lab_1.entities.Enterprise;
import com.example.lab_1.entities.User;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.EnterpriseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouchbaseEnterpriseRepository implements EnterpriseRepository {
    private final Bucket bucket;
    private final Collection collection;
    private final ObjectMapper mapper = new ObjectMapper();

    public CouchbaseEnterpriseRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        this.collection = bucket.scope("_default").collection("enterprises"); // Коллекция в БД
    }

    @Override
    public Optional<Enterprise> getEnterpriseByName(String name) {
        List<Enterprise> enterprises = new ArrayList<>();
        String query = "SELECT enterprises.* FROM `Lab_1`.`_default`.`enterprises` WHERE name = $name";
        QueryResult result = CouchbaseConnection.getCluster().query(
                query, QueryOptions.queryOptions().parameters(JsonObject.create().put("name", name)));

        result.rowsAsObject().forEach(row -> {
            try {
                var enterprise = mapper.readValue(row.toString(), Enterprise.class);
                enterprises.add(enterprise);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        return enterprises.stream().findFirst();
    }

    @Override
    public void save(Enterprise enterprise) {
        collection.upsert(String.valueOf(enterprise.getId()), enterprise);
    }

    @Override
    public Optional<Enterprise> findById(String id) {
        List<Enterprise> enterprises = new ArrayList<>();
        String query = "Select enterprises.* from `Lab_1`.`_default`.`enterprises` where id = " + id;
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        result.rowsAsObject().forEach(row -> {
            try {
                Enterprise enterprise = mapper.readValue(row.toString(), Enterprise.class);
                enterprises.add(enterprise);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        return enterprises.stream().findFirst();
    }

    @Override
    public List<Enterprise> findAll() {
        List<Enterprise> enterprises = new ArrayList<>();
        String query = "SELECT enterprises.* FROM `Lab_1`.`_default`.`enterprises`";
        QueryResult result = CouchbaseConnection.getCluster().query(query);
        result.rowsAsObject().forEach(row -> {
            try {
                Enterprise enterprise = mapper.readValue(row.toString(), Enterprise.class);
                enterprises.add(enterprise);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return enterprises;
    }

    @Override
    public void delete(String id) {
        collection.remove(id);
    }

    @Override
    public List<Enterprise> findByBankId(String bankId) {
        List<Enterprise> enterprises = new ArrayList<>();
        String query = "SELECT enterprises.* FROM `Lab_1`.`_default`.`enterprises` WHERE bankId = " + bankId;
        QueryResult result = CouchbaseConnection.getCluster().query(query);
        result.rowsAsObject().forEach(row -> {
            try {
                Enterprise enterprise = mapper.readValue(row.toString(), Enterprise.class);
                enterprises.add(enterprise);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return enterprises;
    }

    @Override
    public List<Enterprise> findUnverifiedEnterprises() {
        List<Enterprise> enterprises = new ArrayList<>();
        String query = "SELECT enterprises.* FROM `Lab_1`.`_default`.`enterprises` WHERE isVerified = false";
        QueryResult result = CouchbaseConnection.getCluster().query(query);
        result.rowsAsObject().forEach(row -> {
            try {
                Enterprise enterprise = mapper.readValue(row.toString(), Enterprise.class);
                enterprises.add(enterprise);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return enterprises;
    }
}
