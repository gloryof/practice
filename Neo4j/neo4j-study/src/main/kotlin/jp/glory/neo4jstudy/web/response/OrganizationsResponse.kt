package jp.glory.neo4jstudy.web.response

import jp.glory.neo4jstudy.usecase.SearchOrganization

/**
 * 組織一覧のレスポンス.
 *
 * @param searchResult 検索結果
 */
data class OrganizationsResponse(private val searchResult: SearchOrganization.OrganizationSearchResult) {

    val results: List<OrganizationResponse> =
        searchResult.results.map { OrganizationResponse(it) }
}