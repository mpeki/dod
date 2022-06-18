import React from 'react';

import Card from '../UI/Card';

const SkillItem = (props) => {
  return (
      <Card className='expense-item'>
        <div>
          <div>{props.name} : {props.fv}</div>
        </div>
      </Card>
  )
}

export default SkillItem
