// REDUX TOOLKIT
// store: 전체 저장소
// slice: store을 이루는 작은 저장소
// reducer: reducer 함수는 dispatch에게서 받은 값에 따라 상태를 변경시키는 함수

import { configureStore } from '@reduxjs/toolkit';
import counterSlice from './features/counterSlice';
import showSlice from './features/showSlice'
import userSlice from './features/userSlice';
import qListSlice from './features/qListSlice';
import questionSlice from './features/questionSlice';
import textEditSlice from './features/textEditSlice';
import searchSlice from './features/searchSlice';

const store = configureStore({
  reducer: {
    counter: counterSlice.reducer, // This slice is just an example to explain how slice works
    toggle: showSlice.reducer,
    user: userSlice.reducer,
    qlist: qListSlice.reducer,
    question: questionSlice.reducer,
    editMode: textEditSlice.reducer,
    queryData: searchSlice.reducer,
  }
});

export default store;