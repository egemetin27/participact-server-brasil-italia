package it.unibo.paserver.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "actionquestionaire_response")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionnaireResponse implements Serializable {
    private static final long serialVersionUID = 4450518653531421673L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Index(name = "idx_questionnaire_response_task_id")
    private Long taskId;
    @NotNull
    @Index(name = "idx_questionnaire_response_action_id")
    private Long actionId;
    @NotNull
    @Index(name = "idx_questionnaire_response_question_id")
    private Long questionId;
    @NotNull
    @Index(name = "idx_questionnaire_response_user_id")
    private Long userId;
    private Long answerId;
    private boolean isCorrect = false;
    private boolean isClosed = false;
    @Column(name = "isphoto")
    private boolean isPhoto = false;
    @Column(name = "altitude")
    private double altitude = 0.0D;
    @Column(name = "latitude")
    private double latitude = 0.0D;
    @Column(name = "longitude")
    private double longitude = 0.0D;
    @Column(name = "accuracy")
    private double accuracy = 0.0D;
    @Column(name = "provider")
    private String provider = "N/A";
    @Column(name = "ipaddress")
    private String ipAddress = "::0";
    @Column(name = "answergroupid")
    private String answerGroupId = "N/A";

    @Column(columnDefinition = "TEXT")
    private String response;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate;
    @Column(name = "removed", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean removed = false;

    public QuestionnaireResponse() {

    }

    public QuestionnaireResponse(Long id, Long taskId, Long actionId, Long questionId, Long userId, String response) {
        super();
        this.id = id;
        this.taskId = taskId;
        this.actionId = actionId;
        this.questionId = questionId;
        this.userId = userId;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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

    /**
     * @return the actionId
     */
    public Long getActionId() {
        return actionId;
    }

    /**
     * @param actionId the actionId to set
     */
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setPhoto(boolean photo) {
        isPhoto = photo;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAnswerGroupId() {
        return answerGroupId != null ? answerGroupId : "N/A";
    }

    public void setAnswerGroupId(String answerGroupId) {
        this.answerGroupId = answerGroupId;
    }
}