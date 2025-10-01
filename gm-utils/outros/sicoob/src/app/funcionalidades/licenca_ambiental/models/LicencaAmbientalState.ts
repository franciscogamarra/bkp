import { NotaFiscalBndes } from "@funcionalidades/pedido-liberacao-bndes/models/nota-fiscal.model";

export class LicencaAmbientalState {

	public static nfs : Array<NotaFiscalBndes> = [];

	public static setNfs(nfs : Array<NotaFiscalBndes>|null|undefined) : void {

		if (!nfs) {
			LicencaAmbientalState.nfs = [];
		} else {
			LicencaAmbientalState.nfs = nfs;
		}

	}

}
