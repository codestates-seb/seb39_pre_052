import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { questionDetails } from "../features/questionSlice";
import PostA from "./PostA";
import PostC from "./PostC";
import PostComm from "./PostComm";
import PostQ from "./PostQ";
import Toolbox from "./Toolbox";

const PostQAC = () => {
  const dispatch = useDispatch();
  const { id } = useParams();
  // const [individualPost, setIndividualPost] = useState([]);

  const [htmlStr, setHtmlStr] = useState(""); //Body (html string)
  // const handlePostAnswer = () => {
  //   fetch("answers?question-id=1", {
  //     method: "POST",
  //     headers: {
  //       Accept: "application/json, text/plain",
  //       "Content-Type": "application/json;charset=UTF-8",
  //     },
  //     body: JSON.stringify({ content: htmlStr }),
  //   });
  // };

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
        <Toolbox></Toolbox>
      </PostAnswer>
      {/* <Button onClick={handlePostAnswer}>Post Your Answer</Button> */}
      <Button>Post Your Answer</Button>
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
