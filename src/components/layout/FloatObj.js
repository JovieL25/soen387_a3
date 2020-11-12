import React from 'react'

function FloatObj(props){

    const newPost = () => {
        console.log("FLOATOBJ NEW POST");
        props.newPost("new",-1);
    }

    return (
    <div>
        <div>
            <button type="button" className="btn btn-info" onClick={() => window.scrollTo({ top: 0, behavior: "smooth" })}>
                ↑</button>
        </div>
        <div>&nbsp;</div>
        <div>
        <span className="d-inline-block" tabIndex="0" data-toggle="tooltip" title="Disabled tooltip">
            <button type="button" className="btn btn-primary" data-toggle="modal"  data-target={"#newPostModal"} onClick={() => newPost()} >
                New Post</button>
        </span>
        </div>
    </div>
    )
  }

  export default FloatObj