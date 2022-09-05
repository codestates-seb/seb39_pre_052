import styled from 'styled-components';
import { useSelector, useDispatch } from 'react-redux';
import { useState } from 'react';

import { setPosts } from '../features/qListSlice';

const Search = () => {

    // Search Value
    const [ query, setQuery ] = useState("");

    // LOGIN STATE
    const isLoggedIn = useSelector(state => {
        return state.user.isLoggedIn;
    })
    
    const handleChange = (e) => {
        setQuery(e.target.value);
    }

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            fetch(`/api/search?q=${query}`)
            .then((res) => res.json())
            .then((data) => {
                console.log(data);
                // dispatch(setPosts({posts: data.data, total: data.pageInfo.totalElements})); 
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
                    ></input>
                </SearchBar>
                :
                <SearchBar>
                    <input placeholder="Search..."
                        onChange={handleChange}
                        onKeyPress={handleKeyPress}
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