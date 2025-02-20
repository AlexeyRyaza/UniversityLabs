package com.example.lab_1.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final int id;
    private final String name;

    private final String UNP;
    private final String Address;

    private Map<Client, List<Account>> accounts = new HashMap<>();
    private Map<Integer, Client> clients = new HashMap<Integer, Client>();
    private Map<Integer, Manager>  managers = new HashMap<>();
    private Map<Integer, Operator>  operators = new HashMap<>();
    private Map<Integer, Specialist>  specialists = new HashMap<>();
    private Map<Integer, Admin>  admins = new HashMap<>();


    Bank(int id, String name, String UNP, String address) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.UNP = UNP;
        this.Address = address;
    }

    //Getters
    //========================================
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUNP() {
        return UNP;
    }

    public String getAddress() {
        return Address;
    }

    public Map<Client, List<Account>> getAccounts() {
        return accounts;
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    public Map<Integer, Manager> getManagers() {
        return managers;
    }

    public Map<Integer, Operator> getOperators() {
        return operators;
    }

    public Map<Integer, Specialist> getSpecialists() {
        return specialists;
    }

    public Map<Integer, Admin> getAdmins() {
        return admins;
    }

    public void addClient(Client client) {
        this.clients.putIfAbsent(client.getUser().getId(), client);
    }

    public void addManager(Manager manager) {
        this.managers.putIfAbsent(manager.getUser().getId(), manager);
    }

    public void addOperator(Operator operator) {
        this.operators.putIfAbsent(operator.getUser().getId(), operator);
    }

    public void addSpecialist(Specialist specialist) {
        this.specialists.putIfAbsent(specialist.getUser().getId(), specialist);
    }

    public void addAdmin(Admin admin) {
        this.admins.putIfAbsent(admin.getUser().getId(), admin);
    }
}
