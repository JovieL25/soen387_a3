import React, { useState, useEffect} from "react";

// https://getbootstrap.com/docs/4.3/components/modal/
function NewPostModal(props) {
  const [text, setText] = useState("");
  const [title, setTitle] = useState("");
  const [errorMsg, setErrorMsg] = useState("");


  const handleClose = () => {
    console.log("HANDLE CLOSE");
    props.modalClose();
  };

  const handlePost = (e) => {
    console.log("HANDLE POST");
    e.preventDefault();
    console.log("[" + text + "]");
    if (!text) {
      console.log("ERROR");
      setErrorMsg("Field is empty");
      console.log(errorMsg);
      return;
    } else {
      console.log("OK");
    }

    var id = props.userId;
    var name = props.userName;
    var txt = text;
    var tit = title;
    const timeElapsed = Date.now();
    const date = new Date(timeElapsed).toLocaleDateString();

    console.log(id + ", " + name + ", " + txt + ", " + date);
    /* @QUERY */
  }

  useEffect(()=>{
    console.log("USE EFFECT: TYPE " + props.modalType + ", POSTID " + props.editPostId);
  })


  const renderErrorMessage = () => {
    return (
      <div>{errorMsg}</div>
    )
  }
  

  return (
    <div
      className="modal fade"
      id="newPostModal"
      tabIndex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
      data-backdrop="static" data-keyboard="false"
    >
      <div className="modal-dialog" role="document">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">
              {props.modalType==="new" ? "NEW POST" : "EDIT POST"}
            </h5>
            <button type="button" className="close" data-dismiss="modal" onClick={handleClose}>
              <span aria-hidden="true">&times;</span>
            </button>
          </div>

          <div className="modal-body ">

            <div className="FILE mb-2">
              <input type="file" multiple className="form-control-file" id="exampleFormControlFile1"/>
            </div>
            
            <div className="TITLE mb-2">
              <input class="form-control" placeHolder="Title" onChange={(e) => setTitle(e.target.value)}/>
            </div>

            <div className="CONTENT">
              <textarea className="form-control" id="exampleFormControlTextarea1" rows="3" onChange={(e) => setText(e.target.value)}></textarea>
            </div>

            <div class="form-group">
                <div class="col-sm-9">
                    <span class="btn btn-default btn-file">
                        <input id="input-2" name="input2[]" type="file" class="file" multiple data-show-upload="true" data-show-caption="true"/>
                    </span>
                </div>
            </div>
            
            {renderErrorMessage}
          </div>


          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-dismiss="modal"
              onClick={handleClose}
            >
              Cancel
            </button>
            <button type="button" className="btn btn-secondary" onClick={handlePost}>
              Post
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

const titleStyle = {
  fontWeight: "bold",
};
const hrStyle = {
  marginTop: "2px",
  marginBottom: "2px",
};
const modalStyle = {
  paddingTop: "10px",
};
const modalContentStyle = {
  minHeight: "65vh",
};
export default NewPostModal;
