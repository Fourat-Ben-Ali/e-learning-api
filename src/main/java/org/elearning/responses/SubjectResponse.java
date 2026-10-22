package org.elearning.responses;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponse {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
}
