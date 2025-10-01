import Dev from "Dev";



export default class SuperComponent {



	constructor() {

		this.log("");

	}



	getClassName() {

		return this.constructor.name;

	}



	log(s: any) {

		if (Dev.in) {

			console.log(this.getClassName() + " -> " + s);

		}

	}



}