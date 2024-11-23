package view.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import view.dao.face.MemberDao;
import view.dto.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "view.dao.face.MemberDao.";

    @Override
    public Member findByMemberID(String memberID) {
        return sqlSession.selectOne(NAMESPACE + "findByMemberID", memberID);
    }

    @Override
    public void insertMember(Member member) {
        sqlSession.insert(NAMESPACE + "insertMember", member);
    }

    @Override
    public int selectCountByMemberId(String memberId) {
        return sqlSession.selectOne(NAMESPACE + "selectCountByMemberId", memberId);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return sqlSession.selectOne(NAMESPACE + "findMemberByEmail", email);
    }

    @Override
    public boolean emailExists(String email) {
        Integer count = sqlSession.selectOne(NAMESPACE + "countByEmail", email);
        return count != null && count > 0;
    }

    @Override
    public List<Member> selectMembersByPhone(String phone) {
		return null;
//        return memberDao.selectMembersByPhone(phone); // 인스턴스 메서드 호출
    }

    @Override
    public int countByPhone(String phone) {
        return sqlSession.selectOne(NAMESPACE + "countByPhone", phone);
    }


    @Override
    public Member findMemberByNameAndPhone(String name, String phone) {
        return sqlSession.selectOne("MemberDao.findMemberByNameAndPhone", 
                                     Map.of("name", name, "phone", phone));
    }

    @Override
    public int countByNameAndPhone(String name, String phone) {
        return sqlSession.selectOne("MemberDao.countByNameAndPhone", 
                                     Map.of("name", name, "phone", phone));
    }

    @Override
    public int updatePassword(String memberId, String newPassword) {
        return sqlSession.update("MemberDao.updatePassword", 
                                 Map.of("memberId", memberId, "newPassword", newPassword));
    }
}
