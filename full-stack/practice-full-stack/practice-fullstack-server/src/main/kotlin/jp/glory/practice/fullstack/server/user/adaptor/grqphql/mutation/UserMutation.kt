package jp.glory.practice.fullstack.server.user.adaptor.grqphql.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import jp.glory.practice.fullstack.server.base.adaptor.graphql.getGraphQLExecutor
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.type.User
import jp.glory.practice.fullstack.server.user.usecase.GetUser
import jp.glory.practice.fullstack.server.user.usecase.UpdateUser
import java.time.LocalDate

class UserMutation(
    private val updateUser: UpdateUser
) : GraphQLMutationResolver {
    fun updateUser(
        input: UserUpdateInput,
        environment: DataFetchingEnvironment
    ): UserUpdatePayload =
        environment.getGraphQLExecutor()
            .let {
                updateUser.update(
                    executeUser = it.toExecuteUser(),
                    input = UpdateUser.Input(
                        name = input.name,
                        birthday = input.birthday
                    )
                )
            }
            .let { UserUpdatePayload(it.updatedId) }
}

class UserUpdateInput(
    val name: String,
    val birthday: LocalDate
)

class UserUpdatePayload(
    val id: String
)

class UserUpdatePayloadResolver(
    private val getUser: GetUser
) : GraphQLResolver<UserUpdatePayload> {

    fun getUpdated(
        payload: UserUpdatePayload
    ): User =
        getUser.getById(GetUser.Input(payload.id))
            .let { User(it) }
}