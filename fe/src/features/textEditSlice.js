// 질문 작성, 수정 관련 저장소
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    title: '',
    htmlStr: '',
    isTitleEmpty: true,
    isContentEmpty: true,
    questionId: 0,
}

const textEditSlice = createSlice({
    name: 'editMode',
    initialState,
    reducers: {
        setTitle(state, action) {
            state.title = action.payload.title;
        },
        setIsTitleEmpty(state, action) {
            state.isTitleEmpty = action.payload.isTitleEmpty;
        },
        setHtmlStr(state, action) {
            state.htmlStr = action.payload.htmlStr;
        },
        setIsContentEmpty(state, action) {
            state.isContentEmpty = action.payload.isContentEmpty;
        },
        setQuestionId(state, action) {
            state.questionId = action.payload.questionId;
        }

    }
});

export default textEditSlice;
export const { setTitle, setIsTitleEmpty, setHtmlStr, setIsContentEmpty, setQuestionId } = textEditSlice.actions;