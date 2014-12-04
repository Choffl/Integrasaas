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

import edu.udima.es.intregrasaas.dominio.Proveedor;
import edu.udima.es.intregrasaas.repository.GenericRepository;

/**
 * Servicio REST para el recurso Proveedor
 * Usa JAXB para la conversion de POJO a JSON o XML
 * y viciversa.
 * 
 * @author Sofia Sabariego
 */
@Path("proveedores")
public class ProveedoresResources {

	/**
	 * Muestra todos los proveedores. Servicio accesible mediante una peticion GET al
	 * baseURL del recurso
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @return una coleccion de proveedores.
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Proveedor> mostrarProveedores(){
		return GenericRepository.getInstance().recuperaTodos(Proveedor.class);
	}

	/**
	 * Recupera un cliente por su id mediante una peticion GET al recurso
	 * baseURL/{id}
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @param id el identificador del cliente.
	 * @return un response con el estado de la peticion, y si ha sido correcta los datos del
	 * proveedor en el body.
	 */
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

	/**
	 * Crea un recurso proveedor en el sistema mediante peticiones POST a la url base
	 * del recurso.
	 * Acepta los datos del recurso tanto en formato XML como JSON. Estos son convertidos
	 * al POJO {@link Proveedor} mediante JAXB.
	 * @param proveedor a agregar
	 * @return un response con el estado de la peticion
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearProveedor(Proveedor proveedor){
		try{
			GenericRepository.getInstance().crear(proveedor);
			return Response.status(Status.CREATED).build();
		}catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No se ha podido crear el recurso").build();
		}

	}

	/**
	 * Modifica los datos de un cliente mediante un peticipn PUT a la url
	 * urlBase/{id}
	 * Acepta tanto datos en formato XML como JSON que son transformados en el POJO {@link Proveedor}
	 * mediante JAXB.
	 * @param id el identificador de BBDD del proveedor del que se quiere modificar los datos.
	 * @param proveedorModificado POJO con los nuevos datos del proveedor.
	 * @return un response con el estado de la peticion.
	 */
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

	/**
	 * Elimina un recurso proveedor mediante una peticion DELETE a la url
	 * urlBase/{id}
	 * @param id el identificador del proveedor en BBDD .
	 * @return un response con el estado de la peticion.
	 */
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

	/*
	 * actualiza los datos del proveedor recuperado de base de datos con
	 * los datos del proveedor enviado en la peticion
	 * NOTA: El CIF no se modifica al considerarse clave de negocio
	 */
	private void actualizarDatos(Proveedor proveedor, Proveedor proveedorModificado) {
		proveedor.setDireccion(proveedorModificado.getDireccion());
		proveedor.setNombre(proveedorModificado.getNombre());
	}

}
