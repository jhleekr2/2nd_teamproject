package view.dto;

import lombok.Data;

//페이징 처리(VO)
//DTO와 비슷하지만, DTO와 달리 데이터 처리 로직이 포함된다
//페이징은 다른 게시판에서도 동일하게 사용할 수 있는 싱글톤 구조로 개발
//하지만 여기서는 게시글과 댓글 페이징에 여러 차이가 있기 때문에 페이징 처리 VO를 분리함
@Data
public class Pagingcomm {
	
	private int curPage; //현재 페이지 번호
	
	private int totalCount; //총 게시글 수
	private int listCount; //한 페이지 당 보여질 게시글 수
	private int totalPage; //총 페이지 수
	
	private int pageCount; //한 화면에 출력될 페이지네이션의 개수
	private int startPage; //화면에 보이는 페이지네이션의 시작 번호
	private int endPage; //화면에 보이는 페이지네이션의 끝 번호
	
	private int startNo; //화면에 보이는 게시글의 시작 번호
	private int endNo; //화면에 보이는 게시글의 끝 번호
	
	private int boardNo; //게시글 번호
	
	//기본 생성자
	public Pagingcomm() {}

	//생성자 만드는 과정에서 방법이 조금 달라진다
//	public Paging(int curPage, int totalCount) {
//		super();
//		this.curPage = curPage;
//		this.totalCount = totalCount;
//		
//		this.makePaging();
//	}
//
//	public Paging(int curPage, int totalCount, int listCount, int pageCount) {
//		super();
//		this.curPage = curPage;
//		this.totalCount = totalCount;
//		this.listCount = listCount;
//		this.pageCount = pageCount;
//		
//		this.makePaging();
//	}

	
	//게시글 번호 반영한 생성자 만들기
	public Pagingcomm(int curPage, int totalCount, int boardNo) {
		super();
		this.curPage = curPage;
		this.totalCount = totalCount;
		this.boardNo = boardNo;
		
		this.makePaging();
	}
	
	public Pagingcomm(int curPage, int totalCount, int listCount, int pageCount, int boardNo) {
		super();
		this.curPage = curPage;
		this.totalCount = totalCount;
		this.listCount = listCount;
		this.pageCount = pageCount;
		this.boardNo = boardNo;
		
		this.makePaging();
	}

	//페이징에 필요한 계산 처리
	private void makePaging() {
		if( totalCount == 0 )	return; //게시글이 없는 경우 처리 중단
		
		//기본값 설정
//		if( curPage <= 0 ) setCurPage(1); //첫 페이지를 기본 페이지로 설정
//		if( listCount == 0 ) setListCount(10); //화면에 보여질 글 수 10개
		if( listCount == 0 ) setListCount(3); //화면에 보여질 글 수 3개
//		if( pageCount == 0 ) setPageCount(10); //화면에 보여질 페이지 수 10개
		if( pageCount == 0 ) setPageCount(3); //화면에 보여질 페이지 수 3개
		
		//---------------------------------------------
		
		//총 페이지 수 계산
		totalPage = totalCount / listCount;
		if( totalCount % listCount > 0) totalPage++;
		
		//총 페이지 수 보정
		if( curPage > totalPage ) curPage = totalPage;
		
		//---------------------------------------------
		

	    // 마지막 페이지를 기본 페이지로 설정 (curPage <= 0일 때)
	    if (curPage <= 0) {
	        curPage = totalPage;  // curPage가 0 이하일 경우 마지막 페이지로 설정
	    }

	    //---------------------------------------------
		
		//화면에 보이는 페이지네이션의 시작, 끝 번호 계산
		startPage = ( (curPage - 1 ) / pageCount ) * pageCount + 1;
		endPage = startPage + pageCount - 1;
		
		//페이지네이션 번호 보정
		if( endPage > totalPage ) endPage = totalPage;

		//---------------------------------------------
		
		//화면에 보이는 게시글의 시작, 끝 번호 계산
		startNo = ( curPage - 1 ) * listCount + 1;
		endNo = curPage * listCount;
	
	}

	
	
//
//	@Override
//	public String toString() {
//		return "Paging [curPage=" + curPage + ", totalCount=" + totalCount + ", listCount=" + listCount + ", totalPage="
//				+ totalPage + ", pageCount=" + pageCount + ", startPage=" + startPage + ", endPage=" + endPage
//				+ ", startNo=" + startNo + ", endNo=" + endNo + "]";
//	}
//
//	public int getCurPage() {
//		return curPage;
//	}
//
//	public void setCurPage(int curPage) {
//		this.curPage = curPage;
//	}
//
//	public int getTotalCount() {
//		return totalCount;
//	}
//
//	public void setTotalCount(int totalCount) {
//		this.totalCount = totalCount;
//	}
//
//	public int getListCount() {
//		return listCount;
//	}
//
//	public void setListCount(int listCount) {
//		this.listCount = listCount;
//	}
//
//	public int getTotalPage() {
//		return totalPage;
//	}
//
//	public void setTotalPage(int totalPage) {
//		this.totalPage = totalPage;
//	}
//
//	public int getPageCount() {
//		return pageCount;
//	}
//
//	public void setPageCount(int pageCount) {
//		this.pageCount = pageCount;
//	}
//
//	public int getStartPage() {
//		return startPage;
//	}
//
//	public void setStartPage(int startPage) {
//		this.startPage = startPage;
//	}
//
//	public int getEndPage() {
//		return endPage;
//	}
//
//	public void setEndPage(int endPage) {
//		this.endPage = endPage;
//	}
//
//	public int getStartNo() {
//		return startNo;
//	}
//
//	public void setStartNo(int startNo) {
//		this.startNo = startNo;
//	}
//
//	public int getEndNo() {
//		return endNo;
//	}
//
//	public void setEndNo(int endNo) {
//		this.endNo = endNo;
//	}
//	
}
