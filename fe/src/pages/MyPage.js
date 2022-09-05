import styled from "styled-components";
import { useState, useEffect } from "react";
import MyQ from "../components/MyQ";
import MyA from "../components/MyA";
import MyPagination from "../components/MyPagination";

import { useSelector } from "react-redux";

import { faCakeCandles } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const MyPage = () => {
    const [myQ, setMyQ] = useState([]);
    const [myA, setMyA] = useState([]);

    const token = localStorage.getItem("access-token");
    const userId = localStorage.getItem("userId");
    const userName = localStorage.getItem("memberName");
    const joinDate = localStorage.getItem("createdAt");

    // Pagination
    const [qTotal, setQTotal] = useState(null);
    const [aTotal, setATotal] = useState(null);
    const [limit, setLimit] = useState(11);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    // // 내 정보 불러오기
    // useEffect(() => {
    //     fetch(`/api/members/me`, {
    //         headers: {
    //             'Accept': 'application/json, text/plain',
    //             'Content-Type': 'application/json;charset=UTF-8',
    //             "Authorization": token,
    //         },
    //     })
    //     .then((res) => res.json())
    //     .then((data) => {
    //         setUserName(data.data.memberName);
    //         setJoinDate(data.data.createdAt);
    //     })
    //     .catch((err) => console.log(`!CANNOT FETCH USER DATA! ${err}!`))
    // }, []);

    // 내 질문 답변 불러오기
    useEffect(() => {
        fetch(`/api/members/${userId}/questions`)
        .then((res) => res.json())
        .then((data) => {
            setMyQ(data.data); 
            setQTotal(data.pageInfo.totalElements);
        })
        .catch((err) => console.log(`!CANNOT FETCH QUESTION DATA! ${err}!`))

        fetch(`/api/members/${userId}/answers`)
        .then((res) => res.json())
        .then((data) => {
            setMyA(data.data); 
            setATotal(data.pageInfo.totalElements);
        })
        .catch((err) => console.log(`!CANNOT FETCH ANSWER DATA! ${err}!`))
    }, [userId]);

    // shows the time since a certain time point
    function timeSince (date) {
    
        let seconds = Math.floor((new Date() - date) / 1000);
    
        let interval = seconds / 31536000;
    
        if (interval > 1) {
            return Math.floor(interval) + " years";
        }
        interval = seconds / 2592000;
        if (interval > 1) {
            return Math.floor(interval) + " months";
        }
        interval = seconds / 86400;
        if (interval > 1) {
            return Math.floor(interval) + " days";
        }
        interval = seconds / 3600;
        if (interval > 1) {
            return Math.floor(interval) + " hours";
        }
        interval = seconds / 60;
        if (interval > 1) {
            return Math.floor(interval) + " minutes";
        }
        return Math.floor(seconds) + " seconds";
    };

    let num = Date.parse(joinDate);

    return (
        <Container>
            <Profile>
                <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="profile"/>
                <div>
                    <div>{userName}</div>
                    <div>
                        <FontAwesomeIcon icon={faCakeCandles} />
                        <div>&nbsp; Member for&nbsp;{timeSince(num)}</div>
                    </div>
                </div>
            </Profile>
            <Body>
                <MyQuestions>
                    <div>My Questions</div>
                    {/* <MyQ></MyQ> */}
                    <List>
                        {myQ.slice(offset, offset + limit).map((post, idx) => {
                            return <MyQ key={post.id} qTitle={post.title} id={post.id}></MyQ>
                        })}
                    </List>
                    <MyPagination
                        total={qTotal}
                        limit={limit}
                        page={page}
                        setPage={setPage}
                        setLimit={setLimit}
                    />
                </MyQuestions>
                <MyAnswers>
                    <div>My Answers</div>
                    {/* <MyA></MyA> */}
                    <List>
                        {myA.map((post, idx) => {
                            return <MyA key={post.id} aContent={post.question.title} id={post.question.id}></MyA>
                        })}
                    </List>
                    <MyPagination
                        total={aTotal}
                        limit={limit}
                        page={page}
                        setPage={setPage}
                        setLimit={setLimit}
                    />
                </MyAnswers>
            </Body>

        </Container>
    )
}

const Container = styled.div`
  flex-basis: 100vw;
  flex-shrink: 6;
  padding: 50px 100px;
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
        margin-top: 10px;
        color: #74777a;
        font-weight: 500;
        display: flex;
        align-items: center;
    }
`

const Body = styled.div`
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    justify-content: space-around;
`

const MyQuestions = styled.div`
    display: flex;
    flex-direction: column;
    width: 40vw;
    margin-right: 20px;
    > div:first-of-type {
        font-size: 24px;
        font-weight: bold;
        margin: 20px 0 14px 0;
    }
    > *:nth-child(2) {
        border: 1px solid darkgray;
        border-bottom: 0px;
        border-radius: 10px;
    }
`

const MyAnswers = styled.div`
    display: flex;
    flex-direction: column;
    width: 40vw;
    margin-left: 20px;
    justify-content: flex-start;

    > div:first-of-type {
        font-size: 24px;
        font-weight: bold;
        margin: 20px 0 14px 0;
    }
    > *:nth-child(2) {
        border: 1px solid darkgray;
        border-bottom: 0px;
        border-radius: 10px;
    }

`

const List = styled.ul`

`

export default MyPage;