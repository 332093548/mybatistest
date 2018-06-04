package dao;

import java.awt.event.MouseWheelEvent;

import org.apache.ibatis.session.SqlSession;

import model.Product;
import mybatis.SqlSessionFactoryUtils;

public class ProductDaoImpl {
	
	public static void main(String[] args) {
		ProductDaoImpl daoImpl = new ProductDaoImpl();
		Product product = new Product();
		product.setName("yyy");
		daoImpl.save(product);
	}

	// mybatis实现数据入库的功能
	public int save(Product product) {
		// 1: 获取SqlSession
		SqlSession sqlSession = null;
		// sqlSesison默认是开启了事务,但是需要自己手动提交,而且出了异常需要回滚
		try {
			sqlSession = SqlSessionFactoryUtils.getSqlSession();
			// 2: 执行相关操作,此处传入的参数,必须与parameterType="model.Product"相同
			int count = sqlSession.insert("aa.bb.cc.save", product);
//			Integer.parseInt("abc");
			sqlSession.commit();   // 手动提交(此处是编程式事务)
			return count;
		}catch (Exception e) {
			sqlSession.rollback();
			throw new RuntimeException(e);
		}finally {
			// 3: 释放资源
			SqlSessionFactoryUtils.closeSession();
		}
	}
}
