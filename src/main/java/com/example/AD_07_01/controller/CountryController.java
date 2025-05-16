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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AD_07_01.domain.Continent;
import com.example.AD_07_01.domain.Country;
import com.example.AD_07_01.repository.ContinentRepository;
import com.example.AD_07_01.repository.CountryRepository;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
	//se auto inyecta
		@Autowired
		private CountryRepository countryRepository;
		@Autowired
		private ContinentRepository continentRepository;
		
		//devuelve todos los continentes
		@GetMapping
		public List<Country> getAll(){
			return countryRepository.findAll();
		}
		
		//READ
		@GetMapping("/{id}")
		public ResponseEntity<Country> getId(@PathVariable int id){
			Optional<Country> country = countryRepository.findById(id);
			//si correcto, devolvemos codigo 200, si no lo encuentra devolvemos continente vacio y codigo 404
			return country.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
		}
		
		//CREATE
		@PostMapping
		public ResponseEntity<Country> create(@RequestBody Country country) {
			if (country.getContinent() != null && !continentRepository.existsById(country.getContinent().getId())) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok(countryRepository.save(country));
		}
		
		//UPDATE
		@PutMapping("/{id}")
		public ResponseEntity<Country> update(@PathVariable int id, @RequestBody Country updatedCountry){
			return countryRepository.findById(id).map(country-> {
				country.setName(updatedCountry.getName());
				country.setPopulation(updatedCountry.getPopulation());
				country.setContinent(updatedCountry.getContinent());
				return ResponseEntity.ok(countryRepository.save(country));
			}).orElseGet(() -> ResponseEntity.notFound().build());
		}
		
		//DELETE
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> delete (@PathVariable int id){
			if(countryRepository.existsById(id)) {
				countryRepository.deleteById(id);
				return ResponseEntity.noContent().build();
			}
				return ResponseEntity.notFound().build();
		}
}
