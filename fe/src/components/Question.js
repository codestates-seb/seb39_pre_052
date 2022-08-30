import styled from 'styled-components';

const Block = styled.div`
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
`

const Main = styled.div`
    display: flex;
    flex-direction: column;
    width: 80vw;
    margin-right: 45px;

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
        align-self: flex-end;
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

    let num = Date.parse(post.createdAt);

    return (
        <Block>
            <Side>
                <div>{post.vote} votes</div>
                <div>{post.answered} answers</div>
                <div>{post.view} views</div>
            </Side>
            <Main>
                <div>{post.title}</div>
                <div>{post.content}</div>
                <div>
                    <img src="https://www.gravatar.com/avatar/fd30a6da006a64e9f2d622341f374e99?s=256&d=identicon&r=PG" alt="profile"></img>
                    <div>{post.name}</div>
                    <div>asked {timeSince(num)} ago</div>
                </div>
            </Main>
        </Block>
    )
};

export default Question;