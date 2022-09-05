import styled from "styled-components";

import { faA } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const MyA = ( {aContent} ) => {

    return (
        <Container>
            <FontAwesomeIcon icon={faA} />
            <div>{aContent}</div>
        </Container>
    )
};

const Container = styled.div`
    height: 50px;
    display: flex;
    flex-direction: row;
    align-items: center;
    border-bottom: 1px solid darkgray;

    // A
    > *:first-child {
        padding: 0 36px;
        color: #F5862A;
    }
`

export default MyA;