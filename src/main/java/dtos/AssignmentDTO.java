package dtos;

import entities.Assignment;
import entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class AssignmentDTO {
    private Long id;
    private String familyName;
    private String createDate;
    private String contactInfo;
    private DinnerEventDTO dinnerevent;
    private Set<String> users;

    public AssignmentDTO(Assignment assignment) {
        this.id = assignment.getId();
        this.familyName = assignment.getFamilyName();
        this.createDate = assignment.getCreateDate();
        this.contactInfo = assignment.getContactInfo();
        if (assignment.getDinnerevent() != null) {
            this.dinnerevent = new DinnerEventDTO(assignment.getDinnerevent());
        }
        if (assignment.getUsers() != null) {
            this.users = assignment.getUsers().stream()
                    .map(User::getUser_name)
                    .collect(Collectors.toSet());
        }
    }

    public AssignmentDTO(String familyName, String createDate, String contactInfo) {
        this.familyName = familyName;
        this.createDate = createDate;
        this.contactInfo = contactInfo;
    }

    public static List<AssignmentDTO> getDtos(List<Assignment> assignments) {
        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();
        assignments.forEach(assignment -> assignmentDTOS.add(new AssignmentDTO(assignment)));
        return assignmentDTOS;
    }
}
