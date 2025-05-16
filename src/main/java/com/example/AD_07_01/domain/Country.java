package com.example.AD_07_01.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Country extends BaseEntity{
	
	@Column
	private String name;
	
	@Column
	private Integer population;
	
	@ManyToOne
	@JoinColumn (name="continent_id")
	@JsonBackReference
	private Continent continent;

	public Country(String name, Integer population) {
		super();
		this.name = name;
		this.population = population;
	}
}
