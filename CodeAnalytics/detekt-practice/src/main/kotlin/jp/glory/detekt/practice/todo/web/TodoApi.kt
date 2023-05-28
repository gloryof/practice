package jp.glory.detekt.practice.todo.web

import jp.glory.detekt.practice.base.web.WebApi
import jp.glory.detekt.practice.base.web.WebApiPath
import jp.glory.detekt.practice.todo.usecase.DeleteTodoUseCase
import jp.glory.detekt.practice.todo.usecase.FindTodoUseCase
import jp.glory.detekt.practice.todo.usecase.FinishTodoUseCase
import jp.glory.detekt.practice.todo.usecase.ListTodoUseCase
import jp.glory.detekt.practice.todo.usecase.RegisterTodoUseCase
import jp.glory.detekt.practice.todo.usecase.StartTodoUseCase
import jp.glory.detekt.practice.todo.usecase.TodoDetail
import jp.glory.detekt.practice.todo.usecase.UpdateTodoUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDate

@WebApi
@RequestMapping(WebApiPath.Todos)
class TodoApi(
    private val listTodoUseCase: ListTodoUseCase,
    private val findTodoUseCase: FindTodoUseCase,
    private val registerTodoUseCase: RegisterTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteUseCase: DeleteTodoUseCase,
    private val startTodoUseCase: StartTodoUseCase,
    private val finishTodoUseCase: FinishTodoUseCase
) {
    @GetMapping
    fun getAll(): ResponseEntity<TodosResponse> =
        listTodoUseCase.findAll()
            .let { toTodosResponse(it) }
            .let { ResponseEntity.ok(it) }

    @PostMapping
    fun register(
        @RequestBody request: RegisterTodoRequest
    ): ResponseEntity<RegisterTodoResponse> =
        registerTodoUseCase.register(toRegisterInput(request))
            .let { toRegisterResponse(it) }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String
    ): ResponseEntity<TodoDetailResponse> =
        findTodoUseCase.findById(FindTodoUseCase.Input(id))
            .let { toTodoResponse(it.detail) }
            .let { ResponseEntity.ok(it) }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: UpdateRequest
    ): ResponseEntity<Any> =
        updateTodoUseCase.update(
            input = UpdateTodoUseCase.Input(
                id = id,
                newTitle = request.title ?: "",
                newDeadLine = request.deadLine ?: LocalDate.now()
            )
        )
            .let { ResponseEntity.noContent().build() }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String
    ): ResponseEntity<TodoDetailResponse> =
        deleteUseCase.delete(DeleteTodoUseCase.Input(id))
            .let { ResponseEntity.noContent().build() }

    @PutMapping("/{id}/start")
    fun start(
        @PathVariable id: String
    ): ResponseEntity<TodoDetailResponse> =
        startTodoUseCase.start(StartTodoUseCase.Input(id))
            .let { ResponseEntity.noContent().build() }

    @PutMapping("/{id}/finish")
    fun finish(
        @PathVariable id: String
    ): ResponseEntity<TodoDetailResponse> =
        finishTodoUseCase.finish(FinishTodoUseCase.Input(id))
            .let { ResponseEntity.noContent().build() }

    private fun toTodosResponse(
        output: ListTodoUseCase.Output
    ): TodosResponse =
        output.details
            .map { toTodoResponse(it) }
            .let { TodosResponse(it) }

    private fun toTodoResponse(
        detail: TodoDetail
    ): TodoDetailResponse =
        TodoDetailResponse(
            id = detail.id,
            title = detail.title,
            deadLine = detail.deadLine,
            progressStatus = detail.progressStatus.name
        )

    private fun toRegisterInput(
        request: RegisterTodoRequest
    ): RegisterTodoUseCase.Input =
        RegisterTodoUseCase.Input(
            title = request.title ?: "",
            deadLine = request.deadLine ?: LocalDate.now()
        )

    private fun toRegisterResponse(
        output: RegisterTodoUseCase.Output
    ): ResponseEntity<RegisterTodoResponse> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(RegisterTodoResponse(output.id))
}

class TodosResponse(
    val details: List<TodoDetailResponse>
)

class TodoDetailResponse(
    val id: String,
    val title: String,
    val deadLine: LocalDate,
    val progressStatus: String
)

class RegisterTodoRequest(
    val title: String? = null,
    val deadLine: LocalDate? = null,
)

class RegisterTodoResponse(
    val id: String
)

class UpdateRequest(
    val title: String? = null,
    val deadLine: LocalDate? = null,
)
