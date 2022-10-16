import React from 'react';

import Card from '../UI/Card';

const BaseTraitItem = (props) => {
    return (
        <Card className='expense-item'>
          <div>
            <div>{props.traitName} : {props.value} ({props.groupValue})</div>
          </div>
        </Card>
    )
}

export default BaseTraitItem
