/*
 * Copyright (C) 2007-2017 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.commons.validation.validators.impl;

import org.craftercms.commons.validation.annotations.param.ValidateNoTagsParam;
import org.craftercms.commons.validation.validators.AnnotationBasedValidatorFactory;
import org.craftercms.commons.validation.validators.Validator;

public class NoTagsParamValidatorFactory implements AnnotationBasedValidatorFactory<ValidateNoTagsParam, String> {

    @Override
    public Validator<String> getValidator(ValidateNoTagsParam annotation) {
        NoTagsValidator validator = new NoTagsValidator(annotation.name());
        validator.setNotNull(annotation.notNull());
        validator.setNotEmpty(annotation.notEmpty());
        validator.setNotBlank(annotation.notBlank());
        validator.setMinLength(annotation.minLength());
        validator.setMaxLength(annotation.maxLength());

        return validator;
    }

}
