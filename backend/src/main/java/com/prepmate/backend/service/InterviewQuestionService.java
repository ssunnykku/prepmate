package com.prepmate.backend.service;

import com.prepmate.backend.dto.PagenationDTO;
import com.prepmate.backend.dto.QuestionDTO;
import com.prepmate.backend.repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewQuestionService {
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final int pageSize = 10;

    /**
     * 문제 리스트 조회
     *
     * @return QuestionDTO 리스트 반환
     */
    @Transactional(readOnly = true)
    public PagenationDTO<QuestionDTO> getQuestionList(Long interviewId, Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<QuestionDTO> questions = interviewQuestionRepository.findByInterviewId(interviewId, pageable)
                .map(question -> new QuestionDTO(
                        question.getId(),
                        question.getQuestion(),
                        question.getAnswer(),
                        question.getCreatedAt()));
        return new PagenationDTO<>(questions);
    }
}
