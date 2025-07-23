package feedbackservice;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public class FeedbackEntity {

    @Id
    private ObjectId id;
    private Integer rating;
    private String feedback;
    private String customer;
    private String product;
    private String vendor;

    public FeedbackEntity() {
    }

    public FeedbackEntity(Integer rating,
                          String customer,
                          String product,
                          String vendor) {
        this.rating = rating;
        this.customer = customer;
        this.product = product;
        this.vendor = vendor;
    }

    public ObjectId getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
