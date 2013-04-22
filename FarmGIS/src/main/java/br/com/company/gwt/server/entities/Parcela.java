package br.com.company.gwt.server.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name="parcela")
public class Parcela implements Serializable {

	private static final long serialVersionUID = 5083203438232635915L;
	
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "sq_parcela_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
	private Integer id;
	
	@Column(name="codigo", length=50)
	private String codigo;
	
	@ManyToOne
	@JoinColumn(name="id_propriedade")
	private PropriedadeRural propriedade;
	
	@ManyToOne
	@JoinColumn(name="id_cultura")
	private Cultura cultura;

	@Column(name = "the_geom")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry theGeom;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public void setTheGeom(Geometry theGeom) {
		this.theGeom = theGeom;
	}

	public Geometry getTheGeom() {
		return theGeom;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setPropriedade(PropriedadeRural propriedade) {
		this.propriedade = propriedade;
	}

	public PropriedadeRural getPropriedade() {
		return propriedade;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	public Cultura getCultura() {
		return cultura;
	}

}