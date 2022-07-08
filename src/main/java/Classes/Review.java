package Classes;

import java.sql.Date;

public class Review {
    private Long id;
    private String text;
    private Date date;
    private Integer nrStars;
    public User user;
    public Restaurant restaurant;
    private static Long ID = 0L;

    public Review() {}
    public Review(Long id, String text, Date date, Integer nrStars) {
        this.text = text;
        this.date = date;
        this.nrStars = nrStars;
        this.id = id;
    }

    public static class Builder {

        private Review review = new Review();

        public Review build() {
            return this.review;
        }

        public Builder(User user, Restaurant restaurant, Integer nrStars) {
            review.user = user;
            review.nrStars = nrStars;
            review.restaurant = restaurant;
        }

        public Builder withText(String text) {
            review.text = text;
            return this;
        }

        public Builder withDate(Date date) {
            review.date = date;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNr_stars() {
        return nrStars;
    }

    public void setNr_stars(Integer nrStars) {
        this.nrStars = nrStars;
    }

    public Review clone() throws CloneNotSupportedException
    {
        Review review = new Review();
        review.user = user; // Here it's not cloning the user.
        review.text=text;
        review.date = date;
        review.nrStars = nrStars;
        review.restaurant = restaurant;
        return review;
    }
}
