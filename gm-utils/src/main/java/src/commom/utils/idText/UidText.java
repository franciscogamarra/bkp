package src.commom.utils.idText;

import gm.languages.ts.javaToTs.annotacoes.Any;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.object.Null;
import src.commom.utils.object.ObjJs;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRemoveAcentos;

@Getter @Setter
public class UidText extends ObjJs {

	private String id = null;
	private String text = null;
	private String icon = null;
	private String search = null;

	public static UidText build(String id, String text) {
		UidText o = new UidText();
		o.id = id;
		o.text = text;
		o.refreshSearch();
		return o;
	}
	
	public static final UidText VAZIO = UidText.build("", "");

	public static UidText fromJson(String s) {
		@Any UidText x = jsonParse(s, UidText.class);
		UidText o = new UidText();
		o.setId(x.id);
		o.setText(x.text);
		o.setIcon(x.icon);
		return o;
	}
	
	public void setId(String id) {
		this.id = id;
		refreshSearch();
	}
	
	public void setText(String text) {
		this.text = text;
		refreshSearch();
	}

	private void refreshSearch() {
		search = "";
		if (StringEmpty.notIs(id)) {
			search += StringRemoveAcentos.exec(id.toLowerCase());
		}
		if (StringEmpty.notIs(text)) {
			search += " " + StringRemoveAcentos.exec(text.toLowerCase());
			search = search.trim();
		}
	}

	public final boolean eqId(String value) {
		return StringCompare.eq(getId(), value);
	}

	public final boolean eq(UidText o) {
		if (Null.is(o)) {
			return false;
		}
		return eqId(o.getId());
	}

	protected String toJsonBody() {
		return itemString("id", getId()) + itemString("text", getText()) + itemString("icon", icon);
	}
	
	@Override
	protected final String toJsonImpl() {
		return "{"+toJsonBody()+"}";
	}
	
	@Override
	public String toString() {
		return getText();
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void javaLoad_replaceTexto(ListString list) {
		list.replaceTexto(" implements GetId", "");
		list.removeIfTrimEquals("import gm.utils.abstrato.GetId;");
	}
		
}
