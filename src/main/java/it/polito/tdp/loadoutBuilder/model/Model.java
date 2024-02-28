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
	
	private Map<Integer, String> idMap; // codice parametro -> (String) nome parametro
	private Map<String, Integer> reverseIdMap; // (String) nome parametro -> codice parametro
	private Map<String, Integer> mappaSoglie; // mappa che contiene i valori minimi per ogni parametro (String) nome parametro -> valore
	
	private List<Emblem> buildFinale;
	private List<Emblem> buildParziale1;
	private List<Emblem> buildParziale2;
	
	private boolean var_stop; // variabile per inibile l'utilizzo di buildParziale1 e usare la 2
	private double bestScore; // variabile che tiene traccia del valore migliore ottenuto
	private int idParam; // id del parametro principale
	private int idSecondario; // id del parametro secondario
	
	public Model() {
		this.dao = new EmblemDAO();
		this.idMap = new HashMap<>();
		this.reverseIdMap = new HashMap<>();
		this.mappaSoglie = new HashMap<>();
		
		this.var_stop=true;
		this.creaMappe(this.idMap, this.reverseIdMap);
		this.aggiungiSoglie(this.mappaSoglie);
		
	}

	
	/**
	 *  Funzione per creare la build manuale
	 *  Riceve il nome del parametro scelto e gli emblemi selezionati
	 * 	
	 * **/
	public void creaBuild(String paramPrincipale, List<Emblem> scelti) {
		
		this.idParam = this.reverseIdMap.get(paramPrincipale);
		this.bestScore = 0.0;
		
		List<Emblem> parziale = new ArrayList<>(scelti);
		
		if (parziale.size() == 10) {
			this.buildFinale = new ArrayList<>(parziale);
			return;
		}else {
			
			List<Emblem> emblemi = new ArrayList<Emblem>();
			emblemi.addAll(this.randomPull(this.listaEmblemi));
			
			this.cerca(paramPrincipale,parziale,emblemi);
		}
	}

	
	/**
	 *  Funzione per creare la build automatica
	 *  Riceve il nome del parametro scelto e quello secondario (null se non scelto)
	 * 	
	 * **/
	public void creaBuild(String paramPrincipale, String paramSecondario) {
		
		this.idParam = this.reverseIdMap.get(paramPrincipale);
		this.bestScore = 0.0;
		
		List<Emblem> parziale = new ArrayList<>();
		
			List<Emblem> emblemi;
			
			
			if(paramSecondario == null) {
				emblemi = new ArrayList<Emblem>();
				emblemi.addAll(this.randomPull(this.listaEmblemi));
				this.cerca(paramPrincipale, parziale,emblemi);
			}else {
				emblemi = new ArrayList<Emblem>();
				emblemi.addAll(this.randomPull(this.listaEmblemi));
				this.idSecondario = this.reverseIdMap.get(paramSecondario);
				
				this.cerca(paramPrincipale, paramSecondario,parziale,emblemi);
				
				
				this.var_stop = false;
				emblemi = new ArrayList<Emblem>();
				emblemi.addAll(this.randomPull(this.listaEmblemi));
				this.cerca(paramPrincipale, paramSecondario,parziale,emblemi);

				
				if(this.buildParziale1!=null && this.buildParziale2!=null) {
					this.buildFinale = new ArrayList<Emblem>();
					this.buildFinale.addAll(this.buildParziale1);
					this.buildFinale.addAll(this.buildParziale2);
					
					double p2 = this.calcolaAttuale(this.buildFinale, this.idSecondario);
					
					if(p2<0) {
						this.buildFinale = new ArrayList<Emblem>();
						System.out.println("svuotato");
					}
				}
			}
		
	}
	
	
	/**
	 * Modalità Automatica
	 * Cerca la combinazione migliore avendo entrambi i parametri (max e non modificabile)
	 * il parziale è vuoto e emblemi contiene i 30 emblemi estratti casualmente
	 * **/
	private void cerca(String paramPrincipale, String parametroSecondario, List<Emblem> parziale, List<Emblem> emblemi) {


		// condizione di terminazione
		if(parziale.size()==5) {
			// calcola il valore del parametro nel parziale attuale
			double valoreAttualePrinc = this.calcolaAttuale(parziale,this.idParam);
			double valoreAttualeSecond = this.calcolaAttuale(parziale,this.idSecondario);
			
			// caso valori troppo bassi
			if(valoreAttualePrinc<=0 || valoreAttualeSecond<=0) {
				return;
			}
			
			// caso bestscore migliore del valore attuale
			else if(this.bestScore>=valoreAttualePrinc) {
				return;
			}
			
			// caso inizializzatore -> setto il valore migliore SE il secondario è >=0
			else if(this.bestScore==0 && valoreAttualeSecond>=0) {
				this.bestScore = valoreAttualePrinc;
				if(this.var_stop) {
					this.buildParziale1 = new ArrayList<Emblem>(parziale);
				} else {
					this.buildParziale2 = new ArrayList<Emblem>(parziale);
				}
				return;
			}
			
			// caso valore attuale migliore del bestscore e secondario accettabile
			else if(this.bestScore<valoreAttualePrinc && valoreAttualeSecond>=0) {
				System.out.println("Valore attuale: "+valoreAttualePrinc+"  "+"Valore migliore: "+this.bestScore);
				this.bestScore = valoreAttualePrinc;
				
				if(this.var_stop) {
					this.buildParziale1 = new ArrayList<Emblem>(parziale);
				} else {
					this.buildParziale2 = new ArrayList<Emblem>(parziale);
				}
				return;
			}
		}
		
		for(int i=0;i<emblemi.size();i++) {
			Emblem e = emblemi.get(i);
			if(!parziale.contains(e) ) {
				parziale.add(e);
				this.cerca(paramPrincipale,parametroSecondario, parziale, emblemi);
				parziale.remove(parziale.size()-1);
				}
			}
		}
	
	
	/**
	 * Modalità Manuale e/o 1 parametro
	 * Cerca la combinazione migliore avendo il parametro da massimizzare,
	 * parziale contenente gli emblemi scelti e emblemi contiene i 30 emblemi estratti casualmente
	 * **/
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
				
				if(parziale.size()==8) {
					if(this.calcolaAttuale(parziale, this.idParam)<this.mappaSoglie.get(paramPrincipale)) {
						parziale.remove(parziale.size()-1);
						return;
					}
				}else {
					this.cerca(paramPrincipale,parziale, emblemi);
					parziale.remove(parziale.size()-1);
				}
			}
		}
		
	}


	/**
	 * Calcola quanto vale il valore legato al parametro di cui viene passato l'id
	 * @return (double) risultato calcoli
	 * **/
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

	
	/**
	 * Riceve come parametro la lista contenente tutti gli emblemi utilizzabili
	 * @return lista con 30 emblemi estratti casualmente
	 * **/
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


	/**
	 * Crea le mappe che collegano l'id del parametro, ad esso, e viceversa
	 * **/
	private void creaMappe(Map<Integer, String> mappa1, Map<String, Integer> mappa2) {
		
		String[] arrParam = {"Attack", "Sp.Atk", "Defense", "Sp.Def","HP","Crit.Rate","Mov.Speed"};
		
		for(int i=0;i<arrParam.length;i++) {
			mappa1.put(i, arrParam[i]);
			mappa2.put(arrParam[i], i);
		}
		return;
	}
	
	/**
	 * Aggiunge parametri corrispondenti ai valori minimi 
	 * accettabili per un dato valore principale
	 * **/
	private void aggiungiSoglie(Map<String, Integer> mappa) {
		mappa.put("Attack", 3);
		mappa.put("Sp.Atk", 3);
		mappa.put("Defense", 3);
		mappa.put("Sp.Def", 3);
		mappa.put("HP", 100);
		mappa.put("Crit.Rate", 1);
		mappa.put("Mov.Speed", 100);
	}
	
	/**
	 * @return lista contenente la build finale
	 * **/
	public List<Emblem> getBuildFinale() {
		return buildFinale;
	}
	
	
	/**
	 * Ottiene dal db tutti gli emblemi utilizzabili
	 * @return lista con tutti gli emblemi
	 * **/
	public List<Emblem> getEmblemi() {
		this.listaEmblemi = this.dao.getEmblemi(this.reverseIdMap);
		return listaEmblemi;
	}
}


