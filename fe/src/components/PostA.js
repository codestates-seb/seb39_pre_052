import styled from "styled-components";

const PostA = ({ answer }) => {
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
  return (
    <>
      <Post>
        <Votecell>{answer.vote}</Votecell>
        <Postcell>
          <Content>{answer.content}</Content>
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
                <span>{fullDateFormat}</span>
                <div>{answer.member.name}</div>
              </div>
            </div>
          </UserContent>
        </Postcell>
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
    }
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
