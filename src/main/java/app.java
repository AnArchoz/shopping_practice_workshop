import DAO.ShoppingCartDAOImplementation;
import model.ShoppingCart;

import java.time.LocalDateTime;

public class app {
	public static void main(String[] args) {
		ShoppingCartDAOImplementation shoppingCartDBO = ShoppingCartDAOImplementation.getInstance();
		ShoppingCart cart = new ShoppingCart(0, LocalDateTime.now(), "TBD", "Derpvägen 4", "Klanto", true);

		shoppingCartDBO.save(cart);
	}
}
