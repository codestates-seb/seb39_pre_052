import styled from 'styled-components';

const Block = styled.div`
    display: flex;
    flex-direction: row;
    padding-top: 10px;
    padding-bottom: 10px;
    :not(:last-child) {
        border-bottom: 1px solid darkgray;
    }
`

const Side = styled.div`
    display: flex;
    flex-direction: column;
    width: 400px;
    align-items: flex-end;
    margin-right: 10px;
`
const Main = styled.div`
    display: flex;
    flex-direction: column;

    > * {
        overflow: hidden;
        padding-bottom: 10px;
    }
    > div:first-child {
        font-weight: bold;
    }
    > div:nth-child(3) {
        display: flex;
        > * {
            margin-right: 10px;
        }
        > img {
            width: 15px;
        }
    }
`


const Question = ( {post} ) => {
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

    let num = Date.parse(post.time);

    return (
        <Block>
            <Side>
                <div>{post.voteNum} votes</div>
                <div>{post.answerNum} answers</div>
                <div>{post.viewNum} views</div>
            </Side>
            <Main>
                <div>{post.title}</div>
                <div>{post.content}</div>
                <div>
                    <img src={post.userUrl} alt="profile"></img>
                    <div>{post.userId}</div>
                    <div>asked {timeSince(num)} ago</div>
                </div>
            </Main>
        </Block>
    )
};

export default Question;