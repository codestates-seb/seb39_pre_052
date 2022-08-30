package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    //1. 질문 등록
    public Question createQuestion(Question question, Long memberId) {
        Member findMember = memberService.findById(memberId);
        question.setQuestionMember(findMember);
        System.out.println("question = " + question.getMember().getId());
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion;
    }

    //2. 질문 수정
    @Transactional
    public Question updateQuestion(Question question, Long memberId) {
        Question findQuestion = findQuestion(question.getId());
        verifyWriter(memberId, findQuestion);

        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);

        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);

        return questionRepository.save(findQuestion);
    }


    //3. 질문 삭제
    //삭제할 때 로그인한 회원, 글 작성자 비교해서 같으면 수정할 수 있도록 로직 구현
    @Transactional
    public void delete(long id, long memberId) {
        Question findQuestion = findQuestion(id);
        verifyWriter(memberId, findQuestion);


        questionRepository.delete(findQuestion);
    }

    //4. 질문 전체 조회
    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    //해당 질문이 존재하는지 확인하는 메서드
    public Question findQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        Question findQuestion = optionalQuestion.orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));

        return findQuestion;
    }

    public void verifyWriter(Long memberId, Question question) {
        //작성자 ID
        Long writerId = question.getMember().getId();

        if (writerId != memberId) {
            throw new RuntimeException("작성자가 아니면 수정 또는 삭제할 수 없습니다!");
        }
    }


}
