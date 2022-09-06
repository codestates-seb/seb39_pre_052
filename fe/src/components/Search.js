import styled from 'styled-components';
import { useSelector, useDispatch } from 'react-redux';
import { useState } from 'react';
import { useNavigate, useSearchParams, useParams } from 'react-router-dom';
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import { setPosts } from '../features/qListSlice';
import { setQuery } from '../features/searchSlice';

const Search = () => {
    const [searchParams, setSearchParams] = useSearchParams();

    const searchTerm = searchParams.get('keyword');

    // Search Value
    // const [ query, setQuery ] = useState("");

    const query = useSelector((state) => {
        return state.queryData.query;
    });
    
    const dispatch = useDispatch();
    const navigate = useNavigate();

    // LOGIN STATE
    const isLoggedIn = useSelector(state => {
        return state.user.isLoggedIn;
    })
    
    const handleChange = (e) => {
        // setQuery(e.target.value);
        setSearchParams({keyword: e.target.value});
        dispatch(setQuery({query: e.target.value}))
    }

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            fetch(`/api/search?keyword=${searchTerm}&page=1&size=50`)
            .then((res) => res.json())
            .then((data) => {
                dispatch(setPosts({posts: data.data, total: data.pageInfo.totalElements}));
                // setQuery("");
                dispatch(setQuery({query: ""}));
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
                    <FontAwesomeIcon icon={faMagnifyingGlass} />
                    <input placeholder="Search by Title..."
                        onChange={handleChange}
                        onKeyPress={handleKeyPress}
                        value={query}
                    ></input>
                </SearchBar>
                :
                <SearchBar>
                    <FontAwesomeIcon icon={faMagnifyingGlass} />
                    <input placeholder="Search by Title..."
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
    > *:first-child {
        padding: 10px;
        background-color: white;
        color: #838C95;
        font-size: 20px;
    }
    > input {
        border: none;
        width: ${props => props.width || "50vw"};
        height: 36px;
        padding: 5px;
        font-size: 16px;
    }
    border: 1px solid #9d9fa0;
    border-radius: 5px;
    width: ${props => props.width || "50vw"};
    margin-right: 10px;
`

export default Search;