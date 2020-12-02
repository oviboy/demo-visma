package com.example.demovisma.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DiscountVolume")
public class DBVolumeDiscount extends DBDiscount {
	public DBVolumeDiscount() {
	}

	public DBVolumeDiscount(int volume) {
		this.minq = volume;
	}

	public void setMinq(int volume) {
		this.minq = volume;
	}
}

/*
@Entity
@DiscriminatorValue("DiscountVolume")
public class DBVolumeDiscount extends DBDiscount {
	@Column(name = "volume", nullable = true)
	private int volume; // used only in volume discount if greater than amount (otherwise this field is
						// NULL)

	public DBVolumeDiscount() {
	}

	public DBVolumeDiscount(int volume) {
		this.volume = volume;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
}
*/