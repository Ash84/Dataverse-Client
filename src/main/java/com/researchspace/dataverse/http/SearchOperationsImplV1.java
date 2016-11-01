package com.researchspace.dataverse.http;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.researchspace.dataverse.api.v1.SearchOperations;
import com.researchspace.dataverse.entities.DataverseResponse;
import com.researchspace.dataverse.search.entities.DatasetItem;
import com.researchspace.dataverse.search.entities.DataverseItem;
import com.researchspace.dataverse.search.entities.FileSearchHit;
import com.researchspace.dataverse.search.entities.Item;
import com.researchspace.dataverse.search.entities.SearchConfig;
import com.researchspace.dataverse.search.entities.SearchConfig.SearchConfigBuilder;
import com.researchspace.dataverse.search.entities.SearchResults;
import com.researchspace.dataverse.search.entities.SearchType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchOperationsImplV1 extends AbstractOpsImplV1 implements SearchOperations {

	private SearchURLBuilder urlBuilder = new SearchURLBuilder();

	@Override
	public SearchConfigBuilder builder() {
		return SearchConfig.builder();
	}

	@Override
	public DataverseResponse<SearchResults<Item>> search(SearchConfig cfg) {
		String url = createV1Url("search");
		url = urlBuilder.buildSearchUrl(url, cfg);
		HttpHeaders headers = addAPIKeyToHeader();
		ParameterizedTypeReference<DataverseResponse<SearchResults<Item>>> type = new ParameterizedTypeReference<DataverseResponse<SearchResults<Item>>>() {
		};
		ResponseEntity<DataverseResponse<SearchResults<Item>>> resp = template.exchange(url, HttpMethod.GET, createHttpEntity(headers), type);
		log.debug(resp.getBody().getData().toString());
		return resp.getBody();
	}

	private HttpEntity<String> createHttpEntity(HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		return entity;
	}
	
	@Override
	public DataverseResponse<SearchResults<FileSearchHit>> searchFiles(SearchConfig cfg) {
		validateSrchConfig(cfg, SearchType.file);
		String url = createV1Url("search");
		url = urlBuilder.buildSearchUrl(url, cfg);
		HttpHeaders headers = addAPIKeyToHeader();
		ParameterizedTypeReference<DataverseResponse<SearchResults<FileSearchHit>>> type = 
				 new ParameterizedTypeReference<DataverseResponse<SearchResults<FileSearchHit>>>() {
		};
		ResponseEntity<DataverseResponse<SearchResults<FileSearchHit>>> resp = template.exchange(url, HttpMethod.GET, createHttpEntity(headers), type);
		log.debug(resp.getBody().getData().toString());
		return resp.getBody();
	}

	@Override
	public DataverseResponse<SearchResults<DatasetItem>> searchDatasets(SearchConfig cfg) {
		validateSrchConfig(cfg, SearchType.dataset);
		String url = createV1Url("search");
		url = urlBuilder.buildSearchUrl(url, cfg);
		HttpHeaders headers = addAPIKeyToHeader();
		ParameterizedTypeReference<DataverseResponse<SearchResults<DatasetItem>>> type = 
				 new ParameterizedTypeReference<DataverseResponse<SearchResults<DatasetItem>>>() {
		};
		ResponseEntity<DataverseResponse<SearchResults<DatasetItem>>> resp = template.exchange(url, HttpMethod.GET, createHttpEntity(headers), type);
		log.debug(resp.getBody().getData().toString());
		return resp.getBody();
	}
	
	@Override
	public DataverseResponse<SearchResults<DataverseItem>> searchDataverses(SearchConfig cfg) {
		validateSrchConfig(cfg, SearchType.dataverse);
		String url = createV1Url("search");
		url = urlBuilder.buildSearchUrl(url, cfg);
		HttpHeaders headers = addAPIKeyToHeader();
		ParameterizedTypeReference<DataverseResponse<SearchResults<DataverseItem>>> type = 
				 new ParameterizedTypeReference<DataverseResponse<SearchResults<DataverseItem>>>() {
		};
		ResponseEntity<DataverseResponse<SearchResults<DataverseItem>>> resp = template.exchange(url, HttpMethod.GET, createHttpEntity(headers), type);
		log.debug(resp.getBody().getData().toString());
		return resp.getBody();
	}

	private void validateSrchConfig(SearchConfig cfg, SearchType expected) {
		if(cfg.getType().size() != 1 || !cfg.getType().contains(expected)) {
			throw new IllegalArgumentException(String.format("Search must be configured to search only  %ss", expected.name()));
		}
	}

	
}
