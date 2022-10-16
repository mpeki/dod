import React, { useState }  from 'react';

const NewSkillForm = (props) => {

  const [enteredName, setEnteredName] = useState('')
  const [enteredFV, setEnteredFV] = useState('')
  const [enteredSP, setEnteredSP] = useState('')

  const nameChangeHandler = (event) => {
    setEnteredName(event.target.value)
  };

  const fvChangeHandler = (event) => {
    setEnteredFV(event.target.value)
  };

  const spChangeHandler = (event) => {
    setEnteredSP(event.target.value)
  };

  const submitHandler = (event) => {
    event.preventDefault();
    const skillData = {
      name: enteredName,
      fv: enteredFV,
      sp: enteredSP
    }
    props.onSaveSkillData(skillData)
    setEnteredName('');
    setEnteredFV('');
    setEnteredSP('');

  };

  return (
    <form onSubmit={submitHandler}>
      <div>
        <div>
          <label>Skill Name:</label>
          <input type='text' value={enteredName} onChange={nameChangeHandler}/>
        </div>

        <div>
          <label>Skill Level:</label>
          <input type='text' value={enteredFV} onChange={fvChangeHandler}/>
        </div>
        <div>
          <label>Skill Points:</label>
          <input type='text' value={enteredSP} onChange={spChangeHandler}/>
        </div>
      </div>
      <div>
        <button type='submit'>Add Skill!</button>
      </div>
    </form>
  )
};

export default NewSkillForm;
