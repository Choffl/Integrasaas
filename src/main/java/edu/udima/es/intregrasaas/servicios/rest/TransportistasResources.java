package edu.udima.es.intregrasaas.servicios.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.udima.es.intregrasaas.dominio.Transportista;
import edu.udima.es.intregrasaas.repository.TransportistasRepository;

/**
 * Servicio REST para el recurso {@link Transportista}
 * Usa JAXB para la conversion de POJO a JSON o XML
 * y viciversa.
 * 
 * @author Sofia Sabariego
 */
@Path("transportistas")
public class TransportistasResources {

	/**
	 * Muestra todos los transportistas. Servicio accesible mediante una peticion GET al
	 * baseURL del recurso
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @return una coleccion de transportistas.
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Transportista> mostrarTransportistas(){
		return TransportistasRepository.getInstance().recuperarTodos();
	}

	/**
	 * Recupera un transportista por su CIF mediante una peticion GET al recurso
	 * baseURL/{CIF}
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @param CIF el identificador de negocio del transportista.
	 * @return un response con el estado de la peticion, y si ha sido correcta los datos del
	 * transportista en el body.
	 */
	@GET
	@Path("{CIF}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response mostrarPorCIF(@PathParam("CIF") String CIF ){
		Response response = null;
		
		Transportista transportista = TransportistasRepository.getInstance().recuperarPorCIF(CIF);
		if(transportista == null){
			response = Response.ok().entity(transportista).build();
		}else{
			response = Response.status(Status.NOT_FOUND).entity("No se ha encontrado el recurso solicitado").build();
		}
		
		return response;
	}
	
	/**
	 * Crea un recurso transportista en el sistema mediante peticiones POST a la url base
	 * del recurso.
	 * Acepta los datos del recurso tanto en formato XML como JSON. Estos son convertidos
	 * al POJO {@link Transportista} mediante JAXB.
	 * @param transportista a agregar
	 * @return un response con el estado de la peticion
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearTransportista(Transportista transportista){
		TransportistasRepository.getInstance().crearTransportista(transportista);
		return Response.status(Status.CREATED).build();
	}

}
