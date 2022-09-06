import styled from "styled-components";
import { useSelector } from "react-redux";

const PostC = () => {
  
  const commentsForQ = useSelector((state) => {
    return state.question.question.comments;
  });

  return (
    <>
      <Comment>
        <div className="for_votecell_space"></div>
        <div className="comment_box">
          {commentsForQ.map((comment) => (
            <div className="each_comment">
              <span className="comment">{comment.content}</span>
              <span className="username">{comment.member.name}</span>
              <span className="commented_at">
                {comment.createdAt.slice(0, 10)}
              </span>
            </div>
          ))}

          <div className="show_more_comment">Show {} more comments </div>
          <div className="add_comment">Add a comment</div>
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
