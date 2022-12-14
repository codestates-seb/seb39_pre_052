import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { useSelector, useDispatch } from "react-redux";
import {
  loginFulfilled,
  loginRejected,
  logoutFulfilled,
  myPageData,
} from "../features/userSlice";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSquareFacebook,
  faGoogle,
  faGithub,
} from "@fortawesome//free-brands-svg-icons";

const LogIn = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emptyEmailMsg, setEmptyEmailMsg] = useState("");
  const [emptyPasswordMsg, setEmptyPasswordMsg] = useState("");
  const [incorrectMessage, setIncorrectMessage] = useState("");
  // const [isLoggedin, setIsLoggedin] = useState(false);
  //로그인 여부 확인해서
  const navigate = useNavigate();
  const dispatch = useDispatch();
  // const logindata = useSelector((state) => {
  //   // 현재 상태 확인용 콘솔
  //   console.log(`EMAIL: ${state.user.userEmail}`);
  //   console.log(`TOKEN: ${state.user.userToken}`);
  //   console.log(`LOGIN?: ${state.user.isLoggedIn}`);
  //   console.log(`--------------------------------`);
  // });

  //Sign up 버튼 누르면 POST 요청하기
  const handleSubmit = (event) => {
    event.preventDefault();
    let user = { email: email, password: password };
    // console.log(user);

    //email, password 빈칸으로 로그인하면 cannot be empty.
    if (email.length === 0) {
      setEmptyEmailMsg("Email cannot be empty.");
    }
    if (password.length === 0) {
      setEmptyPasswordMsg("Password cannot be empty.");
    }

    fetch("api/login", {
      method: "POST",
      headers: {
        'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8',
      },
      body: JSON.stringify(user),
    })
      // .then((res) => console.log(res))
      // .then((res) => res.json()) //SyntaxError: Unexpected end of JSON input
      // .then((res) => console.log(res.status)) //200
      .then((res) => {
        if (res.status === 200) {
          console.log(res.headers.get("authorization")); //Bearer eyJ0eXAi...
          const token = res.headers.get("authorization");
          localStorage.setItem("access-token", token); //로컬 스토리지에 저장
          // setIsLoggedin(true);
          dispatch(loginFulfilled({ userEmail: email, userToken: token }));
          fetchUserId(token);
          navigate("/");
        }
        //데이터에 맞지않는 이메일, pw 보낼때
        // else if (!res.ok)
        else if (res.status === 401) {
          console.log(res);
          setIncorrectMessage("The email or password is incorrect.");
        }
      });
    // .catch(() => console.log("error"));

    // fetch("/login", {
    //   method: "GET",
    //   headers: {
    //     "Content-Type": "application/json",
    //     Authorization: localStorage.getItem("access-token"),
    //   },
    // }).then((res) => console.log(res));
  };


  //유저 아이디 GET 요청
  const fetchUserId = (token) => {

    fetch(`/api/members/me`, {
      headers: {
        'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8',
        "Authorization": token,
      },
    })
    .then((res) => res.json())
    .then((data) => {
        localStorage.setItem("userId", data.data.memberId)
        localStorage.setItem("memberName", data.data.memberName)
        localStorage.setItem("createdAt", data.data.createdAt)
    })
  }

  return (
    <LogInWrapper className="login_wrapper">
      <div>
        <img src="https://cdn.sstatic.net/Sites/stackoverflow/Img/apple-touch-icon@2.png?v=73d79a89bded" />
      </div>
      {/* 소셜 로그인 */}
      <section className="social_buttons">
        <div>
          <button>
            <FontAwesomeIcon
              icon={faGoogle}
              size="lg"
              style={{ marginRight: 5 }}
            />
            Log in with Google
          </button>
          <button>
            <FontAwesomeIcon
              icon={faGithub}
              size="lg"
              style={{ marginRight: 5 }}
            />
            Log in with Github
          </button>
          <button>
            {/* <FontAwesomeIcon icon="fa-brands fa-square-facebook" /> */}
            <FontAwesomeIcon
              icon={faSquareFacebook}
              size="lg"
              style={{ marginRight: 5 }}
            />
            Log in with Facebook
          </button>
        </div>
      </section>

      {/* 일반 로그인 */}
      <section className="form_container">
        <form onSubmit={handleSubmit}>
          <div>
            <label>Email</label>
            <input
              type="text"
              name="email"
              onChange={(e) => setEmail(e.target.value)}
            ></input>
            {email.length === 0 ? (
              <p className="message error">{emptyEmailMsg}</p>
            ) : (
              <p className="message error">{incorrectMessage}</p>
            )}
          </div>
          <div>
            <label>Password</label>
            <input
              type="password"
              onChange={(e) => setPassword(e.target.value)}
            ></input>
            {<p className="message error">{emptyPasswordMsg}</p>}
          </div>
          <button className="log_in_btn">Log in</button>
        </form>
      </section>
    </LogInWrapper>
  );
};

//css
const LogInWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100%;
  background-color: #f1f2f3;

  > div:first-of-type {
    /* flex-basis: 10%; */
    > img {
      width: 70px;
      height: 70px;
    }
    margin-bottom: 10px;
  }

  > section.social_buttons {
    /* flex-basis: 20%; */

    > div {
      display: flex;
      flex-direction: column;
      justify-content: center;
      width: 316px;
      height: 138px;
    }

    > div > button {
      /* width: 316px; */
      height: 38px;
      margin: 4px;
      border: 1px;
      border-radius: 5px;
      cursor: pointer;

      :first-of-type {
        background-color: white;
        :hover {
          background-color: #f8f9f9;
        }
      }
      :nth-of-type(2) {
        background-color: #232628;
        color: white;
        :hover {
          background-color: black;
        }
      }
      :nth-of-type(3) {
        background-color: #3a5796;
        color: white;
        :hover {
          background-color: #304a86;
        }
      }
    }
  }

  > section.form_container {
    /* flex-basis: 70%; */
    margin: 20px 52px 24px 52px;
    width: 316px;
    height: 300px;
    padding: 24px;
    border-radius: 10px;
    background-color: white;
    box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;

    > form {
      display: flex;
      flex-direction: column;
      /* justify-content: center;
      align-content: center; */
      width: 268px;
      /* min-height: 300px; */
    }

    > form > div {
      height: 80px;
      margin: 6px 0;
      display: flex;
      flex-direction: column;

      > label {
        flex-basis: 25%;
        /* width: 268px;
        height: 35px; */
        margin: 2px 0;
        font-size: 14px;
      }

      > input {
        flex-basis: 45%;
        /* width: 268px;
        height: 35px; */
      }
      > p {
        flex-basis: 30%;
        font-size: 12px;
        /* width: 268px;
        height: 60px; */
        padding: 2px;
      }
    }

    > form > button {
      /* position: relative;
      top: 50%; */
      margin-top: 15px;
      width: 268px;
      height: 38px;
      background-color: #0a95ff;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      color: white;
      cursor: pointer;

      :hover {
        background-color: #0074cc;
      }
    }
  }
`;
export default LogIn;
