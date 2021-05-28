package it.unibo.paserver.domain;
/**
 * Objeto para representacao das consultas de filtro avancada
 * @author Claudio
 *
 */
public class ReceiveAdvancedSearch {

	private String key;
	private String value;
	private String item;
	private String label;
	/**
	 * Setter/Getter
	 */
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
