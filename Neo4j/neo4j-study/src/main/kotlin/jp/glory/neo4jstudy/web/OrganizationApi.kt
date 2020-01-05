package jp.glory.neo4jstudy.web

import jp.glory.neo4jstudy.usecase.ModifyOrganization
import jp.glory.neo4jstudy.usecase.SearchOrganization
import jp.glory.neo4jstudy.web.request.EntryRequest
import jp.glory.neo4jstudy.web.request.JoinRequest
import jp.glory.neo4jstudy.web.request.LeaveRequest
import jp.glory.neo4jstudy.web.request.RetireRequest
import jp.glory.neo4jstudy.web.response.OrganizationsResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 組織に関するAPI.
 *
 * @param search 組織検索ユースケース
 * @param modify 部署変更ユースケース
 */
@RestController
@RequestMapping("/api/organization")
class OrganizationApi(
    private val search: SearchOrganization,
    private val modify: ModifyOrganization) {

    /**
     * 組織の一覧を取得する.
     *
     * @return レスポンス
     */
    @GetMapping
    fun getAll(): ResponseEntity<OrganizationsResponse> {

        val result = OrganizationsResponse(search.searchAll())

        return ResponseEntity.ok(result)
    }

    /**
     * 従業員を入社させる。
     *
     * @param entryRequest リクエスト
     */
    @PostMapping("entry")
    fun entry(@RequestBody entryRequest: EntryRequest): ResponseEntity<Long> {

        val employeeId: Long = modify.entryEmployee(
            ModifyOrganization.EntryInfo(
                entryAt = entryRequest.entryAt,
                lastName = entryRequest.lastName,
                firstName = entryRequest.firstName,
                age = entryRequest.age,
                postId = entryRequest.postId
            )
        )

        return ResponseEntity.ok(employeeId)
    }

    /**
     * 部署に従業員を追加する.
     *
     * @param request リクエスト
     * @return レスポンス
     */
    @PostMapping("join")
    fun join(@RequestBody request: JoinRequest): ResponseEntity<Unit> {

        modify.joinEmployee(
            joinAt = request.joinAt,
            postId = request.postId,
            employeeId = request.employeeId)

        return ResponseEntity.noContent().build()
    }

    /**
     * 部署から従業員を退任させる.
     *
     * @param request リクエスト
     * @return レスポンス
     */
    @PostMapping("leave")
    fun leave(@RequestBody request: LeaveRequest): ResponseEntity<Unit> {

        modify.leaveEmployee(
            leaveAt = request.leaveAt,
            postId = request.postId,
            employeeId = request.employeeId)

        return ResponseEntity.noContent().build()
    }

    /**
     * 従業員を退職させる.
     *
     * @param request
     */
    @PostMapping("retire")
    fun retire(@RequestBody request: RetireRequest): ResponseEntity<Unit> {

        modify.retireEmployee(
            ModifyOrganization.RetireInfo(
                retireAt = request.retireAt,
                employeeId = request.employeeId
            )
        )

        return ResponseEntity.noContent().build()
    }
}