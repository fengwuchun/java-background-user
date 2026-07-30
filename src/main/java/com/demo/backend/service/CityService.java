package com.demo.backend.service;

import com.demo.backend.entity.City;
import com.demo.backend.respository.CityRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * save() 方法：JpaRepository 提供的标准方法
     * 如果 city 对象没有 ID，则执行 INSERT 操作（新增）
     * 如果 city 对象有 ID，则执行 UPDATE 操作（更新）
     */

    //事物
    @Transactional
    public City createCity(City city) {
        return cityRepository.save(city);

    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id){
           if(!cityRepository.existsById(id)){
            throw new RuntimeException("City not found id:"+id);
        }
        return cityRepository.findById(id).get();
    }

    //也需要加入事物
    @Transactional
    public City updateCity(Long id,City city){
        City cityToUpdate = cityRepository.findById(id).get();
        cityToUpdate.setName(city.getName());
        cityToUpdate.setPopulation(city.getPopulation());
        return cityRepository.save(cityToUpdate);
    }

    @Transactional
    public void deleteCity(Long id){
        if(!cityRepository.existsById(id)){
            throw new RuntimeException("City not found id:"+id);
        }
        cityRepository.deleteById(id);
    }
}
