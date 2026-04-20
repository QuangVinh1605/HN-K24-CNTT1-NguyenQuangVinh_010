package org.example.hackathon.respository;

import org.example.hackathon.model.Laptop;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class LaptopRespository {

    private final List<Laptop> laptops = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public LaptopRespository() {
        laptops.add(new Laptop(idCounter.getAndIncrement(), "MacBook Pro M3", "Apple", 39000000.0, 15, "default.png"));
        laptops.add(new Laptop(idCounter.getAndIncrement(), "Dell XPS 15", "Dell", 28000000.0, 10, "default.png"));
        laptops.add(new Laptop(idCounter.getAndIncrement(), "ThinkPad X1 Carbon", "Lenovo", 25000000.0, 8, "default.png"));
    }

    public List<Laptop> findAll() {
        return new ArrayList<>(laptops);
    }

    public Optional<Laptop> findById(Long id) {
        return laptops.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    public void save(Laptop laptop) {
        laptop.setId(idCounter.getAndIncrement());
        laptops.add(laptop);
    }

    public void update(Laptop updated) {
        for (int i = 0; i < laptops.size(); i++) {
            if (laptops.get(i).getId().equals(updated.getId())) {
                laptops.set(i, updated);
                return;
            }
        }
    }

    public void deleteById(Long id) {
        laptops.removeIf(l -> l.getId().equals(id));
    }

    public List<Laptop> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return findAll();
        String lower = keyword.toLowerCase().trim();
        return laptops.stream()
                .filter(l -> l.getModelName().toLowerCase().contains(lower)
                        || l.getBrand().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }
}