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
            name: "PUBLIC", path: "", submenus: [
                { name: "Questions", path: "" },
                { name: "Users", path: "" }
            ]
        }
    ]

    return (
        <Nav>
            {menus.map((menu, idx) => {
                if (menu.submenus !== undefined) {
                    menu.submenus.map((sub, idx) => {
                        return (
                            <>
                                <div>{sub.name}</div>
                                <div>WORKED!</div>
                            </>
                        )
                    })
                }
                return (
                    <>
                        <div>didn't work</div>
                        <div>{menus[1].submenus[0].name}no</div>
                    </>
                )
                // return (
                //     <>
                //         <div>{menu.name}</div>
                //     </>
                // )
            })}
        </Nav>
    )
}

export default SNB;