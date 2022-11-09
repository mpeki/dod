import { CharacterService } from "./character.service";
import { Character } from "../types/character";
import { Race } from "../types/race";


const raceData: Race = {
  id: 1,
  name: 'human'
}
const charPostData: Character = {
  name: 'Børge Blåtand',
  race: raceData,
  ageGroup: 'MATURE',
  hero: true
}


describe( 'Character Service', () => {
  it('get all characters',async () => {
    await CharacterService.getCharacters().then((data) => {
      expect(data).toBeTruthy();
      expect(data.length).toBeGreaterThanOrEqual(1);
      expect(data[0].id).toBeTruthy();
      expect(data[0].name).toBe("vokan fagerhård");
      expect(data[0].hero).toBe(true);
    });
  });

  it('get character by id',async () => {
    await CharacterService.getCharacter("1").then((data) => {
      expect(data).toBeTruthy();
      expect(data.id).toBeTruthy();
      expect(data.name).toBe("vokan fagerhård");
      expect(data.hero).toBe(true);
    });
  });

  it('create new character',async () => {
    await CharacterService.createCharacter(charPostData).then((data) => {
      expect(data).toBeTruthy();
      expect(data.id).toBeGreaterThan(1);
      expect(data.name).toBe("Børge Blåtand");
      expect(data.hero).toBe(true);
    });
  });

  it('create new character - find by name',async () => {
    charPostData.name = "Hansi Hinterseer"
    await CharacterService.createCharacter(charPostData).then((data) => {
      expect(data).toBeTruthy();

      // expect(data.id).toBeGreaterThan(1);
      expect(data.name).toBe("Hansi Hinterseer");
      expect(data.hero).toBe(true);
    });
    await CharacterService.getCharactersByName("Hansi Hinterseer").then((data) => {
      expect(data).toBeTruthy();
      expect(data.length).toBeGreaterThanOrEqual(1);
      expect(data[0].id).toBeGreaterThan(1);
      expect(data[0].name).toBe("Hansi Hinterseer");
      expect(data[0].hero).toBe(true);
    });


  });


});