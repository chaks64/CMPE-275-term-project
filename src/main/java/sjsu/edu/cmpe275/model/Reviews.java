package sjsu.edu.cmpe275.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="reviews")
public class Reviews {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long reviewId;
	
	private int rating;
	private String review;
	private String reviewType;
	
//	@JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)  
    @JoinColumn(name = "userId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","reviews"}) 
	private User user;
	
	public Reviews() {
		// TODO Auto-generated constructor stub
	}

	public Reviews(long reviewId, int rating, String review, String reviewType, User user) {
		super();
		this.reviewId = reviewId;
		this.rating = rating;
		this.review = review;
		this.reviewType = reviewType;
		this.user = user;
	}

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
