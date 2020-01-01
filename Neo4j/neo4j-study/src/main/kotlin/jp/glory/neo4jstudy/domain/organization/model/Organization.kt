package jp.glory.neo4jstudy.domain.organization.model

import jp.glory.neo4jstudy.domain.employee.model.Employee
import jp.glory.neo4jstudy.domain.post.model.Post
import jp.glory.neo4jstudy.domain.post.model.PostId

/**
 * 組織.
 *
 * @param post 部署
 * @param children 子部署
 * @param belongedEmployees 所属従業員
 */
class Organization(
    val post: Post,
    val children:MutableList<Organization> = mutableListOf(),
    val belongedEmployees: BelongedEmployees = BelongedEmployees()
) {

    /**
     * 親部署配下の部署に入れる.
     *
     * @param parentPostId 親部署ID
     * @param post 追加部署
     *
     * @return 配下になった場合：true、配下になっていない場合：false
     */
    fun belongTo(parentPostId: PostId, post: Post): Boolean {

        val type: ChildPostMatchType = checkMatchType(parentPostId, post)

        return when(type) {
            ChildPostMatchType.NOT_MATCH -> {
                children.map {
                    it.belongTo(parentPostId, post)
                }.any()
            }
            ChildPostMatchType.MATCH -> {
                children.add(Organization(post = post))
                return true
            }
            ChildPostMatchType.DUPLICATE -> true
        }
    }

    /**
     * 組織に十魚院を所属させる.
     *
     * @param targetPostId 対象部署ID
     * @param employee 従業員
     */
    fun joinTo(targetPostId: PostId, employee: Employee): Boolean {

        if (post.postId.value == targetPostId.value) {

            belongedEmployees.join(employee)
            return true
        }

        // anyに対して副作用のあるメソッドを呼ぶのは違和感があるが
        // 良い方法が思いつかないのでanyで対処
        return children.any { it.joinTo(targetPostId, employee) }
    }

    /**
     * 子部署の一致タイプをチェックする.
     *
     * @param parentPostId 親部署ID
     * @param targetPost 追加部署
     * @return 一致タイプ
     */
    private fun checkMatchType(
        parentPostId: PostId,
        targetPost: Post
    ): ChildPostMatchType {

        if (post.postId.value != parentPostId.value) {

            return ChildPostMatchType.NOT_MATCH
        }

        val isDuplicated = children.any{ it.post.postId.value == targetPost.postId.value }

        if (isDuplicated) {

            return ChildPostMatchType.DUPLICATE
        }

        return ChildPostMatchType.MATCH
    }

    /** 子所属追加時の一致タイプ. */
    enum class ChildPostMatchType {
        /** 不一致.*/
        NOT_MATCH,

        /** 一致. */
        MATCH,

        /**
         * 重複.
         * 一致はしたが既に登録されている。
         */
        DUPLICATE;
    }
}