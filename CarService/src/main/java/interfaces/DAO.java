package main.java.interfaces;

import java.util.List;

public interface DAO<T> {
    T findById(long id);

    void save(T t);

    void update(T t);

    void delete(T t);

    List<T> getAll();
}
