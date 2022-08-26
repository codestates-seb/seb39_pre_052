import styled from "styled-components";

const Nav = styled.nav`
    padding-right: 20px;
    border-right: 1px solid darkgray;
    flex-basis: 150px;
    flex-shrink: 1;
`


const SNB = () => {

    const menus = [
        { name: "Home", path: "" },
        {
            name: "PUBLIC", path: "", children: [
                { name: "Questions", path: "" },
                { name: "Users", path: "" }
            ]
        }
    ]

    return (
        <Nav>
            {menus.map((menu, idx) => {
                 
                return (
                    <>
                        <div>{menu.name}</div>
                    </>
                )
            })}
        </Nav>
    )
}

export default SNB;