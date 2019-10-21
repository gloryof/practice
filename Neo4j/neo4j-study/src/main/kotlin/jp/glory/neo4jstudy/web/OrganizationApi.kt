package jp.glory.neo4jstudy.web

import jp.glory.neo4jstudy.usecase.ModifyOrganization
import jp.glory.neo4jstudy.usecase.SearchOrganization
import jp.glory.neo4jstudy.web.request.JoinRequest
import jp.glory.neo4jstudy.web.response.OrganizationsResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 組織に関するAPI.
 *
 * @param search 組織検索ユースケース
 * @param modify 組織変更ユースケース
 */
@RestController
@RequestMapping("/api/organization")
class OrganizationApi(private val search: SearchOrganization, private val modify: ModifyOrganization) {

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
     * 組織に従業員を追加する.
     *
     * @param request リクエスト
     * @return レスポンス
     */
    @PostMapping("join")
    fun join(request: JoinRequest): ResponseEntity<Unit> {

        modify.joinEmployee(request.postId, request.employeeId)

        return ResponseEntity.noContent().build()
    }

    /**
     * 組織から従業員を外す.
     *
     * @param request リクエスト
     * @return レスポンス
     */
    @PostMapping("leave")
    fun leave(request: JoinRequest): ResponseEntity<Unit> {

        modify.laeveEmployee(request.postId, request.employeeId)

        return ResponseEntity.noContent().build()
    }
}