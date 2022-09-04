import styled from "styled-components";
import dummy from "../dummy";
import Moment from "react-moment";

const PostQ = ({ individualPost }) => {
  /* answered Jul 28, 2011 at 22:22 으로 나타내기*/
  const datedata = new Date(individualPost.askedAt);
  // const datedata = new Date(dummy[0].time);
  // console.log(datedata); //2022-09-02T07:31:25.340465
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
      <QHeader>
        <div>
          <Title>{individualPost.title}</Title>
          <Button>Ask Question</Button>
        </div>
        <TitleInfo>
          <div>
            Asked <Moment fromNow>{datedata}</Moment>
          </div>
          <div>
            Modified <Moment fromNow>{datedata}</Moment>
          </div>
          <div> Viewed {individualPost.view} times</div>
        </TitleInfo>
      </QHeader>
      <Post>
        <Votecell>{individualPost.vote}</Votecell>
        <Postcell>
          <Content>{individualPost.content}</Content>
          <UserContent>
            <div className="edit">
              <div>Share</div>
              <div>Edit</div>
              {/* edit 페이지 연결하기 */}
              <div>Follow</div>
            </div>
            <div className="userinfo">
              <div>
                <div>asked </div>
                <div>
                  <img src={dummy[0].userUrl} alt="img" />
                </div>
              </div>
              <div>
                <span>{fullDateFormat}</span>
                <div>{individualPost.member.name}</div>
              </div>
            </div>
          </UserContent>
        </Postcell>
      </Post>
    </>
  );
};

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

const Title = styled.div`
  flex-basis: 70%;
  font-size: 27px;
  font-weight: 500;
`;

const Button = styled.button`
  width: 100px;
  height: 38px;
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

export default PostQ;
