package api.mybatis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// 这里获取资源必须使用Main.class
		InputStream is = Main.class.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			
			List<UserModel> userList = sqlSession.selectList("api.mybatis.queryUser");
			if (!userList.isEmpty()) {
				for (UserModel user : userList) {
					System.out.print("=====size====" + user);
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}

}
