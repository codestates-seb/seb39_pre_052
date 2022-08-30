import { configureStore } from '@reduxjs/toolkit';
import counterSlice from './features/counterSlice';
import showSlice from './features/showSlice'

const store = configureStore({
  reducer: {
    counter: counterSlice.reducer,
    toggle: showSlice.reducer
  }
});

export default store;