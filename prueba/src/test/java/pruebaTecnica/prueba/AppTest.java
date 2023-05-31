package pruebaTecnica.prueba;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.opencsv.exceptions.CsvValidationException;

import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@Mock
	private ProductController productController;

	@BeforeEach
	public void setup() {
		productController = new ProductController();
	}

	@Test
	public void testGetVisibleProducts() throws CsvValidationException, NumberFormatException {
		// Mock los datos de los CSV para los tests
		List<Product> products = Arrays.asList(new Product(1, 10), new Product(2, 7), new Product(3, 15),
				new Product(4, 13), new Product(5, 6));

		List<Size> sizes = Arrays.asList(new Size(11, 1, true, false), new Size(12, 1, false, false),
				new Size(13, 1, true, false), new Size(21, 2, false, false), new Size(22, 2, false, false),
				new Size(23, 2, true, true), new Size(31, 3, true, false), new Size(32, 3, true, false),
				new Size(33, 3, false, false), new Size(41, 4, false, false), new Size(42, 4, false, false),
				new Size(43, 4, false, false), new Size(44, 4, true, true), new Size(51, 5, true, false),
				new Size(52, 5, false, false), new Size(53, 5, false, false), new Size(54, 5, true, true));

		List<Stock> stocks = Arrays.asList(new Stock(11, 0), new Stock(12, 0), new Stock(13, 0), new Stock(22, 0),
				new Stock(31, 10), new Stock(32, 10), new Stock(33, 10), new Stock(41, 0), new Stock(42, 0),
				new Stock(43, 0), new Stock(44, 10), new Stock(51, 10), new Stock(52, 10), new Stock(53, 10),
				new Stock(54, 10));

		Mockito.when(productController.readProductsFromCsv()).thenReturn(products);
		Mockito.when(productController.readSizesFromCsv()).thenReturn(sizes);
		Mockito.when(productController.readStocksFromCsv()).thenReturn(stocks);

		// Configurar los datos de prueba en el controlador
		// Puedes hacer esto mediante el uso de Mockito o establecer directamente los
		// valores

		// Obtener los productos visibles
		String visibleProducts = productController.getVisibleProducts();

		// Comprobar que los productos visibles son los esperados
		Assertions.assertEquals("5,1,3", visibleProducts);

	}
}
