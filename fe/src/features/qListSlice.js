// 질문 리스트 데이터 저장소
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    posts: [],
    total: 0,
}

const qListSlice = createSlice({
    name: 'qlist',
    initialState,
    reducers: {
        setPosts: (state, action) => {
            state.posts = action.payload.posts; 
            state.total = action.payload.total;
        },
    }

});

export default qListSlice;
export const { setPosts } = qListSlice.actions