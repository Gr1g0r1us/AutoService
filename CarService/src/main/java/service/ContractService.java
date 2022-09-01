package service;

import dao.ContractDAOImpl;
import model.Contract;

import java.util.List;

public class ContractService {
    private final ContractDAOImpl contractDAO;

    public ContractService(ContractDAOImpl contractDAO) {
        this.contractDAO = contractDAO;
    }

    public Contract findContractById(long id) {
        return contractDAO.findById(id);
    }

    public void saveContract(Contract contract) {
        contractDAO.save(contract);
    }

    public void updateContract(Contract contract) {
        contractDAO.update(contract);
    }

    public void deleteContract(Contract contract) {
        contractDAO.delete(contract);
    }

    public List<Contract> getAllContracts() {
        return contractDAO.getAll();
    }

}
