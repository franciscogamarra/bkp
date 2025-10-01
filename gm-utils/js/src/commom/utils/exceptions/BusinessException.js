export default class BusinessException extends Error {

	getMessage() {
		return this.message;
	}

}
