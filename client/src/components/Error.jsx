export default function Error(props){

    let styles={
        error: {
            display: "block",
            borderRadius: "25px",
            border: "1px solid red",
            backgroundColor: "rgba(210, 22, 22, 0.4)",
            color: "#fff8f8f0",
            margin: 0,
            padding: 0
        }
    }

    return (
        <div className="error" style={styles.error}>
            {props.message !== "" && <p>ERROR: {props.message}</p>}
        </div>
    )
}