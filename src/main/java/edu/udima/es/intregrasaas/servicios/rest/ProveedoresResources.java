package edu.udima.es.intregrasaas.servicios.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import edu.udima.es.intregrasaas.dominio.Proveedor;
import edu.udima.es.intregrasaas.repository.GenericRepository;

@Path("proveedores")
public class ProveedoresResources {
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Proveedor> mostrarProveedores(){
		return GenericRepository.getInstance().recuperaTodos(Proveedor.class);
	}
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response mostrarProveedor(@PathParam("id") Long id){
		Response response = null;
		
		Proveedor proveedor = (Proveedor)GenericRepository.getInstance().recuperaPorId(Proveedor.class, id);
		if(proveedor == null){
			response = Response.status(Status.NOT_FOUND).entity("No se ha encontrado el recurso").build();
		}else{
			response = Response.ok(proveedor).build();
		}
		return response;
	}

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearProveedor(@Context final UriInfo uriInfo, Proveedor proveedor){
		try{
			GenericRepository.getInstance().crear(proveedor);
			return Response.status(Status.CREATED).build();
		}catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No se ha podido crear el recurso").build();
		}

	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response modificarProveedor(@PathParam("id") Long id, Proveedor proveedorModificado){
		try{
			Proveedor proveedor = (Proveedor)GenericRepository.getInstance().recuperaPorId(proveedorModificado.getClass(), id);
			actualizarDatos(proveedor, proveedorModificado);
			GenericRepository.getInstance().modificar(proveedor);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.NOT_MODIFIED).entity("No se ha podido modificar el recursos seleccionado").build();
		}

	}

	@DELETE
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response eliminarProveedor(@PathParam("id") Long id){
		try{
			GenericRepository.getInstance().eliminar(Proveedor.class, id);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.METHOD_NOT_ALLOWED).entity("No se puede eliminar el recurso seleccionado").build();
		}
	}


	private void actualizarDatos(Proveedor proveedor, Proveedor proveedorModificado) {
		proveedor.setDireccion(proveedorModificado.getDireccion());
		proveedor.setNombre(proveedorModificado.getNombre());
	}

}
