package br.com.company.gwt.server.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cultura")
public class Cultura implements Serializable {

	private static final long serialVersionUID = 5083203438232635915L;
	
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "sq_cultura_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
	private Integer id;
	
	@Column(name="nome", length=50)
	private String nome;

	@Column(name="cor_mapa", length=10)
	private String corMapa;	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setCorMapa(String corMapa) {
		this.corMapa = corMapa;
	}

	public String getCorMapa() {
		return corMapa;
	}	

}