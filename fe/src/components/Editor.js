import { useState } from "react";
import styled from "styled-components"
import { Markup } from 'interweave'; // react library to interpret html string to jsx

// import ReactQuill from 'react-quill';
// import 'react-quill/dist/quill.snow.css';
import Toolbox from "./Toolbox";

const Container = styled.div`
    flex-basis: 100vw;
    width: 65vw;
    flex-shrink: 6;
    display: flex;
    flex-direction: column;
    padding: 10px 50px 0 50px;
    > * {
        margin-top: 30px;
    }
    // Title & Body
    > * > h1 {
        font-size: 18px;
    }
    // p tag for Title and Body
    > * > p {
        font-size: 14px;
    }
    > * > * {
        margin-bottom: 5px;
    }
`

const Header = styled.div`
    > input {
        width: 100%;
        height: 34px;
        border: 1px solid #D1D1D1;
        padding-left: 10px; 
    }
`

const Suggestion = styled.div`
    display: none;
    border: 1px solid black;

`

const Body = styled.div`
    /* > textarea {
        width: 100%;
        height: 30vh;
    } */
`

const Button = styled.button`
    background-color: #0A95FF;
    border: none;
    padding: 15px;
    color: white;
    font-size: 16px;
    font-weight: bold;
    :hover {
        background-color: #0074CC;
    }
`

const Editor = () => {
    const [htmlStr, setHtmlStr] = useState('');

    return (
        <Container>
            <Header>
                <h1>Title</h1>
                <p>Be specific and imagine you’re asking a question to another person</p>
                <input type="text" placeholder="e.g. Is there an R function for finding the index of an element in a vector?"></input>
            </Header>
            <Suggestion>
                <div>Similar questions</div>
                <div>content</div>
            </Suggestion>
            <Body>
                <h1>Body</h1>
                <p>Include all the information someone would need to answer your question</p>
                {/* <ReactQuill theme="snow" value={value} onChange={setValue} /> */}
                <Toolbox htmlStr={htmlStr} setHtmlStr={setHtmlStr}></Toolbox>
            </Body>
            <Button>Submit</Button>
            <Markup content={htmlStr} />
        </Container>
    )
};

export default Editor;

