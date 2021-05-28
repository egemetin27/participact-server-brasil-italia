package it.unibo.paserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "issue_report")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IssueReport implements Serializable {
    private static final long serialVersionUID = 1428089398109010673L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("formatted_address")
    @Column(columnDefinition = "TEXT", name = "formatted_address")
    private String formattedAddress;

    @JsonProperty("formatted_city")
    @Column(name = "formatted_city", nullable = true)
    private String formattedCity;

    @JsonIgnore
    @Column(name = "optionalUserName", nullable = true)
    private String optionalUserName;

    @JsonIgnore
    @Column(name = "optionalUserEmail", nullable = true)
    private String optionalUserEmail;

    @JsonProperty("addr_street")
    @Transient
    private String addrStreet;

    @JsonProperty("idCity")
    @Transient
    private Long idCity;

    @JsonProperty("idSphere")
    @Transient
    private Long idSphere;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private long negativeScore = 0L;
    /**
     * Apenas um long, numero da chave, pois nao temos controle dos codigos importados.
     */
    @Column(name = "ombudsman_id")
    private long ombudsmanId = 0L;

    @Column(name = "ombudsman_name")
    private String ombudsmanName;

    @Column(name = "siorg_id")
    private long siOrgId = 0L;

    @Column(name = "isombudsman")
    private boolean isOmbudsman;

    @Column(name = "issecondary")
    private boolean isSecondary;

    @Column(name = "parent_id")
    private long parentId = 0L;

    @Transient
    private List<HashMap<String, Object>> ombudsmanList;

    @Transient
    private List<HashMap<String, Object>> ombudsmanHistory;

    @Column(name = "isqueued")
    private boolean isQueued;

    @Column(name = "isfail")
    private boolean isFail;

    @Column(name = "isresolved")
    private boolean isResolved;

    @Transient
    private boolean isTransient = false;

    @Column(name = "pub_protocol", columnDefinition = "TEXT")
    private String publicProtocol;

    @Column(name = "pub_url", columnDefinition = "TEXT")
    private String publicUrl;

    @Column(name = "pub_message", columnDefinition = "TEXT")
    private String publicMessage;

    @Column(name = "pub_email", columnDefinition = "TEXT")
    private String publicEmail;

    @Column(name = "priv_protocol")
    @JsonIgnore
    private String privateProtocol;

    @Column(name = "priv_code_access")
    @JsonIgnore
    private String privateCodeAccess;

    @Column(name = "priv_rel_self", columnDefinition = "TEXT")
    @JsonIgnore
    private String privateRelSelf;

    @Column(name = "priv_rel_eouv", columnDefinition = "TEXT")
    @JsonIgnore
    private String privateRelEouv;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "pub_date", nullable = true)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime publicDate;

    @NotNull
    private double longitude;

    @NotNull
    private double latitude;

    @NotNull
    private double accuracy;

    @NotNull
    private String provider;
    @Column(name = "gpsInfo", nullable = true)
    private String gpsInfo;
    private double altitude = 0.0D;
    private double horizontalAccuracy = 0.0D;
    private double verticalAccuracy = 0.0D;
    private double course = 0.0D;
    private double speed = 0.0D;
    private double floor = 0.0D;
    @Column(name = "file_counter")
    private int fileCounter = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "issue", targetEntity = IssueAbuse.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<IssueAbuse> abuses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategory_id")
    private IssueSubCategory subcategory;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = StorageFile.class, cascade = CascadeType.ALL)
    @JoinTable(name = "issue_report_storage_file", joinColumns = {@JoinColumn(name = "issue_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "storage_file_id", referencedColumnName = "id")})
    //@JsonIgnore
    private List<StorageFile> files;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime creationDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime updateDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "editDate", updatable = true, nullable = false)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime editDate;

    @Column(name = "queryAt", updatable = true, nullable = true)
    private Date queryAt;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime sampleTimestamp;

    @NotNull
    @Column(name = "received_timestamp")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime dataReceivedTimestamp;

    @Column(name = "removed", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean removed = false;

    /**
     * Setters/Getters
     */
    @Override
    public IssueReport clone() throws CloneNotSupportedException {
        IssueReport ir = (IssueReport) super.clone();
        ir.setId(null);
        return ir;
    }

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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the score
     */
    public long getNegativeScore() {
        return negativeScore;
    }

    /**
     * @param score the score to set
     */
    public void setNegativeScore(long score) {
        this.negativeScore = score;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
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
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the accuracy
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * @param accuracy the accuracy to set
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * @return the provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the horizontalAccuracy
     */
    public double getHorizontalAccuracy() {
        return horizontalAccuracy;
    }

    /**
     * @param horizontalAccuracy the horizontalAccuracy to set
     */
    public void setHorizontalAccuracy(double horizontalAccuracy) {
        this.horizontalAccuracy = horizontalAccuracy;
    }

    /**
     * @return the verticalAccuracy
     */
    public double getVerticalAccuracy() {
        return verticalAccuracy;
    }

    /**
     * @param verticalAccuracy the verticalAccuracy to set
     */
    public void setVerticalAccuracy(double verticalAccuracy) {
        this.verticalAccuracy = verticalAccuracy;
    }

    /**
     * @return the course
     */
    public double getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(double course) {
        this.course = course;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return the floor
     */
    public double getFloor() {
        return floor;
    }

    /**
     * @param floor the floor to set
     */
    public void setFloor(double floor) {
        this.floor = floor;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the abuses
     */
    public List<IssueAbuse> getAbuses() {
        return abuses;
    }

    /**
     * @param abuses the abuses to set
     */
    public void setAbuses(List<IssueAbuse> abuses) {
        this.abuses = abuses;
    }

    /**
     * @return the subcategory
     */
    public IssueSubCategory getSubcategory() {
        return subcategory;
    }

    /**
     * @param subcategory the subcategory to set
     */
    public void setSubcategory(IssueSubCategory subcategory) {
        this.subcategory = subcategory;
    }

    /**
     * @return the files
     */
    public List<StorageFile> getFiles() {
        return files != null ? files : new ArrayList<StorageFile>();
    }

    /**
     * @param files the files to set
     */
    public void setFiles(List<StorageFile> files) {
        this.files = files;
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
     * @return the editDate
     */
    public DateTime getEditDate() {
        return editDate;
    }

    /**
     * @param editDate the editDate to set
     */
    public void setEditDate(DateTime editDate) {
        this.editDate = editDate;
    }

    /**
     * @return the sampleTimestamp
     */
    public DateTime getSampleTimestamp() {
        return sampleTimestamp;
    }

    /**
     * @param sampleTimestamp the sampleTimestamp to set
     */
    public void setSampleTimestamp(DateTime sampleTimestamp) {
        this.sampleTimestamp = sampleTimestamp;
    }

    /**
     * @return the dataReceivedTimestamp
     */
    public DateTime getDataReceivedTimestamp() {
        return dataReceivedTimestamp;
    }

    /**
     * @param dataReceivedTimestamp the dataReceivedTimestamp to set
     */
    public void setDataReceivedTimestamp(DateTime dataReceivedTimestamp) {
        this.dataReceivedTimestamp = dataReceivedTimestamp;
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

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public long getOmbudsmanId() {
        return ombudsmanId;
    }

    public void setOmbudsmanId(long ombudsmanId) {
        this.ombudsmanId = ombudsmanId;
    }

    public String getAddrStreet() {
        return this.getFormattedAddress();
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    public long getSiOrgId() {
        return siOrgId;
    }

    public void setSiOrgId(long siOrgId) {
        this.siOrgId = siOrgId;
    }

    public boolean isOmbudsman() {
        return isOmbudsman;
    }

    public void setOmbudsman(boolean ombudsman) {
        isOmbudsman = ombudsman;
    }

    public boolean isQueued() {
        return isQueued;
    }

    public void setQueued(boolean queued) {
        isQueued = queued;
    }

    public boolean isFail() {
        return isFail;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }

    public String getPublicProtocol() {
        return publicProtocol;
    }

    public void setPublicProtocol(String publicProtocol) {
        this.publicProtocol = publicProtocol;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public String getPublicMessage() {
        return publicMessage;
    }

    public void setPublicMessage(String publicMessage) {
        this.publicMessage = publicMessage;
    }

    public String getPublicEmail() {
        return publicEmail;
    }

    public void setPublicEmail(String publicEmail) {
        this.publicEmail = publicEmail;
    }

    public DateTime getPublicDate() {
        if (publicDate != null) {
            return publicDate;
        }
        isTransient = true;
        return new DateTime();
    }

    public void setPublicDate(DateTime publicDate) {
        this.publicDate = publicDate;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean aTransient) {
        isTransient = aTransient;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
    }

    public Long getIdSphere() {
        return idSphere;
    }

    public void setIdSphere(Long idSphere) {
        this.idSphere = idSphere;
    }

    public String getPrivateProtocol() {
        return privateProtocol;
    }

    public void setPrivateProtocol(String privateProtocol) {
        this.privateProtocol = privateProtocol;
    }

    public String getPrivateCodeAccess() {
        return privateCodeAccess;
    }

    public void setPrivateCodeAccess(String privateCodeAccess) {
        this.privateCodeAccess = privateCodeAccess;
    }

    public String getPrivateRelSelf() {
        return privateRelSelf;
    }

    public void setPrivateRelSelf(String privateRelSelf) {
        this.privateRelSelf = privateRelSelf;
    }

    public String getPrivateRelEouv() {
        return privateRelEouv;
    }

    public void setPrivateRelEouv(String privateRelEouv) {
        this.privateRelEouv = privateRelEouv;
    }

    public String getFormattedCity() {
        return formattedCity;
    }

    public void setFormattedCity(String formattedCity) {
        this.formattedCity = formattedCity;
    }

    public int getFileCounter() {
        return fileCounter;
    }

    public void setFileCounter(int fileCounter) {
        this.fileCounter = fileCounter;
    }

    public String getOptionalUserName() {
        return optionalUserName;
    }

    public void setOptionalUserName(String optionalUserName) {
        this.optionalUserName = optionalUserName;
    }

    public String getOptionalUserEmail() {
        return optionalUserEmail;
    }

    public void setOptionalUserEmail(String optionalUserEmail) {
        this.optionalUserEmail = optionalUserEmail;
    }

    public String getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(String gpsInfo) {
        this.gpsInfo = gpsInfo;
    }

    public String getOmbudsmanName() {
        return ombudsmanName;
    }

    public void setOmbudsmanName(String ombudsmanName) {
        this.ombudsmanName = ombudsmanName;
    }

    public List<HashMap<String, Object>> getOmbudsmanHistory() {
        return ombudsmanHistory;
    }

    public void setOmbudsmanHistory(List<HashMap<String, Object>> ombudsmanHistory) {
        this.ombudsmanHistory = ombudsmanHistory;
    }

    public void setOmbudsmanList(List<HashMap<String, Object>> listOmbudsman) {
        this.ombudsmanList = listOmbudsman;
    }

    public List<HashMap<String, Object>> getOmbudsmanList() {
        return ombudsmanList;
    }

    public boolean isSecondary() {
        return isSecondary;
    }

    public void setSecondary(boolean issecondary) {
        this.isSecondary = issecondary;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public IssueReport copy() {
        IssueReport copy = new IssueReport();
        copy.setId(null);
        copy.setComment(getComment());
        copy.setNegativeScore(getNegativeScore());
        copy.setLongitude(getLongitude());
        copy.setLatitude(getLatitude());
        copy.setAccuracy(getAccuracy());
        copy.setProvider(getProvider());
        copy.setAltitude(getAltitude());
        copy.setHorizontalAccuracy(getHorizontalAccuracy());
        copy.setVerticalAccuracy(getVerticalAccuracy());
        copy.setCourse(getCourse());
        copy.setSpeed(getSpeed());
        copy.setFloor(getFloor());
        copy.setUser(getUser());
        copy.setSubcategory(getSubcategory());
        copy.setCreationDate(getCreationDate());
        copy.setUpdateDate(getUpdateDate());
        copy.setEditDate(getEditDate());
        copy.setSampleTimestamp(getSampleTimestamp());
        copy.setDataReceivedTimestamp(getDataReceivedTimestamp());
        copy.setRemoved(isRemoved());
        copy.setQueryAt(getQueryAt());
        copy.setFormattedAddress(getFormattedAddress());
        copy.setOmbudsmanId(getOmbudsmanId());
        copy.setAddrStreet(getAddrStreet());
        copy.setSiOrgId(getSiOrgId());
        copy.setOmbudsman(isOmbudsman());
        copy.setQueued(isQueued());
        copy.setFail(isFail());
        copy.setPublicProtocol(getPublicProtocol());
        copy.setPublicUrl(getPublicUrl());
        copy.setPublicMessage(getPublicMessage());
        copy.setPublicEmail(getPublicEmail());
        copy.setPublicDate(getPublicDate());
        copy.setResolved(isResolved());
        copy.setIdCity(getIdCity());
        copy.setIdSphere(getIdSphere());
        copy.setPrivateProtocol(getPrivateProtocol());
        copy.setPrivateCodeAccess(getPrivateCodeAccess());
        copy.setPrivateRelSelf(getPrivateRelSelf());
        copy.setPrivateRelEouv(getPrivateRelEouv());
        copy.setFormattedCity(getFormattedCity());
        copy.setFileCounter(getFileCounter());
        copy.setOptionalUserName(getOptionalUserName());
        copy.setOptionalUserEmail(getOptionalUserEmail());
        copy.setGpsInfo(getGpsInfo());
        copy.setOmbudsmanName(getOmbudsmanName());
        copy.setSecondary(true);
        copy.setParentId(getId());
        //Res
        return copy;
    }

    @Override
    public String toString() {
        return "IssueReport{" +
                "id=" + id +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", formattedCity='" + formattedCity + '\'' +
                ", optionalUserName='" + optionalUserName + '\'' +
                ", optionalUserEmail='" + optionalUserEmail + '\'' +
                ", addrStreet='" + addrStreet + '\'' +
                ", idCity=" + idCity +
                ", idSphere=" + idSphere +
                ", comment='" + comment + '\'' +
                ", negativeScore=" + negativeScore +
                ", ombudsmanId=" + ombudsmanId +
                ", ombudsmanName='" + ombudsmanName + '\'' +
                ", siOrgId=" + siOrgId +
                ", isOmbudsman=" + isOmbudsman +
                ", isSecondary=" + isSecondary +
                ", parentId=" + parentId +
                ", isQueued=" + isQueued +
                ", isFail=" + isFail +
                ", isResolved=" + isResolved +
                ", isTransient=" + isTransient +
                ", publicProtocol='" + publicProtocol + '\'' +
                ", publicUrl='" + publicUrl + '\'' +
                ", publicMessage='" + publicMessage + '\'' +
                ", publicEmail='" + publicEmail + '\'' +
                ", privateProtocol='" + privateProtocol + '\'' +
                ", privateCodeAccess='" + privateCodeAccess + '\'' +
                ", privateRelSelf='" + privateRelSelf + '\'' +
                ", privateRelEouv='" + privateRelEouv + '\'' +
                ", publicDate=" + publicDate +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", accuracy=" + accuracy +
                ", provider='" + provider + '\'' +
                ", gpsInfo='" + gpsInfo + '\'' +
                ", altitude=" + altitude +
                ", horizontalAccuracy=" + horizontalAccuracy +
                ", verticalAccuracy=" + verticalAccuracy +
                ", course=" + course +
                ", speed=" + speed +
                ", floor=" + floor +
                ", fileCounter=" + fileCounter +
                ", user=" + user +
                ", subcategory=" + subcategory +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", editDate=" + editDate +
                ", queryAt=" + queryAt +
                ", sampleTimestamp=" + sampleTimestamp +
                ", dataReceivedTimestamp=" + dataReceivedTimestamp +
                ", removed=" + removed +
                '}';
    }
}
