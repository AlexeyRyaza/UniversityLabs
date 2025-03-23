package com.example.lab_1.repositories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.json.JsonObject;
import com.example.lab_1.entities.Enterprise;
import com.example.lab_1.entities.SalaryProject;
import com.example.lab_1.entities.User;
import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.repositories.Interfaces.SalaryProjectRepository;
import com.example.lab_1.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouchbaseSalaryProjectRepository implements SalaryProjectRepository {
    private final Bucket bucket;
    private final Collection collection;
    private final ObjectMapper mapper = new ObjectMapper();

    public CouchbaseSalaryProjectRepository() {
        this.bucket = CouchbaseConnection.getBucket();
        this.collection = bucket.scope("_default").collection("salary_projects"); // Коллекция в БД
    }

    @Override
    public void save(SalaryProject salaryProject) {
        collection.upsert(String.valueOf(salaryProject.getId()), salaryProject);
    }

    @Override
    public Optional<SalaryProject> findById(String id) {
        List<SalaryProject> salaryProjects = new ArrayList<>();
        String query = "Select salary_projects.* from `Lab_1`.`_default`.`salary_projects` where id = " + id;
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        result.rowsAsObject().forEach(row -> {
            try {
                SalaryProject salaryProject = mapper.readValue(row.toString(), SalaryProject.class);
                salaryProjects.add(salaryProject);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        return salaryProjects.stream().findFirst();
    }

    @Override
    public Optional<SalaryProject> findByEnterpriseId(String enterpriseId) {
        List<SalaryProject> salaryProjects = new ArrayList<>();

        String query = "SELECT salary_projects.* FROM `Lab_1`.`_default`.`salary_projects` where enterpriseId = " + enterpriseId;
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        result.rowsAsObject().forEach(row -> {
            try {
                SalaryProject salaryProject = mapper.readValue(row.toString(), SalaryProject.class);
                salaryProjects.add(salaryProject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return salaryProjects.isEmpty() ? Optional.empty() : Optional.of(salaryProjects.get(0));
    }

    @Override
    public List<SalaryProject> findAll() {
        List<SalaryProject> salaryProjects = new ArrayList<>();
        String query = "SELECT salary_projects.* FROM `Lab_1`.`_default`.`salary_projects`";
        QueryResult result = CouchbaseConnection.getCluster().query(query);
        result.rowsAsObject().forEach(row -> {
            try {
                SalaryProject salaryProject = mapper.readValue(row.toString(), SalaryProject.class);
                salaryProjects.add(salaryProject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return salaryProjects;
    }

    @Override
    public boolean delete(String id) {
        try {
            collection.remove(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void replace(SalaryProject salaryProject) {
        collection.replace(String.valueOf(salaryProject.getId()), salaryProject);
    }

    @Override
    public List<SalaryProject> findByUserId(String userId) {
        List<SalaryProject> salaryProjects = new ArrayList<>();
        String query = "SELECT salary_projects.* FROM `Lab_1`.`_default`.`salary_projects` WHERE userId = $userId";
        QueryResult result = CouchbaseConnection.getCluster().query(
                query, QueryOptions.queryOptions().parameters(JsonObject.create().put("userId", Integer.valueOf(userId)))
        );
        result.rowsAsObject().forEach(row -> {
            try {
                SalaryProject salaryProject = mapper.readValue(row.toString(), SalaryProject.class);
                salaryProjects.add(salaryProject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return salaryProjects;
    }

    @Override
    public List<SalaryProject> findByBankId(String bankId) {
        List<SalaryProject> salaryProjects = new ArrayList<>();
        String query = "SELECT salary_projects.* FROM `Lab_1`.`_default`.`salary_projects` WHERE bankId = $bankId";
        QueryResult result = CouchbaseConnection.getCluster().query(
                query, QueryOptions.queryOptions().parameters(JsonObject.create().put("bankId", Integer.valueOf(bankId)))
        );
        result.rowsAsObject().forEach(row -> {
            try {
                SalaryProject salaryProject = mapper.readValue(row.toString(), SalaryProject.class);
                salaryProjects.add(salaryProject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return salaryProjects;
    }

    @Override
    public List<SalaryProject> findPendingApprovals(String bankId) {
        List<SalaryProject> salaryProjects = new ArrayList<>();
        String query = "SELECT salary_projects.* FROM `Lab_1`.`_default`.`salary_projects` WHERE approved = false and bankId = " + bankId;
        QueryResult result = CouchbaseConnection.getCluster().query(query);
        result.rowsAsObject().forEach(row -> {
            try {
                SalaryProject salaryProject = mapper.readValue(row.toString(), SalaryProject.class);
                salaryProjects.add(salaryProject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return salaryProjects;
    }

    @Override
    public List<Integer> getUsersOfSalaryProject(String salaryProjectId) {
        String query = "SELECT user_salaryPrj.* FROM `Lab_1`.`_default`.`user_salaryPrj` where salaryProjectId = " + salaryProjectId;
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        List<Integer> users = new ArrayList<>();
        result.rowsAsObject().forEach(row -> {
            try{
                int userId = row.getInt("userId");
                users.add(userId);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        return users;
    }

    @Override
    public int getMaxSalaryProjectId() {
        String query = "SELECT MAX(id) AS maxId FROM `Lab_1`.`_default`.`salary_projects`";
        QueryResult result = CouchbaseConnection.getCluster().query(query);

        return result.rowsAsObject()
                .stream()
                .findFirst()
                .map(row -> row.getInt("maxId"))
                .orElse(0);
    }
}
