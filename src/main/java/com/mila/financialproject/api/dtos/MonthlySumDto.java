package com.mila.financialproject.api.dtos;

import java.math.BigDecimal;

public class MonthlySumDto {
	
	private BigDecimal sumIncome;
	private BigDecimal sumOutcome;
	private BigDecimal difference;
	
	public MonthlySumDto() {
	}
	
	public BigDecimal getSumIncome() {
		if(sumIncome == null) {
			sumIncome = new BigDecimal(0.00);
		}
		return sumIncome;
	}

	public void setSumIncome(BigDecimal sumIncome) {
		this.sumIncome = sumIncome;
	}

	public BigDecimal getSumOutcome() {
		if(sumOutcome == null) {
			sumOutcome = new BigDecimal(0.00);
		}
		return sumOutcome;
	}

	public void setSumOutcome(BigDecimal sumOutcome) {
		this.sumOutcome = sumOutcome;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	
}
