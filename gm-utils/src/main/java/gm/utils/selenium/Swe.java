package gm.utils.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.USystem;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringCompare;

@Getter
public class Swe extends SweGetSubs {

	private WebElement el;
	private SeleniumPlus se;
	public static long tempoEsperaAposClickPadrao = 100;
	
	@Setter
	private long tempoEsperaAposClick = tempoEsperaAposClickPadrao;

	public Swe(SeleniumPlus se, WebElement el) {
		this.se = se;
		this.el = el;
	}

	protected Lst<Swe> by(By by) {

		List<WebElement> es = el.findElements(by);
		
		Lst<Swe> lst = new Lst<>();
		
		for (WebElement i : es) {
			lst.add(new Swe(se, i));
		}
		
		return lst;
		
	}
	
	@Override
	public Lst<Swe> byTag(String nome) {
		return by(By.tagName(nome));
	}
	
	@Override
	public Lst<Swe> byClass(String nome) {
		return by(By.className(nome));
	}

	@Override
	public Lst<Swe> byName(String s) {
		return by(By.name(s));
	}
	
	@Override
	public String toString() {
		return getText();
	}

	public String getInnerHtml() {
		return se.execScript("return arguments[0].innerHTML;", el);
	}
	
	public String getInnerHtmlRight(int size) {
		String s = "let s = arguments[0].innerHTML; s = s.substring(s.length - "+size+"); return s;";
		return se.execScript(s, el);
	}
	
	public String getText() {
		return el.getText();
	}

	public void click() {
		el.click();
		USystem.sleepMiliSegundos(tempoEsperaAposClick);
	}

	public Swe set(String s) {
		el.sendKeys(s);
		return this;
	}
	
	public Swe set(int i) {
		return set(i + "");
	}
	
	public Swe clear() {
		el.clear();
		return this;
	}
	
	public Swe tab() {
		el.sendKeys(Keys.TAB);
		USystem.sleepMiliSegundos(tempoEsperaAposClick);
		return this;
	}
	
	public boolean isAtributo(String name, String id) {
		return StringCompare.eq(id, getAttribute(name));
	}
	
	public String getAttribute(String name) {
		return el.getAttribute(name);
	}
	
	public boolean isId(String id) {
		return isAtributo("id", id);
	}

	public boolean isName(String id) {
		return isAtributo("name", id);
	}

	public boolean isValue(String id) {
		return isAtributo("value", id);
	}

	public boolean isIdOrName(String s) {
		return isId(s) || isName(s);
	}
	

	public boolean isText(String s) {
		return StringCompare.eq(getText(), s);
	}

	public boolean isNgClick(String s) {
		return isAtributo("ng-click", s);
	}

	public boolean isType(String s) {
		return isAtributo("type", s);
	}
	
	public boolean isTypeSubmit() {
		return isType("submit");
	}
	
	public Lst<Swe> options() {
		return byTag("option");
	}
	
	public boolean isTitle(String s) {
		
//		public boolean isTitle(String s) {
//			el.getAttribute(name);
//			return isAtributo("ng-click", s);
//		}

		
		return isAtributo("title", s);
	}

	public boolean isClass(String s) {
		
		try {

			ListString in = ListString.byDelimiter(s, " ");
			s = el.getAttribute("class");
			ListString ou = ListString.byDelimiter(s, " ");
			
			for (String a : in) {
				if (!ou.contains(a)) {
					return false;
				}
			}
			
			return true;
			
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			return false;
		}
		
	}

	public void print() {
		SystemPrint.ln(this);
	}

	public String getDataName() {
		return el.getAttribute("data-name");
	}

	public boolean ngReflectName(String s) {
		return isAtributo("ng-reflect-name", s);
	}

	public boolean placeholder(String s) {
		return isAtributo("placeholder", s);
	}
	
	public boolean isString(String s) {
		return toString().trim().contentEquals(s.trim());
	}

	public boolean isSrc(String s) {
		return isAtributo("src", s);
	}

	public boolean isHref(String s) {
		return isAtributo("href", s);
	}
	
	public void select(String s) {
		se.select(this, s);
	}
	
}