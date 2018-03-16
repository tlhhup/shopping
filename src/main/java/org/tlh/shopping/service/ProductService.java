package org.tlh.shopping.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tlh.shopping.entity.Product;
import org.tlh.shopping.util.PageInfo;

@Transactional(readOnly = true)
@Service("productService")
public class ProductService {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = false)
	public int insert(Product product) {
		String sql = "insert into products(name,description,price) values(?,?,?)";
		return this.jdbcTemplate.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, product.getName());
				ps.setString(2, product.getDescription());
				ps.setFloat(3, product.getPrice());
			}

		});
	}
	
	@Transactional(readOnly=false)
	public int delete(int id){
		String sql="delete from products where id =?";
		return this.jdbcTemplate.update(sql, new Object[]{id});
	}
	
	public List<Product> findAll(){
		String sql="select * from products";
		return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<Product>(Product.class));
	}

	public PageInfo<Product> findAllWithPage(int number, int size) {
		String sql="select count(*) from products";
		Integer total = this.jdbcTemplate.queryForObject(sql, Integer.class);
		
		PageInfo<Product> pageInfo=new PageInfo<Product>();
		pageInfo.setTotal(total);
		pageInfo.setNumber(number);
		pageInfo.setSize(size);
		
		sql="select * from products limit ?,?";
		List<Product> data = this.jdbcTemplate.query(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pageInfo.getFrom());
				ps.setInt(2, pageInfo.getSize());
			}
		}, new BeanPropertyRowMapper<Product>(Product.class));
		pageInfo.setContent(data);
		
		return pageInfo;
	}

}
