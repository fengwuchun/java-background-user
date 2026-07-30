package com.demo.backend.controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.demo.backend.entity.City;
import com.demo.backend.service.CityService;


@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @PostMapping
    public City add(@RequestBody City city){
        return service.createCity(city);
    }

    @GetMapping
    public List<City> searchAll(){
        return service.getAllCities();
    }

    @GetMapping("/{id}")
    public City searchById(@PathVariable("id") Long id){
        return service.getCityById(id);
    }

    @PutMapping("/{id}")
    public City update(@PathVariable("id") Long id, @RequestBody City city){
        return service.updateCity(id,city);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) throws Exception{
        service.deleteCity(id);
    }
    
}
