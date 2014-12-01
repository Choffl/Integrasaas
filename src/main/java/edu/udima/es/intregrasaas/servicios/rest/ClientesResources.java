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

import edu.udima.es.intregrasaas.dominio.Cliente;
import edu.udima.es.intregrasaas.dominio.Producto;
import edu.udima.es.intregrasaas.dominio.Proveedor;
import edu.udima.es.intregrasaas.repository.GenericRepository;

@Path("clientes")
public class ClientesResources {

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Cliente> mostrarClientes(){
		return GenericRepository.getInstance().recuperaTodos(Cliente.class);
	}


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

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response crearCliente(@Context final UriInfo uriInfo, Cliente cliente){
		try{
			GenericRepository.getInstance().crear(cliente);
			return Response.status(Status.CREATED).build();
		}catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No se ha podido crear el recurso").build();
		}

	}

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


	private void actualizarDatos(Cliente cliente, Cliente clienteModificado) {
		cliente.setApellidos(clienteModificado.getApellidos());
		cliente.setNombre(clienteModificado.getNombre());
		cliente.setDireccion(clienteModificado.getDireccion());
		cliente.setPassword(clienteModificado.getPassword());
	}

}
