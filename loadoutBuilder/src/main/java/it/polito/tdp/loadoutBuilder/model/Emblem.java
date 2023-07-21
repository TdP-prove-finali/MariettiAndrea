package it.polito.tdp.loadoutBuilder.model;

import java.util.Objects;

public class Emblem {
	
	private String nome;
	private String colore;
	private String param_up;
	private double val_up;
	private boolean isPerc_up;
	private String param_down;
	private double val_down;
	private boolean isPerc_down;
	
	
	public Emblem(String nome, String colore, String param_up, double val_up, boolean isPerc_up, String param_down,
			double val_down, boolean isPerc_down) {
		this.nome = nome;
		this.colore = colore;
		this.param_up = param_up;
		this.val_up = val_up;
		this.isPerc_up = isPerc_up;
		this.param_down = param_down;
		this.val_down = val_down;
		this.isPerc_down = isPerc_down;
	}


	public String getNome() {
		return nome;
	}


	public String getColore() {
		return colore;
	}


	public String getParam_up() {
		return param_up;
	}


	public double getVal_up() {
		return val_up;
	}


	public boolean isPerc_up() {
		return isPerc_up;
	}


	public String getParam_down() {
		return param_down;
	}


	public double getVal_down() {
		return val_down;
	}


	public boolean isPerc_down() {
		return isPerc_down;
	}


	@Override
	public String toString() {
		
		String result = "";
		
		if(isPerc_up == true) {
			if(isPerc_down == true) {
				result = nome +" "+param_up+":"+" "+val_up+"%"+" "+param_down+":"+" "+val_down+"%";
			}else {
				result = nome +" "+param_up+":"+" "+val_up+"%"+" "+param_down+":"+" "+val_down;
			}
		}else {
			result = nome +" "+param_up+":"+" "+val_up+" "+param_down+":"+" "+val_down;
		}
		
		return result;
	}


	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emblem other = (Emblem) obj;
		return Objects.equals(nome, other.nome);
	}
	
	
	
	
	
	
}
