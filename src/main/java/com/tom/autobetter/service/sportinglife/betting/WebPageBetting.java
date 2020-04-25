//package com.tom.autobetter.service.sportinglife.betting;
//
//import com.amazonaws.services.dynamodbv2.xspec.S;
//import com.tom.autobetter.util.CommonConstants;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//
//import javax.print.Doc;
//import java.io.IOException;
//import java.util.HashMap;
//
//import static com.tom.autobetter.util.CommonConstants.PASS_TWO;
//
//@Service
//public class WebPageBetting {
//
//    final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
//    final String BASE_URL = "https://sports.williamhill.com";
//    final String HORSE_RACE_URL = "/betting/en-gb/horse-racing/meetings";
//    final String LOGIN_ACTION_URL = "/bet/en-gb";
//    final String USERNAME = "tomeyre";
//    final String PASSWORD = PASS_TWO;
//
////page1 = webClient.getPage("https://sports.williamhill.com/betting/en-gb");
////
////// Get the form that we are dealing with and within that form,
////// find the submit button and the field that we want to change.
////final HtmlForm form = page1.getFormByName("account");
////
////final HtmlSubmitInput button = form.getInputByName("loginButton");
////final HtmlTextInput usernameField = form.getInputByName("loginUsernameInput");
////final HtmlTextInput passwordField = form.getInputByName("loginUsernameInput");
////
////// Change the value of the text field
////usernameField.setValueAttribute("tomeyre");
////passwordField.setValueAttribute(CommonConstants.PASS_TWO);
//
//
//    public void placeBet() {
//
//        try{
//            Connection.Response loginForm = Jsoup.connect(BASE_URL + HORSE_RACE_URL)
//                    .method(Connection.Method.GET)
//                    .userAgent(USER_AGENT)
//                    .execute();
//
//            Document loginDoc = loginForm.parse(); // this is the document containing response html
//            HashMap<String, String> cookies = new HashMap<>(loginForm.cookies()); // save the cookies to be passed on to next request
//
//            HashMap<String, String> formData = new HashMap<>();
//            formData.put("loginUsernameInput", USERNAME);
//            formData.put("loginPasswordInput", PASSWORD);
//
//            Connection.Response homePage = Jsoup.connect(BASE_URL + HORSE_RACE_URL)
//                    .cookies(cookies)
//                    .data(formData)
//                    .method(Connection.Method.POST)
//                    .userAgent(USER_AGENT)
//                    .execute();
//
//            Document horseDoc = homePage.parse();
//
//            System.out.println(horseDoc);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        // # Go to login page and grab cookies sent by server
////        Connection.Response loginForm = null;
////        try {
////            loginForm = Jsoup.connect(BASE_URL + LOGIN_FORM_URL)
////                    .method(Connection.Method.GET)
////                    .userAgent(USER_AGENT)
////                    .execute();
////
////            Document loginDoc = loginForm.parse(); // this is the document containing response html
////            HashMap<String, String> cookies = new HashMap<>(loginForm.cookies()); // save the cookies to be passed on to next request
////
////            HashMap<String, String> formData = new HashMap<>();
////            formData.put("loginUsernameInput", USERNAME);
////            formData.put("loginPasswordInput", PASSWORD);
////
////// # Now send the form for login
////            Connection.Response homePage = Jsoup.connect(BASE_URL + LOGIN_ACTION_URL)
////                    .cookies(cookies)
////                    .data(formData)
////                    .method(Connection.Method.POST)
////                    .userAgent(USER_AGENT)
////                    .execute();
////
////            Document homePageDoc = homePage.parse();
////
////            Elements links = homePageDoc.select("li[id~=nav]");
////
////            System.out.println("start");
////            for (Element link : links) {
////                if(link.text().equalsIgnoreCase("horses")) {
////                    System.out.println("https://sports.williamhill.com/betting/en-gb/horse-racing/meetings");
////                    System.out.println(BASE_URL + link.children().attr("href"));
////                    Connection.Response horsePage = Jsoup.connect(BASE_URL + link.children().attr("href"))
////                            .cookies(cookies)
////                            .method(Connection.Method.GET)
////                            .userAgent(USER_AGENT)
////                            .execute();
////                    Document horsePageDoc = horsePage.parse();
////
////                    Elements races = homePageDoc.select("div[class~=]");
////                    for(Element race : races){
////                        System.out.println(race.text() + race.attr("href"));
//////                        System.out.println(link.children().attr("href"));
//////                        System.out.println(link.text());
////                    }
//////                    System.out.println(homePageDoc.html());
////                }
////            }
////            System.out.println("end");
//
////            System.out.println(homePageDoc.html());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//}