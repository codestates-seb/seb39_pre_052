import { useState, useEffect } from 'react';
import Question from '../Components/Question';
import Pagination from '../Components/Pagination';
import styled from "styled-components";

const Container = styled.div`

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
        fetch("/test/question")
            .then((res) => res.json())
            .then((data) => setPosts(data))
            .catch((err) => console.log(`!ERROR: ${err}!`))
    }, []);
    

    return (
        <Container>
            <div>
                All Questions
            </div>
            <div>
                {posts.length} questions
            </div>
            <List>
                {posts.slice(offset, offset + limit).map((post, idx) => {
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

export default Questions;

