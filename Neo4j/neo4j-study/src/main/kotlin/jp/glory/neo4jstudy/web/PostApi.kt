package jp.glory.neo4jstudy.web

import jp.glory.neo4jstudy.usecase.ModifyPost
import jp.glory.neo4jstudy.web.request.PostModifyRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 部署API.
 *
 * @param modify 部署編集ユースケース
 */
@RestController
@RequestMapping("/api/post")
class PostApi(private val modify: ModifyPost) {

    /**
     * 部署の登録を行う.
     *
     * @param request リクエスト
     */
    @PostMapping
    fun register(@RequestBody request: PostModifyRequest): ResponseEntity<Long> {

        val result: Long = modify.register(
            ModifyPost.RegisterInfo(
                name = request.name
            )
        )

        return ResponseEntity.ok(result)
    }

    /**
     * 部署の更新を行う.
     *
     * @param postId 部署ID
     * @param request リクエスト
     */
    @PutMapping("{postId}")
    fun update(
        @PathVariable postId: Long,
        @RequestBody request: PostModifyRequest): ResponseEntity<Long> {

        val result: Long = modify.update(
            ModifyPost.UpdateInfo(
                postId = postId,
                name = request.name
            )
        )

        return ResponseEntity.ok(result)
    }


    /**
     * 部署の削除を行う.
     *
     * @param postId 部署ID
     */
    @DeleteMapping("{postId}")
    fun delete(@PathVariable postId: Long): ResponseEntity<Unit> {

        modify.delete(postId)

        return ResponseEntity.noContent().build()
    }

    /**
     * 子部署の登録を行う.
     *
     * @param postId 部署ID
     * @param childPostId 子部署ID
     */
    @PostMapping("{postId}/{childPostId}")
    fun addChild(
        @PathVariable postId: Long,
        @PathVariable childPostId: Long): ResponseEntity<Unit> {

        modify.addChild(
            parentPostId = postId,
            childPostId = childPostId
        )

        return ResponseEntity.noContent().build()
    }

}