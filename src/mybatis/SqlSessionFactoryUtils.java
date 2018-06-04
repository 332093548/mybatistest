package mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// 用来与数据库建立连接的工具类
public class SqlSessionFactoryUtils {
	// 用来创建SqlSession的工厂,SqlSession就是之前Connectoin
	private static SqlSessionFactory factory;
	// 声明一个线程变量
	private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
	
	// 构造方法私有,避免创建对象
	private SqlSessionFactoryUtils() {
		
	}
	
	public static void main(String[] args) {
		// SqlSession代理,里面封装了Connection
		SqlSession sqlSession = getSqlSession();
		SqlSession sqlSession2 = getSqlSession();
		
		// 创建一个独立的线程进行测试
		new Thread() {
			@Override
			public void run() {
				SqlSession sqlSession = getSqlSession();
				SqlSession sqlSession2 = getSqlSession();
				System.out.println("t:" + sqlSession.getConnection());
				System.out.println("t:" + sqlSession2.getConnection());
			}
			
		}.start();
		
		System.out.println("m:" + sqlSession.getConnection());
		System.out.println("m:" + sqlSession2.getConnection());
	}
	
	// SqlSessionFactory是用来创建SqlSessoin工厂.
	public static SqlSession  getSqlSession() {
		// 首先判断当前线程变量中是否已有SqlSession
		SqlSession sqlSession = threadLocal.get();
		if(sqlSession==null) {
			// 当前线程变量中没有存储sqlSession,需要重新创建
			sqlSession = factory.openSession();
			// 把当前变量存储到线程变量中
			threadLocal.set(sqlSession);
		}
		return sqlSession;
	}
	
	public static void closeSession() {
		SqlSession sqlSession = threadLocal.get();
		if(sqlSession!=null) {
			sqlSession.close();
			threadLocal.set(null);
		}
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
