package org.lessons.java.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizze")
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=255)
	private String name;
	
	@NotNull
	@Size(min=2,max=1000)
	@Column(length=1000)
	private String description;
	private String url;
	
	@NotNull
	@DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di zero")
	private Double prezzo;
	
	@OneToMany(mappedBy = "pizza", cascade = CascadeType.REMOVE)
	private List<OffertaSpeciale> offerteSpeciali;
	
	@ManyToMany
    @JoinTable(
        name = "pizza_ingrediente",
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredienti;
	
	
	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}
	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	public List<OffertaSpeciale> getOfferteSpeciali() {
		return offerteSpeciali;
	}
	public void setOfferteSpeciali(List<OffertaSpeciale> offerteSpeciali) {
		this.offerteSpeciali = offerteSpeciali;
	}
	
	
	
}
