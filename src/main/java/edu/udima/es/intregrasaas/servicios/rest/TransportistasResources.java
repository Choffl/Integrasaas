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

@Path("transportistas")
public class TransportistasResources {
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Transportista> mostrarTransportistas(){
		return TransportistasRepository.getInstance().recuperarTodos();
	}
	
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
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearTransportista(Transportista transportista){
		TransportistasRepository.getInstance().crearTransportista(transportista);
		return Response.status(Status.CREATED).build();
	}

}
