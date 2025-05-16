package com.example.AD_07_01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.AD_07_01.domain.Continent;
import com.example.AD_07_01.domain.Country;
import com.example.AD_07_01.repository.ContinentRepository;
import com.example.AD_07_01.repository.CountryRepository;

@RestController
@RequestMapping("/api/continents")
public class ContinentController {
	//se auto inyecta
	@Autowired
	private ContinentRepository continentRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	//devuelve todos los continentes
	@GetMapping
	public List<Continent> getAll(){
		return continentRepository.findAll();
	}
	
	//READ
	@GetMapping("/{id}")
	public ResponseEntity<Continent> getId(@PathVariable int id){
		Optional<Continent> continent = continentRepository.findById(id);
		//si correcto, devolvemos codigo 200, si no lo encuentra devolvemos continente vacio y codigo 404
		return continent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	//CREATE
	@PostMapping
	public Continent create(@RequestBody Continent continent) {
		
		return continentRepository.save(continent);
	}
	
	//UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<Continent> update(@PathVariable int id, @RequestBody Continent updatedContinent) {
	    return continentRepository.findById(id).map(continent -> {
	        continent.setName(updatedContinent.getName());

	        if (updatedContinent.getCountries() != null) {

	            continent.getCountries().clear();


	            for (Country c : updatedContinent.getCountries()) {
	                countryRepository.findById(c.getId()).ifPresent(continent.getCountries()::add);
	            }
	        }

	        return ResponseEntity.ok(continentRepository.save(continent));
	    }).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	//DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable int id){
		if(continentRepository.existsById(id)) {
			continentRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
			return ResponseEntity.notFound().build();
	}
}
