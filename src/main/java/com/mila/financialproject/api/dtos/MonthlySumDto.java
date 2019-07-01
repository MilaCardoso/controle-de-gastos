package com.mila.financialproject.api.dtos;

public class MonthlySumDto {
	
	private Double sumIncome;
	private Double sumOutcome;
	private Double difference;
	
	public MonthlySumDto() {
	}
	
	public Double getSumIncome() {
		if(sumIncome == null) {
			sumIncome = (double) 00;
		}
		return sumIncome;
	}

	public void setSumIncome(Double sumIncome) {
		this.sumIncome = sumIncome;
	}

	public Double getSumOutcome() {
		if(sumOutcome == null) {
			sumOutcome = (double) 00;
		}
		return sumOutcome;
	}

	public void setSumOutcome(Double sumOutcome) {
		this.sumOutcome = sumOutcome;
	}

	public Double getDifference() {
		return difference;
	}

	public void setDifference(Double difference) {
		this.difference = difference;
	}
	
}
