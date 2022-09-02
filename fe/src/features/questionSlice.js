// 질문 세부내용 저장소
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    question: {},
    answers: {}
}

const questionSlice = createSlice({
    name: 'question',
    initialState,
    reducers: {
        questionDetails(state, action) {
            state.question = action.payload.data;
            state.answers = action.payload.answers;
        }
    }
});

export default questionSlice;
export const { questionDetails } = questionSlice.actions;