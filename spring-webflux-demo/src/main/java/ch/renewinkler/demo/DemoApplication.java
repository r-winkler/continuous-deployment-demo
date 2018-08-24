package ch.renewinkler.demo;

import ch.renewinkler.demo.product.Product;
import ch.renewinkler.demo.product.ProductHandler;
import ch.renewinkler.demo.product.ProductRepository;
import ch.renewinkler.demo.shop.Shop;
import ch.renewinkler.demo.shop.ShopRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductRepository productRepository, ShopRepository shopRepository) {
        return args -> {
            Flux<Product> productFlux = Flux.just(
                    new Product("Banana", 1.30),
                    new Product("Orange", 0.8),
                    new Product("Strawberry", 1.15))
                    .map(productRepository::save);


            productFlux.subscribe();

            productRepository.findAll().stream().forEach(System.out::println);


            Flux<Shop> shopFlux = Flux.just(
                    new Shop("Green Perl", 50.6),
                    new Shop("Big O", 120.3),
                    new Shop("Fruit Mart", 70.8))
                    .map(shopRepository::save);


            shopFlux.subscribe();

            shopRepository.findAll().stream().forEach(System.out::println);
        };
    }


    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
        return route(GET("/products").and(accept(MediaType.APPLICATION_JSON)), productHandler::getAllProducts)
                .andRoute(POST("/products").and(accept(MediaType.APPLICATION_JSON)), productHandler::saveProduct)
                .andRoute(DELETE("/products").and(accept(MediaType.APPLICATION_JSON)), productHandler::deleteAllProducts)
                .andRoute(GET("/products/events").and(accept(MediaType.TEXT_EVENT_STREAM)), productHandler::getProductEvents)
                .andRoute(GET("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productHandler::getProduct)
                .andRoute(PUT("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productHandler::updateProduct)
                .andRoute(DELETE("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productHandler::deleteProduct);

 /*       return nest(path("/products"),
                nest(accept(MediaType.APPLICATION_JSON).or(contentType(MediaType.APPLICATION_JSON)).or(accept(MediaType.TEXT_EVENT_STREAM)),
                        route(GET("/"), productHandler::getAllProducts)
                                .andRoute(method(HttpMethod.POST), productHandler::saveProduct)
                                .andRoute(method(HttpMethod.DELETE), productHandler::deleteAllProducts)
                                .andRoute(GET("/events"), productHandler::getProductEvents)
                                .andNest(path("/{id}"),
                                        route(method(HttpMethod.GET), productHandler::getProduct)
                                                .andRoute(method(HttpMethod.PUT), productHandler::updateProduct)
                                                .andRoute(method(HttpMethod.DELETE), productHandler::deleteProduct)
                                )
                )
        );
*/

    }
}
