package demo;

public class Product implements Comparable<Product> {
    private String title;
    private String imageurl;
    private int numberOfReviews;

    public Product(String title, String imageurl, int numberOfReviews) {
        this.title = title;
        this.imageurl = imageurl;
        this.numberOfReviews = numberOfReviews;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    @Override
    public int compareTo(Product other) {
        // Compare by number of reviews in descending order
        // return Integer.compare(other.numberOfReviews, this.numberOfReviews);

        // sort in descnding order:
        return Integer.compare(this.numberOfReviews, other.numberOfReviews);
    }
}
