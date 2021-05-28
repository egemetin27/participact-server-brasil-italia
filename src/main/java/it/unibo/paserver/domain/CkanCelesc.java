package it.unibo.paserver.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "ckan_celesc")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CkanCelesc implements Serializable {

	private static final long serialVersionUID = 8401985516746601452L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "longitude", nullable = true)
	private Double longitude;

	@Column(name = "latitude", nullable = true)
	private Double latitude;

	@Column(name = "consumo", nullable = true)
	private Long consumo;

	@Column(name = "cep", nullable = true)
	private Long cep;

	@Column(name = "bairro", columnDefinition = "TEXT", nullable = true)
	private String bairro;

	@Column(name = "logradouro", columnDefinition = "TEXT", nullable = true)
	private String logradouro;

	@Column(name = "uc", nullable = true)
	private Long uc;

	@Column(name = "classe", nullable = true)
	private String classe;

	@Column(name = "referencia", nullable = true)
	private String referencia;

	@Column(name = "queryAt", updatable = true, nullable = true)
	private Date queryAt;

	@Column(name = "queryTime", updatable = true, nullable = true)
	private Time queryTime;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	private DateTime creationDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	private DateTime updateDate;

	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Long getConsumo() {
		return consumo;
	}

	public void setConsumo(Long consumo) {
		this.consumo = consumo;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getUc() {
		return uc;
	}

	public void setUc(Long uc) {
		this.uc = uc;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Date getQueryAt() {
		return queryAt;
	}

	public void setQueryAt(Date queryAt) {
		this.queryAt = queryAt;
	}

	public Time getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Time queryTime) {
		this.queryTime = queryTime;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

}
