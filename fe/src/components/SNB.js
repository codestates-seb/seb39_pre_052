import styled from "styled-components";
import { NavLink } from 'react-router-dom' //similar to { Link } but you can add styling attributes to the rendered element

// redux toolkit related
import { changeShow } from '../features/showSlice';
import { useSelector } from 'react-redux';

const Nav = styled.nav`
    padding: 20px 0 0 0px;
    border-right: 1px solid darkgray;
    flex-basis: 200px;
    flex-shrink: 1;
    background-color: white;
    min-height: 80vh;

    > * {

    }
    > div > * > * {

        :link {
            font-weight: bold;
        }
        /* color: #6b6b6b; */
    }
    > div:nth-of-type(1) {
        > * {
            text-decoration: none;
}
        // Home
        > *:nth-child(1) > div:first-of-type {
            margin-bottom: 30px;
            font-size: 18px;
            position: fixed;

            z-index: 20;
            top: 120px;
            padding-left: 30px;

            :hover {
                color: #191919;
            }
        }
    }
    > div:nth-of-type(2) {
        > * {
            text-decoration: none;
            /* color: #2b2b2b; */
        }
        // PUBLIC
        > *:nth-child(1) > div:first-of-type {
            margin-bottom: 10px;
            padding-left: 30px;
            font-size: 17px;
            cursor: default;
            font-weight: normal;
            position: fixed;
            top: 180px;
            color: #5d5e60;
        }
        //Questions
        > *:nth-child(2) > div {
            padding-left: 70px;
            margin-bottom: 10px;
            position: fixed;
            top: 220px;
            :hover {
                color: #191919;
            }
        }
        //Users
        > *:nth-child(3) > div {
            padding-left: 70px;
            margin-bottom: 10px;
            position: fixed;
            top: 250px;
            :hover {
                color: #191919;
            }
        }
    }
`

const Public = styled.div`
    margin-bottom: 10px;
    padding-left: 30px;
    font-size: 17px;
    cursor: default;
    font-weight: normal;
    position: fixed;
    top: 180px;
    color: #5d5e60;
`

const SNB = () => {

    // retrieves the state back from redux store(slice)
    const show = useSelector(state => {
        return state.toggle.active;
    })

    const menus = [
        { name: "Home", path: "/", position: "120px", submenus: [] },
        {
            name: "PUBLIC", position: "120px", submenus: [
                { name: "Questions", position: "220px", path: "/questions" },
                { name: "Search", position: "250px", path: "/search" }
            ]
        }
    ]

    //path will be added later with { NavLink }'s "to" attribute
    return (
        <Nav hidden={!show}> {/* the nav bar will be shown/hidden when GNB menu icon is clicked */}
            {menus.map((menu, idx) => {
                return (
                    <div key={idx}>
                        {menu.path
                            ? <NavLink
                                to={menu.path}
                                style={({ isActive }) => ({
                                    fontWeight: isActive ? 'bold' : null,
                                    color: isActive ? '#191919' : '#5d5e60',
                                    textShadow: isActive ? '0px 0px 5px rgba(109,109,109,0.4)' : "",
                                    borderRight: isActive ? '5px solid #F48225' : "",
                                    position: "fixed",
                                    top: menu.position,
                                    width: '196px',
                                    height: '24px',
                                })}>
                                <div >{menu.name}</div>
                            </NavLink>
                            : <Public>{menu.name}</Public>
                        }
                        {menu.submenus.map((sub, index) => {
                            return (
                                <NavLink to={sub.path} key={index} style={({ isActive }) => ({
                                    fontWeight: isActive ? 'bold' : null,
                                    color: isActive ? '#191919' : '#5d5e60',
                                    textShadow: isActive ? '0px 0px 5px rgba(109,109,109,0.3)' : "",
                                    borderRight: isActive ? '5px solid #F48225' : "",
                                    position: "fixed",
                                    top: sub.position,
                                    width: '196px',
                                    height: '24px',
                                })}>
                                    <div>{sub.name}</div>
                                </NavLink>
                            )
                        })}
                    </ div>
                    
                )
            })}
        </Nav>
    )
}

export default SNB;