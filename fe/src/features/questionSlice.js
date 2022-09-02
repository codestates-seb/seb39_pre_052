// 질문 세부내용 저장소
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    question: {},
}

const questionSlice = createSlice({
    name: 'question',
    initialState,
    reducers: {
        questionDetails(state, action) {
            state.question = action.payload.data;
        }
    }
});

export default questionSlice;
export const { questionDetails } = questionSlice.actions;