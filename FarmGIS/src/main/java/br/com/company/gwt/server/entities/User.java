package br.com.company.gwt.server.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name="tb_user")
@NamedQueries(value={@NamedQuery(name="userTest", query="from User u")})
public class User implements Serializable {

	private static final long serialVersionUID = 5083203438232635915L;
	
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "sq_user_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
	private Integer id;
	
	@Column(name="user_name", length=30)
	private String userName;

	@Column(length=20)
	private String password;
	
	private Boolean enabled;
	
	@Column(name = "location_geom")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry locationGeom;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Geometry getLocationGeom() {
		return locationGeom;
	}
	
	public void setLocationGeom(Geometry locationGeom) {
		this.locationGeom = locationGeom;
	}	

}