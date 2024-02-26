package it.polito.tdp.loadoutBuilder.model;

import java.util.Objects;

public class Emblem {
	
	private String nome;
	private String colore;
	private int idUp;
	private String param_up;
	private double val_up;
	private int idDown;
	private String param_down;
	private double val_down;
	private int usi;



	public Emblem(String nome, String colore, int idUp, String param_up, double val_up, int idDown, String param_down,
			double val_down) {
		this.nome = nome;
		this.colore = colore;
		this.idUp = idUp;
		this.param_up = param_up;
		this.val_up = val_up;
		this.idDown = idDown;
		this.param_down = param_down;
		this.val_down = val_down;
		this.usi = 2;
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


	public String getParam_down() {
		return param_down;
	}


	public double getVal_down() {
		return val_down;
	}

	

	public int getIdUp() {
		return idUp;
	}


	public int getIdDown() {
		return idDown;
	}

	
	

	public int getUsi() {
		return usi;
	}


	public void setUsi(int usi) {
		this.usi = usi;
	}


	@Override
	public String toString() {
		String result = "";
		if(param_up.equals("Crit.Rate")) {
			result = nome +" "+param_up+":"+" "+val_up+"% "+param_down+":"+" "+val_down;
		} else if(param_down.equals("Crit.Rate")) {
			result = nome +" "+param_up+":"+" "+val_up+" "+param_down+":"+" "+val_down+"%";
		} else {
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
