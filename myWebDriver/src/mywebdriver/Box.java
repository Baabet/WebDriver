/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mywebdriver;

/**
 *
 * @author Baa
 */
class Box {
    public static final String TwitterNickPattern = "@\\w+";
    private String content;
    String[] words;

    public Box(String content) {
	this.content = content;
	this.words = content.split("[ ]");
    }

    protected int getNumber() throws Exception {
	String[] temp = content.split("[.]");
	if (temp[0].matches("\\d+")) {
	    return Integer.parseInt(temp[0]);
	} else {
	    throw new Exception("Info o leaderovi neobsahuje poradove cislo.");
	}
    }

    protected boolean correctLeaderContent() {
	return content.matches("^\\d+\\."	    // indexing
		+ "(\\s+\\S+){1,2}\\s+"		    // name
		+ "\\(" + TwitterNickPattern + "\\)"		    // twitter
		+ "\\s+\\d+\\s+learners\\s+[+-]\\s*\\d+\\s+bonus score$");   // counting
    }
    
    protected boolean correctLearnerContent() {
	return content.matches("^" + TwitterNickPattern + "\\s*$"); // counting
    }
}
