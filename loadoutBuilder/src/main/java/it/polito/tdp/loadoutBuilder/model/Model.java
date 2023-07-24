	package it.polito.tdp.loadoutBuilder.model;
	
	import java.util.ArrayList;
	import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
	import java.util.Map;
	import java.util.Set;
	
	
	import it.polito.tdp.loadoutBuilder.dao.EmblemDAO;
	
	public class Model {
		
		private EmblemDAO dao;
		private List<Emblem> listaEmblemi;
		
		private Map<Integer, String> idMap;
		private Map<String, Integer> reverseIdMap;
		
		private List<Emblem> buildFinale;
		
		private double bestPrimo;
		private int idPrimo;
		
		public Model() {
			this.dao = new EmblemDAO();
			this.idMap = new HashMap<>();
			this.reverseIdMap = new HashMap<>();
			this.creaMappe(this.idMap, this.reverseIdMap);
		}
		
		
	
		public void creaBuild(String paramPrincipale, Boolean[] arrChecked, Set<Emblem> scelti) {
			
			this.idPrimo = this.reverseIdMap.get(paramPrincipale);
			this.bestPrimo = 0.0;
			
			List<Emblem> parziale = new ArrayList<>(scelti);
			Set<Emblem> usati = new HashSet<>();
			
			if (parziale.size() == 10) {
				this.buildFinale = new ArrayList<>(parziale);
			}else {
				
				List<Emblem> emblemi = new ArrayList<>(this.listaEmblemi);
//				if(parziale.size()>0) {
//					emblemi.removeAll(parziale);
//				}
				
				long tic = System.nanoTime();
				this.cerca(paramPrincipale, arrChecked,parziale,emblemi,usati,0);
				long tac = System.nanoTime();
				System.out.println("Tempo: "+(tac-tic)/1000000000+" secondi");
			}
			
			
		}
	
	
	
		private void cerca(String paramPrincipale, Boolean[] arrChecked, List<Emblem> parziale, List<Emblem> emblemi, Set<Emblem> usati, int index) {
	
	
			// condizione di terminazione
			if(parziale.size()==10) {
				// calcola il valore del parametro nel parziale attuale
				double valoreAttuale = this.calcolaAttuale(parziale);
				if(valoreAttuale<=0) {
					return;
				}
				if(this.bestPrimo==0) {
					this.bestPrimo = valoreAttuale;
				}
				if(this.bestPrimo>=valoreAttuale) {
					return;
				}else {
					System.out.println("Valore attuale: "+valoreAttuale+"  "+"Valore migliore: "+this.bestPrimo);
					boolean isValido = this.isBuildValid(parziale,arrChecked);
					if(isValido) {
						this.bestPrimo = valoreAttuale;
						this.buildFinale = new ArrayList<>(parziale);
						return;
					}
				}
			}
			
			for(int i=index;i<emblemi.size();i++) {
				Emblem e = emblemi.get(i);
				if(!parziale.contains(e) && !usati.contains(e)) {
					parziale.add(e);
					usati.add(e);
					this.cerca(paramPrincipale,arrChecked, parziale, emblemi, usati, i+1);
					parziale.remove(parziale.size()-1);
				}
			}
//			for(Emblem e : emblemi) {
//				if(!parziale.contains(e)) {
//					parziale.add(e);
//					this.cerca(paramPrincipale,arrChecked, parziale, emblemi);
//					parziale.remove(parziale.size()-1);
//				}
//			}
			
		}
	
	
	
		private double calcolaAttuale(List<Emblem> parziale) {
	
			double value=0.0;
			for(Emblem e : parziale) {
				if(e.getIdUp()==this.idPrimo) {
					value += e.getVal_up();
				}else if(e.getIdDown()==this.idPrimo) {
					value +=e.getVal_down();
				}
			}
			return value;
		}
	
	
	
		private boolean isBuildValid(List<Emblem> list, Boolean[] arrChecked) {
	
			Double[] valori = {0.0,0.0,0.0,0.0,0.0,0.0,0.0};
			
			for(int i=0;i<7;i++) {
				for(Emblem e : list) {
					if(e.getIdUp()==i) {
						valori[i] += e.getVal_up();
					} else if(e.getIdDown()==i) {
						valori[i] += e.getVal_down();
					}
				}
			}
			// se il valore è true vuol dire che il parametro è richiesto >=0
			for(int k=0;k<arrChecked.length;k++) {
				if(arrChecked[k]==true && valori[k]<0) {
					return false;
				}
			}
			return true;
		}
	
		
		public List<Emblem> getAllEmblems() {
			
			this.listaEmblemi = this.dao.getAllEmblems(this.reverseIdMap);
			
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
