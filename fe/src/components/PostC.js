import styled from "styled-components";
import dummy from "../dummy";
import { useState } from "react";
const PostC = () => {
  const [comments, setComments] = useState([]);
  const initialDummy = dummy.slice(0, 5);
  const nextDummy = dummy.slice(6);
  return (
    <>
      <Comment>
        <div className="for_votecell_space"></div>
        <div className="comment_box">
          {dummy.length <= 5
            ? dummy.map((el) => (
                <div className="each_comment">{el.content}</div>
              ))
            : initialDummy.map}
          {nextDummy.length > 0 ? (
            <div
              onClick={nextDummy.map((el) => (
                <div className="each_comment">{el.content}</div>
              ))}
              className="show_more_comment"
            >
              Show {dummy.length - 5} more comments
            </div>
          ) : (
            <div className="add_comment">Add a comment</div>
          )}
        </div>
      </Comment>
    </>
  );
};

const Comment = styled.div`
  display: flex;
  margin: 16px 0;

  > div.for_votecell_space {
    //votecell에 맞춰 비울곳
    flex-basis: 10%;
  }
  > div.comment_box {
    flex-basis: 90%;
    border-top: 1px solid darkgray;
    > div.each_comment {
      border-bottom: 1px solid darkgray;
    }

    > div.show_more_comment {
      color: #5aaaf4;
    }
  }
`;

export default PostC;
