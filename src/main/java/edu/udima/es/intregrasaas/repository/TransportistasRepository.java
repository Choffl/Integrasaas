package edu.udima.es.intregrasaas.repository;

import java.util.Collection;
import java.util.HashMap;

import edu.udima.es.intregrasaas.dominio.Transportista;

public class TransportistasRepository {
	
	private static TransportistasRepository instancia;
	
	private HashMap<String, Transportista> transportistas;
	
	
	public Collection<Transportista> recuperarTodos(){
		return transportistas.values();
	}
	
	public Transportista recuperarPorCIF(String CIF){
		return transportistas.get(CIF);
	}
	
	public void crearTransportista(Transportista transportista){
		transportistas.put(transportista.getCif(), transportista);
	}
	

	public static synchronized TransportistasRepository getInstance() {
		if (instancia == null){
			instancia = new TransportistasRepository();
		}
		return instancia;
	}
	
	private TransportistasRepository(){
		this.transportistas = new HashMap<String, Transportista>();
	}

}
