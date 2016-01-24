import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class Grades {

    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Creating Browser
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);

        // LoginGUI Page
        HtmlPage page = webClient.getPage(Input.getUrl());

        HtmlTextInput username = page.getHtmlElementById("username");
        HtmlPasswordInput password = page.getHtmlElementById("password");

        String usernamestring = Input.getUsername();
        String passwordstring = Input.getPassword();

        username.setValueAttribute(usernamestring);
        password.setValueAttribute(passwordstring);

        HtmlElement loginbutton = page.getHtmlElementById("signinbtn");
        // Page after LoginGUI
        HtmlPage waitingpage = loginbutton.click();

        // Page with frame loaded
        HtmlPage framepage = webClient.getPage("https://ic.d214.org/campus/portal/portal.xsl?x=portal.PortalOutline&lang=en&mode=notices");
        webClient.waitForBackgroundJavaScript(500);
        HtmlElement gradeslink = framepage.getHtmlElementById("grades");

        // Grades Page
        HtmlPage gradespage = gradeslink.click();
        webClient.waitForBackgroundJavaScript(500);

        List <HtmlTableCell> gradesdata = (List<HtmlTableCell>) gradespage.getByXPath("//fieldset/table/tbody/tr/td[2]/table/tbody/tr[6]/td[2]");
        List <HtmlElement> subject = (List<HtmlElement>) gradespage.getByXPath("//fieldset/table/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/a/b");

        List <HtmlTableCell> recentgradessubject = (List<HtmlTableCell>) gradespage.getByXPath("/html/body/div/div/div[2]/div[2]/div/div[2]/table/tbody/tr/td[3]");
        List <HtmlAnchor> recentgradesassignment = (List<HtmlAnchor>) gradespage.getByXPath("/html/body/div/div/div[2]/div[2]/div/div[2]/table/tbody/tr/td[4]/a");
        List <HtmlTableCell> recentgradespercentage = (List<HtmlTableCell>) gradespage.getByXPath("/html/body/div/div/div[2]/div[2]/div/div[2]/table/tbody/tr/td[7]");

// Console Output
        int i = 0;
        while(i<=gradesdata.size()-1){
            if(gradesdata.get(i).asText().length() == 8) {
                System.out.format("%30s%65s", subject.get(i).asText(), gradesdata.get(i).asText());
                System.out.println();
                i++;
            }
            else
                i++;
        }

        System.out.println();

        boolean redo = true;
        System.out.println("Recent Assignments - ");

        for (i = 0; i < recentgradessubject.size(); i++) {
            System.out.format("%25s%50s%25s", recentgradessubject.get(i).asText(), recentgradesassignment.get(i).asText(), recentgradespercentage.get(i).asText());
            System.out.println();
        }
    }
}