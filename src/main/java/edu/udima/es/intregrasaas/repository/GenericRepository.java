package edu.udima.es.intregrasaas.repository;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import edu.udima.es.intregrasaas.dominio.Cliente;
import edu.udima.es.intregrasaas.dominio.Pedido;
import edu.udima.es.intregrasaas.dominio.Producto;
import edu.udima.es.intregrasaas.dominio.Proveedor;


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
		
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("from " + clazz.getName());
		resultado = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
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
		
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		
		 entidad = em.find(clazz, id);
		
		em.getTransaction().commit();
		em.close();
		return entidad;
	}
	
	/**
	 * Persiste a BBDD una entidad
	 * @param entidad a persistir.
	 */
	public void crear(Object entidad) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(entidad);
		
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Persiste las modificaciones realizadas sobre un elemento.
	 * @param entidad con modificaciones.
	 */
	public void modificar(Object entidad) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();		
		
		em.merge(entidad);
		
		em.getTransaction().commit();
		em.close();		
	}
	
	/**
	 * Elimina un elemento de una determinada clase
	 * @param clazz la clase del elemento que se desea eliminar.
	 * @param id el identificador de BBDD del elemento a eliminar.
	 */
	public void eliminar(Class clazz, Long id) {
		Object object = null;
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		
		object = em.find(clazz, id);
		
		if(clazz.equals(Producto.class)){
			((Producto)object).setProveedor(null);
		}
		
		if(clazz.equals(Pedido.class)){
			((Pedido)object).setCliente(null);
			((Pedido)object).getProductos().clear();
		}
		
		em.remove(object);
		
		em.getTransaction().commit();
		em.close();	
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
