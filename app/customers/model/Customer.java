package customers.model;

import lombok.Data;

@Data
public final class Customer {

    private Long id;
    private String nationalId;
    private String firstName;
    private String lastName;

}
