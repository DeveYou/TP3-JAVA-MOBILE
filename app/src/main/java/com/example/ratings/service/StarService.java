package com.example.ratings.service;

import com.example.ratings.beans.Star;
import com.example.ratings.dao.IDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarService implements IDAO<Star> {

    private List<Star> stars;
    private static StarService instance;

    private StarService(){
        this.stars = new ArrayList<>();
    }

    public static StarService getInstance(){
        if(instance == null){
            instance = new StarService();
        }
        return instance;
    }

    @Override
    public boolean create(Star obj) {
        return stars.add(obj);
    }

    @Override
    public boolean update(Star obj) {
        for(Star s : stars){
            if(s.getStar() == obj.getStar()){
                s.setImg(obj.getImg());
                s.setName(obj.getName());
                s.setStar(obj.getStar());
            }
        }
        return true;
    }

    @Override
    public boolean delete(Star obj) {
        return stars.remove(obj);
    }

    @Override
    public Star findById(int id) {
        for(Star s : stars){
            if(s.getId() == id)
                return s;
        }
        return null;
    }

    @Override
    public List<Star> findAll() {
        return stars;
    }
}
