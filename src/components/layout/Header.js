import React, { useState } from "react";

function Header(props) {
  const [postInViewList] = useState([5, 10, 20, 30]);
  const [value, setValue] = useState("01-01-0101");
  const [errMsg,setErrMsg] = useState("ErrMsg");
  
  const renderDropDown = () => {
    var i = props.numPostInView;
    return postInViewList.map((item,key) => {
      if (item !== i) {
        return (
          <a className="dropdown-item" onClick={() => props.updateNumPostInView(item)} key={key}> {item} </a>
        );
      }
    });
  };

  const errorMsg = () => {
    setValue("");
    setErrMsg("Please input a valid input");
    return;
  }
  const handleSearch = (e) => {
    console.log("HEADER HANDLE SEARCH [" + value + "]");
    e.preventDefault();
    if (!value) {
      errorMsg();
      return;
    }

    var type = "";
    var str = value;
    
    if(str.substring(0, 1)==="#"){ //#
      if(str.length===1){
        errorMsg();
        return;  
      }
      type = "hashtag";
    } else if (!isNaN(str)){ //id
      type = "id";
    } else {
      var c = 0;
      for(var i=0; i<str.length;i++) {
        if (str[i] === "-")   c++; 
      }

      
      if(c===2){
        var d = str.split("-");
        if((d[0].length!==2) && (d[1].length!==2) && (d[2].length!==4)) {
          errorMsg();
          return;
        } else type = "date";
      } else if(c>2){
        type = "date range"
      } else {
        errorMsg();
        return;
      }
    }

    console.log("FINAL " + type)
    props.handleSearch(str, type);
    setValue("");
    setErrMsg(type);
  };

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="navbar-brand">SOEN387 A2</a>

        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarNavAltMarkup"
          aria-controls="navbarNavAltMarkup"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item active">
              <a className="nav-link">User {props.userId}</a>
            </li>
            <li className="nav-item">
              <a type="button" className="nav-link active" onClick={() => props.logOut()}>Log out</a>
            </li>
            <li className="nav-item active dropdown">
              <a
                className="nav-link dropdown-toggle" 
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                # in view: {props.numPostInView}
              </a>
              <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                {renderDropDown()}
              </div>
            </li>
          </ul>
          <span className="mr-2">{errMsg}</span>
          <div className="my-2 my-lg-0">
            <form onSubmit={handleSearch}>
              <input
                type="text"
                className="form-control form-control-sm"
                value={value}
                onChange={(e) => setValue(e.target.value)}
                placeholder="search"
              />
            </form>
          </div>
        </div>
        
        
      </nav>
    </div>
  );
}

export default Header;
