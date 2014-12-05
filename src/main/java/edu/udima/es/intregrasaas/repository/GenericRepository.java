package edu.udima.es.intregrasaas.repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

import edu.udima.es.intregrasaas.dominio.Pedido;
import edu.udima.es.intregrasaas.dominio.Producto;


/**
 * Repositorio generico que facilita el acceso a la BBDD
 * Da soporte a todas las clases de dominio persistentes.
 * 
 * @author Sofia Sabariego
 */
public class GenericRepository<E> {

	/*
	 * Contiene una instancia de si mismo para asegurar
	 * de que el punto de acesso a la persistencia es unico,
	 * aunque las sesiones pueden ser multiples
	 */
	private static GenericRepository instancia;
	private static EntityManagerFactory entityManagerFactory;

	/**
	 * Recupera todas los objetos persistidos de la clase.
	 * @param clazz la clase de la que se quieren sus objetos.
	 * @return una coleccion con los todos los objetos de la clase.
	 */
	public Collection<E> recuperaTodos(Class<E> clazz){
		Collection<E> resultado = new HashSet();

		EntityManager em = recuperarTransaccion();

		Query query = em.createQuery("from " + clazz.getName());
		resultado = query.getResultList();

		consignarTransaccion(em);
		return resultado;
	}


	/**
	 * Recupera un elemento de una clase por su id
	 * @param clazz la clase del elemento que se quiere recuperar
	 * @param id el identificador de BBDD del elemento
	 * @return el elemento 
	 */
	public Object recuperaPorId(Class clazz, Long id) {
		Object entidad = null;

		EntityManager em = recuperarTransaccion();

		entidad = em.find(clazz, id);

		consignarTransaccion(em);
		return entidad;
	}


	private void consignarTransaccion(EntityManager em) {
		em.getTransaction().commit();
		em.close();
	}


	private EntityManager recuperarTransaccion() {
		EntityManager em = entityManagerFactory.createEntityManager();
		
		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) em.unwrap(Session.class).getSessionFactory();
	    ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
	    	try {
				connectionProvider.getConnection().isValid(180);
			} catch (SQLException e) {
				System.out.println("Conexion perdida");
			}
		em.getTransaction().begin();
		return em;
	}

	/**
	 * Persiste a BBDD una entidad
	 * @param entidad a persistir.
	 */
	public void crear(Object entidad) {
		EntityManager em = recuperarTransaccion();

		em.persist(entidad);

		consignarTransaccion(em);
	}

	/**
	 * Persiste las modificaciones realizadas sobre un elemento.
	 * @param entidad con modificaciones.
	 */
	public void modificar(Object entidad) {
		EntityManager em = recuperarTransaccion();		

		em.merge(entidad);

		consignarTransaccion(em);		
	}

	/**
	 * Elimina un elemento de una determinada clase
	 * @param clazz la clase del elemento que se desea eliminar.
	 * @param id el identificador de BBDD del elemento a eliminar.
	 */
	public void eliminar(Class clazz, Long id) {
		Object object = null;
		EntityManager em = recuperarTransaccion();

		object = em.find(clazz, id);

		if(clazz.equals(Producto.class)){
			((Producto)object).setProveedor(null);
		}

		if(clazz.equals(Pedido.class)){
			((Pedido)object).setCliente(null);
			((Pedido)object).getProductos().clear();
		}

		em.remove(object);

		consignarTransaccion(em);	
	}	

	/**
	 * Recupera una instancia del repositorio generico
	 * @return una instancia de {@link GenericRepository}
	 */
	public static synchronized GenericRepository getInstance(){
		if(instancia == null){
			instancia = new GenericRepository();
		}
		return instancia;
	}

	/**
	 * Inicializa el repositorio
	 */
	public static synchronized void init(){
		instancia = new GenericRepository();
	}

	/*
	 * Constructor privado, inicializa el EntityManagerFactory de JPA
	 */
	private GenericRepository(){
		entityManagerFactory = Persistence.createEntityManagerFactory("integrasaas");
	}	

}
