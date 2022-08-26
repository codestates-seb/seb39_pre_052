import dummy from '../dummy';
import Question from './Question';
import { useState, useEffect } from 'react';
import Pagination from './Pagination';
import styled from "styled-components";

const List = styled.div`
    height: 80vh;
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
        <>
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
        </>
    )
};

export default Questions;

