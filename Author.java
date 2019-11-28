package classesTest;

public class Author extends User {
	
	private boolean mainAuthor;
	
	public void submitArticle() {
		//one main author is in charge of submitting an article for consideration
	}
	
	public void submitRevisedArticle() {
		
	}
	
	public void registerCoAuthor() {
		//and by submitting,
		//they self-register as an author and register all the co-authors of the article;	
	}
	
	public void trackStatusArticle() {
		//all authors may log in to track the status of their submitted article, until the editor dealing
		//with this submission decides to accept or reject the article;
	}
	
	//all logged-in authors can see the reviews (of their own submission), the initial verdicts, their
	//responses, the final verdicts and the initial and revised versions of their article;
	
	public void checkReview() {
		
	}
	
    public void checkInitialVerdict() {
		
	}
    
    public void checkFinalVerdict() {
		
	}
    
    public void checkInitialArticle() {
		
	}
    
    public void checkFinalArticle() {
		
	}
    
    public void checkResponse() {
		//responses to the reviews
	}
    
    public void respondToReviews() {
    	//make an oblect of class response
    	
    	//one main author is responsible for responding to the criticisms expressed in the reviews and
    	//for submitting the revised article, along with a response to each reviewer;
    	//each response must include a list of answers to each of that reviewer’s questions, where the
    	//review contained a list of questions (or criticisms).
    }
}
