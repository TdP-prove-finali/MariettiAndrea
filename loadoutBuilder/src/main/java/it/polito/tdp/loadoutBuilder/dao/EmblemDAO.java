package it.polito.tdp.loadoutBuilder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.loadoutBuilder.model.Emblem;


public class EmblemDAO {

	public List<Emblem> getAllEmblems(){
		String sql = "SELECT * "
				+ "FROM emblems";
		List<Emblem> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				String[] arr = rs.getString("Gold").split(" ");
				System.out.println(rs.getString("Name"));
				if(arr[1].contains("%")) {
					if(arr[3].contains("%")) {
						Emblem em = new Emblem(rs.getString("Name"), rs.getString("Color"), arr[0], Double.parseDouble(arr[1].replace('%', ' ')), true, arr[2], Double.parseDouble(arr[3].replace('%', ' ')), true);
						result.add(em);
					} else {
						Emblem em = new Emblem(rs.getString("Name"), rs.getString("Color"), arr[0], Double.parseDouble(arr[1].replace('%', ' ')), true, arr[2], Double.parseDouble(arr[3]), false);
						result.add(em);
					}
				}else if(!arr[1].contains("%") && arr[3].contains("%")) {
					Emblem em = new Emblem(rs.getString("Name"), rs.getString("Color"), arr[0], Double.parseDouble(arr[1]), false, arr[2], Double.parseDouble(arr[3].replace('%', ' ')), true);
					result.add(em);
				}else {
						Emblem em = new Emblem(rs.getString("Name"), rs.getString("Color"), arr[0], Double.parseDouble(arr[1]), false, arr[2], Double.parseDouble(arr[3]), false);
						result.add(em);
					}
				
				}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return result;
	}
	
}
