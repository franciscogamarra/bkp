import { ControleItemLiberacao } from "@funcionalidades/pedido-liberacao-bndes/models/controle-item-liberacao.model";
import { int } from "../../../../commom/utils/comum/OurTypes";

export class ItensLiberacao {

	public static value : Array<ControleItemLiberacao> = [];

	public static set(itens : Array<ControleItemLiberacao>|null|undefined) : void {

		if (!itens) {
			ItensLiberacao.value = [];
		} else {
			ItensLiberacao.value = itens.map((i : ControleItemLiberacao) => ({...}i} as ControleItemLiberacao));
		}

	}

	public static get() : Array<ControleItemLiberacao> {
		return ItensLiberacao.value;
	}

	public static isEmpty() : boolean {
		return !ItensLiberacao.get().length;
	}

	public static add(o : ControleItemLiberacao) : void {
		ItensLiberacao.value.push(o);
	}

	public static remove(index : int) : void {
		ItensLiberacao.value.splice(index, 1);
	}

	public static change(index : int, o : ControleItemLiberacao) : void {
		Object.assign(ItensLiberacao.value[index], o);
	}

	public static clear() : void {
		ItensLiberacao.value = [];
	}

}
