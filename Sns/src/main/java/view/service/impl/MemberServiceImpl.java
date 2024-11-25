package view.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import view.dao.face.MemberDao;
import view.dto.Login;
import view.dto.Member;
import view.service.face.MemberService;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    
    @Autowired private MemberDao memberDao;
    
    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean signup(Member member) {
        try {
            memberDao.insertMember(member);
            return true; // 성공 시 true 반환
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 실패 시 false 반환
        }
    }

    @Override
    public boolean isUsernameExists(String memberID) {
        Member member = memberDao.findByMemberID(memberID);
        return member != null;
    }

    @Override
    public boolean checkPassword(Login login) {
        Member member = memberDao.findByMemberID(login.getMemberID());
        if (member == null) {
            return false;
        }
        return member.getMemberPW().equals(login.getPassword()); // 평문 비교
    }

    @Override
    public boolean isIdDuplicate(String memberId) {
        // 아이디 중복 여부 확인
        return memberDao.selectCountByMemberId(memberId) > 0;
    }
    
    @Override
    public Member findMemberByEmail(String email) {
        return memberDao.findMemberByEmail(email);
    }
    
    @Override
    public boolean isEmailRegistered(String email) {
        return memberDao.emailExists(email);
    }
    
    @Override
    public List<Member> findMembersByPhone(String phone) {
        return memberDao.selectMembersByPhone(phone);
    }

    @Override
    public boolean isPhoneRegistered(String phone) {
        Integer count = memberDao.countByPhone(phone);
        return count != null && count > 0;
    }
    
    @Override
    public boolean isNameAndPhoneRegistered(String name, String phone) {
        int count = memberDao.countByNameAndPhone(name, phone);
        return count > 0;  // 해당 이름과 전화번호로 등록된 회원이 있으면 true 반환
    }
    
    @Override
    public Member findMemberByNameAndPhone(String name, String phone) {
        // 이름과 전화번호로 회원 조회
        return memberDao.findMemberByNameAndPhone(name, phone);
    }

    @Override
    public boolean updatePassword(String memberId, String newPassword) {
        // 비밀번호 변경
        return memberDao.updatePassword(memberId, newPassword) > 0;  // 성공 시 1 이상
    }

	@Override
	public int getMemberno(Login login) {
		// 로그인 성공시 회원번호 조회
		return memberDao.getMemberno(login);
	}
}
