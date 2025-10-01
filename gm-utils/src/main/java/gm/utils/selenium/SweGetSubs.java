package gm.utils.selenium;

import java.util.function.Predicate;

import gm.utils.comum.Lst;

public abstract class SweGetSubs {

	public abstract Lst<Swe> byClass(String s);
	public abstract Lst<Swe> byName(String s);
	public abstract Lst<Swe> byTag(String s);

	public final Lst<Swe> buttons() {
		return byTag("button");
	}
	
	public final Lst<Swe> buttons(Predicate<Swe> predicate) {
		return buttons().filter(predicate);
	}
	
	public final Swe button(Predicate<Swe> predicate) {
		return buttons(predicate).getUnique();
	}

	public final Lst<Swe> h2s() {
		return byTag("h2");
	}

	public final Lst<Swe> divs() {
		return byTag("div");
	}

	public final Lst<Swe> spans() {
		return byTag("span");
	}
	
	public final Lst<Swe> imgs() {
		return byTag("img");
	}
	
	public final Lst<Swe> inputs() {
		return byTag("input");
	}
	
	public final Lst<Swe> lis() {
		return byTag("li");
	}
	
	public Lst<Swe> tables() {
		return byTag("table");
	}
	
	public final Swe table(String id) {
		return tables().unique(i -> i.isId(id));
	}

	public final Lst<Swe> inputs(Predicate<Swe> predicate) {
		return inputs().filter(predicate);
	}
	
	public final Swe input(Predicate<Swe> predicate) {
		return inputs(predicate).getUnique();
	}
	
	public final Swe input(String id) {
		return input(i -> i.isId(id));
	}
	
	public final Lst<Swe> li() {
		return byTag("li");
	}
	
	public final Lst<Swe> as() {
		return byTag("a");
	}
	
	public final Lst<Swe> labels() {
		return byTag("label");
	}
	
	public final Lst<Swe> selects() {
		return byTag("select");
	}

	public final Lst<Swe> trs() {
		return byTag("tr");
	}
	
	public final Lst<Swe> tds() {
		return byTag("td");
	}
	
	public final Lst<Swe> textareas() {
		return byTag("textarea");
	}
	
}