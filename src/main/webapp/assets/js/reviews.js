/**
 * Fetches reviews from backend and displays them in sections under the appropriate business page.
 */
async function getReviews() {
  const response = await fetch('/mockdatareview'); // Fetching mock data for now, to be changed
  var reviewsList = await response.text();

  /** Display reviews in sections. */
  reviewsList = JSON.parse(reviewsList)
  var reviewsContainer = document.getElementById('reviews')
  for (i=0; i<reviewsList.length; i++) {
    var reviewSection = createReviewSection(reviewsList[i]);
    reviewsContainer.appendChild(reviewSection);
  }
}

/**
 * Returns the formatted review section.
 */
function createReviewSection(review) {
  const reviewWrapper = document.createElement("div");
  reviewWrapper.class = "review-grid";

  reviewWrapper.innerHTML =
    `<div class="review-card">
    <div class="review-card-container">
      <div class="name-and-date">
        <p>${review.userID}</p>
        <div class="rating">
          <i class="fas fa-star yellow-star"></i>
          <i class="fas fa-star yellow-star"></i>
          <i class="fas fa-star yellow-star"></i>
          <i class="fas fa-star yellow-star"></i>
          <i class="fas fa-star yellow-star"></i>
        </div>
        <p>${review.dateTime}</p>
      </div>
      <p>${review.comment}</p>
    </div>
    </div>`;

  return reviewWrapper;
}
