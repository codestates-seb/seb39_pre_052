import styled from "styled-components";
import { useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { setHtmlStr } from "../features/textEditSlice";
import Toolbox from "./Toolbox";
import { Markup } from "interweave";

const PostComm = ({ answerId, commentsForA, Button }) => {
  //for Toobox component
const contentRef = useRef();
const [emptyContentMsg, setEmptyContentMsg] = useState("");
 
const dispatch = useDispatch();
const navigate = useNavigate();
const {id} = useParams();

const [isAddingComment, setIsAddingComment] = useState(false);

const clickAddCommentA = () => {
  setIsAddingComment(true)
}

const htmlStr = useSelector((state) => {
  return state.editMode.htmlStr;
});

console.log(localStorage.getItem("access-token"))
const handleAddCommentABtn = () => {
  fetch(`/api/comments?post-type=ANSWER&id=${answerId}`, { 
    method: "POST",
    headers: {
      "Accept": "application/json, text/plain",
      "Content-Type": "application/json;charset=UTF-8",
      "Authorization": localStorage.getItem("access-token"),
    },
    body: JSON.stringify({ content: htmlStr })
  }) 
  .then((res) => {
    if (res.status === 201) {
      window.location.reload();
      console.log(res);
      alert("successfully added your comment on answer");
      navigate("/questions/"+id); //useParams로 question id
      dispatch(setHtmlStr({htmlStr: ""}));
      setIsAddingComment(false);
    }
  })
  .catch(err => console.log(err))
}

// const clickDeleteCommentA = () => 
// console.log('commentId', commentId)

  return (
    <>
      <Comment>
        <div className="for_votecell_space"></div>
        <div className="comment_box">
          {commentsForA.map((comment) => (
            <div key={comment.id} className="each_comment">
              {/* <Markup content={comment.content}><span className="comment">{comment.content}</span></Markup> */}
              <Markup content={comment.content}><span className="comment">{comment.content}</span></Markup>
              <span className="username">{comment.member.name}</span>
              <span className="commented_at">
                {comment.createdAt.slice(0, 10)}
              </span>
              <span onClick={()=> {
                console.log(comment.id)
                  fetch(`/api/comments/${comment.id}`, {
                    method: "DELETE",
                    headers: {
                      "Authorization": localStorage.getItem("access-token")
                    }
                  })
                  .then((res) => {
                    if(res.ok) {
                      window.location.reload();
                      alert('deleted a comment')
                      navigate("/questions/"+id);
                    }
                  })
                  .catch(err => {
                  console.log(err) 
                  })
                }
              } className="delete_comment">Delete</span>
            </div>
          ))}
          {isAddingComment? 
          <>
            <Toolbox contentRef={contentRef} setEmptyContentMsg={setEmptyContentMsg}></Toolbox>
            <Button onClick={handleAddCommentABtn}>Add Comment</Button>
          </>
          :
          <div onClick={clickAddCommentA} className="add_comment">Add a comment</div>
         
          }

          {/* <div className="show_more_comment">Show {} more comments </div> */}

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
      padding: 4px 0;
      display: flex;

      > span {
        padding-right: 4px;
        flex-basis: 50%;
      }

      /* > span > p {
        flex-basis: 50%;
      } */

      > span.username {
        flex-basis: 10%;
        color: #1893f7;
      }
      > span.commented_at {
        flex-basis: 30%;
      }
      > span.delete_comment {
        text-align: right;
        flex-basis: 10%;
        cursor: pointer;
      }
    }

    > div.show_more_comment {
      color: #5aaaf4;
    }

    > div.add_comment {
    cursor: pointer;
    padding: 4px 0;
    color: grey;
    :hover {
      color: #5aaaf4;}
    }
  }
`;

export default PostComm;
