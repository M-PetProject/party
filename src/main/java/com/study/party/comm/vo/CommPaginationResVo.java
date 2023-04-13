package com.study.party.comm.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommPaginationResVo {

    /* 기본 파라미터 정보 */
    protected int pageNo;
    protected int limit;
    private int offset;
    /* 기본 파라미터 정보 */

    /* 페이지네이션 결과 정보 */
    private int totalItems;
    private int totalPages;
    private int startPage;
    private int endPage;
    private List<Integer> pages;
    private int maxPages;
    private Object data;
    /* 페이지네이션 결과 정보 */

    public CommPaginationResVo pagination() {
        return pagination(5);
    }

    public CommPaginationResVo pagination(int maxPages) {
        this.maxPages = maxPages;
        if ( this.totalItems == 0 ) {
            this.pages = List.of();
            return this;
        }


        int totalPages = ((this.totalItems / this.limit) + ((this.totalItems % this.limit == 0) ? 0 : 1));

        if (this.pageNo < 1) {
            this.pageNo = 1;
        } else if (this.pageNo > totalPages) {
            this.pageNo = totalPages;
        }

        int startPage = 1;
        int endPage   = 1;
        if ( totalPages <= this.maxPages ) {
            // total pages less than max so show all pages
            startPage = 1;
            endPage   = totalPages;
        } else {
            // total pages more than max so calculate start and end pages
            int maxPagesBeforeCurrentPage = this.maxPages / 2;
            int maxPagesAfterCurrentPage  = ((this.maxPages / 2) + ((this.maxPages % 2 == 0) ? 0 : 1)) - 1;
            if ( this.pageNo <= maxPagesBeforeCurrentPage ) {
                // current page near the start
                startPage = 1;
                endPage   = this.maxPages;
            } else if ( this.pageNo + maxPagesAfterCurrentPage >= totalPages ) {
                // current page near the end
                startPage = totalPages - this.maxPages + 1;
                endPage   = totalPages;
            } else {
                System.out.println("else      :: ");
                // current page somewhere in the middle
                startPage = this.pageNo - maxPagesBeforeCurrentPage;
                endPage   = this.pageNo + maxPagesAfterCurrentPage;
            }
        }

        List<Integer> pages = new ArrayList<>();
        for ( int i = startPage ; i <= endPage ; i++ ) {
            pages.add(i);
        }
        this.totalPages = totalPages;
        this.startPage  = startPage;
        this.endPage    = endPage;
        this.pages      = pages;
        this.maxPages   = maxPages;
        return this;
    }
}
