package DAO;

import model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImplementation implements ProductDAO {

	private static ProductDAOImplementation INSTANCE;

	public static ProductDAOImplementation getInstance() {
		if (INSTANCE == null) INSTANCE = new ProductDAOImplementation();
		return INSTANCE;
	}

	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/shopping_practice",
				"root",
				"root");
	}


	@Override
	public Product save(Product product) {
		try {
			Connection conn = getConnection();
			PreparedStatement save = conn.prepareStatement("INSERT INTO shopping_practice.product(name, price) " +
					"VALUES (?,?) ");

			save.setString(1, product.getName());
			save.setBigDecimal(2, BigDecimal.valueOf(product.getPrice()));

			save.executeUpdate();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return product;
	}

	@Override
	public Optional<Product> findById(int id) {
		Product product = new Product();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.product WHERE id = ?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				product.setId(id);
				product.setName(rs.getString("name"));
				product.setPrice(rs.getBigDecimal("price").doubleValue());
			}

			rs.close();
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.of(product);
	}

	@Override
	public List<Product> findAll() {
		List<Product> productList = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.product");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productList.add(new Product(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getBigDecimal("price").doubleValue()
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList;
	}

	@Override
	public List<Product> findByName(String name) {
		List<Product> productList = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.product WHERE name = ?");
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productList.add(new Product(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getBigDecimal("price").doubleValue()
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList;
	}

	@Override
	public List<Product> findByPriceBetween(int low, int high) {
		List<Product> productList = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.product WHERE price BETWEEN ? AND ?");
			ps.setInt(1, low);
			ps.setInt(2, high);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productList.add(new Product(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getBigDecimal("price").doubleValue()
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList;
	}

	@Override
	public void delete(int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM shopping_practice.product WHERE id = ?");
			ps.setInt(1, id);

			ps.executeUpdate();
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

