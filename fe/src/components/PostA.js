import styled from "styled-components";
import dummy from "../dummy";
import Pagination from "./Pagination";

const PostA = () => {
  return (
    <>
      <AHeader>
        <div>
          <Title>{dummy.length} Answers</Title>
          {/* <Button>Ask Question</Button> */}
        </div>
        <Pagination total={10} limit={5} page={1}></Pagination>
      </AHeader>
      <Post>
        <Votecell>{dummy[0].voteNum}</Votecell>
        <Postcell>
          <Content>{dummy[0].content}</Content>
          <UserContent>
            <div className="edit">
              <div>Share</div>
              <div>Edit</div>
              <div>Follow</div>
            </div>
            <div className="userinfo">
              <div>
                <div>answered </div>
                <div>img</div>
              </div>
              <div>
                <span>{dummy[0].time}</span>
                <div>{dummy[0].userId}</div>
              </div>
            </div>
          </UserContent>
        </Postcell>
      </Post>
    </>
  );
};

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

// const Button = styled.button`
//   width: 100px;
//   height: 38px;
// `;

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
    }
  }
  > div.userinfo {
    flex-basis: 40%;
    /* background-color: #d9eaf7; */ //answered엔 없음
    border: solid 1px; //임시
    border-radius: 5px;
    display: flex;
    flex-wrap: wrap;
  }
`;

export default PostA;
