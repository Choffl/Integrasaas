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
import edu.udima.es.intregrasaas.repository.GenericRepository;

/**
 * Servicio REST para el recurso Cliente
 * Usa JAXB para la conversion de POJO a JSON o XML
 * y viciversa.
 * 
 * @author Sofia Sabariego
 */
@Path("clientes")
public class ClientesResources {

	/**
	 * Muestra todos los clientes. Servicio accesible mediante una peticion GET al
	 * baseURL del recurso
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @return una coleccion de clientes.
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Cliente> mostrarClientes(){
		return GenericRepository.getInstance().recuperaTodos(Cliente.class);
	}


	/**
	 * Recupera un cliente por su id mediante una peticion GET al recurso
	 * baseURL/{id}
	 * Produce tanto una respuesta XML como JSON dependiendo del media type
	 * especificado por el cliente.
	 * @param id el identificador del cliente.
	 * @return un response con el estado de la peticion, y si ha sido correcta los datos del
	 * cliente en el body.
	 */
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response mostrarCliente(@PathParam("id") Long id){
		Response response = null;
		
		Cliente cliente = (Cliente)GenericRepository.getInstance().recuperaPorId(Cliente.class, id);
		if(cliente == null){
			response = Response.status(Status.NOT_FOUND).entity("No se ha encontrado el recurso").build();
		}else{
			response = Response.ok(cliente).build();
		}
		return response;
	}

	/**
	 * Crea un recurso cliente en el sistema mediante peticiones POST a la url base
	 * del recurso.
	 * Acepta los datos del recurso tanto en formato XML como JSON. Estos son convertidos
	 * al POJO {@link Cliente} mediante JAXB.
	 * @param cliente a agregar
	 * @return un response con el estado de la peticion
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearCliente(Cliente cliente){
		try{
			GenericRepository.getInstance().crear(cliente);
			return Response.status(Status.CREATED).build();
		}catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No se ha podido crear el recurso").build();
		}

	}

	/**
	 * Modifica los datos de un cliente mediante un peticipn PUT a la url
	 * urlBase/{id}
	 * Acepta tanto datos en formato XML como JSON que son transformados en el POJO {@link Cliente}
	 * mediante JAXB.
	 * @param id el identificador de BBDD del cliente del que se quiere modificar los datos.
	 * @param clienteModificado POJO con los nuevos datos del cliente.
	 * @return un response con el estado de la peticion.
	 */
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response modificarCliente(@PathParam("id") Long id, Cliente clienteModificado){
		try{
			Cliente cliente = (Cliente)GenericRepository.getInstance().recuperaPorId(clienteModificado.getClass(), id);
			actualizarDatos(cliente, clienteModificado);
			GenericRepository.getInstance().modificar(cliente);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.NOT_MODIFIED).entity("No se ha podido modificar el recursos seleccionado").build();
		}

	}

	/**
	 * Elimina un recurso cliente mediante una peticion DELETE a la url
	 * urlBase/{id}
	 * @param id el identificador del cliente en BBDD .
	 * @return un response con el estado de la peticion.
	 */
	@DELETE
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response eliminarCliente(@PathParam("id") Long id){
		try{
			GenericRepository.getInstance().eliminar(Cliente.class, id);
			return Response.status(Status.ACCEPTED).build();
		}catch(Exception e){
			return Response.status(Status.METHOD_NOT_ALLOWED).entity("No se puede eliminar el recurso seleccionado").build();
		}
	}

	/*
	 * actualiza los datos del cliente recuperado de base de datos con
	 * los datos del cliente enviado en la peticion
	 * NOTA: El NIF no se modifica al considerarse clave de negocio
	 */
	private void actualizarDatos(Cliente cliente, Cliente clienteModificado) {
		cliente.setApellidos(clienteModificado.getApellidos());
		cliente.setNombre(clienteModificado.getNombre());
		cliente.setDireccion(clienteModificado.getDireccion());
		cliente.setPassword(clienteModificado.getPassword());
	}

}
