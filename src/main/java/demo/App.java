package demo;
import java.net.MalformedURLException;
//import src.test.demo.wrappers;

public class App {
    public void getGreeting() throws InterruptedException, MalformedURLException {
        System.out.println("Hello Autmation Wizards!");
        // TestCases tc = new TestCases();
        // tc.testCase01();
    }

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        new App().getGreeting();
    }
}
