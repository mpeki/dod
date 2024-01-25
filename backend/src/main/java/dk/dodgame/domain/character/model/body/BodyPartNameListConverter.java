package dk.dodgame.domain.character.model.body;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class BodyPartNameListConverter implements AttributeConverter<List<BodyPartName>, String> {

  // Separator for storing the list as a single string
  private static final String SEPARATOR = ",";

  @Override
  public String convertToDatabaseColumn(List<BodyPartName> list) {
    if (list == null || list.isEmpty()) {
      return "";
    }
    return list.stream()
        .map(Enum::ordinal)
        .map(String::valueOf)
        .collect(Collectors.joining(SEPARATOR));
  }


  @Override
  public List<BodyPartName> convertToEntityAttribute(String dbData) {
    if (isNullOrEmpty(dbData)) {
      return null;
    }
    return Arrays.stream(dbData.split(SEPARATOR)).map(Integer::parseInt).map(ordinal -> BodyPartName.values()[ordinal])
        .toList();
  }

  private boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }
}
