/**
 * Display the review form if the user is logged in.
 */
async function displayReviewForm() {
  const response = await fetch("/authentication");
  const userJson = await response.json();
  if (userJson.userEmail != "") {
    document.getElementsByClassName("review-form")[0].style.visibility = "visible";
  }
}

/**
 * Fetches reviews from ReviewsServlet and displays them in sections under the appropriate business page.
 */
async function getReviews() {
  var reviewsList = await getReviewData();
  /** Display reviews in sections. */
  if (reviewsList !== null) {
    var reviewsContainer = document.getElementById('review-grid');
    for (let i=0; i<reviewsList.length; i++) {
      var reviewSection = createReviewSection(reviewsList[i]);
      reviewsContainer.appendChild(reviewSection);
    }
  }

  setReviewsActionUrl();
  setupRatingActions();
}

/**
 * Fetch review data based on the URL parameter. Fetches mock data if parameter is not present.
 */
async function getReviewData() {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  if (urlParams.has("businessID")) {

    const response = await fetch(`/reviews?businessID=${urlParams.get("businessID")}`);
    var reviewsList = await response.json();
    return reviewsList;
  } else {
    let response = await fetch('/mockdatareview');
    let mockdata = await response.json();
    return mockdata;
  }
}

/**
 * Sets URL-parameter populated URL for review form action.
 */
function setReviewsActionUrl(){
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  if (urlParams.has("businessID")) {
    document.getElementById("review-form-container").action = `/reviews?businessID=${urlParams.get("businessID")}`;
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
  var starHTML = '';
  for (let i=0; i<5; i++) {
    if (i<rating) {
      starHTML += '<i class="fas fa-star yellow-star"></i>';
    } else {
      starHTML += '<i class="fas fa-star"></i>';
    }
  }
  return starHTML;
}

function setupRatingActions(){
  for (let i=1; i<=5; i++) {
    document.getElementById("rating-" + i).addEventListener("click", function(){ rateReview(i);});
  }

}

function rateReview(rating){
  document.getElementById("star-rating").value = rating;
  for (let i=1; i<=5; i++) {
    if (i <= rating){
      document.getElementById("rating-" + i).className = "fas fa-star yellow-star";
    } else {
      document.getElementById("rating-" + i).className = "fas fa-star";
    }
    
  }
}