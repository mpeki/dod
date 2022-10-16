package dk.pekilidi.dod.changerequest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ChangeKeyDeserializer.class)
public interface ChangeKey {}
