package com.mila.controledegastos.api.dtos;

import java.math.BigDecimal;

public class MonthlySumDto {
	
	private Double sumIncome;
	private Double sumOutcome;
	
	public MonthlySumDto() {
	}
	
	public Double getSumIncome() {
		return sumIncome;
	}

	public void setSumIncome(Double sumIncome) {
		this.sumIncome = sumIncome;
	}

	public Double getSumOutcome() {
		return sumOutcome;
	}

	public void setSumOutcome(Double sumOutcome) {
		this.sumOutcome = sumOutcome;
	}

}
