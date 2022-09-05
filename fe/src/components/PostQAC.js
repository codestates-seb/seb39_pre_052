import { useEffect, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import { questionDetails } from "../features/questionSlice";
import { setTitle, setHtmlStr } from "../features/textEditSlice";
import PostA from "./PostA";
import PostC from "./PostC";
import PostComm from "./PostComm";
import PostQ from "./PostQ";
import Toolbox from "./Toolbox";
import { Markup } from 'interweave'; 

const PostQAC = () => {
  const contentRef = useRef();
  const [emptyContentMsg, setEmptyContentMsg] = useState("");
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const { id } = useParams();

  //redux 대신 상태로 question 포스트 받아오기
  const [dataid, setDataid] = useState();
  const [datatitle, setDatatitle] = useState("");
  const [datacontent, setDatacontent] = useState("");
  const [dataaskedAt, setDataaskedAt] = useState("");
  const [dataview, setDataview] = useState();
  const [datavote, setDatavote] = useState();
  const [datamember, setDatamember] = useState({});

  const individualPost = useSelector((state) => {
    return state.question.question;
  });
  // console.log("individualPost: ", individualPost);
  //{id: 1, title: 'Test Question', content: 'Test Question Content', askedAt: '2022-09-02T07:31:25.340465', view: 1111, …}

  const commentsForQ = useSelector((state) => {
    return state.question.question.comments;
  });
  // console.log("commentsForQ", commentsForQ);
  // [{…}, {…}, {…}]
  // [
  //   { "id": 1, "content": "Question comment 1", "createdAt": "2022-09-02T07:31:25.43259", "member": {
  //         "id": 6,
  //         "name": "BBB"
  //     }
  // },..]
  // let idx =0;
  // for (let i=0; i<commentsForQ.length; i++) {
  //   idx = i;
  // }

  const answers = useSelector((state) => {
    return state.question.question.answers;
  });
  // console.log(answers); // [{…}, {…}]


  const htmlStr = useSelector((state) => {
    return state.editMode.htmlStr;
  });

//   const questionId = useSelector((state) => {
//     return state.editMode.questionId;
// });
// console.log(questionId);

  //Fetch 질문 세부 내용 조회하기
  useEffect(() => {
    // fetch(`/api/questions/${questionId}`)
    fetch(`/api/questions/`+id) //useParams
      .then((res) => res.json())
      .then((data) => {
        console.log(data.data);
        dispatch(questionDetails({ question: data.data }));
        //redux 대신 상태로
        setDataid(data.data.id)
        setDatatitle(data.data.title)
        setDatacontent(data.data.content)
        setDataaskedAt(data.data.askedAt)
        setDataview(data.data.view)
        setDatavote(data.data.vote)
        setDatamember(data.data.member)
      })
      .catch((err) => console.log("cannot fetch individual question data"));
  }, []);
 
  //답변 생성
  const handlePostAnswer = () => {
    fetch(`/api/answers?question-id=${id}`, {
      method: "POST",
      headers: {
        "Accept": "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
        "Authorization": localStorage.getItem("access-token"),
      },
      body: JSON.stringify({ content: htmlStr }),
    })
      // .then((res) => console.log(res)); //Response {type: 'basic', url: 'http://localhost:3000/api/answers?question-id=1', redirected: false, status: 201, ok: true, …}
      .then((res) => {
        if (res.status === 201) {
          window.location.reload();
          console.log(res);
          alert("successfully posted your answer");
          navigate("/questions/"+id);
          // window.location.reload();
          dispatch(setHtmlStr({htmlStr: ""}));
        }
      })
      .catch((err) => {
        alert(err)
      console.log(err)});
  };
  // console.log("htmlStr", htmlStr);
  // console.log(localStorage.getItem("access-token"))

  //edit 버튼 누르면 Editor 컴포넌트로 넘어가고 현재 질문 제목과 컨텐츠를 textEditSlice 상태 저장소로 저장
  //Editor컴포넌트는 Toolbox 컴포넌트를 포함. Editor에서 저장된 title, htmlStr 상태 불러온다
  const editHandler = () => {
    dispatch(setTitle({title: datatitle}))
    dispatch(setHtmlStr({htmlStr: datacontent}))
  }

  //질문 삭제 
  const deleteQuestion = () => {
    fetch(`/api/questions/`+id, {
      method: "DELETE",
      headers: {
        "Authorization": localStorage.getItem("access-token")
      }
    })
    .then((res) => {
      if(res.ok) {
        alert('deleted your question')
        navigate("/")
      }
    })
    .catch(err => {
    console.log(err) 
    })
  }

  return (
    <Container>
      {/* <PostQ individualPost={individualPost}></PostQ> */}
      {/* 상태로 띄우기 */}
      
      <QHeader>
        <div>
          <Title>{datatitle}</Title>
          <Button>Ask Question</Button>
        </div>
        <TitleInfo>
          {/* <div>
            Asked <Moment fromNow>{datedata}</Moment>
          </div>
          <div>
            Modified <Moment fromNow>{datedata}</Moment>
          </div> */}
            <div>
            Asked {dataaskedAt}
          </div>
          <div> Viewed {dataview} times</div>
        </TitleInfo>
      </QHeader>
      <Post>
        <Votecell>{datavote}</Votecell>
        <Postcell>
          <Markup content={datacontent}><Content>{datacontent}</Content></Markup>
          <UserContent>
            <div className="edit">
              <div>Share</div>
              <Link to="/questions/edit">
              <div className="edit_q" onClick={editHandler}>Edit</div> {/* edit 페이지 연결하기 */}
              </Link>

              &nbsp;&nbsp;&nbsp;&nbsp;
              <div className="delete" onClick={deleteQuestion}>Delete</div>
              
              <div>Follow</div>
            </div>
            <div className="userinfo">
              <div>
                <div>asked </div>
                {/* <div>
                  <img src={dummy[0].userUrl} alt="img" />
                </div> */}
              </div>
              <div>
                <span>{dataaskedAt}</span>
                <div>{datamember.name}</div>
              </div>
            </div>
          </UserContent>
        </Postcell>
      </Post>
      {/*  */}

      {/* <PostC commentsForQ={commentsForQ}></PostC> */}
      {commentsForQ? 
      <PostC commentsForQ={commentsForQ} Button={Button}></PostC> : 'comment 안받아오기'}
      {answers?  
      <>  
      <AHeader>
        <div>
          <Title>{answers.length} Answers</Title>
        </div>
      </AHeader>
      {answers.map((answer) => (
        <div className="br">
          <PostA key={answer.id} answer={answer} Button={Button}></PostA>
          <PostComm
            key={answer.comments.id}
            commentsForA={answer.comments}
            Button={Button}
          ></PostComm>
        </div>
      ))} 
      </>  : <>answers 없음</>}
      <PostAnswer>
        <div>Your Answer</div>
        <Toolbox
          contentRef={contentRef}
          setEmptyContentMsg={setEmptyContentMsg}
        ></Toolbox>
      </PostAnswer>
      <Button onClick={handlePostAnswer}>Post Your Answer</Button>
    </Container>
  );
};
const Container = styled.div`
  height: 100%;
  width: 80%;
  margin: 20px;
  /* ::-webkit-scrollbar {
    display: none;
  } */

  > div.br {
    border-bottom: 1px solid darkgray;
  }
`;

const AHeader = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;

  > div {
    display: flex;
    flex-direction: row;
    width: 95%;
    margin: 10px;
    justify-content: space-between;
  }
`;

const Title = styled.div`
  flex-basis: 70%;
  font-size: 20px;
  font-weight: 500;
`;

const PostAnswer = styled.div``;

const Button = styled.button`
  width: 128px;
  height: 38px;
`;

// PostQ 컴포넌트 추가
const QHeader = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  border-bottom: 1px solid darkgray;

  > div {
    display: flex;
    flex-direction: row;
    width: 95%;
    margin: 10px;
    justify-content: flex-start;
  }
`;


const TitleInfo = styled.div`
  /* display: flex; */
  /* flex-direction: row; */

  > div {
    background-color: turquoise;
    padding-right: 20px;
  }
`;

const Post = styled.div`
  display: flex;
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
      text-decoration: none;
      cursor: not-allowed
    }

    > div.edit_q {
      cursor: pointer;
    }

    > div.delete {
      cursor: pointer;
    }
  }
  > div.userinfo {
    flex-basis: 40%;
    max-width: 200px;
    max-height: 66px;
    background-color: #d9eaf7;
    border-radius: 5px;
    display: flex;
    flex-wrap: wrap;
    padding: 5px;

    > div > div > img {
      width: 32px;
      height: 32px;
    }
  }
`;

export default PostQAC;
