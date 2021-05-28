package it.unibo.paserver.domain.flat;

import java.io.Serializable;

import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueReportStatsFlat implements Serializable {

	private static final long serialVersionUID = -3847692371940815003L;

	@JsonProperty("report_id")
	private long reportId;
	@JsonProperty("category_id")
	private long categoryId;
	private double longitude;
	private double latitude;
	@JsonProperty("date_time")
	private String dateTime;
	@JsonProperty("query_at")
	@Null
	private String queryAt;	
	@JsonProperty("category_name")
	@Null
	private String categoryName;	
	@JsonProperty("category_color")
	@Null
	private String categoryColor;
	/**
	 * @return the reportId
	 */
	public long getReportId() {
		return reportId;
	}

	/**
	 * @param reportId
	 *            the reportId to set
	 */
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the categoryId
	 */
	public long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime
	 *            the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the categoryColor
	 */
	public String getCategoryColor() {
		return categoryColor;
	}

	/**
	 * @param categoryColor the categoryColor to set
	 */
	public void setCategoryColor(String categoryColor) {
		this.categoryColor = categoryColor;
	}

	/**
	 * @return the queryAt
	 */
	public String getQueryAt() {
		return queryAt;
	}

	/**
	 * @param queryAt the queryAt to set
	 */
	public void setQueryAt(String queryAt) {
		this.queryAt = queryAt;
	}	
}
