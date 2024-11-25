package view.service.face;

import java.util.List;

import view.dto.Login;
import view.dto.Member;

public interface MemberService {

    /**
     * 회원가입 성공 여부 반환
     * 
     * @param member 가입할 회원 정보를 담고 있는 Member 객체
     * @return 회원가입 성공 시 true, 실패 시 false
     */
    public boolean signup(Member member);

    /**
     * 아이디 중복 여부 확인
     * 
     * @param memberId 중복 확인할 회원 ID
     * @return 중복된 아이디가 있으면 true, 없으면 false
     */
    public boolean isIdDuplicate(String memberId);

    /**
     * 비밀번호 확인
     * 
     * @param login 비밀번호를 확인할 로그인 정보
     * @return 비밀번호가 일치하면 true, 일치하지 않으면 false
     */
    public boolean checkPassword(Login login);

    /**
     * 사용자 아이디 존재 여부 확인
     * 
     * @param memberID 존재 여부를 확인할 사용자 ID
     * @return 아이디가 존재하면 true, 존재하지 않으면 false
     */
    public boolean isUsernameExists(String memberID);

    /**
     * 이메일로 회원 정보를 조회
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
    public boolean isEmailRegistered(String email);

    /**
     * 전화번호로 회원 목록 조회
     * 
     * @param phone 조회할 전화번호
     * @return 전화번호와 일치하는 회원 목록을 담은 List 객체
     */
    public List<Member> findMembersByPhone(String phone);

    /**
     * 전화번호 등록 여부 확인
     * 
     * @param phone 등록 여부를 확인할 전화번호
     * @return 전화번호가 등록되어 있으면 true, 아니면 false
     */
    public boolean isPhoneRegistered(String phone);
    
    public boolean isNameAndPhoneRegistered(String name, String phone);
   
    
    // 이름과 전화번호로 회원 조회
    public Member findMemberByNameAndPhone(String name, String phone);

    // 비밀번호 변경
    boolean updatePassword(String memberId, String newPassword);

    // 로그인 성공시 회원번호 조회
	public int getMemberno(Login login);

}
