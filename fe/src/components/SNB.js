import styled from "styled-components";

const Nav = styled.nav`
    padding: 20px;
    border-right: 1px solid darkgray;
    flex-basis: 150px;
    flex-shrink: 1;
    height: 90vh;
`

const Menu = styled.div`
    > * {
        margin-bottom: 5px;
    }

    /* Sub Menu */
    > div:not(:first-child) {
        margin-left: 30px;
    }
`

const SNB = () => {

    const menus = [
        { name: "Home", path: "", submenus: [] },
        {
            name: "PUBLIC", path: "", submenus: [
                { name: "Questions", path: "" },
                { name: "Users", path: "" }
            ]
        }
    ]

    //path will be added later with { NavLink }'s "to" attribute
    return (
        <Nav>
            {menus.map((menu, idx) => {
                return (
                    <Menu key={idx}>
                        <div >{menu.name}</div>

                        {menu.submenus.map((sub, index) => {
                            return (
                                <div key={index}>{sub.name}</div>
                            )
                        })}
                    </Menu>
                )
            })}
        </Nav>
    )
}

export default SNB;