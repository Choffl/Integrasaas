package edu.udima.es.intregrasaas;

import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;

import edu.udima.es.intregrasaas.repository.GenericRepository;

public class RestServletContainer extends ServletContainer{

	/**
	 * id para la serializacion
	 */
	private static final long serialVersionUID = 5368209059621618110L;
	
	@Override
	public void init() throws ServletException {
		configurarPersistencia();
		super.init();
	}
	
	
	private void configurarPersistencia() {
		GenericRepository.init();	
	}

}
