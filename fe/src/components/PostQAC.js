import styled from "styled-components";
import PostA from "./PostA";
import PostC from "./PostC";
import PostQ from "./PostQ";

const PostQAC = () => {
  return (
    <Container>
      <PostQ></PostQ>
      <PostC></PostC>
      <PostA></PostA>
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

export default PostQAC;
