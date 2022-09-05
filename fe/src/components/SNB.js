import styled from "styled-components";
import { NavLink } from 'react-router-dom' //similar to { Link } but you can add styling attributes to the rendered element

// redux toolkit related
import { changeShow } from '../features/showSlice';
import { useSelector } from 'react-redux';

const Nav = styled.nav`
    padding: 20px 0 0 30px;
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
            /* color: #2b2b2b; */
        }
        // Home
        > *:nth-child(1) > div:first-of-type {
            margin-bottom: 30px;
            font-size: 20px;
            position: fixed;
            top: 120px;
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
            font-size: 19px;
            cursor: default;
            font-weight: normal;
            position: fixed;
            top: 180px;
            color: #5d5e60;
        }
        //Questions
        > *:nth-child(2) > div {
            padding-left: 40px;
            margin-bottom: 10px;
            position: fixed;
            top: 220px;
            :hover {
                color: #191919;
            }
        }
        //Users
        > *:nth-child(3) > div {
            padding-left: 40px;
            margin-bottom: 10px;
            position: fixed;
            top: 250px;
            :hover {
                color: #191919;
            }
        }
    }
`

const SNB = () => {

    // retrieves the state back from redux store(slice)
    const show = useSelector(state => {
        return state.toggle.active;
    })

    const menus = [
        { name: "Home", path: "/", submenus: [] },
        {
            name: "PUBLIC", path: "/", submenus: [
                { name: "Questions", path: "/questions" },
                { name: "Users", path: "/users" }
            ]
        }
    ]

    //path will be added later with { NavLink }'s "to" attribute
    return (
        <Nav hidden={!show}> {/* the nav bar will be shown/hidden when GNB menu icon is clicked */}
            {menus.map((menu, idx) => {
                return (
                    <div key={idx}>
                        <NavLink 
                            to={menu.path} 
                            style={({ isActive }) => ({ 
                                fontWeight: isActive ? 'bold' : null,
                                color: isActive? '#191919' : '#5d5e60',
                                })}>
                            <div >{menu.name}</div>
                        </NavLink>
                        {menu.submenus.map((sub, index) => {
                            return (
                                <NavLink to={sub.path} key={index} style={({ isActive }) => ({
                                    fontWeight: isActive ? 'bold' : null,
                                    color: isActive ? '#191919' : '#5d5e60',
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