package com.example.demovisma.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demovisma.DiscountCalcOp;

@Entity
@DiscriminatorColumn(
		name="type", 
		discriminatorType=DiscriminatorType.STRING)
public class DBDiscount {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	private String description;	//description of this discount
	
	@Enumerated(EnumType.STRING)
	private DiscountCalcOp calculation; //use PERCENT or REPLACEBY or REDUCEDBY to indicate how a discount is calculated
	
	private float amount;	//the amount to be reduced (based on calculation)
	
	//minimum quantity to apply a discount
	@Column(name="minq")
	protected int minq = 1;
	
	@Column(name = "starts_on")	//validity start
	@Temporal(TemporalType.TIMESTAMP)
	private Date starts_on;
	
	@Column(name = "ends_on")	//validity end
	@Temporal(TemporalType.TIMESTAMP)
	private Date ends_on;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
	
	public DBDiscount() {
		
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DiscountCalcOp getCalculation() {
		return calculation;
	}

	public void setCalculation(DiscountCalcOp calculation) {
		this.calculation = calculation;
	}

	public float getAmount() {
		return amount;
	}

	public int getMinq() {
		return minq;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getStarts_on() {
		return starts_on;
	}

	public void setStarts_on(Date starts_on) {
		this.starts_on = starts_on;
	}

	public Date getEnds_on() {
		return ends_on;
	}

	public void setEnds_on(Date ends_on) {
		this.ends_on = ends_on;
	}
	
	@Override
	public String toString() {
        StringBuilder builder = new StringBuilder();
        switch(calculation) {
	        case PERCENT: {
	        	builder
	        		.append(String.format("%g", this.amount))
	        		.append(" % off");
	        	break;
	        }
	        case REPLACEBY: {
	        	builder
	        		.append("New price ")
	        		.append(String.format("%g", this.amount));
	        	break;
	        }
	        case REDUCEDBY: {
	        	builder
	        		.append("Price reduced by ")
	        		.append(String.format("%g", this.amount));
	        	break;
	        }
        }

        return builder.toString();
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
		DBDiscount other = (DBDiscount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
