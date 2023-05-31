package pruebaTecnica.prueba;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

@RestController
public class ProductController {

	@GetMapping("/products")
    public String getVisibleProducts() throws CsvValidationException, NumberFormatException {
        List<Product> products = readProductsFromCsv();
        List<Size> sizes = readSizesFromCsv();
        List<Stock> stocks = readStocksFromCsv();

        List<Integer> visibleProductIds = new ArrayList<Integer>();

        for (Product product : products) {
            boolean hasVisibleSize = false;
            boolean hasSpecialSize = false;

            for (Size size : sizes) {
                if (size.getProductId() == product.getId()) {
                    Stock stock = findStockBySizeId(stocks, size.getId());

                    if (stock != null && stock.getQuantity() > 0) {
                        if (!size.isSpecial()) {
                            hasVisibleSize = true;
                        } else {
                            hasSpecialSize = true;
                        }
                    } else if (size.isBackSoon()) {
                        hasVisibleSize = true;
                    }
                }
            }

            if ((hasSpecialSize && hasVisibleSize) || (!hasSpecialSize && !hasVisibleSize)) {
                visibleProductIds.add(product.getId());
            }
        }

        // Ordena los identificadores de producto por campo sequence
        visibleProductIds.sort(Comparator.comparingInt(id -> findProductById(products, id).getSequence()));

        return String.join(",", visibleProductIds.stream().map(Object::toString).collect(Collectors.toList()));
    }

    // Método para leer productos desde el archivo product.csv
	public List<Product> readProductsFromCsv() throws CsvValidationException, NumberFormatException {
    	List<Product> products = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("product.csv"))) {
            String[] line;
            // Saltar la primera línea que contiene los encabezados
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                int id = Integer.parseInt(line[0]);
                int sequence = Integer.parseInt(line[1]);

                Product product = new Product(id, sequence);
                products.add(product);
            }
        } catch (IOException e) {
            // Manejar la excepción en caso de error de lectura del archivo
            e.printStackTrace();
        }

        return products;
    }

    // Método para leer tallas desde el archivo size.csv
    public List<Size> readSizesFromCsv() throws CsvValidationException {
    	// Método para leer tallas desde el archivo size.csv
            List<Size> sizes = new ArrayList<>();

            try (CSVReader reader = new CSVReader(new FileReader("size.csv"))) {
                String[] line;
                // Saltar la primera línea que contiene los encabezados
                reader.readNext();

                while ((line = reader.readNext()) != null) {
                    int id = Integer.parseInt(line[0]);
                    int productId = Integer.parseInt(line[1]);
                    boolean backSoon = Boolean.parseBoolean(line[2]);
                    boolean special = Boolean.parseBoolean(line[3]);

                    Size size = new Size(id, productId, backSoon, special);
                    sizes.add(size);
                }
            } catch (IOException e) {
                // Manejar la excepción en caso de error de lectura del archivo
                e.printStackTrace();
            }

            return sizes;
        }
    

    // Método para leer stocks desde el archivo stock.csv
    public List<Stock> readStocksFromCsv() throws CsvValidationException {
    	List<Stock> stocks = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("stock.csv"))) {
            String[] line;
            // Saltar la primera línea que contiene los encabezados
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                int sizeId = Integer.parseInt(line[0]);
                int quantity = Integer.parseInt(line[1]);

                Stock stock = new Stock(sizeId, quantity);
                stocks.add(stock);
            }
        } catch (IOException e) {
            // Manejar la excepción en caso de error de lectura del archivo
            e.printStackTrace();
        }

        return stocks;
    }

    // Método auxiliar para buscar un stock por su identificador de talla
    private Stock findStockBySizeId(List<Stock> stocks, int sizeId) {
    	for (Stock stock : stocks) {
            if (stock.getSizeId() == sizeId) {
                return stock;
            }
        }

        return null; 
    }

    // Método auxiliar para buscar un producto por su identificador
    private Product findProductById(List<Product> products, int productId) {
    	for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }

        return null; 
    }
}
