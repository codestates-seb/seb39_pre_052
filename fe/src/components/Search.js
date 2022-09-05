import styled from 'styled-components';
import { useSelector, useDispatch } from 'react-redux';
import { useState } from 'react';
import { useNavigate, useSearchParams, useParams } from 'react-router-dom';

import { setPosts } from '../features/qListSlice';

const Search = () => {
    const [searchParams, setSearchParams] = useSearchParams();

    const searchTerm = searchParams.get('keyword');

    // Search Value
    const [ query, setQuery ] = useState("");
    
    const dispatch = useDispatch();
    const navigate = useNavigate();

    // LOGIN STATE
    const isLoggedIn = useSelector(state => {
        return state.user.isLoggedIn;
    })
    
    const handleChange = (e) => {
        setQuery(e.target.value);
        setSearchParams({keyword: e.target.value});
    }

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            fetch(`/api/search?keyword=${searchTerm}&page=1&size=50`)
            .then((res) => res.json())
            .then((data) => {
                dispatch(setPosts({posts: data.data, total: data.pageInfo.totalElements}));
                setQuery("");
                navigate(`/search`);
            })
            .catch((err) => console.log(`!CANNOT FETCH QUESTION DATA! ${err}!`))
        }
    }

    return (
        <>
            {isLoggedIn
                ?
                <SearchBar width="61vw">
                    <input placeholder="Search..."
                        onChange={handleChange}
                        onKeyPress={handleKeyPress}
                        value={query}
                    ></input>
                </SearchBar>
                :
                <SearchBar>
                    <input placeholder="Search..."
                        onChange={handleChange}
                        onKeyPress={handleKeyPress}
                        value={query}
                    ></input>
                </SearchBar>
            }
        </>
    )
};

const SearchBar = styled.div`
    > input {
        border: none;
        width: ${props => props.width || "50vw"};
        height: 36px;
        padding: 5px;
    }
    border: 1px solid #9d9fa0;
    border-radius: 5px;
    width: ${props => props.width || "50vw"};
    margin-right: 10px;
`

export default Search;