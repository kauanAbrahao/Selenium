package testeDeSistema;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class REQ01MantemLivrosTests {


    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://ts-scel-web.herokuapp.com/login");
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void ct01_livroCadastroSucesso() {
        driver.get("https://ts-scel-web.herokuapp.com/login");
        driver.manage().window().setSize(new Dimension(1616, 876));
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Livros")).click();
        driver.findElement(By.id("isbn")).click();
        driver.findElement(By.id("isbn")).sendKeys("4414");
        driver.findElement(By.id("autor")).click();
        driver.findElement(By.id("autor")).sendKeys("Pedro Pedroso");
        driver.findElement(By.id("titulo")).sendKeys("Teste Selenium para Iniciantes");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();

        assertEquals(("Lista de livros"), driver.findElement(By.id("paginaConsulta")).getText());
        assertTrue(driver.getPageSource().contains("Teste Selenium para Idiotas"));

        //Deleta o registro
        driver.findElement(By.linkText("Excluir")).click();
    }

    @Test
    @Order(2)
    public void ct02_livroAlteracaoAutorSucesso(){
        driver.get("https://ts-scel-web.herokuapp.com/login");
        driver.manage().window().setSize(new Dimension(1616, 876));
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Livros")).click();
        driver.findElement(By.id("isbn")).click();
        driver.findElement(By.id("isbn")).sendKeys("4414");
        driver.findElement(By.id("autor")).click();
        driver.findElement(By.id("autor")).sendKeys("Pedro Pedroso");
        driver.findElement(By.id("titulo")).sendKeys("Teste Selenium para Iniciantes");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
        driver.findElement(By.linkText("Editar")).click();
        driver.findElement(By.id("autor")).sendKeys("Pedroso Pedro");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
        assertTrue(driver.getPageSource().contains("Teste Selenium para Idiotas"));
        assertTrue(driver.getPageSource().contains("Pedroso Pedro"));

        driver.findElement(By.linkText("Excluir")).click();

    }

    @Test
    @Order(3)
    public void ct03_livroExclusaoSucesso(){
        driver.get("https://ts-scel-web.herokuapp.com/login");
        driver.manage().window().setSize(new Dimension(1616, 876));
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Livros")).click();
        driver.findElement(By.id("isbn")).click();
        driver.findElement(By.id("isbn")).sendKeys("4414");
        driver.findElement(By.id("autor")).click();
        driver.findElement(By.id("autor")).sendKeys("Pedro Pedroso");
        driver.findElement(By.id("titulo")).sendKeys("Teste Selenium para Iniciantes");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();

        driver.findElement(By.linkText("Excluir")).click();

        assertFalse(driver.getPageSource().contains("Teste Selenium para Idiotas"));
        assertFalse(driver.getPageSource().contains("Pedro Pedroso"));

    }


}
