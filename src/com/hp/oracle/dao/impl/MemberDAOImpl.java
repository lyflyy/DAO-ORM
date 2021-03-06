package com.hp.oracle.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hp.oracle.dao.IMemberDAO;
import com.hp.oracle.vo.Member;
import com.hp.util.dao.AbstractDAO;

public class MemberDAOImpl extends AbstractDAO implements IMemberDAO {
	@Override 
	public boolean doCreate(Member vo) throws Exception {
		return super.createSupport(vo) ;
	}

	@Override
	public boolean doUpdate(Member vo) throws Exception {
		return super.updateSupport(vo) ;
	}

	@Override
	public boolean doRemoveBatch(Set<String> ids) throws Exception {
		return super.removeSupport(ids,Member.class) ; 
	}

	@Override
	public Member findById(String id) throws Exception {
		String sql = "SELECT mid,name,age,phone,birthday,note FROM member WHERE mid=?" ;
		return super.findByIdSupport(sql, id, Member.class);
	} 

	@Override
	public Member findByPhone(String phone) throws Exception {
		String sql = "SELECT mid,name,age,phone,birthday,note FROM member WHERE phone=?" ;
		return super.findByIdSupport(sql, phone, Member.class);
	}

	@Override
	public List<Member> findAll() throws Exception {
		String sql = "SELECT mid,name,age,phone,birthday,note FROM member" ;
		return super.findAllSupport(sql,Member.class);
	}

	@Override
	public List<Member> findAllSplit(Integer currentPage, Integer lineSize) throws Exception {
		String sql = "SELECT * FROM ( "
				+ " 	SELECT mid,name,age,phone,birthday,note,ROWNUM rn "
				+ " 	FROM member WHERE ROWNUM<=?) temp"
				+ " WHERE temp.rn>?" ;
		return super.findAllSupport(sql,Member.class,currentPage * lineSize,(currentPage - 1) * lineSize);
	}

	@Override
	public List<Member> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize)
			throws Exception {
		String sql = "SELECT * FROM ( "
				+ " 	SELECT mid,name,age,phone,birthday,note,ROWNUM rn "
				+ " 	FROM member WHERE "+column+" LIKE ? AND ROWNUM<=?) temp"
				+ " WHERE temp.rn>?" ;
		return super.findAllSupport(sql,Member.class,"%" + keyWord + "%",currentPage * lineSize,(currentPage - 1) * lineSize);
	}

	@Override
	public Long getAllCount() throws Exception {
		String sql = "SELECT COUNT(*) FROM member" ;
		return super.getSupport(sql, Long.class) ;
	}

	@Override
	public Long getAllCount(String column, String keyWord) throws Exception {
		String sql = "SELECT COUNT(*) FROM member WHERE " + column + " LIKE ?" ;
		this.pstmt = this.conn.prepareStatement(sql) ;
		this.pstmt.setString(1, "%" + keyWord + "%");
		ResultSet rs = this.pstmt.executeQuery() ;
		if (rs.next()) {
			return rs.getLong(1) ;
		}
		return 0L ;
	}

}
