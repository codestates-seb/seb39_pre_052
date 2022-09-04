import styled from "styled-components";

import { faQ } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const MyQ = () => {

    return (
        <Container>
            <FontAwesomeIcon icon={faQ} />
            <div>Question Title</div>
        </Container>
    )
};

const Container = styled.div`
    height: 50px;
    display: flex;
    flex-direction: row;
    align-items: center;
    border-bottom: 1px solid darkgray;

    // Q
    > *:first-child {
        padding: 0 36px;
        color: #F5862A;
    }
`

export default MyQ;