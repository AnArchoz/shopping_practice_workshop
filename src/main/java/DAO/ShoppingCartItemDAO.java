package DAO;

import model.ShoppingCartItem;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemDAO {
	ShoppingCartItem save(ShoppingCartItem cartItem);

	Optional<ShoppingCartItem> findById(int id);

	List<ShoppingCartItem> findAll();

	List<ShoppingCartItem> findByCartId(int id);

	List<ShoppingCartItem> findByProduct(int prodId);

	void delete(int id);

}
