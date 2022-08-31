import styled from "styled-components";
// useSelector: 등록한 상태를 가져와서 쓸 수 있도록 하는 것
// useDispatch: 상태를 새로 업데이트 하는 것
import { useSelector, useDispatch } from "react-redux";
// 아래 저장소에서 reducer 함수 불러오기
import { loginFulfilled, loginRejected, logoutFulfilled } from "../features/userSlice";

const ReduxTest = () => {

    // 데이터를 가져오는 useSelector 사용법
    const logindata = useSelector(state => {
        // 현재 상태 확인용 콘솔
        console.log(`EMAIL: ${state.user.userEmail}`)
        console.log(`TOKEN: ${state.user.userToken}`)
        console.log(`LOGIN?: ${state.user.isLoggedIn}`)
        console.log(`--------------------------------`)
    })
    // 변수를 선언하여 state값을 리턴하는 함수를 useSelector와 사용하면 현 isLoggedIn 위치에 있는 값을 가져올 수 있다
    const loginData = useSelector(state => {
        return state.user.isLoggedIn
    })
    
    // 데이터 업데이트하는 useDispatch 사용법
    const dispatch = useDispatch();
    
    const handleLogin = () => {
        dispatch(loginFulfilled({userEmail: 'abc@a.com', userToken: `def`}));
        // 로그인 성공으로 상태 변경 시 위와 같은 형태로 dispatch에 데이터 전달
        // 콘솔창을 확인하면 현재 상태 변경됨을 확인할 수 있음
    }

    const handleLogout = () => {
        dispatch(logoutFulfilled());
        // 로그아웃으로 상태 변경 시 위와 같은 형태로 dispatch에 전달
        // 콘솔창을 확인하면 현재 상태 초기화됨을 확인할 수 있음
    }

    const handleError = () => {
        dispatch(loginRejected({error: "error message"}));
        // 로그인 실패로 상태 변경 시 위와 같은 형태로 dispatch에 데이터 전달
        // 콘솔창을 확인하면 현재 상태 변경됨을 확인할 수 있음
    }


    return (
        <>
         <Div>
            {loginData? <div>로그인되었습니다</div> : <div>로그아웃되었습니다</div>}
            <button onClick={handleLogin}>LOGIN</button>
            <button onClick={handleLogout}>LOGOUT</button>
            <button onClick={handleError}>LOGIN REJECTED</button>
         </Div>
        </>
    )
};

const Div = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    > button {
        width: 300px;
        height: 50px;
        margin-bottom: 20px;
    }
    > div {
        margin-bottom: 20px;
    }
    flex-basis: 100vw; 
    flex-shrink: 6;
    height: 90vh;
`

export default ReduxTest;