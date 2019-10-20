package jp.glory.neo4jstudy.domain.model

/**
 * 所属部署.
 *
 * @param mainPost メイン所属
 * @param subPost サブ所属
 */
class BelongedPosts(val mainPost: Post, val subPost: List<Post>) {
}