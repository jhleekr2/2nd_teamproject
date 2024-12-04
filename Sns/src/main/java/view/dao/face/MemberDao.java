package view.dao.face;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import view.dto.Login;
import view.dto.Member;

public interface MemberDao {

	/**
	 * 새로운 회원 정보를 DB에 삽입
	 * 
	 * @param member 삽입할 회원 정보를 담고 있는 Member 객체
	 */
	public void insertMember(Member member);
    
	/**
     * 주어진 memberID로 회원 정보를 조회
     * 
     * @param memberID 조회할 회원의 ID
     * @return 조회된 회원 정보를 담은 Member 객체, 없으면 null 반환
     */
    public Member findByMemberID(String memberID);

    /**
     * 주어진 memberId의 중복 여부를 확인
     * 
     * @param memberId 중복 여부를 확인할 회원 ID
     * @return 중복된 회원 ID 개수 (0 또는 1)
     */
    public int selectCountByMemberId(String memberId);

    /**
     * 이메일로 Member 객체를 조회
     * 
     * @param email 조회할 이메일 주소
     * @return 이메일과 일치하는 Member 객체, 없으면 null 반환
     */
    public Member findMemberByEmail(String email);

    /**
     * 이메일 등록 여부 확인
     * 
     * @param email 등록 여부를 확인할 이메일 주소
     * @return 이메일이 등록되어 있으면 true, 아니면 false
     */
    public boolean emailExists(String email);

    /**
     * 전화번호로 멤버 목록 조회
     * 
     * @param phone 조회할 전화번호
     * @return 전화번호와 일치하는 회원 목록을 담은 List 객체
     */
    public List<Member> selectMembersByPhone(String phone);

    /**
     * 전화번호 등록 여부 확인
     * 
     * @param phone 등록 여부를 확인할 전화번호
     * @return 전화번호가 등록되어 있으면 해당하는 행의 개수
     */
    public int countByPhone(String phone);
    
    public int countByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    // 이름과 전화번호로 회원 조회
    public Member findMemberByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    // 비밀번호 변경
    public int updatePassword(@Param("memberId") String memberId, @Param("newPassword") String newPassword);

    /**
     * 로그인 성공시 회원정보 조회
     * @param login 로그인 정보
     * @return 로그인한 회원의 회원번호
     */
	public int getMemberno(Login login);

	/**
	 * 회원번호로 회원정보 조회
	 * @param memberno 회원번호
	 * @return 회원정보
	 */
	public Member findMemberBymemberno(int memberno);

	/**
	 * 회원정보 수정
	 * @param member 회원정보
	 * @return 회원정보 수정 성공여부
	 */
	public boolean myinfo(Member member);

	/**
	 * 회원 탈퇴
	 * @param memberno 탈퇴할 회원번호
	 */
	public void leave(int memberno);



}
