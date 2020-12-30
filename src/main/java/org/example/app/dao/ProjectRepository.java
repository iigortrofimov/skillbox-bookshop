package org.example.app.dao;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();

    void store(T object);

    boolean removeItemById(Integer id);
}
