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
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import edu.udima.es.intregrasaas.dominio.Cliente;
import edu.udima.es.intregrasaas.dominio.Pedido;
import edu.udima.es.intregrasaas.dominio.Producto;
import edu.udima.es.intregrasaas.dominio.Proveedor;
import edu.udima.es.intregrasaas.repository.GenericRepository;

@Path("/pedidos")
public class PedidosResources {

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Pedido> mostrarPedidos(){
		return GenericRepository.getInstance().recuperaTodos(Pedido.class);
	}

	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response mostrarPedido(@PathParam("id") Long id){
		Response response = null;
		
		Pedido pedido = (Pedido)GenericRepository.getInstance().recuperaPorId(Pedido.class, id);
		if(pedido == null){
			response = Response.ok(pedido).build();
		}else{
			response = Response.status(Status.NOT_FOUND).entity("No se ha encontrado el recurso").build();
		}
		return response;
	}

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearPedido(@Context final UriInfo uriInfo, Pedido pedido){
		try{
			GenericRepository.getInstance().crear(pedido);
			return Response.status(Status.CREATED).build();
		}catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No se ha podido crear el recurso").build();
		}

	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response modificarPedido(@PathParam("id") Long id, Pedido pedidoModificado){
		try{
			Pedido pedido = (Pedido)GenericRepository.getInstance().recuperaPorId(pedidoModificado.getClass(), id);
			actualizarDatos(pedido, pedidoModificado);
			GenericRepository.getInstance().modificar(pedido);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.NOT_MODIFIED).entity("No se ha podido modificar el recurso seleccionado").build();
		}

	}

	@DELETE
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response eliminarPedido(@PathParam("id") Long id){
		try{
			GenericRepository.getInstance().eliminar(Pedido.class, id);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.METHOD_NOT_ALLOWED).entity("No se puede eliminar el recurso seleccionado").build();
		}
	}


	private void actualizarDatos(Pedido pedido, Pedido pedidoModificado) {
		pedido.setFechaPedido(pedidoModificado.getFechaPedido());
		pedido.getProductos().clear();
		pedido.setProductos(pedidoModificado.getProductos());
	}

}
