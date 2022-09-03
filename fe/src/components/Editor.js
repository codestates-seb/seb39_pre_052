import styled from "styled-components"
import { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from "react-redux";
import { setTitle, setIsTitleEmpty, setQuestionId } from "../features/textEditSlice";

import Toolbox from "./Toolbox";

const Editor = ({ fetchMode }) => {
    // Empty Input Handling State
    const [emptyTitleMsg, setEmptyTitleMsg] = useState("");
    const [emptyContentMsg, setEmptyContentMsg] = useState("");

    const navigate = useNavigate();
    const dispatch = useDispatch();

    // To focus certain empty input box
    const titleRef = useRef();
    const contentRef = useRef();

    // Take token, title, htmlStr states from redux slices
    const token = useSelector((state) => {
        return state.user.userToken
    });
    const title = useSelector((state) => {
        return state.editMode.title;
    });
    const htmlStr = useSelector((state) => {
        return state.editMode.htmlStr;
    });
    const questionId = useSelector((state) => {
        return state.editMode.questionId;
    });

    const handleTitleInput = (e) => {
        dispatch(setTitle({ title: e.target.value }));

        // The red message disappears when input is entered
        if (title.length < 0) {
            dispatch(setIsTitleEmpty({ isTitleEmpty: true }));
            setEmptyTitleMsg("Title is missing.");
        }
        else {
            dispatch(setIsTitleEmpty({ isTitleEmpty: false }));
            setEmptyTitleMsg("");
        }
    }

    const handleSubmit = () => {
        // 유효성 검사
        if (title.length === 0) {
            // 제목, 컨텐츠 둘다 비었을 때
            if (htmlStr === '<p><br></p>' || htmlStr === '<p></p>' || htmlStr.length === 0) {
                titleRef.current.focus();
                setEmptyTitleMsg("Title is missing.");
                setEmptyContentMsg("Body is missing.");
            }
            // 제목만 비었을 때
            else {
                titleRef.current.focus();
                setEmptyTitleMsg("Title is missing.");
                setEmptyContentMsg("");
            }
        }
        // 컨텐츠만 비었을 때
        else if (htmlStr === '<p><br></p>' || htmlStr === '<p></p>' || htmlStr.length === 0) {
            contentRef.current.focus();
            setEmptyContentMsg("Body is missing.")
            setEmptyTitleMsg("");
        }
        // 유효성 검사 통과
        else {
            // 에디터 사용 컴포넌트가 새 게시글 작성일 때
            if (fetchMode === "post") {
                fetch("/api/questions", {
                    method: "POST",
                    headers: {
                        'Accept': 'application/json, text/plain',
                        'Content-Type': 'application/json;charset=UTF-8',
                        "Authorization": token,
                    },
                    body: JSON.stringify({ title: title, content: htmlStr }),
                })
                .then((res) => {
                    if (res.status === 201) {
                        return res.json();
                    }
                })
                .then((json) => {
                    console.log(json.data.id)
                    alert(`Successfully Submitted!`)
                    dispatch(setQuestionId(json.data.id))
                    navigate(`/questions/${json.data.id}`);
                })
                .catch(() => console.log("ERROR!"))
            }
            // 에디터 사용 컴포넌트가 게시글 (Q) 수정일 때
            else if (fetchMode === 'put') {
                fetch(`/api/questions/${questionId}`, {
                    method: "PUT",
                    headers: {
                        'Accept': 'application/json, text/plain',
                        'Content-Type': 'application/json;charset=UTF-8',
                        "Authorization": token,
                    },
                    body: JSON.stringify({ title: title, content: htmlStr }),
                })
                .then((res) => {
                    if (res.status === 201) {
                        console.log(res);
                        alert(`Successfully Submitted!`)
                        // navigate(`/questions/${res.data.id}`); 
                        navigate(`/questions/1`); // 질문 상세 페이지로 변경 예정
                    }
                    // else if to be deleted
                    else if (res.status === 500) {
                        alert(`ERROR: check your token`)
                    }
                    else {
                        console.log(res);
                        alert(`ERROR: ${res.status}`)
                    }
                });
            }
        }
    }

    return (
        <Container>
            <Header>
                <h1>Title</h1>
                <p>Be specific and imagine you’re asking a question to another person</p>
                <input
                    type="text"
                    placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
                    onChange={handleTitleInput}
                    ref={titleRef}
                ></input>
                <Msg>{emptyTitleMsg}</Msg>
            </Header>
            <Suggestion>
                <div>Similar questions</div>
                <div>content</div>
            </Suggestion>
            <Body>
                <h1>Body</h1>
                <p>Include all the information someone would need to answer your question</p>
                {/* <ReactQuill theme="snow" value={value} onChange={setValue} /> */}
                <Toolbox
                    contentRef={contentRef}
                    setEmptyContentMsg={setEmptyContentMsg}
                ></Toolbox>
                <Msg>{emptyContentMsg}</Msg>
            </Body>
            <Button onClick={handleSubmit}>Submit</Button>
        </Container>
    )
};

// Styled Components
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

const Msg = styled.div`
    color: red;
`

const Button = styled.button`
    background-color: #0A95FF;
    border: none;
    border-radius: 8px;
    padding: 15px;
    color: white;
    font-size: 16px;
    font-weight: bold;
    :hover {
        background-color: #0074CC;
    }
`

export default Editor;

