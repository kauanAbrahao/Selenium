package testeDeSistema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

public class REQ02MantemAlunoTests {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://ts-scel-web.herokuapp.com/login");
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void ct01cadastraralunocomsucesso() throws InterruptedException {
        // dado que o aluno não esta cadastrado
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Alunos")).click();
        Thread.sleep(2000);

        // quando o usuario cadastrar um aluno
        driver.findElement(By.id("ra")).click();
        driver.findElement(By.id("ra")).sendKeys("1111");
        driver.findElement(By.id("nome")).click();
        driver.findElement(By.id("nome")).sendKeys("jose");
        driver.findElement(By.id("email")).sendKeys("jose@gmail.com");
        driver.findElement(By.id("cep")).sendKeys("04280130");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();

        // entao apresenta as informacoes do aluno
        assertEquals("Rua Frei João", driver.findElement(By.cssSelector("td:nth-child(6)")).getText());
        assertEquals(("Lista de alunos"), driver.findElement(By.id("titulopagina")).getText());
        assertEquals("https://ts-scel-web.herokuapp.com/sig/alunos", driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("1111"));

        // *********************************************************************************
        // teardown - exclusao do registro
        // *********************************************************************************
        driver.findElement(By.linkText("Excluir")).click();
    }

    @Test
    public void ct02atualizaalunocomsucesso() throws InterruptedException {
        //***********************************************************************************
        // dado que o aluno esta cadastrado
        //***********************************************************************************
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Alunos")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("ra")).click();
        driver.findElement(By.id("ra")).sendKeys("1111");
        driver.findElement(By.id("nome")).sendKeys("jose");
        driver.findElement(By.id("email")).sendKeys("jose@gmail.com");
        driver.findElement(By.id("cep")).sendKeys("04280130");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
        Thread.sleep(2000);
        assertEquals("Rua Frei João", driver.findElement(By.cssSelector("td:nth-child(6)")).getText());
        //**********************************************************************************
        // quando o usuario altera o CEP do endereco
        //**********************************************************************************
        driver.findElement(By.linkText("Editar")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
        driver.findElement(By.id("cep")).clear();
        driver.findElement(By.id("cep")).sendKeys("08545160");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
        // entao o sistema apresenta as informações do aluno com o CEP alterado
        assertTrue(driver.getPageSource().contains("Rua João Soliman"));
        //************************************************************************************
        // teardown - exclusao do registro
        // ***********************************************************************************
        driver.findElement(By.linkText("Excluir")).click();
    }

    @Test
    public void ct03cadastraraluno_ja_cadastrado() throws InterruptedException {
        // ******************************************************************************
        // dado que o aluno esta cadastrado
        // ******************************************************************************
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("jose");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.linkText("Alunos")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("ra")).click();
        driver.findElement(By.id("ra")).sendKeys("1111");
        driver.findElement(By.id("nome")).click();
        driver.findElement(By.id("nome")).sendKeys("jose");
        driver.findElement(By.id("email")).sendKeys("jose@gmail.com");
        driver.findElement(By.id("cep")).sendKeys("04280130");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
        Thread.sleep(2000);

        // asserts com objetivo de dupuracao
        assertEquals("Rua Frei João", driver.findElement(By.cssSelector("td:nth-child(6)")).getText());
        assertEquals(("Lista de alunos"), driver.findElement(By.id("titulopagina")).getText());
        assertEquals("https://ts-scel-web.herokuapp.com/sig/alunos", driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("1111"));
        // ******************************************************************************
        // quando tenta cadastrar um aluno ja cadastrado
        // ******************************************************************************
        driver.findElement(By.linkText("Voltar")).click();
        driver.findElement(By.linkText("Alunos")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("ra")).click();
        driver.findElement(By.id("ra")).sendKeys("1111");
        driver.findElement(By.id("nome")).sendKeys("jose");
        driver.findElement(By.id("email")).sendKeys("jose@gmail.com");
        driver.findElement(By.id("cep")).sendKeys("04280130");
        driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
        //*******************************************************************************
        // entado retorna dados invalidos
        //*******************************************************************************
        assertEquals("Dados invalidos", driver.findElement(By.cssSelector(".text-danger")).getText());
        // ******************************************************************************
        // teardown - exclusao do registro
        // ******************************************************************************
        vars.put("window_handles", driver.getWindowHandles());
        driver.findElement(By.cssSelector(".btn:nth-child(2)")).click(); //botao consultar
        vars.put("win7071", waitForWindow(2000));
        driver.switchTo().window(vars.get("win7071").toString());
        driver.findElement(By.cssSelector(".delete")).click();
    }


    public String waitForWindow(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<String> whNow = driver.getWindowHandles();
        Set<String> whThen = (Set<String>) vars.get("window_handles");
        if (whNow.size() > whThen.size()) {
            whNow.removeAll(whThen);
        }
        return whNow.iterator().next();
    }

    @Test
    public void ct01consulta_cep_pelo_logradouro_com_sucesso() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://buscacepinter.correios.com.br/app/localidade_logradouro/index.php");
        driver.manage().window().setSize(new Dimension(1012, 728));
        driver.findElement(By.id("uf")).click();

        WebElement dropdownFederacao = driver.findElement(By.id("uf"));
        Select combo1 = new Select(dropdownFederacao);
        combo1.selectByIndex(26);
        combo1.selectByVisibleText("SP");
        driver.findElement(By.id("localidade")).click();
        driver.findElement(By.id("localidade")).sendKeys("São Paulo");
        driver.findElement(By.id("tipologradouro")).click();
        WebElement dropdownTipo = driver.findElement(By.id("tipologradouro"));
        Select combo2 = new Select(dropdownTipo);
        combo2.selectByIndex(32);
        combo2.selectByVisibleText("Rua");

        driver.findElement(By.id("logradouro")).click();
        driver.findElement(By.id("logradouro")).sendKeys("Frei João");
        driver.findElement(By.id("btn_pesquisar")).click();
        Thread.sleep(2000);
        assertThat(driver.findElement(By.cssSelector("#mensagem-resultado > h4")).getText(),
                is("Resultado da Busca por Localidade/Logradouro"));
    }


}
