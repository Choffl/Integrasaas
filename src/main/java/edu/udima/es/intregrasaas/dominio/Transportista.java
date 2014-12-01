package edu.udima.es.intregrasaas.dominio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Transportista implements Serializable {

	/**
	 * Id para la serializacion
	 */
	@XmlTransient
	private static final long serialVersionUID = -3798520829292704754L;
	
	private String cif;
	
	private String nombre;
	
	private String direccion;
	
	public Transportista(){}
	
	public Transportista(String cif, String nombre, String direccion) {
		this.cif = cif;
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
}
