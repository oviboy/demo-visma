package com.example.demovisma;

import java.util.Date;
import java.util.Optional;

import com.example.demovisma.model.DBDiscount;

public class DiscountDecorator extends BaseProduct {
	protected BaseProduct bp;
	protected Integer quantity;
	protected DBDiscount d;
	protected Date checkDate;
	
	public DiscountDecorator(BaseProduct p, Integer quantity, DBDiscount d, Optional<Date> checkDate) {
		this.bp = p;
		this.d = d;
		this.quantity = quantity;
		if(checkDate.isPresent())
			this.checkDate = checkDate.get();
		else
			this.checkDate = new Date();
	}
	
	private boolean isValid() {
		if(!checkDate.after(this.d.getStarts_on()) && checkDate.before(this.d.getEnds_on())) {
			return false;
		}
		if(quantity < this.d.getMinq())
			return false;
		
		return true;
	}
	
	@Override
	public float getPrice() {
		if(!isValid()) {
			return this.bp.getPrice();	//no discount can be applied, since it expired
		}
		
		float basePrice = this.bp.getPrice();
		float discount = 0.0f;
		switch(this.d.getCalculation()) {
			case PERCENT: {		
				discount = (basePrice*this.d.getAmount())/100;
				if(basePrice - discount > 0)
					basePrice = basePrice - discount;
				else basePrice = 0.0f;
				break;
			}
			case REPLACEBY: {
				basePrice = this.d.getAmount() > 0 ? this.d.getAmount():0.0f;
				break;
			}
			case REDUCEDBY: {
				basePrice = basePrice - this.d.getAmount() > 0 ? basePrice - this.d.getAmount() : 0; 
				break;
			}
		}
		return basePrice * quantity;	//modified basePrice with the right discount
	}
}
