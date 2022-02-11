package DAO;

import model.ShoppingCart;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ShoppingCartDAOImplementation implements ShoppingCartDAO {


	private static ShoppingCartDAOImplementation INSTANCE;

	public static ShoppingCartDAOImplementation getInstance() {
		if (INSTANCE == null) INSTANCE = new ShoppingCartDAOImplementation();
		return INSTANCE;
	}


	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/shopping_practice",
				"root",
				"root");
	}

	@Override
	public ShoppingCart save(ShoppingCart shoppingCart) {
		try {
			Connection conn = getConnection();
			PreparedStatement save = conn.prepareStatement("INSERT INTO shopping_cart(last_update, order_status, delivery_address, customer_reference) " +
					"VALUES (?,?,?,?) ");

			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			save.setString(1, shoppingCart.getLastUpdate().format(format));
			save.setString(2, shoppingCart.getOrderStatus());
			save.setString(3, shoppingCart.getDeliveryAddress());
			save.setString(4, shoppingCart.getCustomerReference());

			save.executeUpdate();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return shoppingCart;
	}

	@Override
	public Optional<ShoppingCart> findById(int id) {
		ShoppingCart cart = new ShoppingCart();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping.cart WHERE id = ?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				cart.setId(id);
				cart.setLastUpdate(LocalDateTime.parse(rs.getString("last_update")));
				cart.setOrderStatus(rs.getString("order_status"));
				cart.setDeliveryAddress(rs.getString("deliver_address"));
				cart.setCustomerReference(rs.getString("customer_reference)"));
			}
			rs.close();
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.of(cart);
	}

	@Override
	public List<ShoppingCart> findAll() {
		List<ShoppingCart> shoppingCartList = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping.cart");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				shoppingCartList.add(new ShoppingCart(
						rs.getInt("id"),
						LocalDateTime.parse(rs.getString("last_update")),
						rs.getString("order_status"),
						rs.getString("deliver_address"),
						rs.getString("customer_reference)"),
						false
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppingCartList;
	}

	@Override
	public List<ShoppingCart> findByOrderStatus(String status) {
		List<ShoppingCart> shoppingCartList = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping.cart WHERE order_status = ?");
			ps.setString(1, status);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				shoppingCartList.add(new ShoppingCart(
						rs.getInt("id"),
						LocalDateTime.parse(rs.getString("last_update")),
						rs.getString("order_status"),
						rs.getString("deliver_address"),
						rs.getString("customer_reference)"),
						false
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppingCartList;
	}

	@Override
	public List<ShoppingCart> findByReference(String customer) {
		List<ShoppingCart> shoppingCartList = new ArrayList<>();

		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping.cart WHERE customer_reference = ?");
			ps.setString(1, customer);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				shoppingCartList.add(new ShoppingCart(
						rs.getInt("id"),
						LocalDateTime.parse(rs.getString("last_update")),
						rs.getString("order_status"),
						rs.getString("deliver_address"),
						rs.getString("customer_reference)"),
						false
				));
			}

			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppingCartList;
	}


	@Override
	public void delete(int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM shopping.cart WHERE id = ?");
			ps.setInt(1, id);

			ps.executeUpdate();
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
