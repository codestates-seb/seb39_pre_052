import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from "react-redux";
import styled from "styled-components";
import Question from "../components/Question";
import Pagination from "../components/Pagination";

import { setPosts } from "../features/qListSlice";

const Questions = () => {
    const [qNum, setQNum] = useState("");

    const [limit, setLimit] = useState(5);
    const [page, setPage] = useState(1);
    const [isDeleted, setIsDeleted] = useState(false);
    const offset = (page - 1) * limit;

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const isLoggedIn = useSelector((state) => {
        return state.user.isLoggedIn;
    });

    const token = useSelector((state) => {
        return state.user.userToken;
    });

    const posts = useSelector((state) => {
        return state.qlist.posts;
    });

    const total = useSelector((state) => {
        return state.qlist.total;
    });

    // // TEST DATA
    // useEffect(() => {
    //     fetch(`/test/question?size=${limit}&page=${page}`)
    //         .then((res) => res.json())
    //         .then((data) => setPosts(data.data))
    //         .catch((err) => console.log(`!CANNOT FETCH QUESTION DATA! ${err}!`))
    // }, [page, limit]);
    
    useEffect(() => {
        fetch(`/api/questions?size=${limit}&page=${page}`)
            .then((res) => res.json())
            .then((data) => {
                dispatch(setPosts({posts: data.data, total: data.pageInfo.totalElements})); 
            })
            .catch((err) => console.log(`!CANNOT FETCH QUESTION DATA! ${err}!`))
    }, [page, limit, isDeleted, dispatch]);

    const deleteHandler = () => {
        fetch(`/api/questions/${posts[qNum-1].id}`, {
            method: "DELETE",
            headers: {
                "Authorization": token,
            },
        })
        .then(res => {console.log(res); /*window.location.reload();*/ setIsDeleted(!isDeleted)})
    }

    const handleClick = () => {
        console.log("hi")
    }

    return (
        <Container>
            <Header>
                <div>
                    <div>All Questions</div>
                    <div>{total} questions</div>
                </div>
                <div>
                    <input onChange={e => setQNum(e.target.value)} placeholder="Which question would you like to delete?" style={{width: "250px"}}></input>
                    <Button onClick={deleteHandler}>DELETE</Button>
                    <Link to={isLoggedIn? "/questions/ask" : "/login"}>
                        <Button>Ask Question</Button>
                    </Link>
                </div>
            </Header>
            {/* The below is for TEST DATA, slicing data from client side
            <List>
                {posts.slice(offset, offset + limit).map((post, idx) => {
                    return <Question key={idx} post={post}></Question>
                })}
            </List> */}
            {/* The below is for MAIN DATA, server side will send sliced data*/}
            <List>
                {posts.map((post, idx) => {
                    return <Question key={post.id} post={post} id={post.id}></Question>
                })}
            </List>
            <Pagination
                total={posts.length}
                limit={limit}
                page={page}
                setPage={setPage}
                setLimit={setLimit}
            />
        </Container>
    )
};

// Styled Components

const Container = styled.div`
  flex-basis: 100vw;
  flex-shrink: 6;
`;

const Header = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  //All Questions
  > div:first-of-type > div:first-of-type {
    font-size: 24px;
    font-weight: bold;
    margin: 10px 0 10px 30px;
  }
  // n questions
  > div:first-of-type > div:nth-of-type(2) {
    margin: 0 0 10px 30px;
  }
`;
//Ask Question Button
const Button = styled.button`
  background-color: #0a95ff;
  border: none;
  border-radius: 5px;
  padding: 15px;
  margin-right: 15px;
  color: white;
  font-size: 16px;
  font-weight: bold;

  :hover {
    background-color: #0074cc;
  }
`;

const List = styled.div`
    max-height: fit-content;

`;

export default Questions;
