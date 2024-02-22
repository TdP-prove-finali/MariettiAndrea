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

	public List<Emblem> getEmblemi(Map<String, Integer> reverseIdMap){
		String sql = "SELECT * "
				+ "FROM emblems";
		List<Emblem> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.println(rs.getString("Name"));
				String[] arr = rs.getString("Gold").split(" ");
				int idUp = reverseIdMap.get(arr[0]);
				int idDown = reverseIdMap.get(arr[2]);
				Emblem em = new Emblem(rs.getString("Name"), rs.getString("Color"), idUp, arr[0], Double.parseDouble(arr[1]),idDown, arr[2], Double.parseDouble(arr[3]));
				result.add(em);
				
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
