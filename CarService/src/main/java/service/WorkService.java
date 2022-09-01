package service;

import dao.WorkDAOImpl;
import exceptions.DuplicateDataExceptions;
import model.Work;

import java.util.List;

public class WorkService {
    private final WorkDAOImpl workDAO;

    public WorkService(WorkDAOImpl WorkDAO) {
        this.workDAO = WorkDAO;
    }

    public Work findWorkById(long id) {
        return workDAO.findById(id);
    }

    public void saveWork(Work work) {
        if (workDAO.findByWorkName(work.getWorkName()).stream().count() != 0) {
            throw new DuplicateDataExceptions("Работа с таким названием уже существует. " +
                    "Название работы должно быть уникальным. Проверьте правильность ввода данных.");
        }
        workDAO.save(work);
    }

    public void updateWork(Work work) {
        if (workDAO.findByWorkName(work.getWorkName()).stream().count() != 0) {
            throw new DuplicateDataExceptions("Работа с таким названием уже существует. " +
                    "Название работы должно быть уникальным. Проверьте правильность ввода данных.");
        }
        workDAO.update(work);
    }

    public void deleteWork(Work work) {
        workDAO.delete(work);
    }

    public List<Work> getAllWork() {
        return workDAO.getAll();
    }

}
