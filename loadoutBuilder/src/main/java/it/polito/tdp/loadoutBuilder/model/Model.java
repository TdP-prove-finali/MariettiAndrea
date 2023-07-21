package it.polito.tdp.loadoutBuilder.model;

import java.util.List;

import it.polito.tdp.loadoutBuilder.dao.EmblemDAO;

public class Model {
	
	private EmblemDAO dao;
	
	public Model() {
		this.dao = new EmblemDAO();
		
	}
	
	

	public List<Emblem> getAllEmblems() {
		
		List<Emblem> listaEmblemi = this.dao.getAllEmblems();
		
		return listaEmblemi;
	}

}
