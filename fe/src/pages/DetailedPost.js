import { useEffect, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { questionDetails } from "../features/questionSlice";
import { setQuestionId } from "../features/textEditSlice";
import styled from "styled-components";

const DetailedPost = () => {

    // 파라미터 id 받아오기
    const { id } = useParams()
    const dispatch = useDispatch();
    const [ qData, setQData ] = useState({});
    
    const questionId = useSelector((state) => {
        return state.editMode.questionId;
    });
    
    //Fetch 질문 세부 내용 조회하기
    useEffect(() => {
        fetch(`/api/questions/${id}`)
            // fetch(`/api/questions/1`)
            .then((res) => res.json())
            .then((data) => {
                console.log(data.data);
                dispatch(questionDetails({ question: data.data }));
                setQData(data.data)
                //redux 대신 상태로
            //     setDataid(data.data.id)
            //     setDatatitle(data.data.title)
            //     setDatacontent(data.data.content)
            //     setDataaskedAt(data.data.askedAt)
            //     setDataview(data.data.view)
            //     setDatavote(data.data.vote)
            //     setDatamember(data.data.member)
        })
        .catch((err) => console.log("cannot fetch individual question data"));
    }, [id, dispatch]);
    
    
        const questionData = useSelector((state) => {
            return state.question.question;
        });

        console.log(`questionData: `, questionData)
    

    return (
        <Container>
            <QHeader>
                <div>
                    <Title>{questionData.title}</Title>
                    <Button>Ask Question</Button>
                </div>
                <TitleInfo>
                    <div>Asked {questionData.askedAt}</div>
                    <div> Viewed {questionData.view} times</div>
                </TitleInfo>
            </QHeader>
            <Post>
                <Votecell>{questionData.vote}</Votecell>
                <Postcell>
                    <Content>{questionData.content}</Content>
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
                            </div>
                        </div>
                        <div>
                            <span>{questionData.askedAt}</span>
                            {/* <div>{questionData.member.name}</div> */}
                        </div>
                    </UserContent>
                </Postcell>
            </Post>

            <div>
                This is Question {id} &nbsp;
                Usestate {qData.title} &nbsp;
                Redux {questionData.title}
                {/* Redux {questionData.member} */}
            </div>

        </Container>
    )
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

const Title = styled.div`
  flex-basis: 70%;
  font-size: 20px;
  font-weight: 500;
`;

const Button = styled.button`
  width: 128px;
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

export default DetailedPost;