export class Annonce {
	id: number | undefined;
	title: string | undefined;
	description: string | undefined;
	fileName: string | undefined;

	constructor(title?: string, description?: string, fileName?: string) {
		this.title = title;
		this.description = description;
		this.fileName = fileName;
	}

	deserialize(input: any): Annonce {
		Object.assign(this, input);
		return this;
	}

	serialize(): any {
		const annonceObj: any = Object.assign({}, this);
		return annonceObj;
	}
}
