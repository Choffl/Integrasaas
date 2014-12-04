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

import edu.udima.es.intregrasaas.dominio.Pedido;
import edu.udima.es.intregrasaas.repository.GenericRepository;

/**
 * Servicio REST para el recurso {@link Pedido}
 * Usa JAXB para la conversion de POJO a JSON o XML
 * y viciversa.
 * 
 * @author Sofia Sabariego
 */
@Path("/pedidos")
public class PedidosResources {

	/**
	 * Muestra todos los pedidos. Servicio accesible mediante una peticion GET al
	 * baseURL del recurso
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @return una coleccion de pedidos
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Pedido> mostrarPedidos(){
		return GenericRepository.getInstance().recuperaTodos(Pedido.class);
	}

	/**
	 * Recupera un pedido por su id mediante una peticion GET al recurso
	 * baseURL/{id}
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @param id el identificador del pedido.
	 * @return un response con el estado de la peticion, y si ha sido correcta los datos del
	 * pedido en el body.
	 */
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

	/**
	 * Crea un recurso pedido en el sistema mediante peticiones POST a la url base
	 * del recurso.
	 * Acepta los datos del recurso tanto en formato XML como JSON. Estos son convertidos
	 * al POJO {@link Pedido} mediante JAXB.
	 * @param pedido a agregar
	 * @return un response con el estado de la peticion
	 */
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

	/**
	 * Modifica los datos de un pedido mediante un peticipn PUT a la url
	 * urlBase/{id}
	 * Acepta tanto datos en formato XML como JSON que son transformados en el POJO {@link Pedido}
	 * mediante JAXB.
	 * @param id el identificador de BBDD del pedido del que se quiere modificar los datos.
	 * @param pedidoModificado POJO con los nuevos datos del pedido.
	 * @return un response con el estado de la peticion.
	 */
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

	/**
	 * Elimina un recurso pedido mediante una peticion DELETE a la url
	 * urlBase/{id}
	 * @param id el identificador del pedido en BBDD .
	 * @return un response con el estado de la peticion.
	 */
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

	/*
	 * actualiza los datos del pedido recuperado de base de datos con
	 * los datos del pedido enviado en la peticion
	 * NOTA: El cliente no se modifica al considerarse que no tiene
	 * sentido desde el punto de vista del negocio
	 */
	private void actualizarDatos(Pedido pedido, Pedido pedidoModificado) {
		pedido.setFechaPedido(pedidoModificado.getFechaPedido());
		pedido.getProductos().clear();
		pedido.setProductos(pedidoModificado.getProductos());
	}

}
