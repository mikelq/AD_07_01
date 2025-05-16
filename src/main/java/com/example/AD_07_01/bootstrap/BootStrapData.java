package com.example.AD_07_01.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.AD_07_01.domain.Continent;
import com.example.AD_07_01.domain.Country;
import com.example.AD_07_01.repository.ContinentRepository;
import com.example.AD_07_01.repository.CountryRepository;

import jakarta.transaction.Transactional;

@Component
public class BootStrapData implements CommandLineRunner {
	//creamos un repositorio de country
	@Autowired
	private CountryRepository countryRepository;
	
	//creamos un repositorio de Continent
	@Autowired
	private ContinentRepository continentRepository;
	
	@Transactional
	@Override
	public void run (String... arg0) throws Exception{
		 // Creamos los continentes
        Continent continent1 = new Continent("America");
        Continent continent2 = new Continent("Europe");
        Continent continent3 = new Continent("Africa");

        // Creamos los países
        Country country1 = new Country("USA", 120);
        Country country2 = new Country("Estonia", 40);
        Country country3 = new Country("Egipt", 80);
        Country country4 = new Country("Canada", 70);
        Country country5 = new Country("UK", 76);

        // Asignamos los continentes a los países
        country1.setContinent(continent1);
        country2.setContinent(continent2);
        country3.setContinent(continent3);
        country4.setContinent(continent1);
        country5.setContinent(continent2);

        // Añadimos los países a la lista de países de cada continente
        continent1.getCountries().add(country1);
        continent1.getCountries().add(country4);
        continent2.getCountries().add(country2);
        continent2.getCountries().add(country5);
        continent3.getCountries().add(country3);

        // Guardamos todos los continentes y países
        continentRepository.save(continent1);
        continentRepository.save(continent2);
        continentRepository.save(continent3);

        countryRepository.save(country1);
        countryRepository.save(country2);
        countryRepository.save(country3);
        countryRepository.save(country4);
        countryRepository.save(country5);
	}
}
