package com.v1.manfaa.Service;

import com.v1.manfaa.Api.ApiException;
import com.v1.manfaa.DTO.In.Ai.EstimateHoursDTOIn;
import com.v1.manfaa.DTO.In.Ai.QueryRagDTOIn;
import com.v1.manfaa.DTO.In.Ai.RankBidsRequestDTOIn;
import com.v1.manfaa.Model.CompanyProfile;
import com.v1.manfaa.Model.ServiceRequest;
import com.v1.manfaa.Repository.CompanyProfileRepository;
import com.v1.manfaa.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class AiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final CompanyProfileRepository companyProfileRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    @Value("${python.api.url}")
    private String pythonApiUrl;

    public JsonNode askRAG(QueryRagDTOIn query,Integer id) {
        CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileById(id);
        if(companyProfile == null || !companyProfile.getIsSubscriber()){
            throw new ApiException("un accessible to non subscribers");
        }
        String url = pythonApiUrl + "/ask-rag";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<QueryRagDTOIn> entity = new HttpEntity<>(query, headers);

        return restTemplate.postForObject(url, entity, JsonNode.class);
    }

    public JsonNode estimateHours(EstimateHoursDTOIn query,Integer id) {
        CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileById(id);
        if(companyProfile == null || !companyProfile.getIsSubscriber()){
            throw new ApiException("un accessible to non subscribers");
        }
        String url = pythonApiUrl + "/suggest-hours";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EstimateHoursDTOIn> entity = new HttpEntity<>(query, headers);
        return restTemplate.postForObject(url, entity, JsonNode.class);
    }

    public JsonNode suggestUser(RankBidsRequestDTOIn query, Integer id) {
        CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileById(id);
        ServiceRequest request = serviceRequestRepository.findServiceRequestById(query.getRequest_id());

        if(request == null){
            throw new ApiException("request not found");
        }

        if(companyProfile == null || !companyProfile.getIsSubscriber()){
            throw new ApiException("un accessible to non subscribers");
        }

        if(!request.getCompanyProfile().getId().equals(id)){
            throw new ApiException("not the ownser");
        }

        String url = pythonApiUrl + "/rank-bids";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RankBidsRequestDTOIn> entity = new HttpEntity<>(query, headers);
        return restTemplate.postForObject(url, entity, JsonNode.class);
    }
}
