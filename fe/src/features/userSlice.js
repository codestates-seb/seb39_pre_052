// 유저 로그인 데이터 저장소
import { createSlice } from "@reduxjs/toolkit";

// 기본 상태
const initialState = {
    loading: false,
    userEmail: null, // for user object
    userToken: null, // for storing the JWT
    error: null,
    isLoggedIn: false, // for monitoring the registration process
}

// 저장소 만들기
const userSlice = createSlice({
    name: 'user', // 저장소 이름
    initialState,
    reducers: {
        // 로그인 성공시 내가 전달한 데이터로 각 상태 변경 (각각 키 값으로 전달)
        loginFulfilled(state, action) {
            state.isLoggedIn = true;
            state.userEmail = action.payload.userEmail;
            state.userToken = action.payload.userToken;
        },
        // 로그인 실패시 내가 전달한 에러 메세지로 에러 상태 변경 (유저 정보 초기화)
        loginRejected(state, action) {
            state.isLoggedIn = false;
            state.userEmail = null;
            state.userToken = null;
            state.error = action.payload.error;
        },
        // 로그아웃 시 전체 상태 초기화
        logoutFulfilled(state) {
            state.isLoggedIn = false;
            state.userEmail = null;
            state.userToken = null;
        },
    },
    extraReducers: {},
});

export default userSlice;
export const { loginFulfilled, loginRejected, logoutFulfilled } = userSlice.actions;