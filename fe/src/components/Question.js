import styled from 'styled-components';
import { Link } from 'react-router-dom'
import { Markup } from 'interweave'; // react library to interpret html string to jsx

import { useDispatch, useSelector } from 'react-redux';
import { setQuestionId } from '../features/textEditSlice';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';

const Question = ({ post, id }) => {

    const [isClicked, setIsClicked] = useState(false);

    // shows the time since a certain time point
    function timeSince (date) {
    
        let seconds = Math.floor((new Date() - date) / 1000);
    
        let interval = seconds / 31536000;
    
        if (interval > 1) {
            return Math.floor(interval) + " years";
        }
        interval = seconds / 2592000;
        if (interval > 1) {
            return Math.floor(interval) + " months";
        }
        interval = seconds / 86400;
        if (interval > 1) {
            return Math.floor(interval) + " days";
        }
        interval = seconds / 3600;
        if (interval > 1) {
            return Math.floor(interval) + " hours";
        }
        interval = seconds / 60;
        if (interval > 1) {
            return Math.floor(interval) + " minutes";
        }
        return Math.floor(seconds) + " seconds";
    };

    let num = Date.parse(post.createdAt);

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const questionId = useSelector((state) => {
        return state.editMode.questionId;
    });   

    const handleContentClick = () => {
        dispatch(setQuestionId({ questionId: id }));
    }

    return (
        <Block>
            {/* <div>{typeof post.createdAt}</div> */}
            <Side>
                <div>{post.vote} votes</div>
                <div>{post.answerNum} answers</div>
                <div>{post.view} views</div>
            </Side>
            <Main>
                <Link to={`/questions/${id}`}>
                    <div onClick={handleContentClick}>{post.title}</div>
                </Link>
                <Link to={`/questions/${id}`}>
                    <Markup content={post.content} onClick={handleContentClick} />
                </Link>
                <div>
                    <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="profile"></img>
                    <div>{post.member.memberName}</div>
                    <div>asked {timeSince(num)} ago</div>
                </div>
            </Main>
        </Block>
    )
};


const Block = styled.div`
    color: #5d5e60;
    display: flex;
    flex-direction: row;
    padding-top: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid darkgray;

    :first-child {
        border-top: 1px solid darkgray;
    }
`

const Side = styled.div`
    display: flex;
    flex-direction: column;
    width: 15vw;
    align-items: flex-end;
    margin-right: 20px;
    > * {
        margin-bottom: 8px;
    }
    // n votes
    > div:first-of-type {
        color: black;
    }
`

const Main = styled.div`
    display: flex;
    flex-direction: column;
    width: 80vw;
    margin-right: 45px;

    > * {
        overflow: hidden;
        padding-bottom: 10px;
        text-decoration: none;
    }
    // Title
    > *:first-child > div:first-child {
        font-weight: 600;
        color: #0074cc;
        font-size: 20px;
        :hover {
            color: #0a95ff;
        }
    }
    // Content
    > *:nth-child(2) > *:nth-child(1) {
        color: #5d5e60;
        :hover {
            color: #191919;
        }
    }
    > div:nth-child(3) {
        display: flex;
        > * {
            margin-right: 10px;
        }
        > img {
            width: 20px;
            border-radius: 3px;
        }
        // username
        > div:first-of-type{
            color: #0074cc;
            :hover {
                color: #0a95ff;
            }
        }
        align-self: flex-end;
    }
`


export default Question;