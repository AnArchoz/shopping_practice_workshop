package DAO;

import model.Product;
import model.ShoppingCart;
import model.ShoppingCartItem;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCartItemDAOImplementation implements ShoppingCartItemDAO {

	private static ShoppingCartItemDAOImplementation INSTANCE;

	public static ShoppingCartItemDAOImplementation getInstance() {
		if (INSTANCE == null) INSTANCE = new ShoppingCartItemDAOImplementation();
		return INSTANCE;
	}

	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/shopping_practice",
				"root",
				"root");
	}


	@Override
	public ShoppingCartItem save(ShoppingCartItem cartItem) {
		try {
			Connection conn = getConnection();
			PreparedStatement save = conn.prepareStatement("INSERT INTO shopping_practice.shopping_cart_item(amount, total_price, product_id, shopping_cart_id) " +
					"VALUES (?,?,?,?) ");


			save.setInt(1, cartItem.getAmount());
			save.setBigDecimal(2, BigDecimal.valueOf(cartItem.getTotalPrice()));
			save.setInt(3, cartItem.getItem().getId());
			save.setInt(4, cartItem.getCart().getId());

			save.executeUpdate();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return cartItem;
	}

	@Override
	public Optional<ShoppingCartItem> findById(int id) {
		ShoppingCartItem cartItem = new ShoppingCartItem();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.shopping_cart_item WHERE id = ?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				cartItem.setId(id);
				cartItem.setAmount(rs.getInt("amount"));
				cartItem.setTotalPrice(rs.getBigDecimal("total_price").doubleValue());
				cartItem.setItem(new Product(rs.getInt("product_id"), null, 0));
				cartItem.setCart(new ShoppingCart(rs.getInt("shopping_cart_id"), null, null,
						null, null, false));
			}
			rs.close();
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.of(cartItem);
	}

	@Override
	public List<ShoppingCartItem> findAll() {

		List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.shopping_cart_item");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				shoppingCartItems.add(new ShoppingCartItem(
						rs.getInt("id"),
						rs.getInt("amount"),
						rs.getBigDecimal("total_price").doubleValue(),
						new Product(rs.getInt("product_id"), null, 0),
						new ShoppingCart(rs.getInt("shopping_cart_id"), null, null,
								null, null, false)
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppingCartItems;
	}

	@Override
	public List<ShoppingCartItem> findByCartId(int id) {
		List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.shopping_cart_item WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				shoppingCartItems.add(new ShoppingCartItem(
						rs.getInt("id"),
						rs.getInt("amount"),
						rs.getBigDecimal("total_price").doubleValue(),
						new Product(rs.getInt("product_id"), null, 0),
						new ShoppingCart(rs.getInt("shopping_cart_id"), null, null,
								null, null, false)
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppingCartItems;
	}

	@Override
	public List<ShoppingCartItem> findByProduct(int prodId) {

		List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_practice.shopping_cart_item WHERE product_id = ?");
			ps.setInt(1, prodId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				shoppingCartItems.add(new ShoppingCartItem(
						rs.getInt("id"),
						rs.getInt("amount"),
						rs.getBigDecimal("total_price").doubleValue(),
						new Product(rs.getInt("product_id"), null, 0),
						new ShoppingCart(rs.getInt("shopping_cart_id"), null, null,
								null, null, false)
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppingCartItems;
	}

	@Override
	public void delete(int id) {

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM shopping_practice.shopping_cart_item WHERE id = ?");
			ps.setInt(1, id);

			ps.executeUpdate();
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

