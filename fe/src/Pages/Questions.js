import { useState, useEffect } from 'react';
import Question from '../Components/Question';
import Pagination from '../Components/Pagination';
import styled from "styled-components";

const Container = styled.div`
    flex-basis: 800px; 
    flex-shrink: 6;
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
            <div>
                All Questions
            </div>
            <div>
                {posts.length} questions
            </div>
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

