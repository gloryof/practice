package jp.glory.boot3practice.user.adaptor.web

import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.user.use_case.BulkRegisterUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Controller
@RequestMapping(EndpointConst.User.bulkRegister)
class BulkRegisterUserApi(
    private val useCase: BulkRegisterUserUseCase
) {

    data class BulkUserRegisterResponse(
        val count: Int
    )

    @PostMapping
    fun registerUser(
        @RequestPart("file") uploadFile: Mono<FilePart>
    ): Mono<ResponseEntity<BulkUserRegisterResponse>> =
        uploadFile
            .flatMap { file ->
                file.content().map { it.asInputStream() }
                    .toMono()
            }
            .map { useCase.register(BulkRegisterUserUseCase.Input(it)) }
            .map { toResponse(it) }
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    private fun toResponse(output: BulkRegisterUserUseCase.Output): BulkUserRegisterResponse =
        BulkUserRegisterResponse(output.count)
}