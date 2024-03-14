package dk.dodgame.mapper;

import dk.dodgame.util.character.CharacterMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CharacterMapperService {

  private ModelMapper modelMapper;
  private CharacterMapper characterMapper;

  public CharacterMapperService() {
    modelMapper = new ModelMapper();
    characterMapper = new CharacterMapper();
  }

}
