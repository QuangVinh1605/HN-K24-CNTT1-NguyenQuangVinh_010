package org.example.hackathon.service;

import org.example.hackathon.model.Laptop;
import org.example.hackathon.respository.LaptopRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaptopService {

    private final LaptopRespository laptopRespository;

    @Autowired
    public LaptopService(LaptopRespository laptopRespository) {
        this.laptopRespository = laptopRespository;
    }

    public List<Laptop> getAllLaptops() {
        return laptopRespository.findAll();
    }

    public Optional<Laptop> getLaptopById(Long id) {
        return laptopRespository.findById(id);
    }

    public void addLaptop(Laptop laptop) {
        laptopRespository.save(laptop);
    }

    public void updateLaptop(Laptop laptop) {
        laptopRespository.update(laptop);
    }

    public void deleteLaptop(Long id) {
        laptopRespository.deleteById(id);
    }

    public List<Laptop> searchLaptops(String keyword) {
        return laptopRespository.search(keyword);
    }
}