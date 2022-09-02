import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import PostA from "./PostA";
import PostC from "./PostC";
import PostQ from "./PostQ";
import Toolbox from "./Toolbox";

const PostQAC = () => {
  const { id } = useParams();
  const [individualPost, setIndividualPost] = useState([]);
  const [commentsForQ, setCommentsForQ] = useState([]);
  const [answers, setAnswers] = useState([]);
  const [commentsForA, setCommentsForA] = useState([]);

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
        setIndividualPost(data.data);
        // setAnswers(data.data.answers);
      })
      .catch((err) => console.log("cannot fetch individual question data"));
  }, []);
  console.log("individualPost", individualPost);
  //{id: 1, title: 'Test Question', content: 'Test Question Content', askedAt: '2022-09-02T07:31:25.340465', view: 1111, …}
  // console.log("answers", answers);
  return (
    <Container>
      <PostQ individualPost={individualPost}></PostQ>
      <PostC></PostC>
      <PostA></PostA>
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
`;

const PostAnswer = styled.div`
  border-top: 1px solid darkgray;
`;

const Button = styled.button`
  width: 128px;
  height: 38px;
`;

export default PostQAC;
