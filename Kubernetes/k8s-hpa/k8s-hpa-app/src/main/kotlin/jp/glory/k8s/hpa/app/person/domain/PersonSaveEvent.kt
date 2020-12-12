package jp.glory.k8s.hpa.app.person.domain

import jp.glory.k8s.hpa.app.base.Either
import jp.glory.k8s.hpa.app.base.Left
import jp.glory.k8s.hpa.app.base.Right
import jp.glory.k8s.hpa.app.base.domain.SpecErrorDetail
import jp.glory.k8s.hpa.app.base.domain.SpecErrors

data class PersonRegisterEvent(
    val name: String,
    val age: Int
)

data class PersonUpdateEvent(
    val personId: PersonId,
    val name: String,
    val age: Int
)

private class PersonSaveEventSpec {
    companion object {
        fun validateName(
            value: String?
        ): Either<SpecErrorDetail, String> {
            val itemName = "name"
            val maxLength = 20
            val error = SpecErrorDetail(itemName)

            if (value.isNullOrEmpty()) {
                error.addRequiredError()
                return Left(error)
            }

            if (value.codePointCount(0, value.length) > 20) {
                error.addMaxLengthError(maxLength)
            }

            return when (error.hasError()) {
                true -> Left(error)
                false -> Right(value)
            }
        }

        fun validateAge(
            value: Int?
        ): Either<SpecErrorDetail, Int> {
            val itemName = "age"
            val error = SpecErrorDetail(itemName)

            if (value == null) {
                error.addRequiredError()
                return Left(error)
            }

            if (value < 0) {
                error.add("$itemName is negative number.input $itemName positive number.")
            }

            return when (error.hasError()) {
                true -> Left(error)
                false -> Right(value)
            }
        }
    }
}

class PersonRegisterEventSpec {
    companion object {
        fun validate(
            name: String?,
            age: Int?
        ): Either<SpecErrors, PersonRegisterEvent> {
            val errors = SpecErrors()

            val nameResult = PersonSaveEventSpec.validateName(name)
            val ageResult = PersonSaveEventSpec.validateAge(age)

            val nameValue = nameResult.mapBoth(
                right = { it },
                left =  {
                    errors.add(it)
                    ""
                }
            )

            val ageValue = ageResult.mapBoth(
                right = { it },
                left = {
                    errors.add(it)
                    0
                }
            )

            return when (errors.hasError()) {
                true -> Left(errors)
                false -> Right(
                    PersonRegisterEvent(
                        name = nameValue,
                        age = ageValue
                    )
                )
            }
        }
    }
}

class PersonUpdateEventSpec() {
    companion object {
        fun validate(
            personId: String?,
            name: String?,
            age: Int?
        ): Either<SpecErrors, PersonUpdateEvent> {
            val errors = SpecErrors()

            val idResult = validatePersonId(personId)
            val nameResult = PersonSaveEventSpec.validateName(name)
            val ageResult = PersonSaveEventSpec.validateAge(age)

            val idValue = idResult.mapBoth(
                right = { it },
                left =  {
                    errors.add(it)
                    ""
                }
            )

            val nameValue = nameResult.mapBoth(
                right = { it },
                left =  {
                    errors.add(it)
                    ""
                }
            )

            val ageValue = ageResult.mapBoth(
                right = { it },
                left = {
                    errors.add(it)
                    0
                }
            )

            return when (errors.hasError()) {
                true -> Left(errors)
                false -> Right(
                    PersonUpdateEvent(
                        personId = PersonId(idValue),
                        name = nameValue,
                        age = ageValue
                    )
                )
            }
        }

        private fun validatePersonId(
            value: String?
        ): Either<SpecErrorDetail, String> {
            val itemName = "personId"
            val error = SpecErrorDetail(itemName)

            if (value.isNullOrEmpty()) {
                error.addRequiredError()
                return Left(error)
            }

            return when (error.hasError()) {
                true -> Left(error)
                false -> Right(value)
            }
        }
    }

}