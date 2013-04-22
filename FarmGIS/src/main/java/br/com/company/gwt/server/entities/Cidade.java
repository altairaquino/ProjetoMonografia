package br.com.company.gwt.server.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name="cidade")
public class Cidade implements Serializable {

	private static final long serialVersionUID = 5083203438232635915L;
	
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "sq_cidade_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
	private Integer id;
	
	@Column(name="nome", length=50)
	private String name;

	@Column(name = "the_geom")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry theGeom;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTheGeom(Geometry theGeom) {
		this.theGeom = theGeom;
	}

	public Geometry getTheGeom() {
		return theGeom;
	}	

}