import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'
import styled from "styled-components";
import Question from '../components/Question';
import Pagination from '../components/Pagination';

const Questions = () => {
    const [qNum, setQNum] = useState("");

    const [posts, setPosts] = useState([]);
    const [total, setTotal] = useState(0);
    const [limit, setLimit] = useState(5);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

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
            .then((data) => {setPosts(data.data); setTotal(data.pageInfo.totalElements);})
            .catch((err) => console.log(`!CANNOT FETCH QUESTION DATA! ${err}!`))
    }, [page, limit]);

    const deleteHandler = () => {
        fetch(`/api/questions/${posts[qNum-1].id}`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsb2dpbiBqd3QgdG9rZW4iLCJleHAiOjE2NjE5OTc2NjUsImVtYWlsIjoic3VqaW5AZ21haWwuY29tIn0.G6EeCQsL3rx5SCZZzK4DCLIA1dcsNwb-XyN1lLgJkGMdDEWr7m3UiMXCEmfDYhcXr_yP9IWPid0FlvGZ3ieUkg",
            },
        })
        .then(res => {console.log(res); window.location.reload();})
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
                    <Link to="/questions/ask">
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
                    return <Question key={idx} post={post}></Question>
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
    height: 90vh;
`

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
    > div:first-of-type > div:nth-of-type(2)  {
        margin: 0 0 10px 30px;
    }
`
//Ask Question Button
const Button = styled.button`
    background-color: #0A95FF;
    border: none;
    padding: 15px;
    margin-right: 15px;
    color: white;
    font-size: 16px;
    font-weight: bold;

    :hover {
        background-color: #0074CC;
    }
`

const List = styled.div`
    overflow-y: scroll;
    max-height: 75vh;
`

export default Questions;

