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

import edu.udima.es.intregrasaas.dominio.Producto;
import edu.udima.es.intregrasaas.repository.GenericRepository;

@Path("productos")
public class ProductosResources {

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Producto> mostrarProductos(){
		return GenericRepository.getInstance().recuperaTodos(Producto.class);
	}
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response mostrarProducto(@PathParam("id") Long id){
		Response response = null;
		
		Producto producto = (Producto)GenericRepository.getInstance().recuperaPorId(Producto.class, id);
		if (producto == null){
			response = Response.ok(producto).build();
		}else{
			response = Response.status(Status.NOT_FOUND).entity("No se ha encontrado el recurso").build();
		}
		
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearProducto(@Context final UriInfo uriInfo, Producto producto){
		try{
			GenericRepository.getInstance().crear(producto);
			return Response.status(Status.CREATED).build();
		}catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No se ha podido crear el recurso").build();
		}

	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response modificarProducto(@PathParam("id") Long id, Producto productoModificado){
		try{
			Producto producto = (Producto)GenericRepository.getInstance().recuperaPorId(productoModificado.getClass(), id);
			actualizarDatos(producto, productoModificado);
			GenericRepository.getInstance().modificar(producto);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.NOT_MODIFIED).entity("No se ha podido modificar el recurso seleccionado").build();
		}

	}
	
	@DELETE
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response eliminarProducto(@PathParam("id") Long id){
		try{
			GenericRepository.getInstance().eliminar(Producto.class, id);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.METHOD_NOT_ALLOWED).entity("No se puede eliminar el recurso seleccionado").build();
		}
	}


	private void actualizarDatos(Producto producto, Producto productoModificado) {
		producto.setDescripcion(productoModificado.getDescripcion());
		producto.setNombre(productoModificado.getNombre());
		producto.setPrecioBase(productoModificado.getPrecioBase());
	}

}
