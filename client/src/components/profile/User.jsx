import { React } from 'react';

function User(props){

    let styles={
        avatar: {
            width: '100px',
            height: '100px',
            borderRadius: '20px'
        },

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
            <img src={props.pfp} alt="User profile picture" style={styles.avatar} />
            <div className="info" style={styles.info}>
                <h1>{props.name}</h1>
                <h3>{props.username}</h3>
            </div>
        </div>
    )
}

export default User;