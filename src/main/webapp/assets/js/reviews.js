/**
 * Fetches reviews from backend and displays them in sections under the appropriate business page.
 */
async function getReviews() {
  const response = await fetch('/mockdatareview'); // Fetching mock data for now, to be changed
  var reviewsList = await response.text();

  /** Display reviews in sections. */
  reviewsList = JSON.parse(reviewsList)
  var reviewsContainer = document.getElementById('review-grid');
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
  reviewWrapper.className = "review-card";
  
  let stars = createStars(review.rating);
  reviewWrapper.innerHTML =
    `<div class="review-card-container">
      <div class="name-and-date">
        <p>${review.userID}</p>
        <div class="rating">${stars}</div>
        <p>${review.dateTime}</p>
      </div>
      <p>${review.comment}</p>
    </div>`;

  return reviewWrapper;
}

function createStars(rating) {
  var i=0;
  var starHTML = '';
  for (; i<rating; i++) {
    starHTML += '<i class="fas fa-star yellow-star"></i>';
  }
  while (i<5) {
    i += 1;
    starHTML += '<i class="fas fa-star"></i>';
  }

  return starHTML;
}
