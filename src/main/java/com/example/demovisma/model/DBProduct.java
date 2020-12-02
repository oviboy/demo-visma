package com.example.demovisma.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DBProduct {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name="name", unique=true, nullable = false)
	private String name;
	
	@Column(name="price", nullable = false)
	private float price = 0.0f;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "product_id")
    private Set<DBDiscount> discounts = new HashSet<DBDiscount>();	
	
	public DBProduct() {
		
	}
	
	public DBProduct(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public Set<DBDiscount> getDiscounts() {
		return discounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBProduct other = (DBProduct) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product {id=")
        	   .append(this.id)
        	   .append(", name=")
               .append(this.name)
        	   .append(", basePrice=")
               .append(String.format("%.2f", this.price))
               .append("}");

        return builder.toString();
    }

}
