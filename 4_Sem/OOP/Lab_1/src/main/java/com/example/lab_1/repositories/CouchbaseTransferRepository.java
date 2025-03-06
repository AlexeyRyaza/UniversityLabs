package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.json.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lab_1.entities.Transfer;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.TransferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouchbaseTransferRepository implements TransferRepository {
    private final Bucket bucket;
    private final Collection collection;
    private final ObjectMapper mapper = new ObjectMapper();

    public CouchbaseTransferRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        this.collection = bucket.scope("_default").collection("transfers");
    }

    @Override
    public int getMaxTransferId() {
        String query = "SELECT MAX(id) AS maxId FROM `Lab_1`.`_default`.`transfers`";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getInt("maxId"))
                .orElse(0);
    }

    @Override
    public void save(Transfer transfer) {
        collection.upsert(String.valueOf(transfer.getId()), transfer);
    }

    @Override
    public void delete(String id) {
        collection.remove(String.valueOf(id));
    }

    @Override
    public Optional<Transfer> findById(String id) {
        List<Transfer> transfers = new ArrayList<>();
        try {
            String query = "SELECT transfers.* FROM `Lab_1`.`_default`.`transfers` WHERE id = " + id;
            QueryResult result = CouchbaseConnection.getCluster().query(query,
                    QueryOptions.queryOptions().parameters(JsonObject.create().put("id", id)));

            result.rowsAsObject().forEach(row -> {
                try {
                    Transfer transfer = mapper.readValue(row.toString(), Transfer.class);
                    transfers.add(transfer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return transfers.stream().findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Transfer> findAll() {
        List<Transfer> transfers = new ArrayList<>();
        String query = "SELECT transfers.* FROM `Lab_1`.`_default`.`transfers` transfers";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        result.rowsAsObject().forEach(row -> {
            try {
                Transfer transfer = mapper.readValue(row.toString(), Transfer.class);
                transfers.add(transfer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return transfers;
    }

    @Override
    public List<Transfer> findBySourceAccount(String sourceAccount) {
        List<Transfer> transfers = new ArrayList<>();
        String query = "SELECT transfers.* " +
                "FROM `Lab_1`.`_default`.`transfers` transfers " +
                "WHERE transfers.sourceAccount = $sourceAccount";

        QueryResult result = CouchbaseConnection.getCluster().query(
                query,
                QueryOptions.queryOptions().parameters(
                        JsonObject.create().put("sourceAccount", sourceAccount)
                )
        );

        result.rowsAsObject().forEach(row -> {
            try {
                Transfer transfer = mapper.readValue(row.toString(), Transfer.class);
                transfers.add(transfer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return transfers;
    }

    @Override
    public List<Transfer> findByDestinationAccount(String destinationAccount) {
        List<Transfer> transfers = new ArrayList<>();
        String query = "SELECT transfers.* " +
                "FROM `Lab_1`.`_default`.`transfers` transfers " +
                "WHERE transfers.destinationAccount = $destinationAccount";

        QueryResult result = CouchbaseConnection.getCluster().query(
                query,
                QueryOptions.queryOptions().parameters(
                        JsonObject.create().put("destinationAccount", destinationAccount)
                )
        );

        result.rowsAsObject().forEach(row -> {
            try {
                Transfer transfer = mapper.readValue(row.toString(), Transfer.class);
                transfers.add(transfer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return transfers;
    }
}