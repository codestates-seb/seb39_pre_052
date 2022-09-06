import styled from "styled-components";
import { Link } from "react-router-dom";
import { useState } from 'react';

import { faQ } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const MyQ = ( {qTitle, id} ) => {

    const [isHovering, setIsHovering] = useState(false);

    const handleMouseEnter = () => {
        setIsHovering(true);
    };
    
      const handleMouseLeave = () => {
        setIsHovering(false);
    };

    const style = {
        textDecoration: isHovering? "underline": "none",
        color: "#35383a",
        fontWeight: isHovering? "bold": "normal",
    }

    return (
        <Container>
            <FontAwesomeIcon icon={faQ} />
            <Link
                to={`/questions/${id}`}
                style={style}
                onMouseEnter={handleMouseEnter}
                onMouseLeave={handleMouseLeave}
            >
                <div>{qTitle}</div>
            </Link>
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