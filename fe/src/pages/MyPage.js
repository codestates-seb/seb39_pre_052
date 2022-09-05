import styled from "styled-components";
import { useState, useEffect } from "react";
import MyQ from "../components/MyQ";
import MyA from "../components/MyA";

import { faCakeCandles } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const MyPage = () => {
    const [userName, setUserName] = useState("");
    const [userId, setUserId] = useState(null);
    const [joinDate, setJoinDate] = useState("");
    const [myQ, setMyQ] = useState([]);
    const [myA, setMyA] = useState([]);

    useEffect(() => {
        fetch(`/api/mypage`)
        .then((res) => res.json())
        .then((data) => {
            setUserName(data.memberName);
            setUserId(data.memberId);
            setJoinDate(data.createdAt);
        })
        .catch((err) => console.log(`!CANNOT FETCH USER DATA! ${err}!`))
    }, []);

    useEffect(() => {
        fetch(`/api/members/${userId}/questions`)
        .then((res) => res.json())
        .then((data) => {
            setMyQ(data); 
        })
        .catch((err) => console.log(`!CANNOT FETCH QUESTION DATA! ${err}!`))

        fetch(`/api/members/${userId}/answers`)
        .then((res) => res.json())
        .then((data) => {
            setMyA(data); 
        })
        .catch((err) => console.log(`!CANNOT FETCH ANSWER DATA! ${err}!`))
    }, [userId]);

    return (
        <Container>
            <Profile>
                <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="profile"/>
                <div>
                    <div>{userName}</div>
                    <div>
                        <FontAwesomeIcon icon={faCakeCandles} />
                        <div>&nbsp; Member for 14 days</div>
                    </div>
                </div>
            </Profile>
            <MyQuestions>
                <div>My Questions</div>
                <MyQ></MyQ>
                {/* <List>
                    {myQ.map((post, idx) => {
                        return <MyQ key={post.id} qTitle={post.title}></MyQ>
                    })}
                </List> */}
            </MyQuestions>
            <MyAnswers>
                <div>My Answers</div>
                <MyA></MyA>
                {/* <List>
                    {myA.map((post, idx) => {
                        return <MyA key={post.id} aContent={post.content}></MyA>
                    })}
                </List> */}
            </MyAnswers>
            
        </Container>
    )
}

const Container = styled.div`
  flex-basis: 100vw;
  flex-shrink: 6;
  padding: 28px;
`;

const Profile = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
    padding-bottom: 28px;
    // Profile Image
    > img {
        width: 128px;
        border-radius: 10px;
        margin-right: 28px;
    }
    // UserName
    > div > div:first-child {
        font-size: 36px;
    }
    // JoinDate with an icon
    > div > div:nth-child(2) {
        color: #74777a;
        font-weight: 500;
        display: flex;
        align-items: center;
    }
`

const MyQuestions = styled.div`
    > div:first-of-type {
        font-size: 24px;
        font-weight: bold;
        margin: 20px 0 14px 0;
    }
    > *:nth-child(2) {
        border: 1px solid darkgray;
        border-radius: 10px;
    }
`

const MyAnswers = styled.div`
    > div:first-of-type {
        font-size: 24px;
        font-weight: bold;
        margin: 40px 0 14px 0;
    }
    > *:nth-child(2) {
        border: 1px solid darkgray;
        border-radius: 10px;
    }

`

const List = styled.ul`

`

export default MyPage;