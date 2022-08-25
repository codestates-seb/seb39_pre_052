import dummy from '../dummy';
import Question from './Question';
import { useState } from 'react';
import Pagination from './Pagination';
import styled from "styled-components";

const List = styled.div`
    height: 80vh;
    overflow-y: scroll;
`

const Questions = () => {
    const [limit, setLimit] = useState(5);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;


    // const [posts, setPosts] = useState([]);
    // useEffect(() => {
    //     fetch("https://jsonplaceholder.typicode.com/posts")
    //         .then((res) => res.json())
    //         .then((data) => setPosts(data));
    // }, []);
    

    return (
        <>
            <div>
                All Questions
            </div>
            <div>
                {dummy.length} questions
            </div>
            <List>
                {dummy.slice(offset, offset + limit).map((post, idx) => {
                    return <Question key={idx} post={post}></Question>
                })}
            </List>
            <Pagination 
                total={dummy.length}
                limit={limit}
                page={page}
                setPage={setPage}
                setLimit={setLimit}
            />
        </>
    )
};

export default Questions;

