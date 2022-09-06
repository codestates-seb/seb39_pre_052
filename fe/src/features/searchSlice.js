// 검색 Query 저장
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  query: "",
};

const searchSlice = createSlice({
    name: "queryData",
    initialState,
    reducers: {
        setQuery(state, action) {
            state.query = action.payload.query;
        },
    },
});

export default searchSlice;
export const { setQuery } = searchSlice.actions;