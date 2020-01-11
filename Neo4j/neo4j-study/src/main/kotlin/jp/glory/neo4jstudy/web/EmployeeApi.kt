package jp.glory.neo4jstudy.web

import jp.glory.neo4jstudy.usecase.ModifyEmployee
import jp.glory.neo4jstudy.usecase.SearchEmployee
import jp.glory.neo4jstudy.web.request.EmployeeModifyRequest
import jp.glory.neo4jstudy.web.response.EmployeeDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 従業員のAPI.
 *
 * @param modify 従業員変更ユースケース
 * @param search 従業員検索ユースケース
 */
@RestController
@RequestMapping("/api/employee")
class EmployeeApi(
    private val modify: ModifyEmployee,
    private val search: SearchEmployee) {


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
    /**
     * 従業員の詳細の取得を行う.
     *
     * @param employeeId 従業員ID
     */
    @GetMapping("{employeeId}/detail")
    fun getDetail(
        @PathVariable employeeId: Long
    ): ResponseEntity<EmployeeDetailResponse> {

        val employeeResult: SearchEmployee.EmployeeDetailResult =
            search.findByEmployeeId(employeeId)
        val response = EmployeeDetailResponse(employeeResult)


        return ResponseEntity.ok(response)
    }
}