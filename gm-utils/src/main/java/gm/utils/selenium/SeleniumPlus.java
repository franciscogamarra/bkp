package gm.utils.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import gm.utils.comum.Lst;
import gm.utils.comum.USystem;
import gm.utils.lambda.F1;
import gm.utils.selenium.components.IFrame;
import lombok.Setter;

public class SeleniumPlus extends SweGetSubs {

	public static void main(String[] args) {
		new SeleniumPlus();
	}
	
	private Selenium selenium;
	
	@Setter
	private F1<SeleniumPlus, Boolean> waitFunc;
	
	public SeleniumPlus() {
		WebDriver.class.getName();
		selenium = new Selenium();
		selenium.start();
	}
	
	@Override
	public Lst<Swe> byClass(String s) {
		return getS().classe(s).map(i -> new Swe(this, i));
	}

	@Override
	public Lst<Swe> byName(String s) {
		return getS().listByName(s).map(i -> new Swe(this, i));
	}
	
	@Override
	public Lst<Swe> byTag(String s) {
		return getS().bysTag(s).map(i -> new Swe(this, i));
	}

	public void get(String url) {
		getS().get(url);
	}

	public void close() {
		selenium.quit();
	}

	public Swe byId(String id) {
		WebElement el = getS().byId(id);
		return new Swe(this, el);
	}

	public Swe byProperty(String tag, String property, String value) {
		WebElement el = getS().byProperty(tag, property, value);
		return new Swe(this, el);
	}
	
	public void scrollDown() {
		getS().scrollDown();
	}

	public void scrollUp() {
		getS().scrollUp();
	}
	
	public void alertOk() {
		getS().alertOk();
	}
	
	private final Lst<IFrame> frames = new Lst<>();
	
	public void frameIn(IFrame frame) {
		frames.add(frame);
		getS().iframe(frame.getEl());
	}

	public void frameOut() {
		getS().iframeOut();
		frames.removeLast();
	}
	
	public IFrame getFrame() {
		return frames.getLast();
	}
	
	public Lst<IFrame> frames() {
		return byTag("iframe").map(i -> new IFrame(i));
	}
	
	private boolean waiting;
	
	private Selenium getS() {
		
		if (waitFunc != null && !waiting) {
			
			try {
				waiting = true;
				while (waitFunc.call(this)) {
					USystem.sleepMiliSegundos(100);
				}
			} finally {
				waiting = false;
			}
			
		}
		
		return this.selenium;
		
	}
	
	public <T> T execScript(String s) {
		return getS().execScript(s);
	}

	public <T> T execScript(String s, WebElement el) {
		return getS().execScript(s, el);
	}

	public String getCurrentUrl() {
		return getS().getCurrentUrl();
	}
	
	public void select(Swe o, String item) {
		selenium.select(o.getEl(), item);
	}
	
}