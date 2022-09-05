import styled from "styled-components";
// import { useState } from "react";
import { faBars, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from 'react-router-dom'
import Search from "./Search";

// redux toolkit related
import { useSelector, useDispatch } from 'react-redux';
import { changeShow } from '../features/showSlice';
import { loginFulfilled, loginRejected, logoutFulfilled } from "../features/userSlice";

// sticky GNB
import { StickyNav } from 'react-js-stickynav'
import 'react-js-stickynav/dist/index.css'

const GNB = () => {

    // disable all console.log
    // console.log = function () { }

    // to set the state of data in redux store(slice)
    const dispatch = useDispatch();

    // LOGIN STATE
    const isLoggedIn = useSelector(state => {
        return state.user.isLoggedIn;
    })

    // LOGOUT HANDLER
    const handleLogout = () => {
        dispatch(logoutFulfilled());
        localStorage.removeItem("access-token");
    }

    // SNB STATE (retrieves the SNB state back from redux slice)
    const show = useSelector(state => {
        return state.toggle.active;
    })

    // sticky GNB
    const style = () => {
        return (
            <style jsx="true">{`
            .nav {
            transition: all 0.1s linear;
            position: fixed;
            z-index: 2000;
            background-color: white;
            width: 100vw;
            }
            .scrollNav {
            transition: all 0.5s ease-in;
            z-index: 2000;
            background: #ffffff;
            width: 100%;
            border-bottom: 1px solid #dddddd;
            }
            .styl {
            padding-top: 80px;
            }
        `}</style>
        )
    }


    return (
        <>
            {style()}
            <StickyNav>
                <Nav>
                    <div onClick={() => dispatch(changeShow())}>
                        {show ? <FontAwesomeIcon icon={faXmark} /> : <FontAwesomeIcon icon={faBars} />}
                    </div>
                    <Link to="/"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Stack_Overflow_logo.svg/2560px-Stack_Overflow_logo.svg.png" alt="logo" /></Link>
                    <ul>
                        {isLoggedIn
                            ? <>
                                <li>Products</li>
                            </>
                            : <>
                                <a href="https://stackoverflow.co/"><li>About</li></a>
                                <li>Products</li>
                                <a href="https://stackoverflow.co/teams/"><li>For Teams</li></a>
                            </>
                        }
                    </ul>
                    <Search />

                    {isLoggedIn
                        ? <>
                            <Link to="/mypage"><Profile><img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="profile" /></Profile></Link>
                            <Link to="/"><Button color="#d1ebff" border="1px solid #0074CC" text="#0074CC" hover="#9bd1f7" onClick={handleLogout}>Log out</Button></Link>
                        </>
                        : <>
                            <Link to="/login"><Button color="#d1ebff" border="1px solid #0074CC" text="#0074CC" hover="#9bd1f7" >Log in</Button></Link>
                            <Link to="/signup"><Button color="#0A95FF">Sign up</Button></Link>
                        </>
                    }
                </Nav>
            </StickyNav>
        </>
    )
};

// css

const Nav = styled.nav`
    overflow: hidden;
    display: flex;
    border-bottom: 1px solid darkgrey;
    padding: 10px 10px 10px 20px;
    align-items: center;
    justify-content: center;
    background-color: white;

    > * {
        display: flex;
        align-items: center;
        text-decoration: none;
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
            :hover {
                    color: #F48225;
            }
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
            padding: 5px;
            border-radius: 20px;
            margin-right: 15px;
            list-style: none;
            text-decoration: none;
            color: #5d5e60;
            :hover {
                color: #191919;
                background-color: #e1e4e8;
            }
        }
    }
`

const Button = styled.button`
    overflow: hidden;
    margin-right: 10px;
    padding: 5px 10px 5px 10px;
    height: 38px;
    background-color: ${props => props.color || 'black'};
    border: ${props => props.border || 'none'};
    color: ${props => props.text || 'white'};
    font-weight: bold;
    text-decoration: none;
    cursor: pointer;
    border-radius: 5px;

    > * {
        text-decoration: none;
    }

    :hover {
        background-color: ${props => props.hover || '#0074CC'};
    }
`

const Profile = styled.div`
    height: 38px;
    width: 38px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 10px;
    > img {
        width: 2.5em;
        border-radius: 5px;
    }

`

export default GNB;