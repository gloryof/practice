package jp.glory.neo4jstudy.web

import jp.glory.neo4jstudy.usecase.ModifyEmployee
import jp.glory.neo4jstudy.web.request.EmployeeModifyRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
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
     * 従業員の更新を行う.
     *
     * @param employeeId 従業員ID
     * @param request リクエスト
     */
    @PutMapping("{employeeId}")
    fun update(
        @PathVariable employeeId: Long,
        @RequestBody request: EmployeeModifyRequest
    ): ResponseEntity<Long> {

        modify.update(
            ModifyEmployee.UpdateInfo(
                employeeId =  employeeId,
                lastName = request.lastName,
                firstName = request.firstName,
                age = request.age
            )
        )

        return ResponseEntity.ok(employeeId)
    }
}