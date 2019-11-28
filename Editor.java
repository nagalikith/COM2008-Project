package classesTest;

public class Editor extends User {
	
	private boolean chiefEditor;
	
	//functions
	
	public void registerAccademics() {
		//the chief editor makes other editors
		//one chief editor for each journal may self-register initially in the system and then controls
		//who is appointed to the board for that journal; 
		
		//supplies the initial pasd for them
	}
	
	public void passRole() {
		//pass role of chief editor to different editor
	}
	
	public void retire() {
		//an editor may retire (possibly temporarily, to avoid a conflict of interest) from the board for
		//a journal, so long as at least one chief editor remains on the board;
	}
	
	public void viewArticles() {
		//an editor may see a list of articles under consideration, about which they are entitled to take
		//a final decision, but which excludes any conflicts of interest;
	}
	
	public void submitVerdict() {
		// related to viewArticles. since they can only makes decision on the ones they can view
		//any editor on the board may make the final decision to publish or reject an article, according
		//to the “Champion and Detractor” rules;
		//a published article is added to the next edition in the current volume of the journal; 

	}
	
	public void delayArticle() {
		//if the next edition is already full, the editor may delay publishing to a subsequent edition;
	
	}
	
	public void publishJournal() {
		//the chief editor may publish the next edition of the journal when it is ready, thereby making
		//all the contained articles available to readers
	}
}
