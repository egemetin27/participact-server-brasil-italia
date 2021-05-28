package it.unibo.paserver.domain;

import java.util.HashMap;
import java.util.List;

/**
 * Classe para respostas padrao ao angularjs/view
 * 
 * @author Claudio

 */
public class ResponseJson extends ResponseJsonRest {
	private List<Object[]> items;
	private ResultType resultType;
	private HashMap<String, Object> chart;
	private HashMap<String, Object> payload;
	private List<HashMap<String, Object>> payloadList;

	public ResponseJson() {
	}

	/**
	 * @return the items
	 */
	public List<Object[]> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<Object[]> items) {
		this.items = items;
	}
	/**
	 * @return the item
	 */
	public Object getData() {
		return getItem();
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setData(String[] item) {
		this.setItem(item);
	}

	/**
	 * @return the resultType
	 */
	public ResultType getResultType() {
		return resultType;
	}

	/**
	 * @param resultType
	 *            the resultType to set
	 */
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the chart
	 */
	public HashMap<String, Object> getChart() {
		return chart;
	}

	/**
	 * @param chart
	 *            the chart to set
	 */
	public void setChart(HashMap<String, Object> chart) {
		this.chart = chart;
	}

	public void setPayload(HashMap<String, Object> payload) {
		this.payload = payload;
	}

	public HashMap<String, Object> getPayload() {
		return payload;
	}

	/**
	 * @return the payloadList
	 */
	public List<HashMap<String, Object>> getPayloadList() {
		return payloadList;
	}

	/**
	 * @param payloadList the payloadList to set
	 */
	public void setPayloadList(List<HashMap<String, Object>> payloadList) {
		this.payloadList = payloadList;
	}
}