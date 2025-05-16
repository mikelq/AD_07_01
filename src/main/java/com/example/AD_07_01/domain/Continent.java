package com.example.AD_07_01.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Continent extends BaseEntity {
	
	@Column(name="continent_name")
	private String name;
	//Añadimos la relacion de continent con country y si borramos un continent, los country relacionados se borraran ON CASCADE
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn (name="continent_id")
	@JsonManagedReference
	List <Country> countries = new ArrayList<>();

	public Continent(String name) {
		super();
		this.name = name;
	}
}
