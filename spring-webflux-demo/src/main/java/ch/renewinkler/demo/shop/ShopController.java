package ch.renewinkler.demo.shop;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/shops")
public class ShopController {

    private ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping
    public Flux<Shop> getAllShops() {

        return Flux.fromIterable(this.shopRepository.findAll());
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<Shop>> getShop(@PathVariable String id) {


        return Mono.just(this.shopRepository.findById(Long.parseLong(id)).get())
                .map(shop -> ResponseEntity.ok(shop))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Shop> saveShop(@RequestBody Shop shop) {

        return Mono.just(this.shopRepository.save(shop));
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<Shop>> updateShop(@PathVariable String id, @RequestBody Shop shop) {

        return Mono.just(this.shopRepository.findById(Long.parseLong(id)).get())
                .map(existingShop -> {
                    existingShop.setName(shop.getName());
                    existingShop.setSize(shop.getSize());
                    return this.shopRepository.save(existingShop);
                })
                .map(updatedShop -> ResponseEntity.ok(updatedShop))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteShop(@PathVariable String id) {

        return Mono.just(this.shopRepository.findById(Long.parseLong(id)).get())
                .flatMap(existingShop -> {
                    this.shopRepository.delete(existingShop);
                    return Mono.just(ResponseEntity.ok().<Void>build());
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping
    public Mono<Void> deleteAllShops() {

        this.shopRepository.deleteAll();
        return Mono.empty();
    }


    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ShopEvent> getShopEvents() {

        return Flux.interval(Duration.ofSeconds(1))
                .map(val -> new ShopEvent(val, "Shop Event"));
    }

}
