package jp.glory.neo4jstudy.web.response

import jp.glory.neo4jstudy.usecase.SearchOrganization

/**
 * 組織一覧のレスポンス.
 *
 * @param results 検索結果
 */
data class OrganizationsResponse(private val results: SearchOrganization.OrganizationSearchResult)