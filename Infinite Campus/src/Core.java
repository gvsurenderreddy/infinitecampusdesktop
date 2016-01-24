import java.io.Console;
import java.io.IOException;
import java.net.CookieStore;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.WebClientOptions;
public class Core {
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException{	
		Scanner sc = new Scanner(System.in);
		Console console = System.console();

		// Creating Browser
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);

		// LoginGUI Page
		HtmlPage page = webClient.getPage("https://ic.d214.org/campus/township_214.jsp");

		HtmlTextInput username = page.getHtmlElementById("username");
		HtmlPasswordInput password = page.getHtmlElementById("password");

/*		System.out.print("Username : ");
		String usernamestring = sc.nextLine();
		String passwordstring =new String(console.readPassword("Password [INPUT HIDDEN] : "));
		System.out.print("Password : ");
		passwordstring = sc.nextLine();*/

        String usernamestring = "jkoreth8180";
        String passwordstring = "Jk10351#";

		username.setValueAttribute(usernamestring);
		password.setValueAttribute(passwordstring);

		HtmlElement loginbutton = page.getHtmlElementById("signinbtn");
		// Page after LoginGUI
		HtmlPage waitingpage = loginbutton.click();

		// Page with frame loaded				
		HtmlPage framepage = webClient.getPage("https://ic.d214.org/campus/portal/portal.xsl?x=portal.PortalOutline&lang=en&mode=notices");
		webClient.waitForBackgroundJavaScript(500);
		HtmlElement gradeslink = framepage.getHtmlElementById("grades");

		boolean redo = true;
		String redostring = "Yes";
		while(redostring.equalsIgnoreCase("Yes")){
			// Grades Page		
			HtmlPage gradespage = gradeslink.click();
			webClient.waitForBackgroundJavaScript(500);

			List <HtmlTableCell> gradesdata = (List<HtmlTableCell>) gradespage.getByXPath("//fieldset/table/tbody/tr/td[2]/table/tbody/tr[6]/td[2]");
			List <HtmlElement> subject = (List<HtmlElement>) gradespage.getByXPath("//fieldset/table/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/a/b");
		
			System.out.println("Grades - ");
			
			int i=0;
			for (HtmlTableCell temp : gradesdata){
				if(temp.asText().length() != 2 ){
					System.out.print(subject.get(i).asText());
					System.out.print("\t" + temp.asText() + "\n");
					i++;
				}
			}
		
			List <HtmlTableCell> recentgradessubject = (List<HtmlTableCell>) gradespage.getByXPath("/html/body/div/div/div[2]/div[2]/div/div[2]/table/tbody/tr/td[3]");
			List <HtmlAnchor> recentgradesassignment = (List<HtmlAnchor>) gradespage.getByXPath("/html/body/div/div/div[2]/div[2]/div/div[2]/table/tbody/tr/td[4]/a");
			List <HtmlTableCell> recentgradespercentage = (List<HtmlTableCell>) gradespage.getByXPath("/html/body/div/div/div[2]/div[2]/div/div[2]/table/tbody/tr/td[7]");
			
			System.out.println("\n" + "Recent Assignments - ");
			
			for (i=20; i>=0; i--){
				System.out.print(recentgradessubject.get(i).asText());
				System.out.print("\t" + recentgradesassignment.get(i).asText());
				System.out.print("\n\t" + recentgradespercentage.get(i).asText() + "\n");
			}
			System.out.print("Again? (\"Yes\" or \"No\"): ");
			redostring = sc.nextLine();
		}
	}
}
