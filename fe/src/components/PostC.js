import { Markup } from "interweave";
import { useRef } from "react";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import { setHtmlStr } from "../features/textEditSlice";
import Toolbox from "./Toolbox";

const PostC = ({ commentsForQ, Button }) => {
    //for Toobox component 
    const contentRef = useRef();
    const [emptyContentMsg, setEmptyContentMsg] = useState("");
   
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {id} = useParams();

  const [isAddingComment, setIsAddingComment] = useState(false);
  const clickAddCommentQ = () => {
    setIsAddingComment(true)
  }
  const htmlStr = useSelector((state) => {
    return state.editMode.htmlStr;
  });

  const handleAddCommentQBtn = () => {
    fetch(`/api/comments?post-type=QUESTION&id=${id}`, { //useParams로 받아오는 question id
      method: "POST",
      headers: {
        "Accept": "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
        "Authorization": localStorage.getItem("access-token"),
      },
      body: JSON.stringify({ content: htmlStr }),
  })
  .then((res) => {
    if (res.status === 201) {
      window.location.reload();
      console.log(res);
      alert("successfully added your comment");
      navigate("/questions/"+id); //useParams로 question id
      dispatch(setHtmlStr({htmlStr: ""}));
      setIsAddingComment(false);
    }
  })
  .catch(err => console.log(err))
}

  return (
    <>
      <Comment>
        <div className="for_votecell_space"></div>
        <div className="comment_box">
          {commentsForQ.map((comment) => (
            <div key={comment.id} className="each_comment">
              <Markup content={comment.content}><span className="comment">{comment.content}</span></Markup>
              <span className="username">{comment.member.name}</span>
              <span className="commented_at">
                {comment.createdAt.slice(0, 10)}
              </span>
              </div>
            ))}
            {/* <div className="show_more_comment">Show {} more comments </div> */}
          {isAddingComment? 
          <>
            <Toolbox contentRef={contentRef} setEmptyContentMsg={setEmptyContentMsg}></Toolbox>
            <Button onClick={handleAddCommentQBtn}>Add Comment</Button>
          </>
          :
          <div onClick={clickAddCommentQ} className="add_comment">Add a comment</div>
          }
        
        </div>
      </Comment>
    </>
  );
};

const Comment = styled.div`
  display: flex;
  margin: 16px 0;

  > div.for_votecell_space {
    //votecell에 맞춰 비울곳
    flex-basis: 10%;
  }
  > div.comment_box {
    flex-basis: 90%;
    border-top: 1px solid darkgray;
    > div.each_comment {
      border-bottom: 1px solid darkgray;
    }

    > div.show_more_comment {
      color: #5aaaf4;
    }  
    
    > div.add_comment {
    cursor: pointer;
  }
  }


`;

export default PostC;
