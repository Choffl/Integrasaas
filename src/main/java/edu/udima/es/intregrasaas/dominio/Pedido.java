package edu.udima.es.intregrasaas.dominio;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Pedido implements Serializable {

	/**
	 * id para la serializacion
	 */
	private static final long serialVersionUID = 730932953655994598L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPedido;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<Producto> productos = new HashSet<Producto>();
	
	@Transient
	private Float precioFinal;
	
	
	
	public Pedido(){}
	
	public Pedido(Date fechaPedido, Float precioFinal, Cliente cliente) {
		this.fechaPedido = fechaPedido;
		this.precioFinal = precioFinal;
		this.cliente = cliente;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Collection<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Collection<Producto> productos) {
		this.productos = productos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Float getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(Float precioFinal) {
		this.precioFinal = precioFinal;
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
		result = prime * result
				+ ((fechaPedido == null) ? 0 : fechaPedido.hashCode());
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
		Pedido other = (Pedido) obj;
		if (fechaPedido == null) {
			if (other.fechaPedido != null)
				return false;
		} else if (!fechaPedido.equals(other.fechaPedido))
			return false;
		return true;
	}

}
