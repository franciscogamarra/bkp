package gm.utils.selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import gm.utils.comum.Lst;
import gm.utils.comum.SO;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;
import gm.utils.robo.Mouse;
import lombok.Setter;

public class Selenium {

	private static final String WINDOWS = "C:\\opt\\desen\\gm\\cs2019\\gm-utils\\src\\main\\resources\\chromedriver.exe";
	private static final String LINUX = "/opt/desen/gm/cs2019/gm-utils/src/main/resources/chromedriver98.0.4758.102";
	
	static {
		WebDriver.class.getName();
        System.setProperty("webdriver.chrome.driver", SO.windows() ? WINDOWS : LINUX);
	}

	@Setter private boolean closed = false;
	@Setter private boolean visible = true;
	@Setter private boolean incognito = true;
	private WebDriver driver;
	private long ultimaAtividade = 0;
	
	@Setter
	private int tempoMaximoDeInatividade = 6000000;

	public void fecharSeInativo() {
		USystem.setTimeout(() -> {
			if (closed) {
			} else if (System.currentTimeMillis() - ultimaAtividade > tempoMaximoDeInatividade) {
				quit();
			} else {
				fecharSeInativo();
			}
		}, 1000);
	}

	private void setUltimaAtividade() {
		ultimaAtividade = System.currentTimeMillis();
	}

	private void esperar() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(webDriver -> "complete".equals(execScript("return document.readyState").toString()));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T execScript(String s) {
		
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return (T) js.executeScript(s);
		} catch (Exception e) {
			SystemPrint.ln(s);
			throw e;
		}
		
	}

	@SuppressWarnings("unchecked")
	public <T> T execScript(String s, WebElement el) {
		
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return (T) js.executeScript(s, el);
		} catch (Exception e) {
			SystemPrint.ln(s);
			throw e;
		}
		
	}
	
	
	public Selenium start() {
    	ChromeOptions options = new ChromeOptions();
    	if (!visible) {
			options.addArguments("--headless");
		}
    	if (!incognito) {
			options.addArguments("--incognito");
		}
    	
    	options.addArguments("--disable-web-security"); // don't enforce the same-origin policy
    	
    	if (SO.windows()) {
    		options.addArguments("--disable-gpu"); // applicable to windows os only
//    		options.addArguments("--user-data-dir=~/chromeTemp"); // applicable to windows os only
		}

    	options.addArguments("start-maximized");
//    	options.addArguments("user-data-dir=/home/gamarra/.config/google-chrome");

//    	chromeOptions.addArguments("--no-sandbox");
		driver = new ChromeDriver(options);
//		driver.manage().window().setPosition(new Point(0,0));
//		driver.manage().window().maximize();
		fecharSeInativo();
		return this;
	}
	
	public void get(String url) {
		setUltimaAtividade();
		driver.get(url);
		esperar();
	}

	private WebElement by(By by) {
		setUltimaAtividade();
		return driver.findElement(by);
	}

	public Lst<WebElement> bys(By by) {
		setUltimaAtividade();
		return new Lst<>(driver.findElements(by));
	}

	public Lst<WebElement> bysClass(String s) {
		return bys(By.className(s));
	}
	
	public Lst<WebElement> bysTag(String s) {
		return bys(By.tagName(s));
	}
	
	private WebElement byId(String s, int tentativa) {
		
		try {
			return by(By.id(s));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			
			if (tentativa > 10 /*serao 5 segundos de espera*/) {
				throw new RuntimeException("Não encontrado elemento com id : " + s); 
			} else {
				USystem.sleepMiliSegundos(500);
				return byId(s, tentativa+1);
			}			
		}
		
	}
	
	public WebElement byId(String s) {
		return byId(s, 0);
	}
	
	
	public WebElement byName(String s) {
		return by(By.name(s));
	}
	public WebElement byProperty(String tag, String property, String s) {
		return by(By.xpath("//"+tag+"[contains(@"+property+",'"+s+"')]"));
	}

	public static By bysPropertyBy(String tag, String property, String s) {
		return By.xpath("//"+tag+"[contains(@"+property+",'"+s+"')]");
	}

	public List<WebElement> bysProperty(String tag, String property, String s) {
		return bys(bysPropertyBy(tag, property, s));
	}
	public WebElement byTitle(String tag, String s) {
		return by(By.xpath("//"+tag+"[contains(@title,'"+s+"')]"));
	}
	public WebElement byText(String tag, String s) {
		return by(By.xpath("//"+tag+"[contains(text(),'"+s+"')]"));
	}
	
	public Lst<WebElement> classe(String s) {
		return bys(By.className(s));
	}
	
	public Lst<WebElement> listByName(String s) {
		return bys(By.name(s));
	}
	
	private int margemLeftSO() {
		return 72;
	}
	private int margemTopBrowser() {
		return 157;
	}
	public void mouseMove(WebElement o) {
		Point location = o.getLocation();
		Mouse.move(location.x + 10 + margemLeftSO(), location.y + 10 + margemTopBrowser());
	}

	public void clickNative(WebElement o) {
		setUltimaAtividade();
		mouseMove(o);
		Mouse.click();
		esperar();
	}

	public void click(WebElement o) {
		setUltimaAtividade();
		try {
			o.click();
			esperar();
		} catch (Exception e) {
			clickNative(o);
		}
	}

	public void select(WebElement o, String s) {
		setUltimaAtividade();

		try {
			new Select(o).selectByVisibleText(s);
		} catch (Exception e) {
			WebElement o2 = byTitle("span", s);
			if (o2 == null) {
				throw new RuntimeException("nao encontrado element: " + s);
			}
			click(o2);
		}
		esperar();
	}

	public void tinymce(String s) {
		((JavascriptExecutor) driver).executeScript("tinyMCE.activeEditor.setContent('"+s+"')");
	}

	public void quit() {
		closed = true;
		try {
			driver.close();
		} catch (Exception e) {}
		try {
			driver.quit();
		} catch (Exception e) {}
	}

	private void scrollY(String h) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementsByClassName('scrollable-content')[0].scrollTo(0, "+h+")");
	}
	
	public void scrollUp() {
		scrollY("0");
	}

	public void scrollDown() {
		scrollY("document.body.scrollHeight");
	}
	
	public void alertOk() {
		driver.switchTo().alert().accept();
	}
	
	public void iframe(WebElement we) {
		driver.switchTo().frame(we);
	}
	
	public void iframeOut() {
		driver.switchTo().parentFrame();
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	} 
	
}