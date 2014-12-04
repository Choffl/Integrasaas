package edu.udima.es.intregrasaas.repository;

import java.util.Collection;
import java.util.HashMap;

import edu.udima.es.intregrasaas.dominio.Transportista;


/**
 * Repositorio exclusivo para la entidad {@link Transportista}
 * que no es persistida a BBDD.
 * 
 * @author Sofia Sabariego
 */
public class TransportistasRepository {
	
	/*
	 * Instancia del repositorio
	 */
	private static TransportistasRepository instancia;
	
	/*
	 * HashMap que mantiene los elementos de la clase Transportistas
	 * solo mientras dure la sesion.
	 */
	private HashMap<String, Transportista> transportistas;
	
	/**
	 * Recupera todos los transportistas.
	 * @return una coleccion de elementos tranportisas.
	 */
	public Collection<Transportista> recuperarTodos(){
		return transportistas.values();
	}
	
	
	/**
	 * @param CIF el CIF del transportista
	 * @return devuelve una transportista por su CIF
	 */
	public Transportista recuperarPorCIF(String CIF){
		return transportistas.get(CIF);
	}
	
	/**
	 * Agrega una transportista al repositorio
	 * @param transportista a agregar.
	 */
	public void crearTransportista(Transportista transportista){
		transportistas.put(transportista.getCif(), transportista);
	}
	

	/**
	 * Recupera una instancia del repositorio
	 * @return la instancia
	 */
	public static synchronized TransportistasRepository getInstance() {
		if (instancia == null){
			instancia = new TransportistasRepository();
		}
		return instancia;
	}
	
	/*
	 * constructor privado de la clase
	 */
	private TransportistasRepository(){
		this.transportistas = new HashMap<String, Transportista>();
	}

}
