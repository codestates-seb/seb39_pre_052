import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const SignUpWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  background-color: #f1f2f3;
  overflow: scroll;

  > div:first-of-type {
    flex-basis: 10%;
    width: 422px;
    height: 55px;
    font-size: 21px;
    text-align: center;
    margin-top: 10px;
  }
  > section.social_buttons {
    flex-basis: 20%;

    > div {
      display: flex;
      flex-direction: column;
    }

    > div > button {
      width: 316px;
      height: 38px;
      margin: 4px;
      cursor: pointer;
    }
  }

  > section.form_container {
    flex-basis: 70%;
    margin: 20px 52px 24px 52px;
    width: 316px;
    height: 660px;
    padding: 24px;
    border-radius: 10px;
    background-color: white;
    box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;

    > form {
      display: flex;
      flex-direction: column;
    }

    > form > div {
      /* flex-basis: 70%; */
      width: 268px;
      height: 100px;
      margin: 6px 0;
      display: flex;
      flex-direction: column;

      > label {
        flex-basis: 33%;
        width: 268px;
        height: 35px;
        margin: 2px 0;
      }

      > input {
        flex-basis: 33%;
        width: 268px;
        height: 34px;
      }
      > p {
        flex-basis: 33%;
        font-size: 12px;
        width: 268px;
        height: 60px;
        padding: 2px;
      }
    }

    > form > button.sign_up_btn {
      /* flex-basis: 30%; */
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

const SignUp = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  //유효성검사
  const [isEmail, setIsEmail] = useState(false);
  const [isPassword, setIsPassword] = useState(false);

  //빈 상태로 회원가입 버튼 누를 시 empty 메시지
  const [emptyEmailMsg, setEmptyEmailMsg] = useState("");
  const [emptyPasswordMsg, setEmptyPasswordMsg] = useState("");

  //오류메시지 상태저장
  const [emailMessage, setEmailMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");

  const navigate = useNavigate();

  //Sign up 버튼 누르면 POST 요청하기
  const handleSubmit = (event) => {
    event.preventDefault();
    let newUser = {
      name: name,
      email: email,
      password: password,
    };
    // console.log(newUser);
    if (email.length === 0) {
      setEmptyEmailMsg("Email cannot be empty.");
    }
    if (password.length === 0) {
      setEmptyPasswordMsg("Password cannot be empty.");
    }
    fetch("/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newUser),
    })
      // .then((res) => res.json())
      .then((res) => {
        //이미 존재하는 이메일로 회원가입 실패시
        if (!res.ok) {
          // setIncorrectMessage("The email already exists");
          setPasswordMessage("The email already exists");
        }
        //회원가입 성공시
        else if (res.ok) {
          navigate("/login");
        }
        return res.json();
      })
      .then((res) => console.log(res)) //{failureReason: '', success: true}
      .catch(() => console.log("error"));
  };

  //유효성 체크 (정규 표현식 사용)
  const onChangeEmail = (e) => {
    const emailCurrent = e.target.value;
    setEmail(emailCurrent);
    const emailRegex =
      /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (!emailRegex.test(emailCurrent)) {
      setEmailMessage(`${emailCurrent} is not a valid email address`);
      setIsEmail(false);
    } else {
      setEmailMessage("");
      setIsEmail(true);
    }
  };

  const onChangePassword = (e) => {
    const passwordCurrent = e.target.value;
    setPassword(passwordCurrent);
    const passwordRegex =
      //   /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/; //영문, 숫자, 특수 1자 이상 8자리 필수
      /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$/; //영문, 숫자 1자이상 8자리 필수 //특수문자 포함은 되도 ok

    if (!passwordRegex.test(passwordCurrent)) {
      setPasswordMessage(
        "Passwords must contain at least eight characters, including at least 1 letter and 1 number."
      );
      setIsPassword(false);
    } else {
      setPasswordMessage("");
      setIsPassword(true);
    }
  };

  return (
    <SignUpWrapper className="signup_wrapper">
      <div>
        Create your Stack Overflow account. It’s free and only takes a minute.
      </div>
      {/* 소셜 회원 가입 */}
      <section className="social_buttons">
        <div>
          <button>Sign up with Google</button>
          <button>Sign up with Github</button>
          <button>Sign up with Facebook</button>
        </div>
      </section>

      {/* 일반 회원 가입 */}
      <section className="form_container">
        <form onSubmit={handleSubmit}>
          <div>
            <label>Display name</label>
            <input
              type="text"
              name="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></input>
            <p></p>
          </div>
          <div>
            <label>Email</label>
            <input
              type="text"
              name="email"
              value={email}
              onChange={onChangeEmail}
            ></input>
            {email.length === 0 ? (
              <p className="message error">{emptyEmailMsg}</p>
            ) : (
              <p className={`message ${isEmail ? "success" : "error"}`}>
                {emailMessage}
              </p>
            )}
            {/* 아무것도 안쓰면 Email cannot be empty. 는 어떻게 추가? */}
          </div>
          <div>
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={onChangePassword}
            ></input>
            {password.length === 0 ? (
              <p className="message error">{emptyPasswordMsg}</p>
            ) : (
              <p className={`message ${isPassword ? "success" : "error"}`}>
                {passwordMessage}
              </p>
            )}
            {/* <p className="message error">{incorrectMessage}</p> */}
          </div>
          <button className="sign_up_btn">Sign up</button>
        </form>
      </section>
    </SignUpWrapper>
  );
};

export default SignUp;
