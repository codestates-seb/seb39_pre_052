import styled from "styled-components";
import { faBars, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from 'react-router-dom'

// redux toolkit related
import { changeShow } from '../features/showSlice';
import { useSelector, useDispatch } from 'react-redux';

const Nav = styled.nav`
    display: flex;
    border-bottom: 1px solid darkgrey;
    padding: 10px 10px 10px 20px;
    align-items: center;
    justify-content: center;

    > * {
        display: flex;
        align-items: center;
    }
    // menu button
    > div:first-of-type {
        display: flex;
        justify-content: flex-start;
        text-align: center;
        width: 50px;
        height: 50px;
        cursor: pointer;
        > * {
            font-size: 24px;
            color: gray;
        }
    }
    // logo
    > * > img {
        max-width: 150px;
        max-height: 38px;
        margin-left: 20px;
        margin-right: 10px;
        padding-bottom: 6px;
    }
    > ul {
        padding-left: 5px;
        padding-right: 5px;
        > * {
            margin-right: 15px;
            list-style: none;
            text-decoration: none;
            color: #000;
        }
    }
    // search bar
    > div:nth-of-type(2) {
        > input {
            border: none;
            width: 50vw;
            height: 36px;
            padding: 5px;
        }
        border: 1px solid black;
        width: 50vw;
        margin-right: 10px;
    }
`

const Button = styled.button`
    margin-right: 10px;
    padding: 5px 10px 5px 10px;
    height: 38px;
    background-color: ${props => props.color || 'black'};
    border: ${props => props.border || 'none'};
    color: ${props => props.text || 'white'};
    font-weight: bold;
    text-decoration: none;
    cursor: pointer;

    :hover {
        background-color: ${props => props.hover || '#0074CC'};
    }
`

const GNB = () => {

    // to set the state of data in redux store(slice)
    const dispatch = useDispatch();
    // retrieves the state back from redux store(slice)
    const show = useSelector(state => {
      return state.toggle.active;
    })

    return (
        <Nav>
            <div onClick={() => dispatch(changeShow())}>
                {show ? <FontAwesomeIcon icon={faXmark} /> : <FontAwesomeIcon icon={faBars} />}
            </div>
            <Link to="/"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Stack_Overflow_logo.svg/2560px-Stack_Overflow_logo.svg.png" alt="logo"/></Link>
            <ul>
                {/* only when Logged out*/}
                <a href="https://stackoverflow.co/"><li>About</li></a>
                <li>Products</li>
                {/* only when Logged out*/}
                <a href="https://stackoverflow.co/teams/"><li>For Teams</li></a>
            </ul>
            <div>
                <input placeholder="Search..."></input>
            </div>
            <Link to="/"><Button color="#d1ebff" border="1px solid #0074CC" text="#0074CC" hover="#9bd1f7">Log in</Button></Link>
            <Link to="/"><Button color="#0A95FF">Sign up</Button></Link>
        </Nav>
    )
};

export default GNB;