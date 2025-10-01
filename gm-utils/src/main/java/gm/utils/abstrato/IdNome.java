package gm.utils.abstrato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringParse;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class IdNome implements IdObject {

	private Integer id;
	private String nome;

	public String getText() {
		return nome;
	}
	public String getDescricao() {
		return nome;
	}
	public void setDescricao(String value) {
		if (value != null) {
			setNome(value);
		}
	}
	public void setText(String value) {
		setDescricao(value);
	}
	public String getCodigo() {
		return StringParse.get(getId());
	}
	public void setCodigo(String value) {
		if (IntegerIs.is(value)) {
			setId(IntegerParse.toInt(value));
		}
	}
}
