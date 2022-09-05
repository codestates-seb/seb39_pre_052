import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'
import { useNavigate, useParams } from 'react-router-dom';
import { useSelector, useDispatch } from "react-redux";
import styled from "styled-components";
import Question from "../components/Question";
import Pagination from "../components/Pagination";

import { setPosts } from "../features/qListSlice";

const SearchResult = () => {
    const params = useParams();
    console.log(`params: `, params);

    const [qNum, setQNum] = useState("");

    const [limit, setLimit] = useState(5);
    const [page, setPage] = useState(1);
    const [isDeleted, setIsDeleted] = useState(false);

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

    return (
        <Container>
            <Header>
                <div>
                    <div>Search Results</div>
                    <div>{total} questions</div>
                </div>
                <div>
                    <Link to={isLoggedIn? "/questions/ask" : "/login"}>
                        <Button>Ask Question</Button>
                    </Link>
                </div>
            </Header>
            <List>
                {posts.map((post, idx) => {
                    return <Question key={post.id} post={post} id={post.id}></Question>
                })}
            </List>
            <Pagination
                total={total}
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

export default SearchResult;
