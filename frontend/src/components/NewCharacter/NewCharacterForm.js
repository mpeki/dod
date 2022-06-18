import React, { useState }  from 'react';

const NewCharacterForm = () => {

  // const [charName, setCharName] = useState('')
  // const [race, setRace] = useState('')
  // const [isHero, setIsHero] = useState('off')

  const [charInput, setCharInput] = useState({
    charName: '',
    race: '',
    isHero: false
  });

  const nameChangeHandler = (event) => {
    setCharInput((prevState) => {
      return {...prevState, charName: event.target.value };
    });
  };

  const raceChangeHandler = (event) => {
    setCharInput((prevState) => {
      return {...prevState, race: event.target.value };
    });
  };

  const heroToggleHandler = (event) => {
    setCharInput((prevState) => {
      return {...prevState, isHero: event.target.checked };
    });
  };

  const submitHandler = (event) => {
    event.preventDefault();
    console.log(charInput)
    setCharInput({charName: '', race: '', isHero: false})

  };

  return (
    <form onSubmit={submitHandler}>
      <div>
        <div>
          <label>Character Name:</label>
          <input type='text' value={charInput.charName} onChange={nameChangeHandler}/>
        </div>

        <div>
          <label>Pick a race:</label>
          <select name='race' value={charInput.race} onChange={raceChangeHandler}>
            <option value=''></option>
            <option value='Human'>Human</option>
          </select>
        </div>
        <div>
          <label>Hero?:</label>
          <input type='checkbox' checked={charInput.isHero} onChange={heroToggleHandler}/>
        </div>
      </div>
      <div>
        <button type='submit'>Create Character!</button>
      </div>
    </form>
  )
};

export default NewCharacterForm;
