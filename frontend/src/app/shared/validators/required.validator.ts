import { AbstractControl } from '@angular/forms';

export class RequiredValidators {

	static notBlank(formControl: AbstractControl) {
		if (!formControl.parent) {
			return null;
		}
		return formControl.value && ((typeof formControl.value === 'string') && formControl.value.trim() == '') ?
				{ 'notBlank': true } :
				null;
	}
}
