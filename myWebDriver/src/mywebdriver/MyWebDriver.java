/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mywebdriver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author Baa
 */
public class MyWebDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
	String baseURL;
	if (args.length > 0) {
	    baseURL = args[0];
	} else {
	    baseURL = "http://beta.topicleaders.com";
	}
	// Create a new instance of the html unit driver
	WebDriver driver = new HtmlUnitDriver();

	try {
	    driver.get(baseURL
		    + "/cs/vyvojari");
	} catch (IllegalArgumentException e) {
	    e.getMessage();
	}

	checkLeaders(driver);

	checkLearners(driver);
    }

    /**
     * 
     * @param driver the HtmlUnitDriver of site instantiated in main
     * @throws Exception if box does not contain the number of leader
     */
    protected static void checkLeaders(WebDriver driver) throws Exception {

	int myIndex = 0;
	String text;
	String text2;
	List<WebElement> content;
	List<WebElement> content2;
	WebElement heading;
	Box box;

	//Find the div element by its id
	WebElement leaders = driver.findElement(By.xpath("//div[@id='leaders']"));
	// Find the leaders as a children of div id=leaders
	List<WebElement> leaderBoxes = leaders.findElements(By.xpath("./div[@class='leaderBox' and @data-username]"));
	System.out.println("V prostrednim sloupci \"leaders\" je " + leaderBoxes.size() + " uzivatelu.\n");
	int[] indexing = new int[leaderBoxes.size()];

	boolean leaderBoxesOK = true;
	for (WebElement leaderBox : leaderBoxes) {
	    // get the heading of leaderbox
	    content = leaderBox.findElements(By.xpath("./descendant::td[@class='info']/h3"));
	    heading = content.get(0);
	    text = heading.getText();
	    // get the counting in span
	    content2 = leaderBox.findElements(By.xpath("./descendant::td[@class='info']/span"));
	    heading = content2.get(0);
	    text2 = heading.getText();
	    text = text + " " + text2;
	    // check the entire contents of leaderBox
	    box = new Box(text);
	    indexing[myIndex] = box.getNumber();
	    if (box.correctLeaderContent()) {
		//log(myIndex + ". leaderBox odpovida vyzadovanemu obsahu.");
	    } else {
		leaderBoxesOK = false;
		log(myIndex + ". leaderBox leadera " + leaderBox.getAttribute("username")
			+ " neodpovida. Text infa je: " + text);
	    }
	    myIndex++;
	}
	if (leaderBoxesOK) {
	    log("Obsah vsech leaderBoxu odpovida pozadovanemu obsahu.");
	}

	// check numbering of leaders
	boolean countingOK = true;
	for (int i = 0; i < indexing.length; i++) {
	    if (indexing[i] != i + 1) {
		log("Neodpovida poradi leaderu na pozici " + i + 1);
		countingOK = false;
	    }
	}
	if (countingOK) {
	    log("Poradi leaderu je v poradku.");
	}
    }

    private static void log(String msg) {
	System.out.println(msg);
    }

    /**
     * 
     * @param driver the HtmlUnitDriver of site instantiated in main
     */
    protected static void checkLearners(WebDriver driver) {

	int myIndex = 0;
	String text;
	List<WebElement> content;
	WebElement heading;
	Box box;

	//Find the div element by its id
	WebElement learners = driver.findElement(By.xpath("//div[@id='learners']"));
	// Find the learners as a grandchildren of div id=learners
	List<WebElement> learnerItems = learners.findElements(By.xpath("./ul/li"));
	System.out.println("V pravem sloupci \"learners\" je " + learnerItems.size() + " uzivatelu.");

	boolean learnerContentOK = true;
	for (WebElement learnerItem : learnerItems) {
	    // get the text of learner item
	    content = learnerItem.findElements(By.xpath("./a")); // must be a reference
	    heading = content.get(0);
	    text = heading.getText();
	    box = new Box(text);
	    List<WebElement> content2 = learnerItem.findElements(By.xpath("./span"));
	    WebElement heading2 = content2.get(0);
	    String text2 = heading2.getText();
	    boolean isNumber = text2.matches("^\\d+$");
	    if (box.correctLearnerContent() && isNumber) {
		//log(myIndex + ". learnerItem odpovida vyzadovanemu obsahu.");
	    } else {
		learnerContentOK = false;
		log(myIndex + ". learnerItem learnera neodpovida. Text je: " + text);
	    }
	    myIndex++;
	}
	if (learnerContentOK) {
	    log("Obsah vsech odrazek s learnery odpovida zadanemu vzoru.");
	}
    }
}
