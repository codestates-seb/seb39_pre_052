import { configureStore } from '@reduxjs/toolkit';
import counterSlice from './features/counterSlice';
import showSlice from './features/showSlice'
import userSlice from './features/userSlice';

const store = configureStore({
  reducer: {
    counter: counterSlice.reducer, // This slice is just an example to explain how slice works
    toggle: showSlice.reducer,
    user: userSlice.reducer,
  }
});

export default store;