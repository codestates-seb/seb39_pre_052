import { Markup } from "interweave";
import { useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import { setHtmlStr } from "../features/textEditSlice";
import Toolbox from "./Toolbox";

const PostA = ({ answer, Button}) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { id } = useParams();

  //for Toobox component 
  const contentRef = useRef();
  const [emptyContentMsg, setEmptyContentMsg] = useState("");

  const datedata = new Date(answer.answeredAt);
  const month = new Intl.DateTimeFormat("en", { month: "short" }).format(
    datedata
  );
  const year = new Intl.DateTimeFormat("en", { year: "numeric" }).format(
    datedata
  );
  const day = new Intl.DateTimeFormat("en", { day: "2-digit" }).format(
    datedata
  );
  const fullDateFormat = `${month} ${day}, ${year} at ${(
    "0" + datedata.getHours()
  ).slice(-2)}:${("0" + datedata.getMinutes()).slice(-2)}`;

  const [isEdited, setIsEdited] = useState(false);
  
  //저장소에 저장해둔 answer.content 불러오기
  const htmlStr = useSelector((state) => {
    return state.editMode.htmlStr;
  });

  const clickEditAnswer = () => {
    setIsEdited(true);
    dispatch(setHtmlStr({htmlStr: answer.content}))
  }

  const clickCancelEditAnswer = () => {
    setIsEdited(false);
    dispatch(setHtmlStr({htmlStr: ""}))
  }

  const clickDeleteAnswer = () => {
    fetch(`/api/answers/`+answer.id, {
      method: "DELETE",
      headers: {
        "Authorization": localStorage.getItem("access-token")
      }
    })
    .then((res) => {
      if (res.ok) {
        window.location.reload(); 
        alert("deleted your answer ")
      }
    })
    .catch(err => console.log(err))
  }

  // console.log("Token:",localStorage.getItem("access-token"))

  const handleEditAnswerBtn = () => {
    fetch(`/api/answers/`+answer.id, {
      method: "PATCH",
      headers: {
        "Accept": "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
        "Authorization": localStorage.getItem("access-token"),
      },
      body: JSON.stringify({content: htmlStr})
    }) 
    .then((res) => {
      if (res.ok) {        
        window.location.reload(); //새로고침
        console.log(res)
        alert("successfully edited your answer")
        setIsEdited(false)
        // navigate("/questions/"+id); //question id는 useParams로 받아옴
        dispatch(setHtmlStr({htmlStr: ""}));
      }
    })
    .catch((err) => {
      // alert(`${err}`)
      console.log(err) 
    })
  }

  return (
    <>
      <Post>
        <Votecell>{answer.vote}</Votecell>
        { isEdited? 
        <Postcell>

        <Toolbox contentRef={contentRef} setEmptyContentMsg={setEmptyContentMsg}></Toolbox>
        <UserContent>
            <div className="edit">
              <Button onClick={handleEditAnswerBtn}>Edit your Answer</Button>
            </div>
            <div className="cancel_edit" onClick={clickCancelEditAnswer}>Cancel</div>
            <div className="userinfo">
              <div>
                <div>answered </div>
                <div>img</div>
              </div>
              <div>
                <span>{fullDateFormat}</span>
                <div>{answer.member.name}</div>
              </div>
            </div>
          </UserContent>
        
        </Postcell>
        :
        <Postcell>
          <Markup content={answer.content}><Content>{answer.content}</Content></Markup>
          <UserContent>
            <div className="edit">
              <div>Share</div>
              <div className="click_edit" onClick={clickEditAnswer}>Edit</div>
              <div className="click_delete" onClick={clickDeleteAnswer}>Delete</div>
              <div>Follow</div>
            </div>
            <div className="userinfo">
              <div>
                <div>answered </div>
                <div>img</div>
              </div>
              <div>
                <span>{fullDateFormat}</span>
                <div>{answer.member.name}</div>
              </div>
            </div>
          </UserContent>
        </Postcell>
        }
        
      </Post>
    </>
  );
};

// const AHeader = styled.div`
//   display: flex;
//   flex-direction: column;
//   align-items: flex-start;

//   > div {
//     display: flex;
//     flex-direction: row;
//     width: 95%;
//     margin: 10px;
//     justify-content: space-between;
//   }
// `;

// const Title = styled.div`
//   flex-basis: 70%;
//   font-size: 20px;
//   font-weight: 500;
// `;

// const Button = styled.button`
//   width: 100px;
//   height: 38px;
// `;

const Post = styled.div`
  display: flex;
  /* border-bottom: 1px solid darkgray; */
`;
const Votecell = styled.div`
  flex-basis: 10%;
  margin: 16px 0;
`;
const Postcell = styled.div`
  flex-basis: 90%;
  display: flex;
  flex-direction: column;
  margin: 16px 0;
  /* border: solid 1px; //임시 */
`;
const Content = styled.div`
  /* flex-basis: 80%; */
  /* border: solid 1px;//임시 */
  height: max-content;
  margin-bottom: 16px;
`;
const UserContent = styled.div`
  /* flex-basis: 20%; */
  /* border: solid 1px;//임시 */
  margin-top: 16px;
  height: 75px;

  display: flex;

  > div.edit {
    flex-basis: 60%;
    display: flex;
    flex-direction: row;

    > div {
      padding-right: 15px;
      cursor: not-allowed;
    }

    > div.click_edit {
      cursor: pointer;
    }
    > div.click_delete{
      cursor: pointer;
    }
  }

  > div.cancel_edit  {
    cursor: pointer;
  }
  > div.userinfo {
    flex-basis: 40%;
    /* background-color: #d9eaf7; */ //answered엔 없음
    border: solid 1px; //임시
    border-radius: 5px;
    display: flex;
    flex-wrap: wrap;
    max-width: 200px;
    max-height: 66px;
  }
`;

export default PostA;
