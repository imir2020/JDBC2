package by.javaguru.je.dao;

import by.javaguru.je.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface Dao<K,E> {
    boolean update(E e);

    List<E> findAll();

    Optional<E> findById(K id);

    E save(E ticket);

    boolean delete(K id);
}
