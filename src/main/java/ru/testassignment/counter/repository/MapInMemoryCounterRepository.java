package ru.testassignment.counter.repository;


import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MapInMemoryCounterRepository implements CounterRepository {

    private static final ConcurrentMap<String, AtomicInteger> memoryStore = new ConcurrentHashMap<>();

    @Override
    public Set<String> getAllNames() {
        return memoryStore.keySet();
    }

    @Override
    public String save(String name) {
        if (name != null) {
            memoryStore.computeIfAbsent(name, key -> new AtomicInteger(0));
        }
        return memoryStore.get(name) != null ? name : null;
    }

    @Override
    public boolean increment(String name) {
        AtomicInteger atomicInteger = memoryStore.get(name);
        if (atomicInteger != null) {
            int beforeUpdate = atomicInteger.get();
            int afterUpdate = atomicInteger.incrementAndGet();
            return beforeUpdate < afterUpdate;
        }
        return false;
    }

    @Override
    public boolean remove(String name) {
        AtomicInteger removedCounter = memoryStore.remove(name);
        return removedCounter != null;
    }

    @Override
    public int getAllCountersSum() {
        return memoryStore.values().stream().mapToInt(AtomicInteger::get).sum();
    }
}
