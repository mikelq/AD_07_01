package com.example.AD_07_01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AD_07_01.domain.Country;

public interface CountryRepository extends JpaRepository<Country,Integer>{

}
