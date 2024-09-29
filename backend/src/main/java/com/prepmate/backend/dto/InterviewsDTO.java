package com.prepmate.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prepmate.backend.domain.Interview;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class InterviewsDTO {
    private List<InterviewDTO> data;
    private long totalElements;
    private int totalPages;
    private int currentPage;

    @JsonProperty("isFirst")
    private boolean isFirst;

    @JsonProperty("isLast")
    private boolean isLast;

    private boolean hasNext;
    private boolean hasPrevious;

    public InterviewsDTO(Page<InterviewDTO> interviewPage) {
        this.setData(interviewPage.getContent());
        this.setTotalElements(interviewPage.getTotalElements());
        this.setTotalPages(interviewPage.getTotalPages());
        this.setCurrentPage(interviewPage.getNumber() + 1);
        this.setFirst(interviewPage.isFirst());
        this.setLast(interviewPage.isLast());
        this.setHasNext(interviewPage.hasNext());
        this.setHasPrevious(interviewPage.hasPrevious());
    }


}
