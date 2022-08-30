import { useState, useEffect } from 'react';
import styled from "styled-components";
import Question from '../components/Question';
import Pagination from '../components/Pagination';

const Container = styled.div`
    flex-basis: 100vw; 
    flex-shrink: 6;
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
    max-height: 80vh;
    overflow-y: scroll;
`

const Questions = () => {
    const [posts, setPosts] = useState([]);
    const [limit, setLimit] = useState(5);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;


    useEffect(() => {
        fetch(`/test/question?size=${limit}&page=${page}`)
            .then((res) => res.json())
            .then((data) => setPosts(data.data))
            .catch((err) => console.log(`!CANNOT FETCH QUESTION DATA! ${err}!`))
    }, [page, limit]);
    

    return (
        <Container>
            <Header>
                <div>
                    <div>All Questions</div>
                    <div>{posts.length} questions</div>
                </div>
                <div>
                    <Button>Ask Question</Button>
                </div>
            </Header>
            {/* The below is for TEST DATA, slicing data from client side*/}
            <List>
                {posts.slice(offset, offset + limit).map((post, idx) => {
                    return <Question key={idx} post={post}></Question>
                })}
            </List>
            {/* The below is for MAIN DATA, server side will send sliced data*/}
            {/* <List>
                {posts.map((post, idx) => {
                    return <Question key={idx} post={post}></Question>
                })}
            </List> */}
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

export default Questions;

