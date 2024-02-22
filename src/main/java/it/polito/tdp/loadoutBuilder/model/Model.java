package it.polito.tdp.loadoutBuilder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.polito.tdp.loadoutBuilder.dao.EmblemDAO;

public class Model {
	
	private EmblemDAO dao;
	private List<Emblem> listaEmblemi;
	
	private Map<Integer, String> idMap;
	private Map<String, Integer> reverseIdMap;
	private Map<String, Integer> mappaSoglie;
	
	private List<Emblem> buildFinale;
	private double bestScore;
	private int idParam;
	
	private int idSecondario;
	
	public Model() {
		this.dao = new EmblemDAO();
		this.idMap = new HashMap<>();
		this.reverseIdMap = new HashMap<>();
		this.mappaSoglie = new HashMap<>();
		this.creaMappe(this.idMap, this.reverseIdMap);
		this.aggiungiSoglie(this.mappaSoglie);
	}
	
	//prova	
	



	public void creaBuild(String paramPrincipale, String parametroSecondario, List<Emblem> scelti) {
		
		this.idParam = this.reverseIdMap.get(paramPrincipale);
		this.bestScore = 0.0;
		
		
		List<Emblem> parziale = new ArrayList<>(scelti);
		
		if (parziale.size() == 10) {
			this.buildFinale = new ArrayList<>(parziale);
			return;
		}else {
			
			/**
			 * estraggo casualmente 30 emblemi e da li parto
			 * riduce i tempi
			 * **/
			
			List<Emblem> emblemi = new ArrayList<Emblem>();
			emblemi.addAll(this.randomPull(this.listaEmblemi));
			
			if(parametroSecondario == null) {
				long tic = System.nanoTime();
				this.cerca(paramPrincipale, parziale,emblemi);
				long tac = System.nanoTime();
				System.out.println("Tempo: "+(tac-tic)/1000000000+" secondi");
			}else {
				
				this.idSecondario = this.reverseIdMap.get(parametroSecondario);
				
				long tic = System.nanoTime();
				this.cerca(paramPrincipale, parametroSecondario,parziale,emblemi);
				long tac = System.nanoTime();
				System.out.println("Tempo: "+(tac-tic)/1000000000+" secondi");
			}
			
			
			
		}
		
		
	}


	private ArrayList<Emblem> randomPull(List<Emblem> pool) {
		
		ArrayList<Emblem> temp = new ArrayList<Emblem>();
		int n = 30;
		
		while(temp.size()<n) {
			Random rand = new Random();
			int pick = rand.nextInt(pool.size());
			Emblem picked = pool.get(pick);
			temp.add(picked);
			pool.remove(pick);
		}
		
		return temp;
	}

	// funzione 1
	private void cerca(String paramPrincipale, String parametroSecondario, List<Emblem> parziale, List<Emblem> emblemi) {


		// condizione di terminazione
		if(parziale.size()==5) {
			// calcola il valore del parametro nel parziale attuale
			double valoreAttualePrinc = this.calcolaAttuale(parziale,this.idParam);
			double valoreAttualeSecond = this.calcolaAttuale(parziale,this.idSecondario);
			if(valoreAttualePrinc<=0 || valoreAttualeSecond<=0) {
				return;
			}
			if(this.bestScore==0 && valoreAttualeSecond>=0) {
				this.bestScore = valoreAttualePrinc;
				this.buildFinale = new ArrayList<>(parziale);
			}
			if(this.bestScore>=valoreAttualePrinc) {
				return;
			}else {
				System.out.println("Valore attuale: "+valoreAttualePrinc+"  "+"Valore migliore: "+this.bestScore);
				boolean isValido = this.isBuildValid(parziale,this.idSecondario);
				if(isValido) {
					this.bestScore = valoreAttualePrinc;
					
					this.buildFinale = new ArrayList<>(parziale);
					return;
				}
			}
		}
		
		for(int i=0;i<emblemi.size();i++) {
			Emblem e = emblemi.get(i);
			if(!parziale.contains(e) ) {
				parziale.add(e);
				e.setUsi(e.getUsi()-1);
				this.cerca(paramPrincipale,parametroSecondario, parziale, emblemi);
				parziale.remove(parziale.size()-1);
				e.setUsi(e.getUsi()-1);
			}
				

			}
		}
	
	
	// funzione 2
	private void cerca(String paramPrincipale, List<Emblem> parziale, List<Emblem> emblemi) {
		
		
		// condizione di terminazione
		if(parziale.size()==10) {
			// calcola il valore del parametro nel parziale attuale
			double valoreAttualePrinc = this.calcolaAttuale(parziale,this.idParam);
			if(valoreAttualePrinc<=0) {
				return;
			}
			if(this.bestScore==0) {
				this.bestScore = valoreAttualePrinc;
				this.buildFinale = new ArrayList<>(parziale);
			}
			if(this.bestScore>=valoreAttualePrinc) {
				return;
			}else {
				System.out.println("Valore attuale: "+valoreAttualePrinc+"  "+"Valore migliore: "+this.bestScore);
				this.bestScore = valoreAttualePrinc;
				this.buildFinale = new ArrayList<>(parziale);
				return;
			}
		}
		
		for(int i=0;i<emblemi.size();i++) {
			Emblem e = emblemi.get(i);
			if(!parziale.contains(e) ) {
				parziale.add(e);
				e.setUsi(e.getUsi()-1);
				
				if(parziale.size()==8) {
					if(this.calcolaAttuale(parziale, this.idParam)<this.mappaSoglie.get(paramPrincipale)) {
						parziale.remove(parziale.size()-1);
						e.setUsi(e.getUsi()+1);
						return;
					}
				}else {
					this.cerca(paramPrincipale,parziale, emblemi);
					parziale.remove(parziale.size()-1);
					e.setUsi(e.getUsi()-1);
			}
				

			}
		}
		
	}



	private double calcolaAttuale(List<Emblem> parziale, int id) {

		double value=0.0;
		for(Emblem e : parziale) {
			if(e.getIdUp()==id) {
				value += e.getVal_up();
			}else if(e.getIdDown()==id) {
				value +=e.getVal_down();
			}
		}
		return value;
	}



	private boolean isBuildValid(List<Emblem> list, int idSecondario) {

		double value = 0.0;
		
		for(Emblem e : list) {
			if(e.getIdUp()==idSecondario) {
				value += e.getVal_up();
			}else if (e.getIdDown()==idSecondario) {
				value += e.getVal_down();
			}
		}
		if(value>=0.0) {
			return true;
		}else {
			return false;
		}
	}

	
	public List<Emblem> getEmblemi() {
		
		this.listaEmblemi = this.dao.getEmblemi(this.reverseIdMap);
		
		return listaEmblemi;
	}




	private void creaMappe(Map<Integer, String> mappa1, Map<String, Integer> mappa2) {
		
		String[] arrParam = {"Attack", "Sp.Atk", "Defense", "Sp.Def","HP","Crit.Rate","Mov.Speed"};
		
		for(int i=0;i<arrParam.length;i++) {
			mappa1.put(i, arrParam[i]);
			mappa2.put(arrParam[i], i);
		}
		return;
	}




	public List<Emblem> getBuildFinale() {
		return buildFinale;
	}
	
	
	private void aggiungiSoglie(Map<String, Integer> mappa) {
		mappa.put("Attack", 3);
		mappa.put("Sp.Atk", 3);
		mappa.put("Defense", 3);
		mappa.put("Sp.Def", 3);
		mappa.put("HP", 100);
		mappa.put("Crit.Rate", 1);
		mappa.put("Mov.Speed", 100);
	}
}


