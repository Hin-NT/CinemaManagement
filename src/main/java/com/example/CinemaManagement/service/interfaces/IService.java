package com.example.CinemaManagement.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IService<T> {
    public List<T> getAll();

    public T getById(T t);

    public ResponseEntity<String> add(T t);

    public ResponseEntity<String> update(T t);

    public ResponseEntity<String> delete(T t);

}
