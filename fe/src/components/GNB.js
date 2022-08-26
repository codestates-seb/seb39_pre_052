import styled from "styled-components";

const Nav = styled.nav`
    display: flex;
    border-bottom: 1px solid darkgrey;
    padding: 10px;

    > * {
        display: flex;
        align-items: center;
    }
    > img {
        max-width: 150px;
        margin-left: 20px;
        margin-right: 20px;
    }
    > ul {
        > * {
            margin-right: 10px;
        }
    }
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
    return (
        <Nav>
            <div>menu</div>
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Stack_Overflow_logo.svg/2560px-Stack_Overflow_logo.svg.png" alt="logo"></img>
            <ul>
                <li> {/* only when Logged out*/}
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