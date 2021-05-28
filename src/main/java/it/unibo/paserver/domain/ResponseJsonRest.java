package it.unibo.paserver.domain;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unibo.paserver.domain.support.JsonDateTimeStandardizationDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeStandardizationSerializer;

/**
 * Classe para respostas padrao ao angularjs/view
 * @author Claudio
 * @param <E>
 */
public class ResponseJsonRest {
	private boolean status = false;
	private String message;
	private int count = 10;
	private int offset = 0;
	private Long total = 0L;
	private Long outcome;
	private Object item;
	private Object stdClass;
	private boolean isRemoved = false;
	@JsonDeserialize(using = JsonDateTimeStandardizationDeserializer.class)
	@JsonSerialize(using = JsonDateTimeStandardizationSerializer.class)	
	private DateTime version = new DateTime();

	public ResponseJsonRest(){}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}


	public void setTotal(int i) {
		
		this.total = (long) i;
	}

	public void setOutcome(Long id) {
		
		this.outcome = id;
	}
	
	public Long getOutcome() {
		return outcome;
	}

	/**
	 * @return the item
	 */
	public Object getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Object item) {
		if (item != null) {
			this.item = item;
		}
	}

	/**
	 * @return the version
	 */
	public DateTime getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(DateTime version) {
		this.version = version;
	}

	/**
	 * @return the isRemoved
	 */
	public boolean isRemoved() {
		return isRemoved;
	}

	/**
	 * @param isRemoved the isRemoved to set
	 */
	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public Object getStdClass() {
		return stdClass;
	}

	public void setStdClass(Object stdClass) {
		this.stdClass = stdClass;
	}
}