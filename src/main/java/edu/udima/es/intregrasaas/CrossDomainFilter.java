package edu.udima.es.intregrasaas;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 * El filtro modifica las cabaceras del response para permitir las
 * peticiones cross domain, que la mayoria de navegadores impiden.
 * Esto es no aceptan peticiones enviadas por XMLHttpRequest de dominios
 * diferentes de los recursos solicitados en la peticion.
 * 
 * @author Sofia Sabariego
 */
@Provider
@PreMatching
public class CrossDomainFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
		containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");		
		containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE");

	}

}
