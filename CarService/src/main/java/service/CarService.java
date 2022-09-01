package service;

import dao.CarDAOImpl;
import model.Car;

import java.util.List;

public class CarService {
    private final CarDAOImpl carDAO;

    public CarService(CarDAOImpl carDAO) {
        this.carDAO = carDAO;
    }

    public Car findCarById(long id) {
        return carDAO.findById(id);
    }

    public void saveCar(Car car) {
        carDAO.save(car);
    }

    public void updateCar(Car car) {
        carDAO.update(car);
    }

    public void deleteCar(Car car) {
        carDAO.delete(car);
    }

    public List<Car> getAllCars() {
        return carDAO.getAll();
    }

}
