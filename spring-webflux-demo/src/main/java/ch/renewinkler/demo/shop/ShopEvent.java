package ch.renewinkler.demo.shop;

public class ShopEvent {

    private Long id;

    private String eventType;

    public ShopEvent(Long id, String eventType) {
        this.id = id;
        this.eventType = eventType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}