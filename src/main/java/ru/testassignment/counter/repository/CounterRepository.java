package ru.testassignment.counter.repository;

import java.util.Set;

public interface CounterRepository {

    Set<String> getAllNames();

    String save(String name);

    boolean increment(String name);

    boolean remove(String name);

    int getAllCountersSum();
}
