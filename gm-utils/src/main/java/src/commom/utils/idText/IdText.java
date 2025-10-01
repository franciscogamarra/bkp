package src.commom.utils.idText;

import gm.languages.ts.javaToTs.annotacoes.Any;
import gm.utils.abstrato.GetId;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;
import src.commom.utils.object.ObjJs;

@Getter @Setter
public class IdText extends ObjJs implements GetId {

	private Integer id = null;
	private String text = null;
	private String icon = null;

	public static IdText build(Integer id, String text) {
		IdText o = new IdText();
		o.id = id;
		o.text = text;
		return o;
	}
	
	public static final IdText VAZIO = IdText.build(0, "");

	public static IdText fromJson(String s) {
		@Any IdText x = jsonParse(s, IdText.class);
		IdText o = new IdText();
		o.setId(x.id);
		o.setText(x.text);
		o.setIcon(x.icon);
		return o;
	}

	public final boolean eqId(Integer value) {
		return IntegerCompare.eq(getId(), value);
	}

	public final boolean eq(IdText o) {
		if (Null.is(o)) {
			return false;
		}
		return eqId(o.getId());
	}

	protected String toJsonBody() {
		return itemInteger("id", getId()) + itemString("text", getText()) + itemString("icon", icon);
	}
	
	@Override
	protected final String toJsonImpl() {
		return "{"+toJsonBody()+"}";
	}
	
	@Override
	public String toString() {
		return getId() + " - " + getText();
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void javaLoad_replaceTexto(ListString list) {
		list.replaceTexto(" implements GetId", "");
		list.removeIfTrimEquals("import gm.utils.abstrato.GetId;");
	}
		
}
