package edu.udima.es.intregrasaas.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase de dominio que representa a un Cliente y funciona como DTO.
 * Contiene las anotaciones necesarias para ser persistido por JPA,
 * asi como anotaciones de JAXB para realizar en marshalling y unmarshalling
 * del la clase a formato JSON o XML.
 * 
 * @author Sofia Sabariego
 */
@Entity
@Table(name="clientes")
@XmlRootElement(name="Cliente")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Cliente implements Serializable{

	/**
	 * id para la serializacion
	 */
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 5094012949421960676L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String apellidos;
	
	private String dni;
	
	private String password;
	
	private String direccion;
	
	public Cliente(){};
	
	public Cliente(String nombre, String apellidos, String dni, String password, String direccion){
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.password = password;
		this.direccion = direccion;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

}
