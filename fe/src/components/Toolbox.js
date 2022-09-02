import { useMemo } from 'react';
import styled from 'styled-components';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import React from 'react';

import { useSelector, useDispatch } from "react-redux";
import { setHtmlStr, setIsContentEmpty } from '../features/textEditSlice';

const CustomReactQuill = styled(ReactQuill)`
    height: 300px;
    border-bottom: 1px solid #D1D1D1;
    overflow: hidden;
`

const Toolbox = ({ contentRef, setEmptyContentMsg }) => {

    const htmlStr = useSelector((state) => {
        return state.editMode.htmlStr;
    })

    const dispatch = useDispatch();

    // Quill js Toolbox Module Options
    const modules = useMemo(() => ({
        toolbar: {
            container: [
                // [{ 'font': [] }], // font 설정
                // [{ 'header': [1, 2, 3, 4, 5, 6, false] }], // header 설정
                ['bold', 'italic', 'underline', 'strike', 'blockquote', 'code-block', 'formula'], // 굵기, 기울기, 밑줄 등 부가 tool 설정
                [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'indent': '-1' }, { 'indent': '+1' }], // 리스트, 인덴트 설정
                ['link', 'image', 'video'], // 링크, 이미지, 비디오 업로드 설정
                // [{ 'align': [] }, { 'color': [] }, { 'background': [] }], // 정렬, 글씨 색깔, 글씨 배경색 설정
                ['clean'], // toolbar 설정 초기화 설정
            ],
            // handlers: {
            //     'code-block': htmlHandler,
            // }
            handlers: {
                'code-block': () => { console.log('code block was clicked') },
                'blockquote': () => { console.log('blockquote was clicked') },
                'strike': () => { console.log('strike was clicked') }
            },
        },
    }), [])

    const formats = [
        "header",
        "font",
        "size",
        "bold",
        "italic",
        "underline",
        "list",
        "bullet",
        "align",
        "color",
        "background",
    ];

    const handleText = (content, delta, source, editor) => {
      dispatch(setHtmlStr({htmlStr: editor.getHTML()}));
    
      // The red essage disappears when input is entered
      if (htmlStr.length < 0 || htmlStr === '<p><br></p>' || htmlStr === '<p></p>') {
          dispatch(setIsContentEmpty({isContentEmpty: true}));
          setEmptyContentMsg("Body is missing.");
      }
      else {
          dispatch(setIsContentEmpty({ isContentEmpty: false }));
          setEmptyContentMsg("");
      }
    };

    return (
        <div>
            <div className="text-editor">
                <CustomReactQuill
                    modules={modules}
                    formats={formats}
                    value={htmlStr}
                    onChange={handleText}
                    theme="snow"
                    ref={contentRef}
                    // style={{overflow: "hidden"}}
                />
            </div>
      </div>
    )
};

export default Toolbox;