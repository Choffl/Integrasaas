package edu.udima.es.intregrasaas.dominio;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase de dominio que representa a un Producto y funciona como DTO.
 * Contiene las anotaciones necesarias para ser persistido por JPA,
 * asi como anotaciones de JAXB para realizar en marshalling y unmarshalling
 * del la clase a formato JSON o XML.
 * 
 * @author Sofia Sabariego
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Producto implements Serializable {

	/**
	 * id para la serializacion
	 */
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -5501159162555041617L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	private Float precioBase;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	private Proveedor proveedor;
	
	
	public Producto(){}	

	public Producto(String nombre, String descripcion, Float precioBase, Proveedor proveedor) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.proveedor = proveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(Float precioBase) {
		this.precioBase = precioBase;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
}
