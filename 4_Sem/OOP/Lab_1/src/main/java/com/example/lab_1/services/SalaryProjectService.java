package com.example.lab_1.services;

import com.example.lab_1.entities.LogEntry;
import com.example.lab_1.entities.SalaryProject;
import com.example.lab_1.entities.User;
import com.example.lab_1.repositories.CouchbaseSalaryProjectRepository;
import com.example.lab_1.repositories.Interfaces.SalaryProjectRepository;

import java.util.List;
import java.util.Optional;

public class SalaryProjectService {
    private static SalaryProjectService instance;
    private final SalaryProjectRepository salaryProjectRepository;

    private SalaryProjectService() {
        this.salaryProjectRepository = new CouchbaseSalaryProjectRepository();

        SalaryProject.setIdGenerator(salaryProjectRepository.getMaxSalaryProjectId());
    }

    public static synchronized SalaryProjectService getInstance() {
        if (instance == null) {
            instance = new SalaryProjectService();
        }
        return instance;
    }

    public List<Integer> getUsersOfSalaryProject(String salaryProjectId) {
        return salaryProjectRepository.getUsersOfSalaryProject(salaryProjectId);
    }

    public void saveSalaryProject(SalaryProject salaryProject) {
        salaryProjectRepository.save(salaryProject);
    }

    public Optional<SalaryProject> getSalaryProjectByEnterpriseId(String enterpriseId) {
        return salaryProjectRepository.findByEnterpriseId(enterpriseId);
    }

    public Optional<SalaryProject> getSalaryProjectById(String id) {
        return salaryProjectRepository.findById(id);
    }

    public List<SalaryProject> getAllSalaryProjects() {
        return salaryProjectRepository.findAll();
    }

    public List<SalaryProject> getSalaryProjectsByUserId(String userId) {
        return salaryProjectRepository.findByUserId(userId);
    }

    public List<SalaryProject> getSalaryProjectsByBankId(String bankId) {
        return salaryProjectRepository.findByBankId(bankId);
    }

    public List<SalaryProject> getPendingApprovals(String bankId) {
        return salaryProjectRepository.findPendingApprovals(bankId);
    }

    public boolean deleteSalaryProject(String id) {
        List<Integer> list = getUsersOfSalaryProject(id);

        boolean result = true;
        for (Integer userId : list) {
            if(!UserService.getInstance().deleteUserFromSalary(String.valueOf(userId))){
                result = false;
            }
        }

        if(!result){
            return false;
        }

        return salaryProjectRepository.delete(id);
    }

    public void approveSalaryProject(String id) {
        Optional<SalaryProject> salaryProjectOpt = salaryProjectRepository.findById(id);
        salaryProjectOpt.ifPresent(salaryProject -> {
            salaryProject.setApproved(true);
            salaryProjectRepository.replace(salaryProject);
        });

        LogService.logAction(new LogEntry(
                "salary_project_creation",
                id,
                "Зарплатный проект " + id + " был одобрен"
        ));
    }
}
