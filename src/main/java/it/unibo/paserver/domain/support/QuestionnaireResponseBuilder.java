package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.QuestionnaireResponse;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireResponseBuilder extends EntityBuilder<QuestionnaireResponse> {

    @Override
    void initEntity() {
        
        entity = new QuestionnaireResponse();
    }

    @Override
    QuestionnaireResponse assembleEntity() {
        
        return entity;
    }

    public QuestionnaireResponseBuilder setAll(Long taskId, Long actionId, Long questionId, Long userId,
                                               String response, Long answerId, boolean isCorrect, boolean isClosed) {

        this.setTaskId(taskId);
        this.setActionId(actionId);
        this.setQuestionId(questionId);
        this.setUserId(userId);
        this.setResponse(response);
        this.setAnswerId(answerId);
        this.setCorrect(isCorrect);
        this.setClosed(isClosed);
        return this;
    }

    public QuestionnaireResponseBuilder setId(Long id) {
        entity.setId(id);
        return this;
    }

    public QuestionnaireResponseBuilder setTaskId(Long taskId) {
        entity.setTaskId(taskId);
        return this;
    }

    public QuestionnaireResponseBuilder setQuestionId(Long questionId) {
        entity.setQuestionId(questionId);
        return this;
    }

    public QuestionnaireResponseBuilder setUserId(Long userId) {
        entity.setUserId(userId);
        return this;
    }

    public QuestionnaireResponseBuilder setResponse(String response) {
        entity.setResponse(response);
        return this;
    }

    public QuestionnaireResponseBuilder setRemoved(boolean removed) {
        entity.setRemoved(removed);
        return this;
    }

    /**
     * @param actionId the actionId to set
     */
    public QuestionnaireResponseBuilder setActionId(Long actionId) {
        entity.setActionId(actionId);
        return this;
    }

    public QuestionnaireResponseBuilder setAnswerId(Long answerId) {
        entity.setAnswerId(answerId);
        return this;
    }

    public QuestionnaireResponseBuilder setCorrect(boolean isCorrect) {
        entity.setCorrect(isCorrect);
        return this;
    }

    public QuestionnaireResponseBuilder setClosed(boolean isClosed) {
        entity.setClosed(isClosed);
        return this;
    }

    public QuestionnaireResponseBuilder setAccuracy(double v) {
        entity.setAccuracy(v);
        return this;
    }

    public QuestionnaireResponseBuilder setLatitude(double v) {
        entity.setLatitude(v);
        return this;
    }

    public QuestionnaireResponseBuilder setLongitude(double v) {
        entity.setLongitude(v);
        return this;
    }

    public QuestionnaireResponseBuilder setAltitude(double v) {
        entity.setAltitude(v);
        return this;
    }

    public QuestionnaireResponseBuilder setProvider(String s) {
        entity.setProvider(s);
        return this;
    }

    public QuestionnaireResponseBuilder setPhoto(boolean v) {
        entity.setPhoto(v);
        return this;
    }

    public QuestionnaireResponseBuilder setIpAddress(String s) {
        entity.setIpAddress(s);
        return this;
    }

    public QuestionnaireResponseBuilder setAnswerGroupId(String s) {
        entity.setAnswerGroupId(s);
        return this;
    }

}