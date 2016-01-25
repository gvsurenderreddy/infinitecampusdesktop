import java.io.*;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import org.apache.commons.io.FileUtils;

public class Core2 {
	private static PrintWriter usernamefile;
	private static PrintWriter passwordfile;

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException{
		Scanner scanner = new Scanner(System.in);

		// Creating Browser
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);

		// LoginGUI Page
		HtmlPage page = webClient.getPage("https://ic.d214.org/campus/township_214.jsp");

		HtmlTextInput username = page.getHtmlElementById("username");
		HtmlPasswordInput password = page.getHtmlElementById("password");
		
		String usernamestring = getUsername();
		String passwordstring = getPassword();
		
		username.setValueAttribute(usernamestring);
		password.setValueAttribute(passwordstring);
		
		HtmlElement loginbutton = page.getHtmlElementById("signinbtn");
		// Page after LoginGUI
		HtmlPage waitingpage = loginbutton.click();

		// Page with frame loaded				
		HtmlPage framepage = webClient.getPage("https://ic.d214.org/campus/portal/portal.xsl?x=portal.PortalOutline&lang=en&mode=notices");
		webClient.waitForBackgroundJavaScript(500);
		HtmlElement gradeslink = framepage.getHtmlElementById("grades");
		
		while(true){
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
			for (i=5; i>=0; i--){
				System.out.print(recentgradessubject.get(i).asText());
				System.out.print("\t" + recentgradesassignment.get(i).asText());
				System.out.print("\n\t" + recentgradespercentage.get(i).asText() + "\n");
			}
			
			Thread.sleep(60 * 1000);
		}
	}
	public static String getUsername() throws IOException{
		String username;
		String inputsave;
		File exists = new File("./.username.txt");
		Scanner scanner = new Scanner(System.in);

		if(!exists.exists()){
			System.out.print("Username :");
			username = scanner.nextLine();
			System.out.print("Do You Want to Save ? (\"Yes\" or \"No\") : ");
			inputsave = scanner.nextLine();

			if (inputsave.equalsIgnoreCase("Yes")) {
				if (usernamefile == null) {
					usernamefile = new PrintWriter(".username.txt");
					usernamefile.write(username);
					usernamefile.close();
				}
				return username;
			}
			else{
				return username;
			}
		}
		else{
			String usernamesaved = null;
			Path file = FileSystems.getDefault().getPath(".username.txt");
			try (InputStream in = Files.newInputStream(file);
					BufferedReader reader =
							new BufferedReader(new InputStreamReader(in))) {
				usernamesaved = reader.readLine();
			} catch (IOException x) {
				System.err.println(x);
			}
			return usernamesaved;
		}
	}
	public static String getPassword() throws FileNotFoundException{
		String password;
		String inputsave;
		File exists = new File("./.password.txt");
		Scanner scanner = new Scanner(System.in);

		if(!exists.exists()){
			System.out.print("Password :");
			password = scanner.nextLine();
			System.out.print("Do You Want to Save ? (\"Yes\" or \"No\") : ");
			inputsave = scanner.nextLine();

			if (inputsave.equalsIgnoreCase("Yes")) {
				if (passwordfile == null) {
					passwordfile = new PrintWriter(".password.txt");
					passwordfile.write(password);
					passwordfile.close();
				}
				return password;
			}
			else{
				return password;
			}
		}
		else{
			String passwordsaved = null;
			Path file = FileSystems.getDefault().getPath(".password.txt");
			try (InputStream in = Files.newInputStream(file);
					BufferedReader reader =
							new BufferedReader(new InputStreamReader(in))) {
				passwordsaved = reader.readLine();
			} catch (IOException x) {
				System.err.println(x);
			}
			return passwordsaved;
		}
	}
}
