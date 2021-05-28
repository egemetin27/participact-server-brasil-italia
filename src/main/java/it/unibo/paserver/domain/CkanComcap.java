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
@Table(name = "ckan_comcap")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CkanComcap implements Serializable {
	private static final long serialVersionUID = -7634266516154244673L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "codigo_balanca")
	@Index(name = "idx_ckan_comcap_codigo_balanca")
	private Long codigoBalanca;
	
	@Column(name = "peso_liquido", nullable = true)
	private Double pesoLiquido;	

	@Column(name = "codigo_roteiro", nullable = true)
	private Long codigoRoteiro;
	
	@Column(name = "roteiro", columnDefinition = "TEXT", nullable = true)
	private String roteiro;
	
	@Column(name = "codigo_destino", nullable = true)
	private Long codigoDestino;	
	
	@Column(name = "destino", columnDefinition = "TEXT", nullable = true)
	private String destino;	

	@Column(name = "codigo_residuo", nullable = true)
	private Long codigoResiduo;	

	@Column(name = "residuo", columnDefinition = "TEXT", nullable = true)
	private String residuo;		

	@Column(name = "codigo_regiao", nullable = true)
	private Long codigo_regiao;		
	
	@Column(name = "regiao", columnDefinition = "TEXT", nullable = true)
	private String regiao;
	
	@Column(name = "data_pesagem", nullable = true)
	private String dataPesagem;	
	
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the codigoBalanca
	 */
	public Long getCodigoBalanca() {
		return codigoBalanca;
	}

	/**
	 * @param codigoBalanca the codigoBalanca to set
	 */
	public void setCodigoBalanca(Long codigoBalanca) {
		this.codigoBalanca = codigoBalanca;
	}

	/**
	 * @return the pesoLiquido
	 */
	public Double getPesoLiquido() {
		return pesoLiquido;
	}

	/**
	 * @param pesoLiquido the pesoLiquido to set
	 */
	public void setPesoLiquido(Double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	/**
	 * @return the codigoRoteiro
	 */
	public Long getCodigoRoteiro() {
		return codigoRoteiro;
	}

	/**
	 * @param codigoRoteiro the codigoRoteiro to set
	 */
	public void setCodigoRoteiro(Long codigoRoteiro) {
		this.codigoRoteiro = codigoRoteiro;
	}

	/**
	 * @return the roteiro
	 */
	public String getRoteiro() {
		return roteiro;
	}

	/**
	 * @param roteiro the roteiro to set
	 */
	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}

	/**
	 * @return the codigoDestino
	 */
	public Long getCodigoDestino() {
		return codigoDestino;
	}

	/**
	 * @param codigoDestino the codigoDestino to set
	 */
	public void setCodigoDestino(Long codigoDestino) {
		this.codigoDestino = codigoDestino;
	}

	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}

	/**
	 * @return the codigoResiduo
	 */
	public Long getCodigoResiduo() {
		return codigoResiduo;
	}

	/**
	 * @param codigoResiduo the codigoResiduo to set
	 */
	public void setCodigoResiduo(Long codigoResiduo) {
		this.codigoResiduo = codigoResiduo;
	}

	/**
	 * @return the residuo
	 */
	public String getResiduo() {
		return residuo;
	}

	/**
	 * @param residuo the residuo to set
	 */
	public void setResiduo(String residuo) {
		this.residuo = residuo;
	}

	/**
	 * @return the codigo_regiao
	 */
	public Long getCodigo_regiao() {
		return codigo_regiao;
	}

	/**
	 * @param codigo_regiao the codigo_regiao to set
	 */
	public void setCodigo_regiao(Long codigo_regiao) {
		this.codigo_regiao = codigo_regiao;
	}

	/**
	 * @return the regiao
	 */
	public String getRegiao() {
		return regiao;
	}

	/**
	 * @param regiao the regiao to set
	 */
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	/**
	 * @return the dataPesagem
	 */
	public String getDataPesagem() {
		return dataPesagem;
	}

	/**
	 * @param dataPesagem the dataPesagem to set
	 */
	public void setDataPesagem(String dataPesagem) {
		this.dataPesagem = dataPesagem;
	}

	/**
	 * @return the queryAt
	 */
	public Date getQueryAt() {
		return queryAt;
	}

	/**
	 * @param queryAt the queryAt to set
	 */
	public void setQueryAt(Date queryAt) {
		this.queryAt = queryAt;
	}

	/**
	 * @return the queryTime
	 */
	public Time getQueryTime() {
		return queryTime;
	}

	/**
	 * @param queryTime the queryTime to set
	 */
	public void setQueryTime(Time queryTime) {
		this.queryTime = queryTime;
	}

	/**
	 * @return the creationDate
	 */
	public DateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the updateDate
	 */
	public DateTime getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the removed
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}		
}
