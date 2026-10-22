package org.elearning.requests;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequest {
    private String name;
    private String description;
    private String imageUrl;
}
