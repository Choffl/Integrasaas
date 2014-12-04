package edu.udima.es.intregrasaas;

import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;

import edu.udima.es.intregrasaas.repository.GenericRepository;


/**
 * Extiende el servlet principal de Jersey para iniciar
 * el {@link EntityManagerFactory} de JPA y habilitar la persistencia.
 * Si el {@link GenericRepository} no esta instanciado, lo instancia.
 * Esta instancia unica es usada por todas las peticiones.
 * 
 * @author Sofia Sabariego
 */
public class RestServletContainer extends ServletContainer{

	/**
	 * id para la serializacion
	 */
	private static final long serialVersionUID = 5368209059621618110L;
	
	/**
	 * En el inicio del Servlet configura la persistencia
	 * @see org.glassfish.jersey.servlet.ServletContainer#init()
	 */
	@Override
	public void init() throws ServletException {
		configurarPersistencia();
		super.init();
	}	
	

	private void configurarPersistencia() {
		GenericRepository.init();	
	}

}
