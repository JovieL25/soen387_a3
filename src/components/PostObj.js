import React from "react";

function PostObj(props) {
  const renderFile = () => {
    if(!props.post.files) return; 
    return props.post.files.map((file,key) => (
      <span>
        <li  onClick={(e) => handleFile()} className="list-group-item">{file}</li>
      </span>
    ));
  }

  const handleFile = e => {
    
  }

  const editPost = () => {
    console.log("POSTOBJ EDIT POST");
    props.newPost("edit",props.post.post_id);
  }

  const renderOwner = () => {
    if(props.post.user_id===parseInt([props.userId])){
      return(
        <div >
          <button className="btn btn-danger mr-2"> DELETE</button>
          <button className="btn btn-info" data-toggle="modal" data-target={"#newPostModal"} onClick={() => editPost()}>EDIT</button>
        </div>
      )
    }
  }

  const renderText = () => {
    var str = props.post.text;
    if(str.includes("#")) {
      var indices = [];
      var keys = [];
      for(var i=0; i<str.length;i++) {
        if (str[i] === "#") {
          keys.push(getClosestWord(str,i));
          indices.push(i);
        }
      }
      for(var ii=0; ii<indices.length;ii++){
        str = str.replace(keys[ii], "<span class=\"hashtag\">" + keys[ii] + "</span>");
      }
      return (<div dangerouslySetInnerHTML={{__html: str}} />);
      
    } else {
      return (
        <span>{str}</span>
      );
    }
  }

  const getClosestWord = (str, pos) =>{
    var left = str.substr(0, pos);
    var right = str.substr(pos);
    left = left.replace(/^.+ /g, "");
    right = right.replace(/ .+$/g, "");
    return right;
  }

  return (
    <div style={mainStyle}>
      <div className="card">
        <div className="card-body">
          <h4 className="card-title"><strong>{props.post.title}</strong> <span className="badge badge-secondary">{props.post.user_id}</span></h4>
          <h6 className="card-subtitle text-muted  mb-2">{props.post.post_date}</h6>
          <ul className="list-group list-group-flush mb-2">
              {renderFile()}
          </ul>
          <span className="card-text">
            {renderText()}
          </span>
          {renderOwner()}
        </div>
      </div>
    </div>
  );
}

const mainStyle = {
  margin: "0px", 
  paddingBottom: "10px",
};

const userProfileStyle = { 
  borderRadius:"50%",
  fontSize:"6px",
  width: "20px",
  height: "20px",
  textAlign: "center"
}

const hashtagStyle = {
  fontWeight: "bold",
}

export default PostObj;
