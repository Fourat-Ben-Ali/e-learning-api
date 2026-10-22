package org.elearning.mappers;

import org.elearning.entities.Subject;
import org.elearning.requests.SubjectRequest;
import org.elearning.responses.SubjectResponse;
import org.springframework.stereotype.Service;

@Service
public class SubjectMapper {

    public Subject toEntity(final SubjectRequest request){
        return  Subject.builder()
                .name(request.getName()).
                description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .deleted(false)
                .tenantId("")
                .build();
    }

    public SubjectResponse toResponse(final  Subject entity){
        return SubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description((entity.getDescription()))
                .imageUrl((entity.getImageUrl()))
                .build();
    }
}
