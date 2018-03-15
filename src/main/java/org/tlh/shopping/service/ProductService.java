package org.tlh.shopping.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tlh.shopping.entity.Product;

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

}
