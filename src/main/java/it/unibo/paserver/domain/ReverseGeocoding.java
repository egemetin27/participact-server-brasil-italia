package it.unibo.paserver.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reverse_geocoding")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReverseGeocoding implements Serializable {

    private static final long serialVersionUID = -7743235849420109220L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "place_id", nullable = true)
    private Long placeId;

    @Column(name = "lat", nullable = true)
    private Double lat;

    @Column(name = "lon", nullable = true)
    private Double lon;

    @Column(name = "bb_north_lat", nullable = true)
    private Double bbNorthLat;

    @Column(name = "bb_north_lng", nullable = true)
    private Double bbNorthLng;

    @Column(name = "bb_south_lat", nullable = true)
    private Double bbSouthLat;

    @Column(name = "bb_south_lng", nullable = true)
    private Double bbSouthLng;

    @Column(name = "licence", columnDefinition = "TEXT", nullable = true)
    private String licence;

    @Column(name = "category", nullable = true)
    private Long category;

    @Column(name = "display_name", nullable = true)
    private String displayName;

    @Column(name = "address_road", nullable = true)
    private String addressRoad;

    @Column(name = "address_neighbourhood", nullable = true)
    private String addressNeighbourhood;

    @Column(name = "address_city_district", nullable = true)
    private String addressCityDistrict;

    @Column(name = "address_city", nullable = true)
    private String addressCity;

    @Column(name = "address_county", nullable = true)
    private String addressCounty;

    @Column(name = "address_state_district", nullable = true)
    private String addressStateDistrict;

    @Column(name = "address_state", nullable = true)
    private String addressState;

    @Column(name = "address_postcode", nullable = true)
    private String addressPostCode;

    @Column(name = "address_country", nullable = true)
    private String addressCountry;

    @Column(name = "address_country_code", nullable = true)
    private String addressCountryCode;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate;

    @Column(name = "removed", nullable = true)
    private boolean removed = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getBbNorthLat() {
        return bbNorthLat;
    }

    public void setBbNorthLat(Double bbNorthLat) {
        this.bbNorthLat = bbNorthLat;
    }

    public Double getBbNorthLng() {
        return bbNorthLng;
    }

    public void setBbNorthLng(Double bbNorthLng) {
        this.bbNorthLng = bbNorthLng;
    }

    public Double getBbSouthLat() {
        return bbSouthLat;
    }

    public void setBbSouthLat(Double bbSouthLat) {
        this.bbSouthLat = bbSouthLat;
    }

    public Double getBbSouthLng() {
        return bbSouthLng;
    }

    public void setBbSouthLng(Double bbSouthLng) {
        this.bbSouthLng = bbSouthLng;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAddressRoad() {
        return addressRoad;
    }

    public void setAddressRoad(String addressRoad) {
        this.addressRoad = addressRoad;
    }

    public String getAddressNeighbourhood() {
        return addressNeighbourhood;
    }

    public void setAddressNeighbourhood(String addressNeighbourhood) {
        this.addressNeighbourhood = addressNeighbourhood;
    }

    public String getAddressCityDistrict() {
        return addressCityDistrict;
    }

    public void setAddressCityDistrict(String addressCityDistrict) {
        this.addressCityDistrict = addressCityDistrict;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCounty() {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    public String getAddressStateDistrict() {
        return addressStateDistrict;
    }

    public void setAddressStateDistrict(String addressStateDistrict) {
        this.addressStateDistrict = addressStateDistrict;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressPostCode() {
        return addressPostCode;
    }

    public void setAddressPostCode(String addressPostCode) {
        this.addressPostCode = addressPostCode;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressCountryCode() {
        return addressCountryCode;
    }

    public void setAddressCountryCode(String addressCountryCode) {
        this.addressCountryCode = addressCountryCode;
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

    @Override
    public String toString() {
        return "ReverseGeocoding{" +
                "id=" + id +
                ", placeId=" + placeId +
                ", lat=" + lat +
                ", lon=" + lon +
                ", bbNorthLat=" + bbNorthLat +
                ", bbNorthLng=" + bbNorthLng +
                ", bbSouthLat=" + bbSouthLat +
                ", bbSouthLng=" + bbSouthLng +
                ", licence='" + licence + '\'' +
                ", category=" + category +
                ", displayName='" + displayName + '\'' +
                ", addressRoad='" + addressRoad + '\'' +
                ", addressNeighbourhood='" + addressNeighbourhood + '\'' +
                ", addressCityDistrict='" + addressCityDistrict + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressCounty='" + addressCounty + '\'' +
                ", addressStateDistrict='" + addressStateDistrict + '\'' +
                ", addressState='" + addressState + '\'' +
                ", addressPostCode='" + addressPostCode + '\'' +
                ", addressCountry='" + addressCountry + '\'' +
                ", addressCountryCode='" + addressCountryCode + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", removed=" + removed +
                '}';
    }
}
