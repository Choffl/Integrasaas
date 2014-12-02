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


public class GenericRepository<E> {
	
	private static GenericRepository instancia;
	private static EntityManagerFactory entityManagerFactory;
	
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
	
	public Object recuperaPorId(Class clazz, Long id) {
		Object entidad = null;
		
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		
		 entidad = em.find(clazz, id);
		
		em.getTransaction().commit();
		em.close();
		return entidad;
	}
	
	public void crear(Object entidad) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(entidad);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public void modificar(Object entidad) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();		
		
		em.merge(entidad);
		
		em.getTransaction().commit();
		em.close();		
	}
	
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
	
	public static synchronized GenericRepository getInstance(){
		if(instancia == null){
			instancia = new GenericRepository();
		}
		return instancia;
	}
	
	public static synchronized void init(){
		instancia = new GenericRepository();
	}
	
	private GenericRepository(){
		entityManagerFactory = Persistence.createEntityManagerFactory("integrasaas");
	}	

}
