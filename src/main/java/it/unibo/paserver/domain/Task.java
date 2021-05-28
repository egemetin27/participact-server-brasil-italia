package it.unibo.paserver.domain;

import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.unibo.paserver.domain.flat.TaskFlat;
import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;
import it.unibo.paserver.domain.support.Pipeline;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"taskReport"})
public class Task implements Serializable {
    private static final long serialVersionUID = -6920308943923407255L;
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Usuario que criou
     */
    @Column(name = "parentId", nullable = true)
    private Long parentId = null;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime deadline;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Task_Actions", joinColumns = {@JoinColumn(name = "task_id")}, inverseJoinColumns = {@JoinColumn(name = "action_id")})
    private Set<Action> actions;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "task")
    private Set<TaskReport> taskReport;
    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime start;
    @NotNull
    private Long duration;
    private Long sensingDuration;
    private Boolean canBeRefused;
    private Boolean onlyOneDeviceCanAccept;
    @Column(columnDefinition = "TEXT")
    private String notificationArea;
    @Column(columnDefinition = "TEXT")
    private String activationArea;
    @Column(name = "isPublish", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isPublish = false;
    @Column(name = "checkDetails", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean checkDetails = false;
    @Column(name = "isDuration", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDuration = false;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate;
    @Column(name = "removed", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean removed = false;
    @Column(name = "isSelectAll", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isSelectAll = false;
    @Column(name = "isAssign", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isAssign = false;
    private Long assignSelected = 0L;
    private Long assignAvailable = 0L;
    @Column(columnDefinition = "TEXT")
    private String assignFilter;

    @Column(columnDefinition = "TEXT")
    private String emailSubject;
    @Column(columnDefinition = "TEXT")
    private String emailBody;
    private Boolean isSendEmail = false;
    private Long emailSystemId = 0L;

    @Column(columnDefinition = "TEXT")
    private String agreement;

    @SuppressWarnings("unused")
    private Boolean hasPhotos = false;
    private Boolean hasQuestionnaire = false;

    private Boolean sensingWeekSun = true;
    private Boolean sensingWeekMon = true;
    private Boolean sensingWeekTue = true;
    private Boolean sensingWeekWed = true;
    private Boolean sensingWeekThu = true;
    private Boolean sensingWeekFri = true;
    private Boolean sensingWeekSat = true;

    @Column(columnDefinition = "TEXT")
    private String wpPublishText = "";
    private boolean wpPublishPage = false;
    private boolean wpPostDescription = false;
    private boolean wpPostSensorList = false;
    private boolean wpPostCampaignStats = false;
    private boolean wpPostQuestionnaire = false;

    private String color = "#7E986C";
    private String iconUrl;

    @Column(name = "copyId")
    private Long copyId = 0L;

    @Column(columnDefinition = "inviteQrCodeToken", nullable = true)
    private String inviteQrCodeToken = null;

    @JsonProperty("dt_start")
    @Transient
    private String dtStart;
    @JsonProperty("time_start")
    @Transient
    private String timeStart;
    @JsonProperty("dt_end")
    @Transient
    private String dtEnd;
    @JsonProperty("time_end")
    @Transient
    private String timeEnd;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    public Task() {
        taskReport = new LinkedHashSet<TaskReport>();
        canBeRefused = Boolean.TRUE;
        actions = new LinkedHashSet<Action>();
    }

    /**
     * Setters/Getters
     */
    public String getNotificationArea() {
        return notificationArea;
    }

    public void setNotificationArea(String notificationArea) {
        this.notificationArea = notificationArea;
    }

    public String getActivationArea() {
        return activationArea;
    }

    public void setActivationArea(String activationArea) {
        this.activationArea = activationArea;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getSensingDuration() {
        return sensingDuration;
    }

    public void setSensingDuration(Long sensingDuration) {
        this.sensingDuration = sensingDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Set<TaskReport> getTaskReport() {
        return taskReport;
    }

    public void setTaskReport(Set<TaskReport> taskReport) {
        this.taskReport = taskReport;
    }

    public Boolean getCanBeRefused() {
        return canBeRefused;
    }

    public void setCanBeRefused(Boolean canBeRefused) {
        this.canBeRefused = canBeRefused;
    }

    @JsonIgnore
    public boolean getHasPhotos() {
        for (Action action : actions) {
            if (action instanceof ActionPhoto) {
                return true;
            }
        }
        return false;
    }

    public void setHasPhotos(Boolean hasPhotos) {
        this.hasPhotos = hasPhotos;
    }

    public TaskFlat convertToTaskFlat() {
        return new TaskFlat(this);
    }

    public boolean hasPipelineType(Pipeline.Type dataType) {
        if (getActions() == null) {
            return false;
        }
        for (Action a : getActions()) {
            if (a instanceof ActionSensing) {
                if (((ActionSensing) a).getInput_type() == dataType.toInt()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasActivityDetection() {
        for (Action action : getActions()) {
            if (action instanceof ActionActivityDetection)
                return true;
        }
        return false;
    }

    /**
     * Se expirou, true expirada, false em andamento
     * @return
     */
    public boolean isExpired() {
        return getDeadline().isBefore(new DateTime());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (id != null) {
            hash += id * 37;
        }
        if (duration != null) {
            hash += duration * 3;
        }
        if (name != null) {
            hash += name.hashCode() * 97;
        }
        return hash;
    }

    public boolean isDuration() {
        return isDuration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setDuration(boolean isDuration) {
        this.isDuration = isDuration;
    }

    public void setIsDuration(boolean isDuration) {
        this.isDuration = isDuration;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Se publicada, True publicada, false nao publicada
     * @return
     */
    public boolean isPublish() {
        return isPublish;
    }

    public void setPublish(boolean isPublish) {
        this.isPublish = isPublish;
    }

    public boolean isCheckDetails() {
        return checkDetails;
    }

    public void setCheckDetails(boolean checkDetails) {
        this.checkDetails = checkDetails;
    }

    public boolean isSelectAll() {
        return isSelectAll;
    }

    public void setSelectAll(boolean isSelectAll) {
        this.isSelectAll = isSelectAll;
    }

    public Long getAssignSelected() {
        return assignSelected;
    }

    public void setAssignSelected(Long assignSelected) {
        this.assignSelected = assignSelected;
    }

    public Long getAssignAvailable() {
        return assignAvailable;
    }

    public void setAssignAvailable(Long assignAvailable) {
        this.assignAvailable = assignAvailable;
    }

    public String getAssignFilter() {
        String str = "[]";
        if (!Validator.isEmptyString(assignFilter)) {
            byte[] decodedBytes = Base64.decodeBase64(assignFilter);
            str = new String(decodedBytes);
        }
        return str;
    }

    public void setAssignFilter(String assignFilter) {
        byte[] encodedBytes = Base64.encodeBase64(assignFilter.getBytes());
        this.assignFilter = new String(encodedBytes);
    }

    public boolean isAssign() {
        return isAssign;
    }

    public void setAssign(boolean isAssign) {
        this.isAssign = isAssign;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public boolean isSendEmail() {
        return isSendEmail;
    }

    public void setSendEmail(boolean isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public boolean getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(Boolean isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public Boolean getHasQuestionnaire() {
        return hasQuestionnaire;
    }

    public void setHasQuestionnaire(Boolean hasQuestionnaire) {
        this.hasQuestionnaire = hasQuestionnaire;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public Long getEmailSystemId() {
        return emailSystemId;
    }

    public void setEmailSystemId(Long emailSystemId) {
        this.emailSystemId = emailSystemId;
    }

    public void setSensingWeekDay(boolean sensingWeekSun, boolean sensingWeekMon, boolean sensingWeekTue, boolean sensingWeekWed, boolean sensingWeekThu, boolean sensingWeekFri, boolean sensingWeekSat) {

        this.setSensingWeekSun(sensingWeekSun);
        this.setSensingWeekMon(sensingWeekMon);
        this.setSensingWeekTue(sensingWeekTue);
        this.setSensingWeekWed(sensingWeekWed);
        this.setSensingWeekThu(sensingWeekThu);
        this.setSensingWeekFri(sensingWeekFri);
        this.setSensingWeekSat(sensingWeekSat);
    }

    public Boolean getSensingWeekSun() {
        return sensingWeekSun;
    }

    public void setSensingWeekSun(Boolean sensingWeekSun) {
        this.sensingWeekSun = sensingWeekSun;
    }

    public Boolean getSensingWeekMon() {
        return sensingWeekMon;
    }

    public void setSensingWeekMon(Boolean sensingWeekMon) {
        this.sensingWeekMon = sensingWeekMon;
    }

    public Boolean getSensingWeekTue() {
        return sensingWeekTue;
    }

    public void setSensingWeekTue(Boolean sensingWeekTue) {
        this.sensingWeekTue = sensingWeekTue;
    }

    public Boolean getSensingWeekWed() {
        return sensingWeekWed;
    }

    public void setSensingWeekWed(Boolean sensingWeekWed) {
        this.sensingWeekWed = sensingWeekWed;
    }

    public Boolean getSensingWeekThu() {
        return sensingWeekThu;
    }

    public void setSensingWeekThu(Boolean sensingWeekThu) {
        this.sensingWeekThu = sensingWeekThu;
    }

    public Boolean getSensingWeekFri() {
        return sensingWeekFri;
    }

    public void setSensingWeekFri(Boolean sensingWeekFri) {
        this.sensingWeekFri = sensingWeekFri;
    }

    public Boolean getSensingWeekSat() {
        return sensingWeekSat;
    }

    public void setSensingWeekSat(Boolean sensingWeekSat) {
        this.sensingWeekSat = sensingWeekSat;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    /**
     * @return the copyId
     */
    public Long getCopyId() {
        return copyId;
    }

    /**
     * @param copyId the copyId to set
     */
    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the iconUrl
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * @param iconUrl the iconUrl to set
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * @return the wpPublishText
     */
    public String getWpPublishText() {
        return wpPublishText;
    }

    /**
     * @param wpPublishText the wpPublishText to set
     */
    public void setWpPublishText(String wpPublishText) {
        this.wpPublishText = wpPublishText;
    }

    /**
     * @return the wpPublishPage
     */
    public boolean isWpPublishPage() {
        return wpPublishPage;
    }

    /**
     * @param wpPublishPage the wpPublishPage to set
     */
    public void setWpPublishPage(boolean wpPublishPage) {
        this.wpPublishPage = wpPublishPage;
    }

    /**
     * @return the wpPostDescription
     */
    public boolean isWpPostDescription() {
        return wpPostDescription;
    }

    /**
     * @param wpPostDescription the wpPostDescription to set
     */
    public void setWpPostDescription(boolean wpPostDescription) {
        this.wpPostDescription = wpPostDescription;
    }

    /**
     * @return the wpPostSensorList
     */
    public boolean isWpPostSensorList() {
        return wpPostSensorList;
    }

    /**
     * @param wpPostSensorList the wpPostSensorList to set
     */
    public void setWpPostSensorList(boolean wpPostSensorList) {
        this.wpPostSensorList = wpPostSensorList;
    }

    /**
     * @return the wpPostCampaignStats
     */
    public boolean isWpPostCampaignStats() {
        return wpPostCampaignStats;
    }

    /**
     * @param wpPostCampaignStats the wpPostCampaignStats to set
     */
    public void setWpPostCampaignStats(boolean wpPostCampaignStats) {
        this.wpPostCampaignStats = wpPostCampaignStats;
    }

    public Boolean getOnlyOneDeviceCanAccept() {
        return onlyOneDeviceCanAccept;
    }

    public void setOnlyOneDeviceCanAccept(Boolean onlyOneDeviceCanAccept) {
        this.onlyOneDeviceCanAccept = onlyOneDeviceCanAccept;
    }

    public String getDtStart() {
        return Useful.dateDbToSystem(start.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    public String getTimeStart() {
        return Useful.timeDbToSystem(start.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    public String getDtEnd() {
        return Useful.dateDbToSystem(deadline.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    public String getTimeEnd() {
        return Useful.timeDbToSystem(deadline.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    public boolean isWpPostQuestionnaire() {
        return wpPostQuestionnaire;
    }

    public void setWpPostQuestionnaire(boolean wpPostQuestionnaire) {
        this.wpPostQuestionnaire = wpPostQuestionnaire;
    }

    public String getInviteQrCodeToken() {
        return inviteQrCodeToken;
    }

    public void setInviteQrCodeToken(String inviteQrCodeToken) {
        this.inviteQrCodeToken = inviteQrCodeToken;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}