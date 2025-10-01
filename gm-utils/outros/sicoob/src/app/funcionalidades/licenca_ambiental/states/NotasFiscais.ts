import { NotaFiscalBndes } from "@funcionalidades/pedido-liberacao-bndes/models/nota-fiscal.model";
import { int } from "../../../../commom/utils/comum/OurTypes";

export class NotasFiscais {

	public static value : Array<NotaFiscalBndes> = [];

	public static set(nfs : Array<NotaFiscalBndes>|null|undefined) : void {

		if (!nfs) {
			NotasFiscais.value = [];
		} else {
			NotasFiscais.value = nfs.map((i : NotaFiscalBndes) => ({...}i} as NotaFiscalBndes));
		}

	}

	public static get() : Array<NotaFiscalBndes> {
		return NotasFiscais.value;
	}

	public static isEmpty() : boolean {
		return !NotasFiscais.get().length;
	}

	public static add(o : NotaFiscalBndes) : void {
		NotasFiscais.value.push(o);
	}

	public static remove(index : int) : void {
		NotasFiscais.value.splice(index, 1);
	}

	public static change(index : int, o : NotaFiscalBndes) : void {
		Object.assign(NotasFiscais.value[index], o);
	}

	public static clear() : void {
		NotasFiscais.value = [];
	}

}
