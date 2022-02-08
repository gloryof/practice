package jp.glory.ci.cd.practice.app.auth.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RegisteredCredentialTest {
    @Nested
    inner class Authenticate {
        private fun createSut(
            credentialUserId: CredentialUserId,
            password: Password
        ): RegisteredCredential =
            RegisteredCredential(
                credentialUserId = credentialUserId,
                password = password,
                givenName = CredentialGivenName("test-given-name"),
                familyName = CredentialFamilyName("test-family-name"),
            )

        @Test
        @DisplayName("When ID and password are matched, then return true")
        fun successPattern() {
            val idValue = "test-id"
            val passwordValue = "test-password"
            val sut = createSut(
                credentialUserId = CredentialUserId(idValue),
                password = Password(passwordValue)
            )
            val actual = sut.authenticate(
                credentialUserId = CredentialUserId(idValue),
                password = Password(passwordValue)
            )
            assertTrue(actual)
        }

        @Test
        @DisplayName("When ID is not matched, then return false")
        fun idIsNotMatch() {
            val idValue = "test-id"
            val passwordValue = "test-password"
            val sut = createSut(
                credentialUserId = CredentialUserId(idValue),
                password = Password(passwordValue)
            )
            val actual = sut.authenticate(
                credentialUserId = CredentialUserId(idValue + "2"),
                password = Password(passwordValue)
            )
            assertFalse(actual)
        }

        @Test
        @DisplayName("When password is not matched, then return false")
        fun passwordIsNotMatch() {
            val idValue = "test-id"
            val passwordValue = "test-password"
            val sut = createSut(
                credentialUserId = CredentialUserId(idValue),
                password = Password(passwordValue)
            )
            val actual = sut.authenticate(
                credentialUserId = CredentialUserId(idValue),
                password = Password(passwordValue + "2")
            )
            assertFalse(actual)
        }

        @Test
        @DisplayName("When ID and password are not matched, then return false")
        fun bothAreNotMatch() {
            val idValue = "test-id"
            val passwordValue = "test-password"
            val sut = createSut(
                credentialUserId = CredentialUserId(idValue),
                password = Password(passwordValue)
            )
            val actual = sut.authenticate(
                credentialUserId = CredentialUserId(idValue + "2"),
                password = Password(passwordValue + "2")
            )
            assertFalse(actual)
        }
    }
}