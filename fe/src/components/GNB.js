import styled from "styled-components";
import { faBars, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";

// redux toolkit related
import { changeShow } from '../features/showSlice';
import { useSelector, useDispatch } from 'react-redux';

const Nav = styled.nav`
    display: flex;
    border-bottom: 1px solid darkgrey;
    padding: 10px 10px 10px 20px;
    align-items: center;

    > * {
        display: flex;
        align-items: center;
    }
    // menu button
    > div:first-of-type {
        display: flex;
        justify-content: center;
        padding: 5px;
        width: 40px;
        font-size: 20px;
    }
    // logo
    > img {
        max-width: 150px;
        max-height: 38px;
        margin-left: 20px;
        margin-right: 20px;
    }
    > ul {
        > * {
            margin-right: 10px;
            list-style: none;
        }
    }
    // search bar
    > div:nth-of-type(2) {
        > input {
            border: none;
        }
        border: 1px solid black;
        width: 50vw;
        margin-right: 10px;
    }
`

const Button = styled.button`
    margin-right: 10px;
    padding: 5px;
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
            <div
                onClick={() => dispatch(changeShow())}
            >
                {show ? <FontAwesomeIcon icon={faXmark} /> : <FontAwesomeIcon icon={faBars} />}
            </div>
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Stack_Overflow_logo.svg/2560px-Stack_Overflow_logo.svg.png" alt="logo"></img>
            <ul>
                <li> { }
                    About
                </li>
                <li>
                    Products
                </li>
                <li> {/* only when Logged out*/}
                    For Teams
                </li>
            </ul>
            <div>
                <input placeholder="Search..."></input>
            </div>
            <Button>Log in</Button>
            <Button>Sign up</Button>
        </Nav>
    )
};

export default GNB;