package mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// 用来与数据库建立连接的工具类
public class SqlSessionFactoryUtils {
	// 用来创建SqlSession的工厂,SqlSession就是之前Connectoin
	private static SqlSessionFactory factory;
	
	public static void main(String[] args) {
		// SqlSession代理,里面封装了Connection
		SqlSession sqlSession = getSqlSession();
		System.out.println(sqlSession.getConnection());
	}
	
	// SqlSessionFactory是用来创建SqlSessoin工厂.
	public static SqlSession  getSqlSession() {
		return factory.openSession();
	}
	
	public static void closeSession(SqlSession sqlSession) {
		if(sqlSession!=null)
			sqlSession.close();
	}

	// 读取jar,xml配置,io流一般都只一次,因此会写到静态块中
	static {
		// 1: 加载配置文件   /mybatis.cfg.xml代表加载src根目录下的配置文件,  直接写名称,则代表与当前类同目录
		InputStream in = SqlSessionFactoryUtils.class.getResourceAsStream("/mybatis.cfg.xml");
		factory = new SqlSessionFactoryBuilder().build(in);
//		System.out.println(factory);
//		SqlSessionFactory sqlSessionFactory = 
	}
}
