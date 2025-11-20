import { React } from 'react';

function User(props){

    let styles={
        info: {
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            textAlign: 'center'
        }
    }

    return (
        <div className="user">
            <div className="info" style={styles.info}>
                <h1>{props.name}</h1>
                <h3>{props.username}</h3>
            </div>
        </div>
    )
}

export default User;