package ch.renewinkler.demo.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;


@Component
public class ProductHandler {

    private ProductRepository productRepository;

    public ProductHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {

        Flux<Product> productFlux = Flux.fromIterable(this.productRepository.findAll());

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productFlux, Product.class);

    }


    public Mono<ServerResponse> getProduct(ServerRequest request) {

        String id = request.pathVariable("id");

        Mono<Product> productMono = Mono.just(this.productRepository.findById(Long.parseLong(id)).get());
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono.flatMap(product ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(product)))
                .switchIfEmpty(notFound);
    }


    public Mono<ServerResponse> saveProduct(ServerRequest request) {

        Mono<Product> productMono = request.bodyToMono(Product.class);

        return productMono.flatMap(product ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(this.productRepository.save(product)), Product.class));
    }


    public Mono<ServerResponse> updateProduct(ServerRequest request) {

        String id = request.pathVariable("id");

        Mono<Product> existingProductMono = Mono.just(this.productRepository.findById(Long.parseLong(id)).get());
        Mono<Product> productMono = request.bodyToMono(Product.class);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono.zipWith(existingProductMono,
                (product, existingProduct) -> new Product(existingProduct.getId(), product.getName(), product.getPrice()))
                .flatMap(product ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(this.productRepository.save(product)), Product.class))
                .switchIfEmpty(notFound);
    }


    public Mono<ServerResponse> deleteProduct(ServerRequest request) {

        String id = request.pathVariable("id");

        Mono<Product> productMono = Mono.just(this.productRepository.findById(Long.parseLong(id)).get());
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono
                .flatMap(existingProduct -> {
                    this.productRepository.delete(existingProduct);
                    return ServerResponse.ok().build(Mono.empty());
                })
                .switchIfEmpty(notFound);
    }


    public Mono<ServerResponse> deleteAllProducts(ServerRequest request) {

        this.productRepository.deleteAll();
        return ServerResponse.ok().build(Mono.empty());
    }


    public Mono<ServerResponse> getProductEvents(ServerRequest request) {

        Flux<ProductEvent> eventsFlux = Flux.interval(Duration.ofSeconds(1)).map(val -> new ProductEvent(val, "Product Event"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventsFlux, ProductEvent.class);
    }

}
