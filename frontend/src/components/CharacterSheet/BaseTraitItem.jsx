const BaseTraitItem = (props) => {
    return (
        <div>
            <div>{props.traitName} : </div>
            <div>{props.value}</div>
            <div>({props.groupValue})</div>
        </div>
    )
}

export default BaseTraitItem