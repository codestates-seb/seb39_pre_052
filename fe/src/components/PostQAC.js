import { useEffect, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import { questionDetails } from "../features/questionSlice";
import { userToken } from "../features/userSlice";
import { setHtmlStr, setQuestionId } from "../features/textEditSlice";
import PostA from "./PostA";
import PostC from "./PostC";
import PostComm from "./PostComm";
import PostQ from "./PostQ";
import Toolbox from "./Toolbox";

const PostQAC = () => {
  const contentRef = useRef();
  const [emptyContentMsg, setEmptyContentMsg] = useState("");
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const { id } = useParams();

  const individualPost = useSelector((state) => {
    return state.question.question;
  });
  // console.log(individualPost);
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

  const answers = useSelector((state) => {
    return state.question.question.answers;
  });
  // console.log("individualPost", individualPost); //useState 상태로 저장했을때
  //{id: 1, title: 'Test Question', content: 'Test Question Content', askedAt: '2022-09-02T07:31:25.340465', view: 1111, …}
  // console.log(answers); // [{…}, {…}]

  //답변 관련
  // const token = useSelector((state) => {
  //   return state.user.userToken;
  // });

  const htmlStr = useSelector((state) => {
    return state.editMode.htmlStr;
  });

  //Fetch 질문 세부 내용 조회하기
  useEffect(() => {
    // fetch(`/api/questions/${id}`)
    fetch(`/api/questions/1`)
      .then((res) => res.json())
      .then((data) => {
        // setIndividualPost(data.data);
        // console.log(data.data);
        dispatch(questionDetails({ question: data.data }));
        // setAnswers(data.data.answers);
      })
      .catch((err) => console.log("cannot fetch individual question data"));
  }, []);
  //답변 생성
  const handlePostAnswer = () => {
    fetch("/api/answers?question-id=1", {
      method: "POST",
      headers: {
        "Accept": "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
        "Authorization": localStorage.getItem("access-token"),
        // "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsb2dpbiBqd3QgdG9rZW4iLCJleHAiOjE2NjIzMDIwMDQsImVtYWlsIjoiYWJjZEAxMjM0LmNvbSJ9.vAaoR7i0qE6nr995ovci9u23ptmbi1jMaVhEVGcKDwWD81b8QNUFGKE0HfBNrAieQbb5nHZYDOeTuS-UTEL3hA"
        // "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsb2dpbiBqd3QgdG9rZW4iLCJleHAiOjE2NjIyOTE5NzUsImVtYWlsIjoiYWJjZEAxMjM0LmNvbSJ9.xXcPST3NdppL7mB8AHsHYpVG0-VOnJZ9ifxVUcI6L-t4AfO61rtW_qDJLp264VLgK5IvGdA5TJDDuEkhyAkvPQ", //만료된 토큰
      },
      body: JSON.stringify({ content: htmlStr }),
    })
      // .then((res) => console.log(res)); //Response {type: 'basic', url: 'http://localhost:3000/api/answers?question-id=1', redirected: false, status: 201, ok: true, …}
      .then((res) => {
        if (res.status === 201) {
          console.log(res);
          alert("successfully posted your answer");
          navigate("/api/questions/1");
        }
      })
      .catch((err) => console.log(err));
  };
  console.log(htmlStr);

  return (
    <Container>
      <PostQ individualPost={individualPost}></PostQ>
      <PostC commentsForQ={commentsForQ}></PostC>
      <AHeader>
        <div>
          <Title>{answers.length} Answers</Title>
        </div>
      </AHeader>
      {answers.map((answer) => (
        <div className="br">
          <PostA key={answer.id} answer={answer}></PostA>
          <PostComm
            key={answer.comments.id}
            commentsForA={answer.comments}
          ></PostComm>
        </div>
      ))}
      <PostAnswer>
        <div>Your Answer</div>
        <Toolbox
          contentRef={contentRef}
          setEmptyContentMsg={setEmptyContentMsg}
        ></Toolbox>
      </PostAnswer>
      <Button onClick={handlePostAnswer}>Post Your Answer</Button>
      {/* <Button>Post Your Answer</Button> */}
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

export default PostQAC;
