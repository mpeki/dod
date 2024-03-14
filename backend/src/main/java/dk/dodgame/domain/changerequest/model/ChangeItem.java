package dk.dodgame.domain.changerequest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ChangeItemDeserializer.class)
public interface ChangeItem {}
