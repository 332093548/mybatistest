package dao;

import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Product;
import mybatis.SqlSessionFactoryUtils;

public class ProductDaoImpl {

	public static void main(String[] args) {
		ProductDaoImpl daoImpl = new ProductDaoImpl();
		Product product = new Product();
		product.setName("yyy");
		product.setId(40);
		product.setPrice(300.00);
		product.setRemark("更新测试!");
		// daoImpl.update(product);
//		for (Product temp : daoImpl.queryByName("")) {
//			System.out.println(temp);
//		}
		for (Product temp : daoImpl.queryLike("", null, 3.0)) {
			System.out.println(temp);
		}
	}

	// mybatis实现数据入库的功能
	public int update(Product product) {
		// 1: 获取SqlSession
		SqlSession sqlSession = null;
		// sqlSesison默认是开启了事务,但是需要自己手动提交,而且出了异常需要回滚
		try {
			sqlSession = SqlSessionFactoryUtils.getSqlSession();
			// 2: 执行相关操作,此处传入的参数,必须与parameterType="model.Product"相同
			int count = sqlSession.update("aa.bb.cc.update", product);
			// Integer.parseInt("abc");
			sqlSession.commit(); // 手动提交(此处是编程式事务)
			return count;
		} catch (Exception e) {
			sqlSession.rollback();
			throw new RuntimeException(e);
		} finally {
			// 3: 释放资源
			SqlSessionFactoryUtils.closeSession();
		}
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
			// Integer.parseInt("abc");
			sqlSession.commit(); // 手动提交(此处是编程式事务)
			return count;
		} catch (Exception e) {
			sqlSession.rollback();
			throw new RuntimeException(e);
		} finally {
			// 3: 释放资源
			SqlSessionFactoryUtils.closeSession();
		}
	}

	// 查询并未修改数据库,因此并不需要事务的提交或者回滚
	public List<Product> queryByName(String keyword) {
		SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession();
		return sqlSession.selectList("aa.bb.cc.queryByName", "%" + keyword + "%");
	}
	// 多条件查询,用户可能会传入所有参数,也有可能只传部分参数
	public List<Product> queryLike(String keyword, Double maxPrice, Double minPrice) {
		SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("keyword", "%" + keyword + "%");
		paramMap.put("max",maxPrice);
		paramMap.put("min", minPrice);
		return sqlSession.selectList("aa.bb.cc.queryLike",paramMap);
	}

	// mybatis实现数据入库的功能
	public int delete(int id) {
		// 1: 获取SqlSession
		SqlSession sqlSession = null;
		// sqlSesison默认是开启了事务,但是需要自己手动提交,而且出了异常需要回滚
		try {
			sqlSession = SqlSessionFactoryUtils.getSqlSession();
			// 2: 执行相关操作,此处传入的参数,必须与parameterType="model.Product"相同
			int count = sqlSession.delete("aa.bb.cc.delete", id);
			// Integer.parseInt("abc");
			sqlSession.commit(); // 手动提交(此处是编程式事务)
			return count;
		} catch (Exception e) {
			sqlSession.rollback();
			throw new RuntimeException(e);
		} finally {
			// 3: 释放资源
			SqlSessionFactoryUtils.closeSession();
		}
	}
}
