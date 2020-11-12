import React, { useState } from "react";
import "./App.css";

/**
 * COMPONENTS
 */
import Header from "./components/layout/Header";
import FloatObj from "./components/layout/FloatObj";
import NewPostModal from "./components/NewPostModal";
import PostObj from "./components/PostObj";

function App() {
  /**
   * STATE
   */
  const [userId] = useState(2);
  const [newPostModalActive, setNewPostModalActive] = useState(false);
  const [modalType, setModalType] = useState("");
  const [numPostInView, setNumPostInView] = useState(5);
  const [editPostId, setEditPostId] = useState(-1);
  

  const [posts, setPosts] = React.useState([
    {
      post_id: 1,
      user_id: 1,
      title: "Title sample",
      post_date: "09/10/2020",
      text: "#BML Lorem Ipsum is simply #WDF dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.#LOL",
    },
    {
      post_id: 2,
      user_id: 2,
      title: "Title sample",
      post_date: "09/10/2020",
      text: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    }
  ]);

  /**
   * FUNCTION
   */
  const handleSearch = (text, type) => {
    console.log("HANDLE SEARCH " + text + " " + type);
  }

  const newPost = (text,i) => {
    console.log(text);
    if(text==="new"){
      setNewPostModalActive(true);
      setModalType("new");
    } else if (text==="edit"){
      setEditPostId(i);
      setNewPostModalActive(true);
      setModalType("edit");
    }
  };

  const modalClose = () => {
    console.log("MODAL CLOSE");
    setNewPostModalActive(false);
    setModalType("");
    setEditPostId(-1);
  }

  const updateNumPostInView = (i) => {
    console.log("New # item in view " + i)
    setNumPostInView(i);
    console.log("NEW VALUE " + numPostInView);
  }

  const renderPosts = () => {
    var len = numPostInView;
    /* call backend */
    /* UPDATE */
    if(posts.length<len) len = posts.length
    console.log("RENDERING " + len + " POSTS")
    return posts.slice(0,len).map((post, key) => (
      <PostObj key={key} post={post} userId={userId} newPost={newPost}/>
    ));
  };

  const logOut = () => { setPage(1);}

  const [page,setPage] = useState(1); //1-login, 2-dashboard, 3-search 
  const [loginErr,setLoginErr] = useState("");


  const handleLogin = () => {
    setPage(2);
  }

  const mainRender = () => {
    if(page===1){
      return (
        <div className=" " style={loginStyle}>
          <div><h1 className="">LOGIN</h1></div>
          <div className="TITLE mb-2">
              <input class="form-control" placeHolder="User Name"/>
              <input class="form-control" type="password" placeHolder="Password "/>
          </div>
          <button type="button" class="btn btn-primary btn-block mb-2" onClick={e => handleLogin()}>LOGIN</button>
          <div><button type="button" class="btn btn-info btn-block" onClick={e => setLoginErr("This function is not implemented yet.")}>REGISTER</button></div>
          <div style={{color:"red"}}>{loginErr===""? "": loginErr}</div>
        </div>
      )
    } else if (page===2){
      return (
        <div>
          <div className="mx-auto" style={containerStyle}>{renderPosts()}</div>
          <div style={floatObjStyle}><FloatObj newPost={newPost}/></div>
          <NewPostModal userId={userId} modalType={modalType} editPostId={editPostId} modalClose={modalClose}/>    
        </div>
      )
    } else if (page===3){
      return (
        <div>
          <h1>SEARCH RESULT</h1>
        </div>
      )
    }
  }

  return (
    <div className="app">
      <Header userId={userId} logOut={logOut} handleSearch={handleSearch} numPostInView={numPostInView} updateNumPostInView={updateNumPostInView} />
      <div>modalType:{modalType}</div>
      <div>editPostId:{editPostId}</div>
      <div>modalActive:{newPostModalActive===true? "TRUE" : "FALSE"}</div>
      <div>page: {page}</div>
      {mainRender()}
    </div>
  );
}

const floatObjStyle = {
  position: "fixed",
  bottom: "10px",
  right: "10px",
  paddingButton:"50px",
};

const containerStyle = {
  marginTop: "10px",
  borderRadius: "4px",
  maxWidth: "500px", 
};

const loginStyle = {
  marginTop: "200px",
  maxWidth: "500px", 
  margin: "auto",
  width: "50%",
  border: "1px solid white",
  padding: "10px",
};




export default App;
