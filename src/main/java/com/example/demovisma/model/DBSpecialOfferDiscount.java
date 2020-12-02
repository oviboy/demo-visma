package com.example.demovisma.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SpecialOfferDiscount")
public class DBSpecialOfferDiscount extends DBDiscount {
	
}
