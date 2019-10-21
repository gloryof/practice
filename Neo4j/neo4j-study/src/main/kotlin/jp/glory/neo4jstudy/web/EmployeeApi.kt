package jp.glory.neo4jstudy.web

import jp.glory.neo4jstudy.usecase.ModifyEmployee
import jp.glory.neo4jstudy.web.request.EmployeeModifyRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 従業員のAPI.
 *
 * @param modify 従業員変更ユースケース
 */
@RestController
@RequestMapping("/api/employee")
class EmployeeApi(private val modify: ModifyEmployee) {

    /**
     * 従業員の登録を行う.
     *
     * @param request リクエスト
     */
    @PostMapping
    fun register(request: EmployeeModifyRequest): ResponseEntity<Long> {

        val result: Long = modify.register(
            ModifyEmployee.RegisterInfo(
                lastName = request.lastName,
                firstName = request.firstName,
                age = request.age
            )
        )

        return ResponseEntity.ok(result)
    }

    /**
     * 従業員の更新を行う.
     *
     * @param employeeId 従業員ID
     * @param request リクエスト
     */
    @PutMapping("{employeeId}")
    fun update(
        employeeId: Long,
        request: EmployeeModifyRequest
    ): ResponseEntity<Long> {

        val result: Long = modify.update(
            ModifyEmployee.UpdateInfo(
                employeeId =  employeeId,
                lastName = request.lastName,
                firstName = request.firstName,
                age = request.age
            )
        )

        return ResponseEntity.ok(result)
    }


    /**
     * 従業員の削除を行う.
     *
     * @param employeeId 従業員ID
     */
    @DeleteMapping("{employeeId}")
    fun delete(employeeId: Long): ResponseEntity<Unit> {

        modify.delete(employeeId)

        return ResponseEntity.noContent().build()
    }
}