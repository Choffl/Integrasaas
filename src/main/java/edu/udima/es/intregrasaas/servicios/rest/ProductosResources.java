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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.udima.es.intregrasaas.dominio.Cliente;
import edu.udima.es.intregrasaas.dominio.Producto;
import edu.udima.es.intregrasaas.repository.GenericRepository;

/**
 * Servicio REST para el recurso {@link Producto}
 * Usa JAXB para la conversion de POJO a JSON o XML
 * y viciversa.
 * 
 * @author Sofia Sabariego
 */
@Path("productos")
public class ProductosResources {

	/**
	 * Muestra todos los productos. Servicio accesible mediante una peticion GET al
	 * baseURL del recurso
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @return una coleccion de productos.
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Producto> mostrarProductos(){
		return GenericRepository.getInstance().recuperaTodos(Producto.class);
	}
	
	/**
	 * Recupera un producto por su id mediante una peticion GET al recurso
	 * baseURL/{id}
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @param id el identificador del producto
	 * @return un response con el estado de la peticion, y si ha sido correcta los datos del
	 * producto en el body
	 */
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response mostrarProducto(@PathParam("id") Long id){
		Response response = null;
		
		Producto producto = (Producto)GenericRepository.getInstance().recuperaPorId(Producto.class, id);
		if (producto != null){
			response = Response.ok(producto).build();
		}else{
			response = Response.status(Status.NOT_FOUND).entity("No se ha encontrado el recurso").build();
		}
		
		return response;
	}

	/**
	 * Crea un recurso {@link Producto} en el sistema mediante peticiones POST a la url base
	 * del recurso.
	 * Acepta los datos del recurso tanto en formato XML como JSON. Estos son convertidos
	 * al POJO {@link Cliente} mediante JAXB.
	 * @param producto a agregar
	 * @return un response con el estado de la peticion
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearProducto(Producto producto){
		try{
			GenericRepository.getInstance().crear(producto);
			return Response.status(Status.CREATED).build();
		}catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No se ha podido crear el recurso").build();
		}

	}
	
	/**
	 * Modifica los datos de un {@link Producto} mediante un peticipn PUT a la url
	 * urlBase/{id}
	 * Acepta tanto datos en formato XML como JSON que son transformados en el POJO {@link Producto}
	 * mediante JAXB.
	 * @param id el identificador de BBDD del producto del que se quiere modificar los datos.
	 * @param productoModificado POJO con los nuevos datos del producto.
	 * @return un response con el estado de la peticion.
	 */
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
	
	/**
	 * Elimina un recurso producto mediante una peticion DELETE a la url
	 * urlBase/{id}
	 * @param id el identificador del prodcucto en BBDD .
	 * @return un response con el estado de la peticion.
	 */
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


	/*
	 * actualiza los datos del producto recuperado de base de datos con
	 * los datos del producto enviado en la peticion
	 * NOTA: El proveedor no se modifica al no considerarse desde el punto de vista del negocio
	 */
	private void actualizarDatos(Producto producto, Producto productoModificado) {
		producto.setDescripcion(productoModificado.getDescripcion());
		producto.setNombre(productoModificado.getNombre());
		producto.setPrecioBase(productoModificado.getPrecioBase());
	}

}
